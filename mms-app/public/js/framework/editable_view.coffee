define [
  'jquery'
  'backbone'
  'bootstrap'
  'bootstrap-modal'
], ($, Backbone) ->

  Backbone.View.extend

    events:
      'click #create-item': 'create'
      'click .item-name': 'show'
      'click .item-edit': 'edit'
      'click #delete-item': 'delete'
      'click .item-undelete': 'undelete'

    create: (event) ->
      event.preventDefault()
      form = new @form.form
        collection: @collection
      modal = new Backbone.BootstrapModal
        title: "New #{@form.name}"
        content: form
        animate: true
      modal.open()
      return false

    edit: (event) ->
      event.preventDefault()
      id = $(event.currentTarget).data('id')
      form = new @form.form
        collection: @collection
        id: id
      modal = new Backbone.BootstrapModal
        title: "Edit #{@form.name}"
        content: form
        animate: true
      #modal.$el.css('width', '590px')
      modal.open()
      return false

    show: (event) ->
      event.preventDefault()
      id = $(event.currentTarget).data('id')
      model = @collection.get(id)
      form = @form
      view = new form.readonly
        collection: @collection
        model: model
        form: form
      modal = new Backbone.BootstrapModal
        title: "#{form.name} Details"
        content: view
        animate: true
        okText: 'Edit'
        okCloses: false
      modal.$el.css({'width': '700px', 'marginLeft': '-350px'})
      modal.open()
      return false

    delete: (event) ->
      event.preventDefault()
      checked = $('.item-delete').map ->
        $(this).data('id') if $(this).prop('checked')
      deletions = (id for id in checked)
      if deletions.length
        modal = new Backbone.BootstrapModal
          title: 'Warning!'
          content: 'Are you sure you want to delete this ' + this.form.name + '?'
          okText: "Yes, I'm sure"
        modal.open()
        this.listenTo modal, 'ok', =>
          this._processDelete(deletions)
      return false

    _processDelete: (deletions) ->
      collection = @collection
      $.ajax(
        type: 'DELETE'
        url: collection.url
        contentType: 'application/json; charset=utf-8'
        dataType: 'json'
        data: JSON.stringify(deletions)
      ).done(=>
        collection.remove(collection.get(id)) for id in deletions
        this.fetch({reset: true})
      ).fail(->
        alert('fail')
      )

    undelete: (event) ->
      event.preventDefault()
      id = $(event.currentTarget).data('id')
      collection = @collection
      url = "#{collection.get(id).url}/#{id}/undelete"
      req = $.ajax
        url: url
        type: 'PUT'
      req.done ->
        collection.fetch({reset: true})
      req.fail ->
        alert('fail')
      return false

    initialize: (options) ->
      @form = options.form
      @collection = options.collection
