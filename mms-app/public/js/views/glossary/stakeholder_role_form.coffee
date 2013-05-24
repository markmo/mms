define [
    'jquery'
    'backbone'
    'cs!events'
    'cs!models/stakeholder_role'
], ($, Backbone, app, StakeholderRole) ->
    Backbone.View.extend

        initialize: (options) ->
            @stakeholderRoleId = options?.stakeholderRoleId
            this.on 'ok', =>
                @role.set(@form.getValue())
                app.stakeholderRoles().done (roles) =>
                    roles.add(@role)
                    @role.save null,
                        success: => this.parent.render()

            this.on 'cancel', ->
                #alert 'cancel'

        render: ->
            if @stakeholderRoleId
                app.stakeholderRoles().done (roles) =>
                    role = roles.get(@stakeholderRoleId)
                    this.renderForm(role)
            else this.renderForm(new StakeholderRole)

        renderForm: (role) ->
            this.cleanForm() if @form
            @form = new Backbone.Form({model: role}).render()
            @role = role
            @$el.html @form.el
            return this

        cleanForm: ->
            @form.remove()
            return
