define [
    'backbone'
    'cs!models/namespace'
], (Backbone, namespaceModel) ->
    Backbone.Collection.extend
        url: ->
            if @context.name == 'datasource'
                '/datasources/' + @context.id + '/namespaces'
            else
                '/sandboxes/' + @context.id + '/namespaces'

        model: namespaceModel

        initialize: (models, options) ->
            @context = options.context
            return this
