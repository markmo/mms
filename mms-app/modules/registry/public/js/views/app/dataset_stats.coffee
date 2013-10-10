define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/app/dataset_stats.html'
    'lib/analysis-result/analysis-result'
], ($, Backbone, Handlebars, app, datasetStatsPageTemplate, AnalysisResult) ->
    Backbone.View.extend
        el: '#page'

        compiled: Handlebars.compile datasetStatsPageTemplate

        render: (datasetId) ->
            $.ajax(
                url: "/datasets/#{datasetId}/stats"
            )
                .done (data) =>
                    $(@el).html @compiled
                        content: data
                    AnalysisResult.apply()
                    window.drillToDetails = AnalysisResult.drillToDetails
                    return this
            return this
