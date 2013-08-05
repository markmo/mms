define [
    'backbone-pageable'
    'cs!models/term'
], (PageableCollection, termModel) ->
    PageableCollection.extend
        model: termModel
        url: '/terms'
        mode: 'infinite'
        state:
            pageSize: 15
        queryParams:
            currentPage: 'p'
            sortKey: 's'
            order: 'o'
