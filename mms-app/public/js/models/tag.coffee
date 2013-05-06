define [
    'backbone'
    'cs!events'
    'backbone-associations'
], (Backbone, app) ->
    Backbone.AssociatedModel.extend
        schema:
            name: 'Text'

        toString: -> this.get('name')
