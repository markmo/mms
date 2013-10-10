define [
  'jquery'
  'underscore'
  'backbone'
  'cs!events'
  'cs!views/glossary/domain_form'
  'cs!collections/domains'
], ($, _, Backbone, app, DomainForm, Domains) ->

  Backbone.View.extend

    manage: true

    el: '#left-drawer'

    template: 'glossary/domains'

    create: ->
      domainForm = new DomainForm
      modal = new Backbone.BootstrapModal(
        title: 'New Domain'
        content: domainForm
        animate: true
      )
      modal.open()
      return false

    edit: (domainId) ->
      domainForm = new DomainForm
        domainId: domainId
      modal = new Backbone.BootstrapModal(
        title: 'Edit Domain'
        content: domainForm
        animate: true
      )
      modal.open()
      return false

    initialize: (options) ->
      unless @collection
        @collection = new Domains
        @collection.fetch()
      this.listenTo(@collection, 'change', this.render)
      $('#left-drawer').on('click', '#create-domain', _.bind(this.create, this))

    beforeRender: ->
      $('#domains-tree').unbind()

    afterRender: ->
      if @collection.length
        domains = app.convertCollectionToTree(@collection)
        $('#domains-tree').tree(
          data: domains
          dragAndDrop: true
          saveState: true
        )
        .bind('tree.select', (event) =>
          node = event.node
          if node
            @domainId = node.id
            app.trigger('domain.selected', @domainId)
        )
        .bind('tree.dblclick', (event) =>
          node = event.node
          if node
            @domainId = node.id
            this.edit(@domainId)
        )
        .bind('tree.move', (event) =>
          movedNode = event.move_info.moved_node
          if movedNode
            targetNode = event.move_info.target_node
            position = event.move_info.position
            previousParent = event.move_info.previous_parent
            @domainId = movedNode.id
            @parentDomain = targetNode
            this.edit(@domainId)
        )

    cleanup: ->
      $('#domains-tree').unbind()
      $('#left-drawer').off('click', '#create-domain')
