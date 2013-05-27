define [
    'jquery'
    'underscore'
    'backbone'
    'cs!events'
    'cs!models/responsibility'
], ($, _, Backbone, app, Responsibility) ->
    Backbone.View.extend

        ok: ->
            @responsibility.set(@form.getValue())
            data = [
                {
                    termId: @termId
                    personId: @responsibility.get('person').id
                    roleId: @responsibility.get('role').id
                }
            ]
            $.ajax
                url: '/responsibilities/patch'
                type: 'put'
                contentType: 'application/json'
                data: JSON.stringify(data)
                success: =>
                    this.render().done =>
                        app.resetCache('terms')
                        $('#alert')
                            .find('strong').html('Responsibilities have been successfully updated')
                            .parent()
                            .removeClass('alert-error')
                            .addClass('alert-success in')
                            .show()
                        this.parent.render()
                error: (jqXHR, textStatus, errorThrown) =>
                    $('#alert')
                        .find('strong').html(errorThrown)
                        .parent()
                        .removeClass('alert-success')
                        .addClass('alert-error in')
                        .show()
                    this.parent.render()
            return

        cancel: ->

        initialize: (options) ->
            @termId = options.termId
            this.on 'ok', _.bind(this.ok, this)
            this.on 'cancel', _.bind(this.cancel, this)

        render: ->
            app.terms().done (terms) =>
                term = terms.get(@termId)
                responsibility = new Responsibility
                responsibility.set('businessTerm', term)
                this.renderForm(responsibility)

        renderForm: (responsibility) ->
            this.cleanForm() if @form
            @form = new Backbone.Form({model: responsibility}).render()
            @responsibility = responsibility
            @$el.html @form.el
            return this

        cleanForm: ->
            @form.remove()
            return
