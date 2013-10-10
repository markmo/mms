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
            if @domainId
                app.domains().done (domains) =>
                    domain = domains.get(@domainId)
                    this.renderForm(domain)
            else this.renderForm(new Domain)

        renderForm: (domain) ->
            this.cleanForm() if @form
            @form = new Backbone.Form({model: domain}).render()
            @domain = domain
            @$el.html @form.el
            return this

        cleanForm: ->
            @form.remove()
            return
