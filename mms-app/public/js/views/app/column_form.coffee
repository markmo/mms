define [
    'jquery'
    'underscore'
    'backbone'
    'backbone-forms'
    'cs!events'
], ($, _, Backbone, Form, app) ->
    Backbone.View.extend
        el: '#page'

        render: (columnId) ->
            app.columns().done (columns) =>
                column = columns.get(columnId)
                form = new Form({
                    fieldsets: [
                        legend: column.get('friendlyName')
                        fields: _.keys(column.schema)
                    ]
                    model: column
                }).render()
                $(@el).html form.el
            return this
