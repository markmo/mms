define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/app/data_sources.html'
], ($, Backbone, Handlebars, app, dataSourcesPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        compiled: Handlebars.compile dataSourcesPageTemplate

        render: () ->
            app.dataSources().done (dataSources) =>
                $(@el).html @compiled
                    dataSources: dataSources.toJSON()
            return this
