define [
    'backbone'
    'cs!events'
    'backbone_associations'
], (Backbone, app) ->
    Backbone.AssociatedModel.extend

        toString: -> this.get('name')
