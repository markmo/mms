###*
* Mixes in the properties and methods of one or more object prototypes into
* the prototype of the target object (mixee). The mixin behaviour follows these
* rules:
* <ul>
* <li>Properties and methods, which don't exist on the target, get copied to the target.
* <li>Properties with the same name get overwritten
* <li>Objects with the same name get the union of key-values pairs. For example, trait-specific events will be added to the target events hash. This is not recursive.
* <li>Methods of the same name will receive a new method, which chains the original method followed by the trait method.
* </ul>
*
* @module mixin
* @author markmo
###
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
