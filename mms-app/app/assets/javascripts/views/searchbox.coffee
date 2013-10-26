define [
  'jquery'
  'underscore'
  'backbone'
  'handlebars'
  'text!templates/search_results.html'
], ($, _, Backbone, Handlebars, searchResultsTemplate) ->

  Backbone.View.extend

    render: ->
      facets = {}
      facetLabels =
        conceptType: 'concept'
        datasource: 'datasource'
        dataType: 'data type'
        domain: 'domain'
        objectType: 'obj type'
        securityClassification: 'sec-class'
        tags_name: 'tag'
      facetLabelReverseLookup = _.invert(facetLabels)
      availFacets = []
      $.ajax
        type: 'POST'
        url: 'http://localhost:9200/mms/_search'
        data: '{"query":{"match_all":{}},"facets":{"objectType":{"terms":{"field":"objectType"}},"dataType":{"terms":{"field":"dataType"}},"tags.name":{"terms":{"field":"tags.name"},"nested":"tags"},"domain":{"terms":{"field":"domain"}},"securityClassification":{"terms":{"field":"securityClassification"}},"conceptType":{"terms":{"field":"conceptType"}},"datasource":{"terms":{"field":"datasource"}}}}'
        success: (result) ->
          fs = result.facets
          for name, facet of fs
            if (name.indexOf('.') != -1)
              name = name.replace('.', '_')
            facets[name] = _.map facet.terms, (term, i) ->
              value: term.term
              label: term.term
          availFacets = _.chain(facets).pairs().filter((pair) ->
            pair[1].length
          )
          .map((pair) ->
            key = pair[0]
            facetLabels[key]
          )
          .value()

      template = Handlebars.compile(searchResultsTemplate)

      visualSearch = VS.init
        container: $('.visual_search')
        query: '',
        callbacks:
          search: (query, searchCollection) ->
#            $('#search').removeClass('wide')
#            $('.nav-collapse').show()
            qstart = '{"highlight":{"fields":{"de*":{}}},"query":{"bool":{"must":['
            qend = ']}}}'
            qs = ''
            n = searchCollection.length

            for i in [0...n]
              model = searchCollection.at(i)
              t = model.toJSON()
              if (t.category == 'text')
                qs += '{"query_string":{"query":"' + t.value + '","default_operator":"OR"}}'
              else
                facet = facetLabelReverseLookup[t.category]
                term = t.value; #facets[facet][t.value - 1]
#                qs += '{"term":{"' + facet + '":"' + term.label + '"}}'
                qs += '{"term":{"' + facet + '":"' + term + '"}}'
              qs += ',' if (i < n - 1)

            data = qstart + qs + qend
            $.ajax
              type: 'POST'
              url: 'http://localhost:9200/mms/_search'
              data: data
              success: (result) ->
                hits = _.map result.hits.hits, (hit) ->
                  ret = hit._source
                  highlight = hit.highlight
                  if (highlight)
                    highlights = []
                    for k, v of highlight
                      text = '&hellip; ' + v
                      highlights.push(text)
                    ret['_highlights'] = highlights.join('<br>')
                  return ret

                $('#shade')
                  .height($(document).height())
                  .addClass('on')
                $('#search-results')
                  .html(template({hits: hits}))
                  .animate({top: '39px'}, 250)

          facetMatches: (callback) ->
            callback(_.map(availFacets, (facet) ->
              label: facet
              category: 'facets'
            ))

          valueMatches: (facet, searchTerm, callback) ->
            switch facet
              when 'concept' then callback(facets.conceptType)
              when 'datasource' then callback(facets.datasource)
              when 'data type' then callback(facets.dataType)
              when 'domain'
#                app.domains().done (coll) ->
#                  domain = _.map coll.models, (domain) ->
#                    value: domain.id + ''
#                    label: domain.get('name')
                callback(facets.domain)

              when 'obj type'
#                callback([
#                  {value: '1', label: 'Business Term'},
#                  {value: '1', label: 'Dataset'},
#                  {value: '1', label: 'Column'}
#                ])
                callback(facets.objectType)

              when 'sec-class' then callback(facets.securityClassification)
              when 'tag'
#                app.tags().done (coll) ->
#                  tags = _.map coll.models, (tag) ->
#                    value: tag.id + ''
#                    label: tag.get('name')
                callback(facets.tags_name)

      closeSearchResults = ->
        visualSearch.searchBox.value('')
        $('#search-results').animate({top: '-500px'}, 250)
        $('#search').removeClass('wide')
        $('.nav-collapse').show()
        $('#user-control').show()
        $('#shade').removeClass('on')

      $('#search').on 'focus', 'input', (event) ->
        $('.nav-collapse').hide()
        $('#user-control').hide()
        $(event.delegateTarget).addClass('wide')

      $(document).on 'click', (event) ->
        target = event.target
        contained = false
        $('#search,#search-results').each ->
          if $.contains(this, target)
            contained = true
            return false # break out of loop
          return true

        contained = contained || ($(target).parents().filter('.VS-interface').length > 0)
        unless contained
          $('#search').removeClass('wide')
          closeSearchResults()

      baseUrl = '/' #'http://localhost:9000/'
      $('#search-results')
      .on 'click', 'li', (event) ->
        event.preventDefault()
        li = $(this)
        uri = li.data('uri')
        location.href = baseUrl + '#/' + uri

        # load the next page before hiding the search results
        setTimeout(->
          closeSearchResults()
        ,1000)
        return false
      .on 'click', 'button.close', (event) ->
        closeSearchResults()
