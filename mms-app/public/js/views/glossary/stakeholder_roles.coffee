define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!views/glossary/stakeholder_role_form'
    'text!templates/glossary/stakeholder_roles.html'
], ($, Backbone, Handlebars, app, Vm, StakeholderRoleForm, stakeholderRolesPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        events:
            'click #create-role': 'create'
            'click .role-name': 'edit'
            'click .role-edit': 'edit'
            'click #btnDelete': 'remove'

        compiled: Handlebars.compile stakeholderRolesPageTemplate

        create: ->
            roleForm = Vm.create(this, 'StakeholderRoleForm', StakeholderRoleForm)
            modal = new Backbone.BootstrapModal
                title: 'New Stakeholder Role'
                content: roleForm
                animate: true
            modal.open()
            return false

        edit: (event) ->
            id = $(event.currentTarget).data('id')
            roleForm = Vm.create(this, 'StakeholderRoleForm', StakeholderRoleForm, {stakeholderRoleId: id})
            modal = new Backbone.BootstrapModal
                title: 'Edit Stakeholder Role'
                content: roleForm
                animate: true
            modal.open()
            return false

        remove: ->
            deletions = []
            $('.role-delete').each ->
                deletions.push($(this).data('id')) if $(this).attr('checked')
            if deletions.length
                $.ajax(
                    type: 'DELETE'
                    url: '/stakeholder-roles'
                    data: {id: deletions}
                ).done =>
                    _.each deletions, (id) =>
                        @roles.remove(@roles.get(id))
                    this.render()
            return false

        render: ->
            app.stakeholderRoles().done (coll) =>
                @roles = coll
                @$el.html @compiled
                    roles: coll.toJSON()
            return this

        clean: ->
            @$el.html('')
