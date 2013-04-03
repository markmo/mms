define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/app/column.html'
], ($, Backbone, Handlebars, app, columnPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        compiled: Handlebars.compile columnPageTemplate

        render: (columnId) ->
            app.columns().done (columns) =>
                column = columns.get(columnId)
                dataset = column.get('dataset')
                namespace = dataset.namespace
                datasource = namespace.datasource
                $(@el).html @compiled
                    datasource: datasource
                    namespace: namespace
                    dataset: dataset
                    column: column.toJSON()
            return this
