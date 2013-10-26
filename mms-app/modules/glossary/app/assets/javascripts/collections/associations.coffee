define [
  'backbone'
  'models/association'
], (Backbone, Association) ->

  Backbone.Collection.extend

    model: Association

    initialize: (models, options) ->
      @url = "/glossary/terms/#{options.termId}/associations"
