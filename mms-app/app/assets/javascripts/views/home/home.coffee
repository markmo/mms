define [
  'jquery'
  'backbone'
  'events'
  'views/attachments'
  'views/sandbox_form'
  'collections/sandboxes'
], ($, Backbone, app, AttachmentsView, SandboxForm, Sandboxes) ->

  Backbone.View.extend

    manage: true

    template: 'home/main'

    events:
      'click #create-sandbox': 'createSandbox'
      'click #btn-attachments': 'addAttachments'

    addAttachments: (event) ->
      event.preventDefault()
      $('#attachments').toggle()
      return false

    createSandbox: ->
      sandboxForm = new SandboxForm
        collection: @sandboxes
      modal = new Backbone.BootstrapModal
        title: 'New Sandbox'
        content: sandboxForm
        animate: true
      modal.open()
      return false

    initialize: (options = {}) ->
      unless options.sandboxes
        sandboxesNotInjected = true
        options.sandboxes = new Sandboxes
      @sandboxes = options.sandboxes
      this.listenTo(options.sandboxes, 'sync', this.render)
      @sandboxes.fetch() if sandboxesNotInjected

    serialize: ->
      sandboxes: @sandboxes.toJSON()

    afterRender: ->
      $('#home-features').equalHeights()
      $('#btn-attachments').popover
        placement: 'bottom'
        trigger: 'click'
        delay:
          show: 500
          hide: 100
        content: =>
          setTimeout(=>
            attachmentsView = this.setView AttachmentsView
              popoverId: 'fileupload'
              entityType: 'sandbox'
              entityId: app.selectedSandbox()?.id
            attachmentsView.render()
          ,0) # allow a repaint cycle
          return 'Loading...'
