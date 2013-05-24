define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!collections/filter_types'
	'cs!collections/datasets'
    'cs!views/app/columns'
    'text!templates/app/datasets.html'
], ($, Backbone, Handlebars, app, Vm, FilterTypesCollection, DatasetsCollection, ColumnsSection, datasetsPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        compiled: Handlebars.compile datasetsPageTemplate

        events:
            'show .collapse': 'show'
            'shown .collapse': 'shown'
            'hidden .collapse': 'hidden'
            'click #filter-type-btn-group .btn': 'filterColumns'

        show: (event) ->
            id = event.target.id
            datasetId = id.substr(7)
            collapsible = $(event.target)
            if not @children?[id]
                el = collapsible.find('.accordion-inner')
                columnsSection = Vm.create(this, id, ColumnsSection,
                    el: el
                    datasetId: datasetId
                )
                columnsSection.render(@filterTypes)
            heading = collapsible.prev()
            heading.addClass('selected')
            return

        shown: (event) ->
            collapsible = $(event.target)
            $('body').animate({scrollTop: collapsible.parent().offset().top - 44}, 'slow')
            return

        hidden: (event) ->
            $(event.target).prev().removeClass('selected')

        filterColumns: (event) ->
            @filterTypes = []
            target = $(event.target)
            target.parent().find('.btn').each (i, elem) =>
                btn = $(elem)
                if target.is(btn) # need to use jquery method to check for equality
                    @filterTypes.push btn.html() unless btn.hasClass('active')
                    # because it is about to become active
                    # click event on button seems to fire before update of class
                else
                    @filterTypes.push btn.html() if btn.hasClass('active')
                return

            idInView = $('.collapse.in').attr('id')

            # cause column subviews to be recreated
            for id of @children
                # except current
                unless id == idInView
                    # remove child views from the DOM and any model events bound to child views
                    #@children[id].remove() # removes the .accordion-inner container as well
                    @children[id].$el.empty()
                    delete @children[id]

            @children[idInView].render(@filterTypes)
            return true

        render: (namespaceId, datasetId) ->
            app.namespaces().done (namespaces) =>
                namespace = namespaces.get(namespaceId)
                catalog = namespace.get('catalog')
                datasource = catalog.datasource
                filterTypes = new FilterTypesCollection
                filterTypes.fetch
                    success: =>
                        datasets = new DatasetsCollection({}, {namespaceId: namespaceId})
                        datasets.fetch
                            success: =>
                                content = $('<div class="page-content clearfix"></div>')
                                content.html @compiled
                                    datasource: datasource
                                    catalog: catalog
                                    namespace: namespace.toJSON()
                                    filterTypes: filterTypes.toJSON()
                                    datasets: datasets.toJSON()
                                $(@el).html content
                                content.animate({top: 0}, 'fast', ->
                                    if datasetId?
                                        section = $('#dataset' + datasetId)
                                        section.collapse('show')
                                        $('body').animate({scrollTop: section.parent().offset().top - 44}, 'slow')
                                    return
                                )
                                return
                        return
            return this
