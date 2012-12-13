define [
    'jquery',
    'underscore',
    'backbone',
    'cs!events'
], ($, _, Backbone, Events) ->
    views = {}

    getNestedProperty = (object, key) ->
        resolve = (obj, keys) ->
            if obj? and keys.length
                resolve(obj[_.head(keys)], _.tail(keys))
            else obj
        resolve(object, key.split('.'))

    create: (context, name, View, options) ->
        if views[name]?
            views[name].undelegateEvents()
            views[name].clean() unless typeof views[name].clean != 'function'

        view = new View(options)
        views[name] = view
        unless context.children?
            context.children = {}
            context.children[name] = view
        else
            context.children[name] = view
        view.parent = context if context instanceof Backbone.View
        view.getProperty = (property) ->
            val = getNestedProperty(view, property)
            if not val? and view.parent?
                view.parent.getProperty(property)
            else val

        Events.trigger 'viewCreated'
        return view
