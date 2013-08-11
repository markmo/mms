define [
  'backbone'
  'cs!models/association'
], (Backbone, associationModel) ->

  Backbone.Collection.extend

    model: associationModel

    initialize: (models, options) ->
      this.url = "/terms/#{options.termId}/associations"
