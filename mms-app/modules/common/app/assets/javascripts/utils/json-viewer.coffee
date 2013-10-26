define ['jquery', 'underscore'], ($, _) ->

  toHtml: (schema, json, container) ->
    obj = $.parseJSON(json)
    if (typeof obj is 'string')
      obj = $.parseJSON(obj)

    visit = (schema, node, container) ->
      if _.isArray(node)
        container.append(visitArray(schema, node, $('<ul>')))
      else if _.isObject(node)
        container.append(visitObject(schema, node, $('<div class="object">')))
      else
        container.append(visitValue(node, $('<div class="readonly">')))

    visitObject = (schema, object, container) ->
      for own key, node of object
        property = schema[key]
        title = property?.title or key
        container.append("<label>#{title}</label>")
        type = property?.type
        nextSchema =
          if type == 'object' then property.properties
          else schema
        visit(nextSchema, node, container)
      container

    visitArray = (schema, array, container) ->
      container.append(visit(schema, node, $('<li>'))) for node in array

    visitValue = (value, container) ->
      container.append(value)

    visit(schema, obj, container)
