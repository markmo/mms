define [
  'backbone-pageable'
  'cs!models/term'
], (PageableCollection, Term) ->

  PageableCollection.extend
    model: Term
    url: '/glossary/terms'
    mode: 'server'
    state:
      pageSize: 15
    queryParams:
      currentPage: 'p'
      sortKey: 's'
      order: 'o'
