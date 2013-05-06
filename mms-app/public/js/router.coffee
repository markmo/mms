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
            'sandboxes/:id/edit': 'editSandbox'
            'sandboxes': 'sandboxes'
            'datasources/:id/edit': 'editDatasource'
            'datasources': 'datasources'
            'datasources/:id/namespaces': 'namespaces'
            'sandboxes/:id/namespaces': 'sandboxNamespaces'
            'namespaces/:id/datasets(/:datasetId)': 'datasets'
            'datasets/new': 'editDataset'
            'datasets/:id/edit': 'editDataset'
            'datasets/:id/columns': 'columns'
            'datasets/:id/stats': 'showDatasetStats'
            'columns/:id/edit': 'editColumn'
            'columns/:id/revisions/:revisionId': 'showColumnRevision'
            'columns/:id/revisions': 'showColumnRevisions'
            'columns/:id': 'showColumn'
            'revisions/:id': 'showRevision'
            'revisions': 'revisions'
            'files': 'files'
            'search': 'search'
            'terms': 'terms'

            # Default
            '*actions': 'defaultAction'

    initialize: (options) ->
        appView = options.appView
        router = new AppRouter(options)
        app.router = router

        router.on 'route:editSandbox', (sandboxId) ->
            require ['cs!views/app/sandbox_form'], (SandboxForm) ->
                sandboxForm = Vm.create(appView, 'SandboxForm', SandboxForm)
                sandboxForm.render(sandboxId)
                return
            return

        router.on 'route:sandboxes', () ->
            require ['cs!views/app/sandboxes'], (SandboxesPage) ->
                sandboxesPage = Vm.create(appView, 'SandboxesPage', SandboxesPage)
                sandboxesPage.render()
                return
            return

        router.on 'route:editDatasource', (datasourceId) ->
            require ['cs!views/app/datasource_form'], (DatasourceForm) ->
                datasourceForm = Vm.create(appView, 'DatasourceForm', DatasourceForm)
                datasourceForm.render(datasourceId)
                return
            return

        router.on 'route:datasources', () ->
            require ['cs!views/app/datasources'], (DatasourcesPage) ->
                datasourcesPage = Vm.create(appView, 'DatasourcesPage', DatasourcesPage)
                datasourcesPage.render()
                return
            return

        router.on 'route:namespaces', (datasourceId) ->
            require ['cs!views/app/namespaces'], (NamespacesPage) ->
                namespacesPage = Vm.create appView, 'NamespacesPage', NamespacesPage,
                    context:
                        name: 'datasource'
                namespacesPage.render(datasourceId)
                return
            return

        router.on 'route:sandboxNamespaces', (sandboxId) ->
            require ['cs!views/app/namespaces'], (NamespacesPage) ->
                namespacesPage = Vm.create appView, 'NamespacesPage', NamespacesPage,
                    context:
                        name: 'sandbox'
                namespacesPage.render(sandboxId)
                return
            return

        router.on 'route:datasets', (namespaceId, datasetId) ->
            require ['cs!views/app/datasets'], (DatasetsPage) ->
                datasetsPage = Vm.create(appView, 'DatasetsPage', DatasetsPage)
                datasetsPage.render(namespaceId, datasetId)
                return
            return

        router.on 'route:editDataset', (datasetId) ->
            require ['cs!views/app/dataset_form'], (DatasetForm) ->
                datasetForm = Vm.create(appView, 'DatasetForm', DatasetForm, {datasetId: datasetId})
                datasetForm.render()
                return
            return

        router.on 'route:columns', (datasetId) ->
            require ['cs!views/app/columns'], (ColumnsPage) ->
                columnsPage = Vm.create(appView, 'ColumnsPage', ColumnsPage)
                columnsPage.render(datasetId)
                return
            return

        router.on 'route:showDatasetStats', (datasetId) ->
            require ['cs!views/app/dataset_stats'], (DatasetStatsPage) ->
                datasetStatsPage = Vm.create(appView, 'DatasetStatsPage', DatasetStatsPage)
                datasetStatsPage.render(datasetId)
                return
            return

        router.on 'route:editColumn', (columnId) ->
            require ['cs!views/app/column_form'], (ColumnForm) ->
                columnForm = Vm.create(appView, 'ColumnForm', ColumnForm)
                columnForm.render(columnId)
                return
            return

        router.on 'route:showColumnRevision', (columnId, revisionId) ->
            require ['cs!views/app/column_revision'], (ColumnRevisionPage) ->
                columnRevisionPage = Vm.create(appView, 'ColumnRevisionPage', ColumnRevisionPage)
                columnRevisionPage.render(columnId, revisionId)
                return
            return

        router.on 'route:showColumnRevisions', (columnId) ->
            require ['cs!views/app/revisions'], (RevisionsPage) ->
                revisionsPage = Vm.create(appView, 'RevisionsPage', RevisionsPage)
                revisionsPage.render('column', columnId)
                return
            return

        router.on 'route:showColumn', (columnId) ->
            require ['cs!views/app/column'], (ColumnPage) ->
                columnPage = Vm.create(appView, 'ColumnPage', ColumnPage)
                columnPage.render(columnId)
                return
            return

        router.on 'route:showRevision', (revisionId) ->
            require ['cs!views/app/revised_entities'], (RevisedEntitiesPage) ->
                revisedEntitiesPage = Vm.create(appView, 'RevisedEntitiesPage', RevisedEntitiesPage)
                revisedEntitiesPage.render(revisionId)
                return
            return

        router.on 'route:revisions', ->
            require ['cs!views/app/timemachine'], (TimemachinePage) ->
                timemachinePage = Vm.create(appView, 'TimemachinePage', TimemachinePage)
                timemachinePage.render()
                return
            return

        router.on 'route:files', ->
            require ['cs!views/file/browser'], (BrowserPage) ->
                browserPage = Vm.create(appView, 'BrowserPage', BrowserPage)
                browserPage.render()
                return
            return

        router.on 'route:search', ->
            require ['cs!views/app/search'], (SearchPage) ->
                searchPage = Vm.create(appView, 'SearchPage', SearchPage)
                searchPage.render()
                return
            return

        router.on 'route:terms', ->
            require ['cs!views/glossary/terms'], (TermsPage) ->
                termsPage = Vm.create(appView, 'TermsPage', TermsPage)
                termsPage.render()
                return
            return

        router.on 'route:defaultAction', () ->
            require ['cs!views/home/home'], (HomePage) ->
                homePage = Vm.create(appView, 'HomePage', HomePage)
                homePage.render()
                return
            return

        Backbone.history.start
            silent: options?.silent || false
        return router
