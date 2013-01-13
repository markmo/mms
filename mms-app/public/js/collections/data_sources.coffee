define [
    'backbone'
    'cs!models/data_source'
], (Backbone, dataSourceModel) ->
    Backbone.Collection.extend
        url: '/data-sources',
        model: dataSourceModel
