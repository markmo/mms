define [
    'backbone-pageable'
    'cs!models/stakeholder_role'
], (PageableCollection, roleModel) ->
    PageableCollection.extend
        model: roleModel
        url: '/glossary/stakeholder-roles'
        mode: 'server'
        state:
            pageSize: 15
        queryParams:
            currentPage: 'p'
            sortKey: 's'
            order: 'o'
