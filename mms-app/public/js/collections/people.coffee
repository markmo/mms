define [
    'backbone'
    'cs!models/person'
], (Backbone, personModel) ->
    Backbone.Collection.extend
        url: '/people'
        model: personModel
