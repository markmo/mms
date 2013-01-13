define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/app/column.html'
], ($, Backbone, Handlebars, app, columnPageTemplate) ->
    Backbone.View.extend
        el: '.page'

        compiled: Handlebars.compile columnPageTemplate

        render: (columnId) ->
            column = app.columns.get(columnId)
            table = column.get('table')
            schema = table.schema
            dataSource = schema.dataSource
            $(@el).html @compiled
                dataSource: dataSource
                schema: schema
                table: table
                column: column.toJSON()
            return this
