define [
  'jquery'
  'underscore'
  'backbone'
  'handlebars'
  'cs!events'
  'cs!models/dataset'
  'text!templates/app/dataset_form.html'
  'text!templates/upload.html'
], ($, _, Backbone, Handlebars, app, Dataset, datasetPageTemplate, uploadPageTemplate) ->

  Backbone.View.extend
    el: '#page'

    events:
      'click #btnSubmit': 'submit'
      'click #btnCancel': 'cancel'

    compiled: Handlebars.compile uploadPageTemplate

    initialize: (options) ->
      @datasetId = options?.datasetId

    submit: ->
      @dataset.set(@form.getValue())
      app.datasets().done (datasets) =>
        datasets.add(@dataset)
        @dataset.save {},
          success: ->
            template = $('.template-upload')
            data = template.data('data')
            if data and data.files.length and data.submit and !data.jqXHR
              data.submit()
            return
        return

    cancel: ->

    render: ->
      @$el.html datasetPageTemplate
      $('#file-upload-container').html @compiled

      $fileupload = $('#fileupload')
      $fileupload.fileupload
        maxNumberOfFiles: 1
        acceptFileTypes: /(\.|\/)(txt|csv|tsv|xlsx?|dat)$/i
        autoUpload: false
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
              entityType: @dataset.get('entityType')
              entityId: @dataset.id
          )
          .done (result) =>
              $fileupload.fileupload 'option',
                url: "upload/#{result.files[0].id}"
                type: 'PUT'
                multipart: false
                done: =>
                  statsLink = $('#stats-link')
                  statsLink.show()
                  setTimeout(=>
                    statsLink.removeClass('red').addClass('green')
                    label = statsLink.find('span')
                    label.html "<a href=\"#/datasets/#{@dataset.id}/stats\">#{label.text()}</a>"
                  , 1000)
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
        return false

      if @datasetId
        app.datasets().done (datasets) =>
          if @datasetId?
            @dataset = datasets.get(@datasetId)
          else
            @dataset = new Dataset
          @clean() if @form?
          @form = new Backbone.Form(
            model: @dataset
          ).render()
          $('#form-container').html @form.el
      else
        @dataset = new Dataset
        @form = new Backbone.Form(
          model: new Dataset
        ).render()
        $('#form-container').html @form.el

      return this

    clean: ->
      @form.undelegateEvents()
      @form.remove()
      return
