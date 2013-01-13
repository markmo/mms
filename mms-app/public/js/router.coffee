define [
    'jquery'
    'underscore'
    'backbone'
    'cs!vm'
    'cs!events'
], ($, _, Backbone, Vm, app) ->
    AppRouter = Backbone.Router.extend
        routes:
            # Pages
            'data-sources/:id/edit': 'editDataSource'
            'data-sources/:id/schemas': 'schemas'
            'schemas/:id/tables(/:tableId)': 'tables'
            'tables/:id/columns': 'columns'
            'columns/:id/edit': 'editColumn'
            'columns/:id': 'showColumn'
            'columns/:id/revisions': 'showColumnRevisions'
            'columns/:id/revisions/:revisionId': 'showColumnRevision'
            'revisions/:id': 'showRevision'
            'revisions': 'revisions'

            # Default
            '*actions': 'defaultAction'

    initialize: (options) ->
        appView = options.appView
        router = new AppRouter(options)
        app.router = router

        router.on 'route:defaultAction', () ->
            require ['cs!views/app/data_sources'], (DataSourcesPage) ->
                dataSourcesPage = Vm.create(appView, 'DataSourcesPage', DataSourcesPage)
                dataSourcesPage.render()
                return
            return

        router.on 'route:editDataSource', (dataSourceId) ->
            require ['cs!views/app/data_source_form'], (DataSourceForm) ->
                dataSourceForm = Vm.create(appView, 'DataSourceForm', DataSourceForm)
                dataSourceForm.render(dataSourceId)
                return
            return

        router.on 'route:schemas', (dataSourceId) ->
            require ['cs!views/app/schemas'], (SchemasPage) ->
                schemasPage = Vm.create(appView, 'SchemasPage', SchemasPage)
                schemasPage.render(dataSourceId)
                return
            return

        router.on 'route:tables', (schemaId, tableId) ->
            require ['cs!views/app/tables'], (TablesPage) ->
                tablesPage = Vm.create(appView, 'TablesPage', TablesPage)
                tablesPage.render(schemaId, tableId)
                return
            return

        router.on 'route:columns', (tableId) ->
            require ['cs!views/app/columns'], (ColumnsPage) ->
                columnsPage = Vm.create(appView, 'ColumnsPage', ColumnsPage)
                columnsPage.render(tableId)
                return
            return

        router.on 'route:showColumn', (columnId) ->
            require ['cs!views/app/column'], (ColumnPage) ->
                columnPage = Vm.create(appView, 'ColumnPage', ColumnPage)
                columnPage.render(columnId)
                return
            return

        router.on 'route:editColumn', (columnId) ->
            require ['cs!views/app/column_form'], (ColumnForm) ->
                columnForm = Vm.create(appView, 'ColumnForm', ColumnForm)
                columnForm.render(columnId)
                return
            return

        router.on 'route:showColumnRevisions', (columnId) ->
            require ['cs!views/app/revisions'], (RevisionsPage) ->
                revisionsPage = Vm.create(appView, 'RevisionsPage', RevisionsPage)
                revisionsPage.render('column', columnId)
                return
            return

        router.on 'route:showColumnRevision', (columnId, revisionId) ->
            require ['cs!views/app/column_revision'], (ColumnRevisionPage) ->
                columnRevisionPage = Vm.create(appView, 'ColumnRevisionPage', ColumnRevisionPage)
                columnRevisionPage.render(columnId, revisionId)
                return
            return

        router.on 'route:revisions', ->
            require ['cs!views/app/timemachine'], (TimemachinePage) ->
                timemachinePage = Vm.create(appView, 'TimemachinePage', TimemachinePage)
                timemachinePage.render()
                return
            return

        router.on 'route:showRevision', (revisionId) ->
            require ['cs!views/app/revised_entities'], (RevisedEntitiesPage) ->
                revisedEntitiesPage = Vm.create(appView, 'RevisedEntitiesPage', RevisedEntitiesPage)
                revisedEntitiesPage.render(revisionId)

        Backbone.history.start
            silent: options?.silent || false
        return router
