define [
    'jquery',
    'underscore',
    'backbone',
	'cs!models/column'
], ($, _, Backbone, Column) ->
    Backbone.View.extend
        el: '.page'

        render: (columnId) ->
            columns = @getProperty('app.columns')
            column = columns.get(columnId)

            form = new Backbone.Form({
                fieldsets: [
                    legend: column.get('name')
                    fields: _.keys(column.schema)
                ]
                model: column
            }).render()

            $(@el).html form.el
            return this
