define [
    'backbone'
    'cs!models/vendor'
], (Backbone, vendorModel) ->
    Backbone.Collection.extend
        url: '/vendors'
        model: vendorModel
