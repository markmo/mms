define [
    'backbone'
    'cs!models/namespace'
], (Backbone, namespaceModel) ->
    Backbone.Collection.extend
        url: ->
            if @context.name == 'catalog'
                '/catalogs/' + @context.id + '/namespaces'
            else
                '/sandboxes/' + @context.id + '/namespaces'

        model: namespaceModel

        initialize: (models, options) ->
            Backbone.Model.prototype.initialize.apply(this, arguments)
            @context = options.context
            return
