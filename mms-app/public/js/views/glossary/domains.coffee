define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!views/glossary/domain_form'
    'text!templates/glossary/domains.html'
], ($, Backbone, Handlebars, app, Vm, DomainForm, domainsPageTemplate) ->
    Backbone.View.extend
        el: '#left-drawer'

        compiled: Handlebars.compile domainsPageTemplate

        create: ->
            domainForm = Vm.create(this, 'DomainForm', DomainForm)
            modal = new Backbone.BootstrapModal(
                title: 'New Domain'
                content: domainForm
                animate: true
            ).open()
            return false

        edit: (domainId) ->
            domainForm = Vm.create(this, 'DomainForm', DomainForm, {domainId: domainId})
            modal = new Backbone.BootstrapModal(
                title: 'Edit Domain'
                content: domainForm
                animate: true
            ).open()
            return false

        initialize: (options) ->
            $('#left-drawer').on('click', '#create-domain', _.bind(this.create, this))

        render: ->
            app.domains().done (coll) =>
                domains = app.convertCollectionToTree(coll)
                @$el.html @compiled
                $('#domains-tree').tree({data: domains, dragAndDrop: true})
                    .bind('tree.select', (event) =>
                        node = event.node
                        if node
                            @domainId = node.id
                            this.parent.render(@domainId)
                        return
                    )
                    .bind('tree.dblclick', (event) =>
                        node = event.node
                        @domainId = node.id
                        this.edit(@domainId)
                        return
                    )
                    .bind('tree.move', (event) =>
                        movedNode = event.move_info.moved_node
                        targetNode = event.move_info.target_node
                        position = event.move_info.position
                        previousParent = event.move_info.previous_parent
                        @domainId = movedNode.id
                        @parentDomain = targetNode
                        #this.edit(event)
                        return
                    )
                return
            return this

        clean: ->
            $('#left-drawer').off('click', '#create-domain')
