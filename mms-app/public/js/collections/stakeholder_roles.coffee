define [
    'backbone-pageable'
    'cs!models/stakeholder_role'
], (PageableCollection, roleModel) ->
    PageableCollection.extend
        model: roleModel
        url: '/stakeholder-roles'
        mode: 'server'
        state:
            pageSize: 25
        queryParams:
            currentPage: 'p'
            sortKey: 's'
            order: 'o'
