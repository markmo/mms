define [
    'backbone'
    'cs!models/tag'
], (Backbone, tagModel) ->
    Backbone.Collection.extend
        url: '/glossary/tags'
        model: tagModel
