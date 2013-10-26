define [
  'jquery'
  'backbone'
  'handlebars'
  'events'
  'vm'
  'views/sandbox_form'
  'text!templates/sandboxes.html'
], ($, Backbone, Handlebars, app, Vm, SandboxForm, sandboxesPageTemplate) ->

  Backbone.View.extend

    el: '#page'

    events:
      'click .item-title': 'link'
      'dblclick .item-title': 'edit'
      'click .edit-btn': 'edit'

    compiled: Handlebars.compile sandboxesPageTemplate

    link: (event) ->
      a = $(event.target)
      href = a.attr('href')
      a.data 'timer', setTimeout(->
        window.location = href
      , 500) unless a.data 'timer'
      return false

    edit: (event) ->
      a = $(event.target)
      clearTimeout(a.data 'timer')
      a.data 'timer', null
      href = a.attr('href')
      if a.hasClass 'edit-btn'
        title = a.parent().find('.item-title').text()
      else
        title = a.text()
        href = href.substr(0, href.lastIndexOf('/')) + '/edit'
      sandboxId = href.match(/sandboxes\/(\d+)\//)[1]

      sandboxForm = Vm.create(this, 'SandboxForm', SandboxForm, {sandboxId: sandboxId})

      modal = new Backbone.BootstrapModal(
        title: 'Edit Sandbox'
        content: sandboxForm
        animate: true
      ).open()

      return false

    render: () ->
      app.sandboxes().done (sandboxes) =>
        @$el.html @compiled
          sandboxes: sandboxes.toJSON()
        this.delay(2000, ->
          $.bootstrapGrowl 'Double click an item to open the edit form',
            type: 'info'
            delay: 4000
        ) unless app.session.notifiedOf('dblclick')
      return this
