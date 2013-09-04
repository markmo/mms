define [
  'jquery'
  'underscore'
  'backbone'
  'visualsearch'
], ($, _, Backbone) ->

  Backbone.View.extend

    initialize: (options) ->
      collection = @collection = options.collection
      # Get filter facets
      $.get(collection.url + '/facets').done (data) =>
        facetKeyReverseLookup = {}
        facetKeyLookup = {}
        @facetValues = _.chain(data).pairs().map((t) ->
          reverseLookup = {}
          a = [t[1].label, _.chain(t[1].value).map((v) ->
            reverseLookup[v.label] = v.value
            [v.label, v.label]
          ).object().value()]
          facetKeyReverseLookup[t[1].label] = reverseLookup
          facetKeyLookup[t[1].label] = t[0]
          a
        ).object().value()
        @facetKeyReverseLookup = facetKeyReverseLookup
        @facetKeyLookup = facetKeyLookup

    afterRender: ->
      collection = @collection
      queryParams = collection.queryParams

      visualSearch = VS.init
        container: $('.visual_search')
        query: ''
        placeholder: 'Select filter criteria...'
        callbacks:
          search: (query, searchCollection) =>
            searchMode = $('#search-mode .btn.active').text()
            terms = searchCollection.chain().map((model) =>
              category = model.get('category')
              value = model.get('value')
              if category == 'text'
                val = value
                key = '__text'
              else
                val = @facetKeyReverseLookup[category][value] ? value
                key = @facetKeyLookup[category]
              if _.isString(val)
                switch searchMode
                  when 'Starts with' then val = "#{val}%"
                  when 'in' then val = "%#{val}%"
              [key, val]
            ).object().value()
            @filter = visualSearch.searchBox.value()
            _.extend(queryParams, terms)
            collection.fetch()

          facetMatches: (callback) =>
            callback(_.keys(@facetValues))

          valueMatches: (facet, searchTerm, callback) =>
            callback(@facetValues[facet])

          clearSearch: (callback) ->
            collection.queryParams =
              page: 1
              per_page: queryParams.per_page ? 12
              sort_by: queryParams.sort_by
              order: queryParams.order
            collection.fetch({reset: true})
            callback()

      ###
      Set the initial state of the filter box
      ###
      visualSearch.searchBox.value(@filter)
