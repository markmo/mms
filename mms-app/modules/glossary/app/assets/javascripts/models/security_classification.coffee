define [
  'backbone'
], (Backbone) ->

  Backbone.AssociatedModel.extend

    toString: ->
      this.get('name')
