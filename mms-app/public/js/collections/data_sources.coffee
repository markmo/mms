define [
    'jquery',
    'underscore',
    'backbone',
    'cs!models/data_source'
], ($, _, Backbone, dataSourceModel) ->
    Backbone.Collection.extend
        url: '/data-sources',
        model: dataSourceModel
