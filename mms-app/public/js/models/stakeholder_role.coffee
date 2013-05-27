define [
    'backbone'
], (Backbone) ->
    Backbone.Model.extend
        schema:
            name:
                type: 'Text'
                validators: ['required']

        toString: -> this.get('name')
