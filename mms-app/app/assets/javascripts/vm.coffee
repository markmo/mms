define [
  'underscore',
  'backbone',
  'events'
], (_, Backbone, app) ->

  views = {}

  getNestedProperty = (object, key) ->
    resolve = (obj, keys) ->
      if obj? and keys.length
        resolve(obj[_.head(keys)], _.tail(keys))
      else obj
    resolve(object, key.split('.'))

  create: (context, name, View, options) ->
    if views.hasOwnProperty(name)
      views[name].stopListening()
      views[name].undelegateEvents()
      views[name].clean() if _.isFunction(views[name]['clean'])

    view = new View(options)
    views[name] = view
    context.childViews = {} unless context.childViews
    context.childViews[name] = view
    view.parentView = context if context instanceof Backbone.View
    view.getProperty = (property) ->
      val = getNestedProperty(view, property)
      if not val? and view.parent?
        view.parentView.getProperty(property)
      else val

    app.trigger 'viewCreated'
    return view
