define [
  'global'
  'jquery'
  'underscore'
  'backbone'
  'events'
  'framework/pageable_view'
  'views/glossary/term_layout'
  'views/posts'
  'collections/domains'
  'collections/terms'
  'jquery.cookie'
  'jqtree'
  'lib/jquery-file-upload/jquery.fileupload-ui' # must use full path since it has a relative dependency
], (global, $, _, Backbone, app, PageableView, TermDetailLayout, PostsView, Domains, Terms) ->

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
      ,500) unless a.data('timer')
      return false

    create: ->
      termLayout = this.setView '.term-detail', new TermDetailLayout
        terms: @collection
      termLayout.render().createTerm()

    edit: ->
      termLayout = this.setView '.term-detail', new TermDetailLayout
        termId: @termId
        parentTerm: @parentTerm
        terms: @collection
      termLayout.render().editTerm()

    show: ->
      @postsView = new PostsView
        entityType: 'term'
        entityId: @termId
      termLayout = this.setView '.term-detail', new TermDetailLayout
        termId: @termId
        parentTerm: @parentTerm
        terms: @collection
      termLayout.render().showTerm()
      app.router.navigate('/terms/' + @termId)

    initialize: (options = {}) ->
      @domainId = options.domainId
      @termId = options.termId

      unless options.domains
        options.domains = new Domains
        options.domains.fetch()
      @domains = options.domains

      unless options.terms
        termsNotInjected = true
        options.terms = new Terms
        this.listenTo(options.terms, 'sync', this.render)
      @collection = options.collection = options.terms

      app.on 'domain.selected', (domainId) =>
        @domainId = domainId
        this.render()

      options.shortPaginatorForm = true
      this._super(options)

      @collection.fetch() if termsNotInjected

    serialize: ->
      domain: @domainTitle

    beforeRender: ->
      domain = @domains.get(@domainId)
      @domainTitle = domain?.toString() ? 'ALL'
      $(global.document).unbind('drop dragover')
      $('#fileupload').fileupload('destroy')
      $('#terms-tree').unbind()

    afterRender: ->
      domainId = @domainId
      if domainId
        terms = @collection.subcollection
          filter: (term) ->
            domainId == term.get('domain')?.id
      else
        terms = @collection
      if terms.length
        ###
        Display the tree of terms for the selected domain
        ###
        nodes = app.convertCollectionToTree(terms)
        termsTree = $('#terms-tree').tree(
          data: nodes
          dragAndDrop: true
          saveState: true
        )
        .bind('tree.click', (event) =>
          node = event.node
          if node
            @termId = node.id
            this.show()
        )
        .bind('tree.move', (event) =>
          movedNode = event.move_info.moved_node
          targetNode = event.move_info.target_node
          position = event.move_info.position
          previousParent = event.move_info.previous_parent
          @termId = movedNode.id
          @parentTerm = targetNode
          this.edit()
        )
        node = termsTree.tree('getNodeById', @termId)
        termsTree.tree('selectNode', node) if node

        ###
        Remind the user that a term can be edited by double clicking the
        item in the tree
        ###
        setTimeout(->
          $.bootstrapGrowl 'Double click an item to open the edit form',
            type: 'info'
            delay: 4000
        ,2000) unless app.session.notifiedOf('dblclick')
      else
        $('#terms-tree').text('No terms')

      ###
      Enable a file to be uploaded from which to extract relationships
      ###
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

      ###
      Upload a file by dragging it onto the page
      ###
      $(global.document).bind 'dragover', (event) ->
        dropzone = $('.full-window-dropzone')
        timeout = global.dropzoneTimeout

        if timeout
          clearTimeout(timeout)
        else
          dropzone.addClass('in')

        if (event.target == dropzone[0])
          dropzone.addClass('hover')
        else
          dropzone.removeClass('hover')

        global.dropzoneTimeout = setTimeout(->
          global.dropzoneTimeout = null
          dropzone.removeClass('in hover')
        ,100)

      $(global.document).bind 'drop dragover', (event) ->
        event.preventDefault()
        return false

      ###
      Show the term detail if an id is present
      ###
      this.show() if @termId

    cleanup: ->
      postsView = @postsView
      # a PostsView is created only when showing the detail for a term
      if postsView
        postsView.undelegateEvents()
        postsView.$el.empty()
        postsView.stopListening()

      $(global.document).unbind('drop dragover')
      $('#fileupload').fileupload('destroy')
      $('#terms-tree').unbind()
      app.off('domain.selected')
      $('.editable').off()
