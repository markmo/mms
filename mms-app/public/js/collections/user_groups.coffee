define [
    'backbone-pageable'
    'cs!models/user_group'
], (PageableCollection, groupModel) ->
    PageableCollection.extend
        model: groupModel
        url: '/usergroups'
        mode: 'infinite'
        state:
            pageSize: 2
        queryParams:
            currentPage: 'p'
            sortKey: 's'
            order: 'o'
