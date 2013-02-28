define [
    'backbone'
    'cs!models/schema'
], (Backbone, schemaModel) ->
    Backbone.Collection.extend
        url: ->
            if @context.name == 'dataSource'
                '/data-sources/' + @context.id + '/schemas'
            else
                '/sandboxes/' + @context.id + '/schemas'

        model: schemaModel

        initialize: (models, options) ->
            @context = options.context
            return this
