define [
  'underscore'
  'backbone'
], (_, Backbone) ->

  Backbone.View.extend

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
