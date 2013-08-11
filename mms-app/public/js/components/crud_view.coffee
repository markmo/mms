define [
  'jquery'
  'backbone'
  'cs!vm'
], ($, Backbone, Vm) ->

  Backbone.View.extend

    events:
      'click #create-item': 'create'
      'click .item-name': 'edit'
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

    delete: (event) ->
      event.preventDefault()
      deletions = []
      $('.item-delete').each ->
        deletions.push($(this).data('id')) if $(this).attr('checked')
      if deletions.length
        $.ajax(
          type: 'DELETE'
          url: @form.url
          data: {id: deletions}
        ).done =>
          @collection.remove(@collection.get(id)) for id in deletions
          this.render()
      return false

    initialize: (options) ->
      @form = options.form
      @collection = options.collection
