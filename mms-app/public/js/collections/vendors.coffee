define [
    'backbone'
    'cs!models/vendor'
    'backbone_pageable'
], (Backbone, vendorModel) ->
    Backbone.PageableCollection.extend
        model: vendorModel
        url: '/vendors'
        mode: 'infinite'
        state:
            pageSize: 10
        queryParams:
            currentPage: 'p'
            sortKey: 's'
            order: 'o'
