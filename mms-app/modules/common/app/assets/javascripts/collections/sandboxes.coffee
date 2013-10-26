define [
  'backbone'
  'models/sandbox'
], (Backbone, sandboxModel) ->

  Backbone.Collection.extend

    url: '/common/sandboxes'

    model: sandboxModel
