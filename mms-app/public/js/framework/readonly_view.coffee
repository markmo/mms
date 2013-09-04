define [
  'underscore'
  'backbone'
], (_, Backbone) ->
  Backbone.View.extend

    initialize: (options) ->
      @collection = options.collection
      @model = options.model
      @form = options.form
      this.on 'ok', this.edit
      this.render()

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

    render: ->
      @$el.html @compiled
        model: @model.toJSON()

    close: ->
      this.off()
