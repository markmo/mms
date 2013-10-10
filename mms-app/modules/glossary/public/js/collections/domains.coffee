define [
    'backbone'
    'cs!models/domain'
], (Backbone, domainModel) ->
    Backbone.Collection.extend
        url: '/glossary/domains'
        model: domainModel
