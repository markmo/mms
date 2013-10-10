define [
    'backbone'
], (Backbone) ->
    Backbone.Model.extend
        schema:
            name:
                type: 'Text'

        toString: -> this.get('name')
