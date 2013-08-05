define [
    'backbone-pageable'
    'cs!models/vendor'
], (PageableCollection, vendorModel) ->
    PageableCollection.extend
        model: vendorModel
        url: '/vendors'
        mode: 'server'
        state:
            pageSize: 15
        queryParams:
            currentPage: 'p'
            sortKey: 's'
            order: 'o'
