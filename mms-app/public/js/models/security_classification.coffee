define [
    'backbone'
    'backbone_associations'
], (Backbone) ->
    Backbone.AssociatedModel.extend

        toString: -> this.get('name')
