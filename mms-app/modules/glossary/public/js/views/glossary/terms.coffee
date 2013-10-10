define [
  'jquery'
  'underscore'
  'backbone'
  'cs!events'
  'cs!framework/pageable_view'
  'cs!views/glossary/term_section'
  'cs!collections/domains'
  'cs!collections/terms'
  'jquery.cookie'
  'jqtree'
  'lib/jquery-file-upload/jquery.fileupload-ui' # must use full path since it has a relative dependency
], ($, _, Backbone, app, PageableView, TermDetailLayout, Domains, Terms) ->

  PageableView.extend

    template: 'glossary/terms'

    domainTitle: 'ALL'

    events: ->
      _.extend {}, PageableView.prototype.events,
        'click #create-item': 'create'
        'click .item-title': 'link'
        'dblclick .item-title': 'edit'
        'click .edit-btn': 'edit'

    link: (event) ->
      a = $(event.target)
      href = a.attr('href')
      a.data 'timer', setTimeout(->
        window.location = href
      , 500) unless a.data('timer')
      return false

    create: ->
      termLayout = this.setView '.term-detail', new TermDetailLayout
        terms: @collection
#        roles: @roles
#        people: @people
      termLayout.render().createTerm()
#      termLayout.render().once 'afterRender', ->
#        termLayout.createTerm()

    edit: ->
      termLayout = this.setView '.term-detail', new TermDetailLayout
        termId: @termId
        parentTerm: @parentTerm
        terms: @collection
#        roles: @roles
#        people: @people
      termLayout.render().editTerm()
#      termLayout.render().once 'afterRender', ->
#        termLayout.editTerm()

    show: ->
      termLayout = this.setView '.term-detail', new TermDetailLayout
        termId: @termId
        parentTerm: @parentTerm
        terms: @collection
#        roles: @roles
#        people: @people
      termLayout.render().showTerm()
#      termLayout.render().once 'afterRender', ->
#        termLayout.showTerm()

    initialize: (options = {}) ->
      @domainId = options.domainId
      @termId = options.termId

      domains = options.domains
      unless domains
        domains = new Domains
        domains.fetch()
      @domains = domains

      terms = options.terms
      unless terms
        terms = new Terms
        terms.fetch()
      @collection = options.collection = terms

#      @roles = options.roles
#      @people = options.people

      app.on 'domain.selected', (domainId) =>
        @domainId = domainId
        this.render()

      options.shortPaginatorForm = true
      this._super(options)

    serialize: ->
      domain: @domainTitle

    beforeRender: ->
      domain = @domains.get(@domainId)
      @domainTitle = domain.toString() if domain
      $(document).unbind('drop dragover')
      $('#fileupload').fileupload('destroy')
      $('#terms-tree').unbind()

    afterRender: ->
      app.termsByDomain(@domainId).done (coll) =>
        @collection = coll
        if coll.length
          terms = app.convertCollectionToTree(coll)
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
          )
          .bind('tree.move', (event) =>
            movedNode = event.move_info.moved_node
            targetNode = event.move_info.target_node
            position = event.move_info.position
            previousParent = event.move_info.previous_parent
            @termId = movedNode.id
            @parentTerm = targetNode
            this.edit(event)
          )
          this.delay(2000, ->
            $.bootstrapGrowl 'Double click an item to open the edit form',
              type: 'info'
              delay: 4000
          ) unless app.session.notifiedOf('dblclick') or coll.isEmpty()
        else
          $('#terms-tree').text('No terms')

        $fileupload = $('#fileupload')
        $fileupload.fileupload
          maxNumberOfFiles: 1
          acceptFileTypes: /(\.|\/)(txt|docx?)$/i
          autoUpload: true
          uploadTemplateId: null
          downloadTemplateId: null
          submit: (e, data) ->
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

        $(document).bind 'dragover', (event) ->
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

        this.show() if @termId

    cleanup: ->
      $(document).unbind('drop dragover')
      $('#fileupload').fileupload('destroy')
      $('#terms-tree').unbind()
      app.off('domain.selected')
      $('.editable').off()
