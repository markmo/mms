define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!components/pageable_view'
    'cs!views/glossary/stakeholder_role_form'
    'text!templates/glossary/stakeholder_roles.html'
], ($, Backbone, Handlebars, app, Vm, PageableView, StakeholderRoleForm, stakeholderRolesPageTemplate) ->
    PageableView.extend
        el: '#page'

        events: ->
            _.extend {}, PageableView.prototype.events,
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
                        @pageableCollection.remove(@pageableCollection.get(id))
                    this.render()
            return false

        doRender: ->
            app.stakeholderRoles().done (coll) =>
                @pageableCollection = coll
                @$el.html @compiled
                    pageableCollection: coll

        clean: ->
            this.stopListening()
            @$el.html('')
