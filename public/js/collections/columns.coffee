define [
    'jquery',
    'underscore',
    'backbone',
    'cs!models/column'
], ($, _, Backbone, columnModel) ->
    Backbone.Collection.extend
        url: ->
            '/tables/' + @tableId + '/columns'

        model: columnModel

        comparator: (column) ->
            column.get('name')

        initialize: (@tableId) ->
