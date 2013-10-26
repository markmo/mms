define [
  'backbone'
  'models/domain'
], (Backbone, Domain) ->

  Backbone.Collection.extend

    url: '/glossary/domains'

    model: Domain
