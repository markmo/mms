define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'snap'
    'cs!views/glossary/domains'
    'cs!views/glossary/term'
    'cs!views/glossary/term_form'
    'cs!views/glossary/associations'
    'text!templates/glossary/terms.html'
    'lib/jqtree/jquery.cookie'
    'lib/jqtree/tree.jquery'
], ($, Backbone, Handlebars, app, Vm, Snap, DomainsSection, TermView, TermForm, AssociationsSection, termsPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        events:
            'click #create-term': 'create'
            'click .item-title': 'link'
            'dblclick .item-title': 'edit'
            'click .edit-btn': 'edit'

        compiled: Handlebars.compile termsPageTemplate

        link: (event) ->
            a = $(event.target)
            href = a.attr('href')
            a.data 'timer', setTimeout(->
                window.location = href
            ,500) unless a.data 'timer'
            return false

        create: ->
            termForm = Vm.create(this, 'TermForm', TermForm)
            termForm.render().done =>

                # TODO replace with data-snap-ignore="true" on the elements

                $('.editable').on('mouseover', => @snapper.disable())
                $('.editable').on('mouseout', => @snapper.enable())
                $('#custom-form').on('mouseover', 'select,input,textarea', => @snapper.disable())
                $('#custom-form').on('mouseout', 'select,input,textarea', => @snapper.enable())
                return
            return false

        edit: ->
            termForm = Vm.create(this, 'TermForm', TermForm,
                termId: @termId
                parentTerm: @parentTerm)
            termForm.render().done =>
                $('.editable').on('mouseover', => @snapper.disable())
                $('.editable').on('mouseout', => @snapper.enable())
                $('#custom-form').on('mouseover', 'select,input,textarea', => @snapper.disable())
                $('#custom-form').on('mouseout', 'select,input,textarea', => @snapper.enable())
                return

            associationsSection = Vm.create(this, 'AssociationsSection', AssociationsSection,
                termId: @termId
            )
            associationsSection.render()
            termForm.on('closed', ->
                associationsSection.clean()
                termForm.off()
            )
            return false

        show: ->
            termView = Vm.create(this, 'TermView', TermView,
                termId: @termId)
            termView.render()
            return false

        initialize: (options) ->
            @domainId = options?.domainId
            domainsSection = Vm.create(this, 'DomainsSection', DomainsSection)
            domainsSection.render()
            return this

        render: (domainId) ->
            @domainId = domainId if domainId
            app.domains().done (domains) =>
                domain = domains.get(@domainId)
                app.termsByDomain(@domainId).done (coll) =>
                    terms = app.convertCollectionToTree(coll)
                    @$el.html @compiled
                        domain: domain?.toString() || 'ALL'
                    $('#terms-tree').tree({data: terms, dragAndDrop: true})
                        .bind('tree.select', (event) =>
                            node = event.node
                            @termId = node.id
                            this.show(event)
                            return
                        )
                        .bind('tree.move', (event) =>
                            movedNode = event.move_info.moved_node
                            targetNode = event.move_info.target_node
                            position = event.move_info.position
                            previousParent = event.move_info.previous_parent
                            @termId = movedNode.id
                            @parentTerm = targetNode
                            this.edit(event)
                            return
                        )

                    snapper = new Snap
                        element: document.getElementById('main')
                        touchToDrag: false

                    $('#open-left').on('click', (event) ->
                        event.preventDefault()
                        if snapper.state().state == 'left' then snapper.close()
                        else snapper.open('left')
                        return
                    )
                    @snapper = snapper

                    this.delay(2000, ->
                        $.bootstrapGrowl 'Double click an item to open the edit form',
                            type: 'info'
                            delay: 4000
                    ) unless app.session.notifiedOf('dblclick') or coll.isEmpty()
                    return
                return
            return this

        clean: ->
            $('#open-left').off()
            $('#left-drawer').off()
            $('.editable').off()
