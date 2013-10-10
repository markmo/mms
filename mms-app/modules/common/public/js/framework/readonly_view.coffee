define [
  'underscore'
  'backbone'
], (_, Backbone) ->

  Backbone.View.extend

    manage: true

    initialize: (options) ->
      @form = options.form
      this.on 'ok', this.edit

    serialize: ->
      return {model: @model.toJSON()}

    edit: (modal) ->
      form = new @form.form
        collection: @collection
        id: @model.id
      _.extend modal.options,
        title: "Edit #{@form.name}"
        content: form
        okText: 'OK'
      modal.render()
      form.trigger('shown')

    close: ->
      this.off()
