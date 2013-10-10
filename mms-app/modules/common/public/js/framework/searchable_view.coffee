define [
  'jquery'
  'underscore'
  'backbone'
  'cs!events'
  'visualsearch'
], ($, _, Backbone, app) ->

  Backbone.View.extend

    events:
      'click #search-mode .btn': 'startSearch'

    startSearch: (event) ->
      app.searchMode[@collection.url] = $(event.currentTarget).text()
      searchBox = @visualSearch.searchBox
      if searchBox.value()
        setTimeout(->
          searchBox.searchEvent(event)
        ,0)

    initialize: (options) ->
      collection = options.collection
      this.listenTo collection, 'sync', ->
        @spinner.stop() if @spinner
      # Get filter facets
      $.get(collection.url + '/facets').done (data) =>
        facetKeyReverseLookup = {}
        facetKeyLookup = {}
        @facetValues = _.chain(data).pairs().map((t) ->
          reverseLookup = {}

          ###
          this is a workaround since visualsearchwill match the last word only
          as a potential facet instead of the whole label. See line 1119 in
          addTextFacetRemainder
          ###
          t[1].label = t[1].label.replace(/\s+/, '_')

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

      visualSearch = @visualSearch = VS.init
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
              else if category in @facetKeyReverseLookup
                val = @facetKeyReverseLookup[category][value] ? value
                key = @facetKeyLookup[category]
              if _.isString(val)
                switch searchMode
                  when 'Starts with' then val = "#{val}%"
                  when 'in' then val = "%#{val}%"
              [key, val]
            ).object().value()
            app.filter[collection.url] = visualSearch.searchBox.value()
            collection.queryParams = _.extend({}, app.queryParams[collection.url], terms)
            @spinner = app.spin()
            collection.getFirstPage({reset: true})

          facetMatches: (callback) =>
            callback(_.keys(@facetValues))

          valueMatches: (facet, searchTerm, callback) =>
            callback(@facetValues[facet])

          clearSearch: (callback) ->
            collection.queryParams = _.extend({}, app.queryParams[collection.url])
            collection.getFirstPage({reset: true})
            callback()

      ###
      Set the initial state of the filter box
      ###
      $('#search-mode .btn').each (i, btn) ->
        if $(this).text() == (app.searchMode[collection.url] || 'Starts with')
          $(this).addClass('active')
        else
          $(this).removeClass('active')
      visualSearch.searchBox.value(app.filter[collection.url])
