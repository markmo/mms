define [
    'jquery'
    'backbone'
    'cs!events'
    'cs!models/user_group'
], ($, Backbone, app, UserGroupRole) ->
    Backbone.View.extend

        initialize: (options) ->
            @userGroupId = options?.userGroupId
            this.on 'ok', =>
                @group.set(@form.getValue())
                app.userGroups().done (groups) =>
                    groups.add(@group)
                    @group.save null,
                        success: => this.parent.render()

            this.on 'cancel', ->
                #alert 'cancel'

        render: ->
            if @userGroupId
                app.userGroups().done (groups) =>
                    group = groups.get(@userGroupId)
                    this.renderForm(group)
            else this.renderForm(new UserGroupRole)

        renderForm: (group) ->
            this.cleanForm() if @form
            @form = new Backbone.Form({model: group}).render()
            @group = group
            @$el.html @form.el
            return this

        cleanForm: ->
            @form.remove()
            return
