define [
    'backbone'
    'cs!models/application'
], (Backbone, applicationModel) ->
    Backbone.Collection.extend
        url: '/applications'
        model: applicationModel
