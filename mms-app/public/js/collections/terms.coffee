define [
    'backbone'
    'cs!models/term'
], (Backbone, termModel) ->
    Backbone.Collection.extend
        url: '/terms'
        model: termModel
