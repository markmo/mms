define [
  'jquery'
  'underscore'
  'backbone'
  'handlebars'
  'cs!framework/pageable_view'
  'cs!framework/crud_view'
], ($, _, Backbone, Handlebars, Pageable, Crud) ->

  Backbone.View.extend
    traits: [Pageable, Crud]

    el: '#page' # default container element

    initialize: ->
      @compiled = Handlebars.compile @template
      @collection = options.collection

    hasData: (collections, callback) ->
      state = {}

      doCallback = (context) =>
        callback.call(context) if _.every(_.values(state))

      for key, collection of collections
        this[key] = collection
        do (key, collection) =>
          this.listenTo collection, 'sync', =>
            state[key] = true
            doCallback(this)
        state[key] = !!collection.length
      doCallback(this)

    render: ->
      this.preRender() if _.isFunction(this['preRender'])
      $.when(this.doRender()).then this.postRender
      return this

    doRender: ->
      @$el.html @compiled
        pageableCollection: @collection

    close: ->
      this.stopListening()
      @$el.empty()
