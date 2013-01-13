define [
    'backbone'
    'cs!models/table'
], (Backbone, tableModel) ->
    Backbone.Collection.extend
        url: ->
            '/schemas/' + @schemaId + '/tables'

        model: tableModel

        comparator: (table) ->
            table.get('name')

        initialize: (models, options) ->
            @schemaId = options.schemaId
