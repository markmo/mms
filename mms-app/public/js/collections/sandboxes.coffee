define [
    'backbone'
    'cs!models/sandbox'
], (Backbone, sandboxModel) ->
    Backbone.Collection.extend
        url: '/sandboxes'
        model: sandboxModel
