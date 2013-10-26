define [
  'backbone'
  'models/security_classification'
], (Backbone, SecurityClassification) ->

  Backbone.Collection.extend

    url: '/glossary/security-classifications'

    model: SecurityClassification
