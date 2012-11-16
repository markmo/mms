define [
    'jquery',
    'underscore',
    'backbone',
	'cs!collections/columns',
    'text!templates/tables/columns.html'
], ($, _, Backbone, ColumnsCollection, columnsPageTemplate) ->
    Backbone.View.extend
        compiled: _.template columnsPageTemplate

        initialize: (@el) ->

        render: (tableId) ->
            columns = new ColumnsCollection(tableId)
            app = @getProperty('app')
            app.columns = columns
            columns.fetch
                success: =>
                    $(@el).html @compiled
                        columns: columns.toJSON()
                    return
            return this
