define [
  'backbone-pageable'
  'models/application'
], (PageableCollection, applicationModel) ->

  PageableCollection.extend

    model: applicationModel

    url: '/glossary/applications'

    mode: 'server'

    state:
      pageSize: 15

    queryParams:
      currentPage: 'p'
      sortKey: 's'
      order: 'o'
