define [
    'jquery'
    'underscore'
    'backbone'
    'cs!events'
    'text!templates/app/search.html'
    'facetview'
], ($, _, Backbone, app, searchPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        clean: ->
            app.unloadCss '/assets/css/jquery-ui-1.8.18.custom/jquery-ui-1.8.18.custom.css'
            app.unloadCss '/assets/css/facetview/facetview.css'
            app.unloadCss '/assets/css/facetview/overrides.css'
            return

        resultTemplate: [
            [{
                pre: '<span class="label">'
                field: 'objectType'
                post: '</span> '
            }
            {
                pre: '<strong>'
                field: 'name'
                post: '</strong>'
            }
#            {
#                pre: ' ('
#                field: 'id'
#                post: ')'
#            }
            ]
            [{
                field: 'description'
            }]
            [{
                pre: "<small>#{app.baseUrl}/datasets/"
                field: 'id'
                post: '</small>'
            }]
        ]

        render: ->
            app.loadCss '/assets/css/jquery-ui-1.8.18.custom/jquery-ui-1.8.18.custom.css'
            app.loadCss '/assets/css/facetview/facetview.css'
            app.loadCss '/assets/css/facetview/overrides.css'
            $(@el).html searchPageTemplate
            $('.facet-view-simple').facetview
                search_url: 'http://localhost:9200/play2-elasticsearch/_search?'
                search_index: 'elasticsearch'
                result_display: @resultTemplate
                facets: [
                    {field: 'objectType', display: 'object type'}
                    {field: 'dataType', display: 'data type'}
                ]
                paging:
                    size: 10
            return this
