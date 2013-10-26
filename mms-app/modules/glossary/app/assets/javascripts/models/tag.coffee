define [
  'backbone'
], (Backbone) ->

  Backbone.AssociatedModel.extend

    schema:
      name: 'Text'

    toString: ->
      this.get('name')
