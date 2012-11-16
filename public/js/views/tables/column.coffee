define [
    'jquery',
    'underscore',
    'backbone',
	'cs!models/column',
    'text!templates/tables/column.html'
], ($, _, Backbone, Column, columnPageTemplate) ->
    Backbone.View.extend
        el: '.page'

        compiled: _.template columnPageTemplate

        render: (columnId) ->
            columns = @getProperty('app.columns')
            column = columns.get(columnId)
            $(@el).html @compiled
                column: column.toJSON()
            return this
