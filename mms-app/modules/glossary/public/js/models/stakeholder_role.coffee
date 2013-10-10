define [
  'backbone'
], (Backbone) ->

  Backbone.AssociatedModel.extend
    schema:
      name:
        type: 'Text'
        validators: ['required']

    toString: ->
      this.get('name')
