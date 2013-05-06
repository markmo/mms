define [
    'jquery'
    'underscore'
    'backbone'
    'cs!events'
    'cs!models/domain'
], ($, _, Backbone, app, Domain) ->
    Backbone.View.extend

        initialize: (options) ->
            @domainId = options?.domainId
            this.on 'ok', =>
                @domain.set(@form.getValue())
                app.domains().done (domains) =>
                    domains.add(@domain)
                    @domain.save()
                    this.parent.render()
                return

            this.on 'cancel', ->
                #alert 'cancel'

        render: ->
            app.domains().done (domains) =>
                if @domainId
                    @domain = domains.get(@domainId)
                else
                    @domain = new Domain
                @clean() if @form?
                @form = new Backbone.Form(
                    model: @domain
                ).render()
                $(@el).html @form.el

            return this

        clean: ->
            @form.undelegateEvents()
            @form.remove()
            return
