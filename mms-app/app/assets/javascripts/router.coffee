define [
  'jquery'
  'underscore'
  'backbone'
  'vm'
  'events'
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

    router.on 'route:access route:applications route:people route:responsibilities
              route:showTerm route:stakeholderRoles route:terms route:userGroups
              route:vendors', ->
      require [
        'views/glossary/left_menu'
      ], (LeftMenuView) ->
        unless app.hasView('#left-menu')
          leftMenuView = app.setView('#left-menu', new LeftMenuView)
          leftMenuView.render()

    router.on 'route:access', ->
      require [
        'views/glossary/access'
      ], (AccessView) ->
        app.setView('.page', new AccessView)

    router.on 'route:applications', ->
      require [
        'views/glossary/applications'
      ], (ApplicationsView) ->
        app.setView('.page', new ApplicationsView)

    router.on 'route:namespaces', (catalogId) ->
      require ['views/app/namespaces'], (NamespacesPage) ->
        namespacesPage = Vm.create appView, 'NamespacesPage', NamespacesPage,
          context:
            name: 'catalog'
        namespacesPage.render(catalogId)

    router.on 'route:editColumn', (columnId) ->
      require ['views/app/column_form'], (ColumnForm) ->
        columnForm = Vm.create(appView, 'ColumnForm', ColumnForm)
        columnForm.render(columnId)

    router.on 'route:showColumnRevision', (columnId, revisionId) ->
      require ['views/app/column_revision'], (ColumnRevisionPage) ->
        columnRevisionPage = Vm.create(appView, 'ColumnRevisionPage', ColumnRevisionPage)
        columnRevisionPage.render(columnId, revisionId)

    router.on 'route:showColumnRevisions', (columnId) ->
      require ['views/app/revisions'], (RevisionsPage) ->
        revisionsPage = Vm.create(appView, 'RevisionsPage', RevisionsPage)
        revisionsPage.render('column', columnId)

    router.on 'route:showColumn', (columnId) ->
      require ['views/app/column'], (ColumnPage) ->
        columnPage = Vm.create(appView, 'ColumnPage', ColumnPage)
        columnPage.render(columnId)

    router.on 'route:columns', (datasetId) ->
      require [
        'collections/columns'
        'views/app/columns'
      ], (Columns, ColumnsView) ->
        columns = useCollection 'columns', Columns,
          datasetId: datasetId
        Vm.create appView, 'ColumnsView', ColumnsView,
          collection: columns
        columns.fetch()

    router.on 'route:editDataset', (datasetId) ->
      require ['views/app/dataset_form'], (DatasetForm) ->
        datasetForm = Vm.create(appView, 'DatasetForm', DatasetForm, {datasetId: datasetId})
        datasetForm.render()

    router.on 'route:showDatasetStats', (datasetId) ->
      require ['views/app/dataset_stats'], (DatasetStatsPage) ->
        datasetStatsPage = Vm.create(appView, 'DatasetStatsPage', DatasetStatsPage)
        datasetStatsPage.render(datasetId)

    router.on 'route:catalogs', (datasourceId) ->
      require ['views/app/catalogs'], (CatalogsPage) ->
        catalogsPage = Vm.create appView, 'CatalogsPage', CatalogsPage,
          datasourceId: datasourceId
        catalogsPage.render()

    router.on 'route:datasources', ->
      require ['views/app/datasources'], (DatasourcesPage) ->
        datasourcesPage = Vm.create(appView, 'DatasourcesPage', DatasourcesPage)
        datasourcesPage.render()

    router.on 'route:files', ->
      require ['views/file/browser'], (BrowserPage) ->
        browserPage = Vm.create(appView, 'BrowserPage', BrowserPage)
        browserPage.render()

    router.on 'route:datasets', (namespaceId, datasetId) ->
      require ['views/app/datasets'], (DatasetsPage) ->
        datasetsPage = Vm.create(appView, 'DatasetsPage', DatasetsPage)
        datasetsPage.render(namespaceId, datasetId)

    router.on 'route:people', ->
      require [
        'views/glossary/people'
      ], (PeopleView) ->
        app.setView('.page', new PeopleView)

    router.on 'route:responsibilities', ->
      require [
        'views/glossary/responsibilities'
      ], (ResponsibilitiesView) ->
        app.setView('.page', new ResponsibilitiesView)

    router.on 'route:showRevision', (revisionId) ->
      require ['views/app/revised_entities'], (RevisedEntitiesPage) ->
        revisedEntitiesPage = Vm.create(appView, 'RevisedEntitiesPage', RevisedEntitiesPage)
        revisedEntitiesPage.render(revisionId)

    router.on 'route:revisions', ->
      require ['views/app/timemachine'], (TimemachinePage) ->
        timemachinePage = Vm.create(appView, 'TimemachinePage', TimemachinePage)
        timemachinePage.render()

    router.on 'route:editSandbox', (sandboxId) ->
      require ['views/app/sandbox_form'], (SandboxForm) ->
        sandboxForm = Vm.create(appView, 'SandboxForm', SandboxForm)
        sandboxForm.render(sandboxId)

    router.on 'route:sandboxNamespaces', (sandboxId) ->
      require ['views/app/namespaces'], (NamespacesPage) ->
        namespacesPage = Vm.create appView, 'NamespacesPage', NamespacesPage,
          context:
            name: 'sandbox'
        namespacesPage.render(sandboxId)

    router.on 'route:sandboxes', ->
      require ['views/app/sandboxes'], (SandboxesPage) ->
        sandboxesPage = Vm.create(appView, 'SandboxesPage', SandboxesPage)
        sandboxesPage.render()

    router.on 'route:search', ->
      require ['views/app/search'], (SearchPage) ->
        searchPage = Vm.create(appView, 'SearchPage', SearchPage)
        searchPage.render()

    router.on 'route:stakeholderRoles', ->
      require [
        'views/glossary/stakeholder_roles'
      ], (StakeholderRolesView) ->
        app.setView('.page', new StakeholderRolesView)

    router.on 'route:showTerm', (termId) ->
      require [
        'views/glossary/terms_layout'
      ], (TermsLayout) ->
        app.setView('.page', new TermsLayout({termId: termId}))

    router.on 'route:terms', ->
      require [
        'views/glossary/terms_layout'
      ], (TermsLayout) ->
        app.setView('.page', new TermsLayout)

    router.on 'route:userGroups', ->
      require [
        'views/glossary/user_groups'
      ], (UserGroupsView) ->
        app.setView('.page', new UserGroupsView)

    router.on 'route:vendors', ->
      require [
        'views/glossary/vendors'
      ], (VendorsView) ->
        app.setView('.page', new VendorsView)

    router.on 'route:defaultAction', () ->
      require [
        'views/home/home'
      ], (HomePage) ->
        homePage = app.setView('#page', new HomePage)
        homePage.render()

    Backbone.history.start
      silent: options?.silent || false

    return router
