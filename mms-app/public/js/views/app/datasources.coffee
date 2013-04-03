define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/app/datasources.html'
], ($, Backbone, Handlebars, app, datasourcesPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        compiled: Handlebars.compile datasourcesPageTemplate

        render: () ->
            app.datasources().done (datasources) =>
                $(@el).html @compiled
                    datasources: datasources.toJSON()
            return this
