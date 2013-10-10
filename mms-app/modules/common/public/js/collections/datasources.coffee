define [
  'backbone'
  'cs!models/datasource'
], (Backbone, datasourceModel) ->

  Backbone.Collection.extend
    url: '/common/datasources',
    model: datasourceModel
