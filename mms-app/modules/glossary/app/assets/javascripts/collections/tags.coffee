define [
  'backbone'
  'models/tag'
], (Backbone, Tag) ->

  Backbone.Collection.extend

    url: '/glossary/tags'

    model: Tag
