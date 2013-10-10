define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/attachments.html'
], ($, Backbone, Handlebars, app, attachmentsPageTemplate) ->
    Backbone.View.extend
        el: '.popover.in .popover-content'

        events:
            'click #fileupload .close': 'closePopover'

        compiled: Handlebars.compile attachmentsPageTemplate

        closePopover: (event) ->
            $el = $(event.target)
            popoverId = $el.data('popover-id')
            $("a[data-popover-id='#{popoverId}']").popover('hide')
            return

        clean: ->
            #$('#fileupload').fileupload('destroy')
            #$(document).unbind('drop dragover')

        initialize: (options) ->
            @popoverId = options.popoverId
            @entityType = options.entityType
            @entityId = options.entityId

        render: ->
            $(@el).html @compiled
                popoverId: @popoverId
            $fileupload = $('#fileupload')
            $fileupload.fileupload
                submit: (e, data) =>
                    file = data.files[0]
                    $.ajax(
                        url: '/upload/metadata'
                        type: 'POST'
                        dataType: 'json'
                        data:
                            name: file.name
                            size: file.size
                            type: file.type
                            lastModifiedDate: file.lastModifiedDate
                            entityType: @entityType
                            entityId: @entityId
                    )
                        .done (result) ->
                            $fileupload.fileupload 'option',
                                url: "upload/#{result.files[0].id}"
                                type: 'PUT'
                                multipart: false
                            $fileupload.fileupload('send', data)
                            return
                    return false

            $fileupload.fileupload(
                'option',
                'redirect',
                window.location.href.replace(
                    /\/[^\/]*$/,
                    '/cors/result.html?%s'
                )
            )

            $fileupload.on 'fileuploadpreviewdone', (event, data) =>
                $.ajax(
                    url: "/uploads/#{@entityType}/#{@entityId}"
                )
                    .done (result) ->
                        template = $fileupload._renderDownload(result)
                            .appendTo($fileupload.options.filesContainer)
                        $fileupload._forceReflow(template)
                        return

            filesInQueue = 0

            $fileupload.bind 'fileuploadadd', (e, data) ->
                hasFiles = ++filesInQueue > 0
                $('button.start').toggle(hasFiles)
                $('button.cancel').toggle(hasFiles)
                return

            $fileupload.bind 'fileuploadfail', (e, data) ->
                hasFiles = --filesInQueue > 0
                $('button.start').toggle(hasFiles)
                $('button.cancel').toggle(hasFiles)
                return

            $(document).bind 'dragover', (event) =>
                dropzone = $('.dropzone')
                timeout = window.dropzoneTimeout
                if timeout
                    clearTimeout(timeout)
                else
                    dropzone.addClass('in')
                if (event.target == dropzone[0])
                    dropzone.addClass('hover')
                else
                    dropzone.removeClass('hover')
                window.dropzoneTimeout = this.delay 100, ->
                    window.dropzoneTimeout = null
                    dropzone.removeClass('in hover')

            $(document).bind 'drop dragover', (event) ->
                event.preventDefault()
                return false;

            return this
