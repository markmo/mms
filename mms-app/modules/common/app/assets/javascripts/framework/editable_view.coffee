###*
* @module editable_view
###
define [
  'jquery'
  'backbone'
], ($, Backbone) ->

  ###*
  * The trait which enables Create, Read, Update, and Delete (CRUD) functions on an item (row)
  * in a GridView. It expects the following properties to have been assigned by the mixee.
  * <ul>
  * <li><pre>form: {
  *		 form: the constructor for the editable form,
  *		 name: the name of the entity
  *		 readonly: the constructor of the readonly view
  * }</pre>
  * <li> collection - the collection to which the shown/edited item belongs
  * </ul>
  *
  * @class Editable
  * @constructor
  * @extends Backbone.View
  * @author markmo
  ###
  Backbone.View.extend

    ###*
    * The events hash used by Backbone. This extends the event hash on the mixee.
    *
    * @property events
    * @type {Object}
    ###
    events:
      'click #create-item': 'create'
      'click .item-name': 'show'
      'click .item-edit': 'edit'
      'click #delete-item': 'delete'
      'click .item-undelete': 'undelete'

    ###*
    * Opens a blank form to create a new entity.
    *
    * @method create
    * @param {Object} event
    ###
    create: (event) ->
      event.preventDefault()
      form = new @form.form
        collection: @collection
      modal = new Backbone.BootstrapModal
        title: "New #{@form.name}"
        content: form
        animate: true
        okCloses: false
      modal.open()
      return false

    ###*
    * Opens a populated form to edit the entity.
    *
    * @method edit
    * @param {Object} event
    ###
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
        okCloses: false
      modal.$el.css({'width': '700px', 'marginLeft': '-350px'})
      modal.open()
      return false

    ###*
    * Shows a readonly detail view of the entity.
    *
    * @method show
    * @param {Object} event
    ###
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

    ###*
    * Deletes one or more selected items.
    *
    * @method delete
    * @param {Object} event
    ###
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

    ###*
    * Process the delete after clicking 'Yes' on the confirmation dialog.
    *
    * @method processDelete
    * @params {Array} ids to delete
    ###
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

    ###*
    * Initializes this view. Expects the following properties in the options hash:
    * <ul>
    * <li><pre>form: {
    *		 form: the constructor for the editable form,
    *		 name: the name of the entity
    *		 readonly: the constructor of the readonly view
    * }</pre>
    * <li> collection - the collection to which the shown/edited item belongs
    * </ul>
    *
    * @method initialize
    * @param options
    ###
    initialize: (options) ->
      @form = options.form
