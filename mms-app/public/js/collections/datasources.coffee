define [
    'backbone'
    'cs!models/datasource'
], (Backbone, datasourceModel) ->
    Backbone.Collection.extend
        url: '/datasources',
        model: datasourceModel
