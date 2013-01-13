define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/app/data_sources.html'
], ($, Backbone, Handlebars, app, dataSourcesPageTemplate) ->
    Backbone.View.extend
        el: '.page'

        compiled: Handlebars.compile dataSourcesPageTemplate

        render: () ->
            $(@el).html @compiled
                dataSources: app.dataSources.toJSON()
            return this
