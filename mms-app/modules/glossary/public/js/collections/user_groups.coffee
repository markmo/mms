define [
  'backbone-pageable'
  'cs!models/user_group'
], (PageableCollection, groupModel) ->

  PageableCollection.extend

    model: groupModel

    url: '/glossary/usergroups'

    mode: 'server'

    state:
      pageSize: 15

    queryParams:
      currentPage: 'p'
      sortKey: 's'
      order: 'o'
