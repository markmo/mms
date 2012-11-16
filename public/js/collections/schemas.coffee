define [
    'jquery',
    'underscore',
    'backbone',
    'cs!models/schema'
], ($, _, Backbone, schemaModel) ->
    Backbone.Collection.extend
        url: ->
            '/data-sources/' + @dataSourceId + '/schemas'

        model: schemaModel

        initialize: (@dataSourceId) ->
