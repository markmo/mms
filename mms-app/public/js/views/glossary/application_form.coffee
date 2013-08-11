define [
  'underscore'
  'backbone'
  'cs!models/application'
  'cs!components/form'
], (_, Backbone, Application) ->

  Backbone.View.extend

    ok: ->
      @application.set(@form.getValue())
      @collection.add(@application)
      @collection.save null,
        success: => this.parentView.render
      return false

    cancel: ->
      return false

    initialize: (options) ->
      @collection = options.collection
      @id = options.id
      this.listenTo @collection, 'sync', this.render
      this.on 'ok', this.ok
      this.on 'cancel', this.cancel

    render: ->
      if @id
        application = @collection.get(@id)
        this.renderForm(application)
      else this.renderForm(new Application)

    renderForm: (application) ->
      @form?.remove()
      @form = new Backbone.Form({model: application}).render()
      @application = application
      @$el.html @form.el
      return this
