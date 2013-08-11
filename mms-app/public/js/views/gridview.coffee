define [
  'jquery'
  'underscore'
  'backbone'
  'handlebars'
  'cs!components/pageable_view'
  'cs!components/crud_view'
  'cs!components/mixin'
], ($, _, Backbone, Handlebars, Pageable, Crud, mixin) ->

  # monkey patch View.extend to include mixins
#  originalExtend = Backbone.View.extend
#
#  extend = (protoProps, classProps) ->
#    clazz = originalExtend.call(this, protoProps, classProps)
#    mixin(clazz, clazz::mixins) if _.has(clazz.prototype, 'mixins')
#    return clazz
#
#  Backbone.View.extend = extend

  Backbone.View.extend
    mixins: [Pageable, Crud]

    el: '#page' # default container element

    initialize: ->
      @compiled = Handlebars.compile @template

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

    close: ->
      this.stopListening()
      @$el.empty()
