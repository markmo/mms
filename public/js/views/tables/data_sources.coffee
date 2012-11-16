define [
    'jquery',
    'underscore',
    'backbone',
	'cs!collections/data_sources',
    'text!templates/tables/data_sources.html'
], ($, _, Backbone, dataSources, dataSourcesPageTemplate) ->
    Backbone.View.extend
        el: '.page'

        compiled: _.template dataSourcesPageTemplate

        render: () ->
            dataSources = @getProperty('app.dataSources')
            $(@el).html @compiled
                dataSources: dataSources.toJSON()
            return this
