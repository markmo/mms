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

  collections = {}

  view = null

  useCollection = (collectionName, CollectionType, options) ->
    collections[collectionName] || collections[collectionName] = new CollectionType(options)

  initialize: (options) ->
    appView = options.appView

    app.useLayout('layout')

    router = new AppRouter(options)
    app.router = router

    router.on 'route', ->
      view?.close()

    router.on 'route:access', ->
      require [
        'cs!views/glossary/access'
      ], (AccessView) ->
        app.setView('.page', new AccessView)

    router.on 'route:applications', ->
      require [
        'cs!views/glossary/applications'
      ], (ApplicationsView) ->
        app.setView('.page', new ApplicationsView)

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
      require [
        'cs!collections/columns'
        'cs!views/app/columns'
      ], (Columns, ColumnsView) ->
        columns = useCollection 'columns', Columns,
          datasetId: datasetId
        Vm.create appView, 'ColumnsView', ColumnsView,
          collection: columns
        columns.fetch()

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
      require [
        'cs!views/glossary/people'
      ], (PeopleView) ->
        app.setView('.page', new PeopleView)

    router.on 'route:responsibilities', ->
      require [
        'cs!views/glossary/responsibilities'
      ], (ResponsibilitiesView) ->
        app.setView('.page', new ResponsibilitiesView)

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
      require [
        'cs!views/glossary/stakeholder_roles'
      ], (StakeholderRolesView) ->
        app.setView('.page', new StakeholderRolesView)

    router.on 'route:showTerm', (termId) ->
      require ['cs!views/glossary/terms'], (TermsPage) ->
        termsPage = Vm.create(appView, 'TermsPage', TermsPage, {termId: termId})
        termsPage.render()

    router.on 'route:terms', ->
      require [
        'cs!views/glossary/glossary_layout'
      ], (GlossaryLayout) ->
        glossaryLayout = app.setView('.page', new GlossaryLayout)
        glossaryLayout.render()

    router.on 'route:userGroups', ->
      require [
        'cs!views/glossary/user_groups'
      ], (UserGroupsView) ->
        app.setView('.page', new UserGroupsView)

    router.on 'route:vendors', ->
      require [
        'cs!views/glossary/vendors'
      ], (VendorsView) ->
        app.setView('.page', new VendorsView)

    router.on 'route:defaultAction', () ->
      require ['cs!views/home/home'], (HomePage) ->
        homePage = Vm.create(appView, 'HomePage', HomePage)
        homePage.render()

    Backbone.history.start
      silent: options?.silent || false

    return router
