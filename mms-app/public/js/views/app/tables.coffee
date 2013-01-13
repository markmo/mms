define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!collections/filter_types'
	'cs!collections/tables'
    'cs!views/app/columns'
    'text!templates/app/tables.html'
], ($, Backbone, Handlebars, app, Vm, FilterTypesCollection, TablesCollection, ColumnsSection, tablesPageTemplate) ->
    Backbone.View.extend
        el: '.page'

        compiled: Handlebars.compile tablesPageTemplate

        events:
            'show .collapse': 'show'
            'hidden .collapse': 'hidden'
            'click #filter-type-btn-group .btn': 'filterColumns'

        show: (event) ->
            id = event.target.id
            tableId = id.substr(5)
            collapsible = $(event.target)
            if not @children?[id]
                el = collapsible.find('.accordion-inner')
                columnsSection = Vm.create(this, id, ColumnsSection,
                    el: el
                    tableId: tableId
                )
                columnsSection.render(@filterTypes)
            heading = collapsible.prev()
            heading.addClass('selected')
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

        render: (schemaId, tableId) ->
            schema = app.schemas.get(schemaId)
            dataSource = schema.get('dataSource')
            filterTypes = new FilterTypesCollection
            filterTypes.fetch
                success: =>
                    tables = new TablesCollection({}, {schemaId: schemaId})
                    app.tables = tables
                    tables.fetch
                        success: =>
                            content = $('<div class="page-content clearfix"></div>')
                            content.html @compiled
                                dataSource: dataSource
                                schema: schema.toJSON()
                                filterTypes: filterTypes.toJSON()
                                tables: tables.toJSON()
                            $(@el).html content
                            content.animate({top: 0}, 'fast', ->
                                if tableId?
                                    section = $('#table' + tableId)
                                    section.collapse('show')
                                    $('body').animate({scrollTop: section.parent().offset().top - 44}, 'slow')
                                return
                            )
                            return
                    return
            return this
