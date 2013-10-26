define [
  'backbone-pageable'
  'models/person'
], (PageableCollection, Person) ->

  PageableCollection.extend

    model: Person

    url: '/glossary/people'

    mode: 'server'

    state:
      pageSize: 15

    queryParams:
      currentPage: 'p'
      sortKey: 's'
      order: 'o'
