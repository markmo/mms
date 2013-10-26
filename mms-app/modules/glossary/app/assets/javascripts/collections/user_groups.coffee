define [
  'backbone-pageable'
  'models/user_group'
], (PageableCollection, Group) ->

  PageableCollection.extend

    model: Group

    url: '/glossary/usergroups'

    mode: 'server'

    state:
      pageSize: 15

    queryParams:
      currentPage: 'p'
      sortKey: 's'
      order: 'o'
