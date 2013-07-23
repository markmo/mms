define [
    'jquery'
    'underscore'
    'backbone'
    'cs!events'
    'cs!models/application'
    'cs!components/form'
], ($, _, Backbone, app, Application) ->
    Backbone.View.extend

        ok: ->
            @application.set(@form.getValue())
            app.applications().done (applications) =>
                applications.add(@application)
                @application.save null,
                    success: => this.parent.render()
            return

        cancel: ->

        initialize: (options) ->
            @applicationId = options?.applicationId
            this.on 'ok', _.bind(this.ok, this)
            this.on 'cancel', _.bind(this.cancel, this)

        render: ->
            if @applicationId
                app.applications().done (applications) =>
                    application = applications.get(@applicationId)
                    this.renderForm(application)
            else this.renderForm(new Application)

        renderForm: (application) ->
            this.cleanForm() if @form
            @form = new Backbone.Form({model: application}).render()
            @application = application
            @$el.html @form.el
            return this

        cleanForm: ->
            @form.remove()
            return
