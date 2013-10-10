define [
  'backbone'
  'cs!models/revision'
], (Backbone, revisionModel) ->

  Backbone.Collection.extend
    url: ->
      "/common/#{@entityType}s/#{@entityId}/revisions"

    model: revisionModel

    initialize: (models, options) ->
      @entityType = options.entityType
      @entityId = options.entityId
