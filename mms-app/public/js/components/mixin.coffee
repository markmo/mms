define [], ->

  isArray = Array.isArray || (obj) ->
    Object::toString.call(obj) == '[object Array]'

  extend = (obj) ->
    for source in Array::slice.call(arguments, 1)
      for prop of source when source
        obj[prop] = source[prop]
    return obj

  wrapped = (origFn, mixnFn) ->
    ->
      args = Array::slice.call(arguments)
      origFn.apply(this, args)
      mixnFn.apply(this, args)
      return this

  (target) ->
    if !isArray(mixins = arguments[1])
      mixins = Array::slice.call(arguments, 1)
    for mixin in mixins
      for own key, val2 of mixin::
        if target::[key]
          val1 = target::[key]
          if typeof val1 == 'function'
            target::[key] = wrapped(val1, val2)
          else if val1 == Object(val1)
            target::[key] = extend({}, val1, val2)
        else
          target::[key] = val2
    return target
