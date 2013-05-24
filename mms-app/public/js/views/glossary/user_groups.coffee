define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!views/glossary/user_group_form'
    'text!templates/glossary/user_groups.html'
], ($, Backbone, Handlebars, app, Vm, UserGroupForm, userGroupsPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        events:
            'click #create-group': 'create'
            'click .group-name': 'edit'
            'click .group-edit': 'edit'
            'click #btnDelete': 'remove'

        compiled: Handlebars.compile userGroupsPageTemplate

        create: ->
            groupForm = Vm.create(this, 'UserGroupForm', UserGroupForm)
            modal = new Backbone.BootstrapModal
                title: 'New User Group'
                content: groupForm
                animate: true
            modal.open()
            return false

        edit: (event) ->
            id = $(event.currentTarget).data('id')
            groupForm = Vm.create(this, 'UserGroupForm', UserGroupForm, {userGroupId: id})
            modal = new Backbone.BootstrapModal
                title: 'Edit User Group'
                content: groupForm
                animate: true
            modal.open()
            return false

        remove: ->
            deletions = []
            $('.group-delete').each ->
                deletions.push($(this).data('id')) if $(this).attr('checked')
            if deletions.length
                $.ajax(
                    type: 'DELETE'
                    url: '/usergroups'
                    data: {id: deletions}
                ).done =>
                    _.each deletions, (id) =>
                        @groups.remove(@groups.get(id))
                    this.render()
            return false

        render: ->
            app.userGroups().done (coll) =>
                @groups = coll
                @$el.html @compiled
                    groups: coll.toJSON()
            return this

        clean: ->
            @$el.html('')
