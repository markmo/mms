define [
  'backbone-pageable'
  'models/vendor'
], (PageableCollection, Vendor) ->

  PageableCollection.extend

    model: Vendor

    url: '/glossary/vendors'

    mode: 'server'

    state:
      pageSize: 15

    queryParams:
      currentPage: 'p'
      sortKey: 's'
      order: 'o'
