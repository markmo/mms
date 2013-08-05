define [
    'backbone-pageable'
    'cs!models/person'
], (PageableCollection, personModel) ->
    PageableCollection.extend
        model: personModel
        url: '/people'
        mode: 'server'
        state:
            pageSize: 15
        queryParams:
            currentPage: 'p'
            sortKey: 's'
            order: 'o'
