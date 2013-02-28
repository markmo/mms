define [
    'jquery'
    'underscore'
    'backbone'
    'cs!events'
    'cs!models/sandbox'
], ($, _, Backbone, app, Sandbox) ->
    Backbone.View.extend

        initialize: (options) ->
            @sandboxId = options?.sandboxId
            this.on 'ok', =>
                @sandbox.set(@form.getValue())
                app.sandboxes().done (sandboxes) =>
                    sandboxes.add(@sandbox)
                    @sandbox.save()
                    this.parent.render()
                return

            this.on 'cancel', ->
                #alert 'cancel'

        render: ->
            app.sandboxes().done (sandboxes) =>
                if @sandboxId?
                    @sandbox = sandboxes.get(@sandboxId)
                else
                    @sandbox = new Sandbox
                @clean() if @form?
                @form = new Backbone.Form(
                    model: @sandbox
                ).render()
                $(@el).html @form.el

            return this

        clean: ->
            @form.undelegateEvents()
            @form.remove()
            return
