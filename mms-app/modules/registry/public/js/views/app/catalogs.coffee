define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/app/catalogs.html'
], ($, Backbone, Handlebars, app, catalogsPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        compiled: Handlebars.compile catalogsPageTemplate

        initialize: (options) ->
            @datasourceId = options.datasourceId
            app.datasources().done (datasources) =>
                @datasource = datasources.get(@datasourceId)

        render: ->
            app.catalogs(null, {datasourceId: @datasourceId}).done (catalogs) =>
                @$el.html @compiled
                    datasource: @datasource.toJSON()
                    catalogs: catalogs.toJSON()
            return this
