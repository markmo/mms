define [
    'backbone'
    'cs!models/tag'
], (Backbone, tagModel) ->
    Backbone.Collection.extend
        url: '/tags'
        model: tagModel
