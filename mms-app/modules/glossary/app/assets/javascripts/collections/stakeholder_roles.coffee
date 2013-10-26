define [
  'backbone-pageable'
  'models/stakeholder_role'
], (PageableCollection, Role) ->

  PageableCollection.extend

    model: Role

    url: '/glossary/stakeholder-roles'

    mode: 'server'

    state:
      pageSize: 15

    queryParams:
      currentPage: 'p'
      sortKey: 's'
      order: 'o'
