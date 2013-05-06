define [
    'backbone'
    'cs!events'
    'backbone-associations'
], (Backbone, app) ->
    Backbone.AssociatedModel.extend

        toString: -> this.get('name')
