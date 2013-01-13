define [
    'backbone'
    'cs!models/schema'
], (Backbone, schemaModel) ->
    Backbone.Collection.extend
        url: ->
            '/data-sources/' + @dataSourceId + '/schemas'

        model: schemaModel

        initialize: (models, options) ->
            @dataSourceId = options.dataSourceId
