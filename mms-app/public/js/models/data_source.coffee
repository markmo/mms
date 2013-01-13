define [
    'backbone'
], (Backbone) ->
    Backbone.Model.extend
        schema:
            name: 'Text'
            shortDescription: 'TextArea'
            longDescription: 'Markdown'
