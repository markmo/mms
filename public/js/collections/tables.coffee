define [
    'jquery',
    'underscore',
    'backbone',
    'cs!models/table'
], ($, _, Backbone, tableModel) ->
    Backbone.Collection.extend
        url: ->
            '/schemas/' + @schemaId + '/tables'

        model: tableModel

        comparator: (table) ->
            table.get('name')

        initialize: (@schemaId) ->
