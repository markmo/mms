define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'snap'
    'cs!views/glossary/domains'
    'cs!views/glossary/term_section'
    'text!templates/glossary/terms.html'
    'lib/jqtree/jquery.cookie'
    'lib/jqtree/tree.jquery'
], ($, Backbone, Handlebars, app, Vm, Snap, DomainsSection, TermSection, termsPageTemplate) ->
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
            ,500) unless a.data('timer')
            return false

        create: ->
            termSection = Vm.create(this, 'TermSection', TermSection)
            termSection.render().createTerm()

        edit: ->
            termSection = Vm.create(this, 'TermSection', TermSection,
                termId: @termId
                parentTerm: @parentTerm)
            termSection.render().editTerm()

        show: ->
            termSection = Vm.create(this, 'TermSection', TermSection,
                termId: @termId)
            termSection.render().showTerm()

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
                        .bind('tree.click', (event) =>
                            node = event.node
                            if node
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
