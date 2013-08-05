define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!components/pageable_view'
    'cs!views/app/posts'
    'cs!views/glossary/domains'
    'cs!views/glossary/left_menu'
    'cs!views/glossary/term_section'
    'text!templates/glossary/terms.html'
    'lib/jqtree/jquery.cookie'
    'lib/jqtree/tree.jquery'
], ($, Backbone, Handlebars, app, Vm, PageableView, PostsSection, DomainsSection, LeftMenuSection, TermSection, termsPageTemplate) ->
    PageableView.extend
        el: '#page'

        events: ->
            _.extend {}, PageableView.prototype.events,
                'click #create-term': 'create'
                'click .item-title': 'link'
                'dblclick .item-title': 'edit'
                'click .edit-btn': 'edit'

        compiled: Handlebars.compile termsPageTemplate

        link: (event) ->
            a = $(event.target)
            href = a.attr('href')
            a.data 'timer', setTimeout(->
                window.location = href
            ,500) unless a.data('timer')
            return false

        create: ->
            termSection = Vm.create(this, 'TermSection', TermSection)
            termSection.render().createTerm()

        edit: ->
            termSection = Vm.create(this, 'TermSection', TermSection,
                termId: @termId
                parentTerm: @parentTerm)
            termSection.render().editTerm()

        show: ->
            termSection = Vm.create(this, 'TermSection', TermSection,
                termId: @termId)
            termSection.render().showTerm()
            postsSection = Vm.create(this, 'PostsSection', PostsSection,
                entityType: 'term'
                entityId: @termId
            )
            postsSection.render()

        initialize: (options) ->
            @domainId = options?.domainId
            @termId = options?.termId
            domainsSection = Vm.create(this, 'DomainsSection', DomainsSection)
            domainsSection.render()
            leftMenuSection = Vm.create(this, 'LeftMenuSection', LeftMenuSection)
            leftMenuSection.render()
            return this

        doRender: (domainId) ->
            @domainId = domainId if domainId
            dfd = $.Deferred()
            app.domains().done (domains) =>
                domain = domains.get(@domainId)
                app.termsByDomain(@domainId).done (coll) =>
                    @pageableCollection = coll
                    terms = app.convertCollectionToTree(coll)
                    @$el.html @compiled
                        domain: domain?.toString() || 'ALL'
                    $('#terms-tree').tree(
                        data: terms
                        dragAndDrop: true
                        saveState: true
                        )
                        .bind('tree.click', (event) =>
                            node = event.node
                            if node
                                @termId = node.id
                                this.show(event)
                            return
                        )
                        .bind('tree.move', (event) =>
                            movedNode = event.move_info.moved_node
                            targetNode = event.move_info.target_node
                            position = event.move_info.position
                            previousParent = event.move_info.previous_parent
                            @termId = movedNode.id
                            @parentTerm = targetNode
                            this.edit(event)
                            return
                        )
                    this.delay(2000, ->
                        $.bootstrapGrowl 'Double click an item to open the edit form',
                            type: 'info'
                            delay: 4000
                    ) unless app.session.notifiedOf('dblclick') or coll.isEmpty()

                    $fileupload = $('#fileupload')
                    $fileupload.fileupload
                        maxNumberOfFiles: 1
                        acceptFileTypes: /(\.|\/)(txt|docx?)$/i
                        autoUpload: true
                        uploadTemplateId: null
                        downloadTemplateId: null
                        submit: (e, data) =>
                            file = data.files[0]
                            metadata =
                                name: file.name
                                size: file.size
                                type: file.type
                                lastModifiedDate: file.lastModifiedDate
                            $fileupload.fileupload 'option',
                                url: "upload/read"
                                type: 'PUT'
                                multipart: false
                            $fileupload.fileupload('send', data)
                            return false

                    $(document).bind 'dragover', (event) =>
                        dropzone = $('.full-window-dropzone')
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

                    if @termId then this.show()
                    dfd.resolve()
            dfd

        clean: ->
#            $('#open-left').off()
#            $('#open-right').off()
#            $('#left-drawer').off()
            $('.editable').off()
