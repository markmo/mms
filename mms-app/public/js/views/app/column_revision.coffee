define [
    'jquery'
    'backbone'
    'handlebars'
    'text!templates/app/column_revision.html'
], ($, Backbone, Handlebars, columnRevisionPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        compiled: Handlebars.compile columnRevisionPageTemplate

        render: (columnId, revisionId) ->
            $.ajax "/columns/#{columnId}/revisions/#{revisionId}",
                success: (data) =>
                    $(@el).html @compiled
                        revision: {id: data.revisionId, date: data.revisionDate}
                        column: data.column
                    return
            return this
