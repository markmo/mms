define [
  'jquery'
  'backbone'
  'cs!vm'
  'bootstrap'
], ($, Backbone, Vm) ->

  Backbone.View.extend

    events:
      'click #create-item': 'create'
      'click .item-name': 'show'
      'click .item-edit': 'edit'
      'click #delete-item': 'delete'

    create: (event) ->
      event.preventDefault()
      form = Vm.create this, "#{@form.name}Form", @form.form,
        collection: @collection
      modal = new Backbone.BootstrapModal
        title: "New #{@form.name}"
        content: form
        animate: true
      modal.$el.css('width', '590px')
      modal.open()
      return false

    edit: (event) ->
      event.preventDefault()
      id = $(event.currentTarget).data('id')
      form = Vm.create this, "#{@form.name}Form", @form.form,
        collection: @collection
        id: id
      modal = new Backbone.BootstrapModal
        title: "Edit #{@form.name}"
        content: form
        animate: true
      modal.$el.css('width', '590px')
      modal.open()
      return false

    show: (event) ->
      event.preventDefault()
      id = $(event.currentTarget).data('id')
      model = @collection.get(id)
      form = @form
      view = Vm.create this, "#{@form.name}View", form.readonly,
        collection: @collection
        model: model
        form: form
      modal = new Backbone.BootstrapModal
        title: "#{form.name} Details"
        content: view
        animate: true
        okText: 'Edit'
        okCloses: false
      modal.open()
      return false

    delete: (event) ->
      event.preventDefault()
      deletions = $('.delete-checkbox').map ->
        $(this).data('id') if $(this).attr('checked')
      collection = @collection
      if deletions.length
        $.ajax(
          type: 'DELETE'
          url: collection.url
          data: {id: deletions}
        ).done =>
          collection.remove(collection.get(id)) for id in deletions
          this.render()
      return false

    initialize: (options) ->
      @form = options.form
