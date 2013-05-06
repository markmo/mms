define [
    'backbone'
    'cs!models/domain'
], (Backbone, domainModel) ->
    Backbone.Collection.extend
        url: '/domains'
        model: domainModel
