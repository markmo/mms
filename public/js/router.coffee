define [
    'jquery',
    'underscore',
    'backbone',
    'cs!vm'
], ($, _, Backbone, Vm) ->
    AppRouter = Backbone.Router.extend
        routes:
            # Pages
            'data-sources/:id/schemas': 'schemas'
            'schemas/:id/tables': 'tables'
            'tables/:id/columns': 'columns'
            'columns/:id': 'showColumn'
            'columns/edit/:id': 'editColumn'

            # Default
            '*actions': 'defaultAction'

    initialize: (options) ->
        appView = options.appView
        router = new AppRouter(options)

        router.on 'route:defaultAction', () ->
            require ['cs!views/tables/data_sources'], (DataSourcesPage) ->
                dataSourcesPage = Vm.create(appView, 'DataSourcesPage', DataSourcesPage)
                dataSourcesPage.render()
                return
            return

        router.on 'route:schemas', (dataSourceId) ->
            require ['cs!views/tables/schemas'], (SchemasPage) ->
                schemasPage = Vm.create(appView, 'SchemasPage', SchemasPage)
                schemasPage.render(dataSourceId)
                return
            return

        router.on 'route:tables', (schemaId) ->
            require ['cs!views/tables/tables'], (TablesPage) ->
                tablesPage = Vm.create(appView, 'TablesPage', TablesPage)
                tablesPage.render(schemaId)
                return
            return

        router.on 'route:columns', (tableId) ->
            require ['cs!views/tables/columns'], (ColumnsPage) ->
                columnsPage = Vm.create(appView, 'ColumnsPage', ColumnsPage)
                columnsPage.render(tableId)
                return
            return

        router.on 'route:showColumn', (columnId) ->
            require ['cs!views/tables/column'], (ColumnPage) ->
                columnPage = Vm.create(appView, 'ColumnPage', ColumnPage)
                columnPage.render(columnId)
                return
            return

        router.on 'route:editColumn', (columnId) ->
            require ['cs!views/tables/column_form'], (ColumnForm) ->
                columnForm = Vm.create(appView, 'ColumnForm', ColumnForm)
                columnForm.render(columnId)
                return
            return

        Backbone.history.start();
        return
