define [
    'jquery',
    'underscore',
    'backbone',
    'cs!vm',
    'cs!collections/filter_types',
	'cs!collections/tables',
    'cs!views/tables/columns',
    'text!templates/tables/tables.html'
], ($, _, Backbone, Vm, FilterTypesCollection, TablesCollection, ColumnsSection, tablesPageTemplate) ->
    Backbone.View.extend
        el: '.page'

        compiled: _.template tablesPageTemplate

        events:
            'show .collapse': 'show'
            'hidden .collapse': 'hidden'

        show: (event) ->
            id = event.target.id
            tableId = id.substr(5)
            collapsible = $(event.target)
            unless @children and @children[id]?
                el = collapsible.find('.accordion-inner')
                columnsSection = Vm.create(this, id, ColumnsSection, el)
                columnsSection.render(tableId)
            heading = collapsible.prev()
            heading.addClass('selected')
#            $('body').animate
#                scrollTop: heading.offset().top
            return

        hidden: (event) ->
            $(event.target).prev().removeClass('selected')

        render: (schemaId) ->
            filterTypes = new FilterTypesCollection
            filterTypes.fetch
                success: =>
                    tables = new TablesCollection(schemaId)
                    tables.fetch
                        success: =>
                            $(@el).html @compiled
                                filterTypes: filterTypes.toJSON()
                                tables: tables.toJSON()
                            return
                    return
            return this
