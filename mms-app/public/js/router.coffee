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
            'access': 'access'
            'applications': 'applications'
            'catalogs/:id/namespaces': 'namespaces'
            'columns/:id/edit': 'editColumn'
            'columns/:id/revisions/:revisionId': 'showColumnRevision'
            'columns/:id/revisions': 'showColumnRevisions'
            'columns/:id': 'showColumn'
            'datasets/:id/columns': 'columns'
            'datasets/:id/edit': 'editDataset'
            'datasets/:id/stats': 'showDatasetStats'
            'datasets/new': 'editDataset'
            'datasources/:id/catalogs': 'catalogs'
            'datasources': 'datasources'
            'files': 'files'
            'namespaces/:id/datasets(/:datasetId)': 'datasets'
            'people': 'people'
            'responsibilities': 'responsibilities'
            'revisions/:id': 'showRevision'
            'revisions': 'revisions'
            'sandboxes/:id/edit': 'editSandbox'
            'sandboxes/:id/namespaces': 'sandboxNamespaces'
            'sandboxes': 'sandboxes'
            'search': 'search'
            'stakeholder-roles': 'stakeholderRoles'
            'terms/:id': 'showTerm'
            'terms': 'terms'
            'usergroups': 'userGroups'
            'vendors': 'vendors'

            # Default
            '*actions': 'defaultAction'

    initialize: (options) ->
        appView = options.appView
        router = new AppRouter(options)
        app.router = router

        router.on 'route:access', ->
            require ['cs!views/glossary/access'], (AccessPage) ->
                accessPage = Vm.create appView, 'AccessPage', AccessPage
                accessPage.render()

        router.on 'route:applications', ->
            require ['cs!views/glossary/applications'], (ApplicationsPage) ->
                applicationsPage = Vm.create(appView, 'ApplicationsPage', ApplicationsPage)
                applicationsPage.render()

        router.on 'route:namespaces', (catalogId) ->
            require ['cs!views/app/namespaces'], (NamespacesPage) ->
                namespacesPage = Vm.create appView, 'NamespacesPage', NamespacesPage,
                    context:
                        name: 'catalog'
                namespacesPage.render(catalogId)

        router.on 'route:editColumn', (columnId) ->
            require ['cs!views/app/column_form'], (ColumnForm) ->
                columnForm = Vm.create(appView, 'ColumnForm', ColumnForm)
                columnForm.render(columnId)

        router.on 'route:showColumnRevision', (columnId, revisionId) ->
            require ['cs!views/app/column_revision'], (ColumnRevisionPage) ->
                columnRevisionPage = Vm.create(appView, 'ColumnRevisionPage', ColumnRevisionPage)
                columnRevisionPage.render(columnId, revisionId)

        router.on 'route:showColumnRevisions', (columnId) ->
            require ['cs!views/app/revisions'], (RevisionsPage) ->
                revisionsPage = Vm.create(appView, 'RevisionsPage', RevisionsPage)
                revisionsPage.render('column', columnId)

        router.on 'route:showColumn', (columnId) ->
            require ['cs!views/app/column'], (ColumnPage) ->
                columnPage = Vm.create(appView, 'ColumnPage', ColumnPage)
                columnPage.render(columnId)

        router.on 'route:columns', (datasetId) ->
            require ['cs!views/app/columns'], (ColumnsPage) ->
                columnsPage = Vm.create(appView, 'ColumnsPage', ColumnsPage)
                columnsPage.render(datasetId)

        router.on 'route:editDataset', (datasetId) ->
            require ['cs!views/app/dataset_form'], (DatasetForm) ->
                datasetForm = Vm.create(appView, 'DatasetForm', DatasetForm, {datasetId: datasetId})
                datasetForm.render()

        router.on 'route:showDatasetStats', (datasetId) ->
            require ['cs!views/app/dataset_stats'], (DatasetStatsPage) ->
                datasetStatsPage = Vm.create(appView, 'DatasetStatsPage', DatasetStatsPage)
                datasetStatsPage.render(datasetId)

        router.on 'route:catalogs', (datasourceId) ->
            require ['cs!views/app/catalogs'], (CatalogsPage) ->
                catalogsPage = Vm.create appView, 'CatalogsPage', CatalogsPage,
                    datasourceId: datasourceId
                catalogsPage.render()

        router.on 'route:datasources', ->
            require ['cs!views/app/datasources'], (DatasourcesPage) ->
                datasourcesPage = Vm.create(appView, 'DatasourcesPage', DatasourcesPage)
                datasourcesPage.render()

        router.on 'route:files', ->
            require ['cs!views/file/browser'], (BrowserPage) ->
                browserPage = Vm.create(appView, 'BrowserPage', BrowserPage)
                browserPage.render()

        router.on 'route:datasets', (namespaceId, datasetId) ->
            require ['cs!views/app/datasets'], (DatasetsPage) ->
                datasetsPage = Vm.create(appView, 'DatasetsPage', DatasetsPage)
                datasetsPage.render(namespaceId, datasetId)

        router.on 'route:people', ->
            require ['cs!views/glossary/people'], (PeoplePage) ->
                peoplePage = Vm.create(appView, 'PeoplePage', PeoplePage)
                peoplePage.render()

        router.on 'route:responsibilities', ->
            require ['cs!views/glossary/responsibilities'], (ResponsibilitiesPage) ->
                responsibilitiesPage = Vm.create appView, 'ResponsibilitiesPage', ResponsibilitiesPage
                responsibilitiesPage.render()

        router.on 'route:showRevision', (revisionId) ->
            require ['cs!views/app/revised_entities'], (RevisedEntitiesPage) ->
                revisedEntitiesPage = Vm.create(appView, 'RevisedEntitiesPage', RevisedEntitiesPage)
                revisedEntitiesPage.render(revisionId)

        router.on 'route:revisions', ->
            require ['cs!views/app/timemachine'], (TimemachinePage) ->
                timemachinePage = Vm.create(appView, 'TimemachinePage', TimemachinePage)
                timemachinePage.render()

        router.on 'route:editSandbox', (sandboxId) ->
            require ['cs!views/app/sandbox_form'], (SandboxForm) ->
                sandboxForm = Vm.create(appView, 'SandboxForm', SandboxForm)
                sandboxForm.render(sandboxId)

        router.on 'route:sandboxNamespaces', (sandboxId) ->
            require ['cs!views/app/namespaces'], (NamespacesPage) ->
                namespacesPage = Vm.create appView, 'NamespacesPage', NamespacesPage,
                    context:
                        name: 'sandbox'
                namespacesPage.render(sandboxId)

        router.on 'route:sandboxes', ->
            require ['cs!views/app/sandboxes'], (SandboxesPage) ->
                sandboxesPage = Vm.create(appView, 'SandboxesPage', SandboxesPage)
                sandboxesPage.render()

        router.on 'route:search', ->
            require ['cs!views/app/search'], (SearchPage) ->
                searchPage = Vm.create(appView, 'SearchPage', SearchPage)
                searchPage.render()

        router.on 'route:stakeholderRoles', ->
            require ['cs!views/glossary/stakeholder_roles'], (StakeholderRolesPage) ->
                rolesPage = Vm.create(appView, 'StakeholderRolesPage', StakeholderRolesPage)
                rolesPage.render()

        router.on 'route:showTerm', (termId) ->
            require ['cs!views/glossary/terms'], (TermsPage) ->
                termsPage = Vm.create(appView, 'TermsPage', TermsPage, {termId: termId})
                termsPage.render()

        router.on 'route:terms', ->
            require ['cs!views/glossary/terms'], (TermsPage) ->
                termsPage = Vm.create(appView, 'TermsPage', TermsPage)
                termsPage.render()

        router.on 'route:userGroups', ->
            require ['cs!views/glossary/user_groups'], (UserGroupsPage) ->
                userGroupsPage = Vm.create(appView, 'UserGroupsPage', UserGroupsPage)
                userGroupsPage.render()

        router.on 'route:vendors', ->
            require ['cs!views/glossary/vendors'], (VendorsPage) ->
                vendorsPage = Vm.create(appView, 'VendorsPage', VendorsPage)
                vendorsPage.render()
#            require ['cs!views/glossary/vendors', 'cs!components/paginator'], (VendorsPage, Paginator) ->
#                vendorsPage = Vm.create(appView, 'VendorsPage', VendorsPage)
#                vendorsPage.render().done (coll) ->
#                    paginator = Vm.create(vendorsPage, 'Paginator', Paginator, {pageableCollection: coll})
#                    paginator.render()
#                    vendorsPage.listenTo(paginator, 'previous next', vendorsPage.render)

        router.on 'route:defaultAction', () ->
            require ['cs!views/home/home'], (HomePage) ->
                homePage = Vm.create(appView, 'HomePage', HomePage)
                homePage.render()

        Backbone.history.start
            silent: options?.silent || false
        return router
