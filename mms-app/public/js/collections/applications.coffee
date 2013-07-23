define [
    'backbone-pageable'
    'cs!models/application'
], (PageableCollection, applicationModel) ->
    PageableCollection.extend
        model: applicationModel
        url: '/applications'
        mode: 'server'
        state:
            pageSize: 25
        queryParams:
            currentPage: 'p'
            sortKey: 's'
            order: 'o'
