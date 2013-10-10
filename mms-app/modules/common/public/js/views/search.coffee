define [
  'jquery'
  'underscore'
  'backbone'
  'cs!events'
  'text!templates/search.html'
], ($, _, Backbone, app, searchPageTemplate) ->

  Backbone.View.extend

    el: '#page'

    clean: ->
      $('#facetview_results').off()
      app.unloadCss '/assets/css/jquery-ui-1.8.18.custom/jquery-ui-1.8.18.custom.css'
      app.unloadCss '/assets/css/facetview/facetview.css'
      app.unloadCss '/assets/css/facetview/overrides.css'
      return

    resultTemplate: [
      [
        {
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
      [
        {
          pre: '<blockquote><small style="margin-right: 6px;">'
          field: '_highlights'
          post: '</small></blockquote>'
        }
        {
          pre: '<a href="#" data-toggle="collapse" data-target="#'
          field: '_id'
          post: '"><i class="icon-chevron-down"></i>more</a>'
        }
      ]
      [
        {
          pre: '<div class="collapse" id="'
          field: '_id'
          post: '"><hr>'
        }
        {
          pre: '<small>'
          field: 'definition'
          post: '</small><br>'
        }
        {
          pre: '<small>'
          field: 'description'
          post: '</small><br>'
        }
        {
          pre: "<small>#{app.baseUrl}/"
          field: 'url'
          post: '</small>'
        }
        {
          pre: '</div><!-- end collapsible #'
          field: '_id'
          post: ' -->'
        }
      ]
    ]

    initialize: ->
      @$el.on 'click', '#facetview_results a', (event) ->
        event.preventDefault()
        a = $(event.currentTarget)
        $(a.data('target')).collapse('toggle')
        if (a.text() == 'more')
          a.html('<i class="icon-chevron-up"></i>less')
        else
          a.html('<i class="icon-chevron-down"></i>more')
        return false
      return

    render: ->
      app.loadCss '/assets/css/jquery-ui-1.8.18.custom/jquery-ui-1.8.18.custom.css'
      app.loadCss '/assets/css/facetview/facetview.css'
      app.loadCss '/assets/css/facetview/overrides.css'
      $(@el).html searchPageTemplate
      $('.facet-view-simple').facetview
        search_url: 'http://localhost:9200/mms/_search?'
        search_index: 'elasticsearch'
        result_display: @resultTemplate
        pushstate: false
        nested: 'tags'
        facets: [
          {field: 'objectType', display: 'object type'}
          {field: 'dataType', display: 'data type'}
          {field: 'tags.name', display: 'tags'}
          {field: 'domain', display: 'domain'}
          {field: 'securityClassification', display: 'security classification'}
          {field: 'conceptType', display: 'concept type'}
          {field: 'datasource', display: 'data source'}
        ]
        paging:
          size: 10
      return this
