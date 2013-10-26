###*
* @module backbone_sub
###
define [
  'underscore'
  'backbone'
  'framework/mixin'
], (_, Backbone, mixin) ->

  # The base Class implementation (does nothing)
  Class = ->

  sub = (parent, protoProps, classProps) ->
    initializing = false
    fnTest = if /xyz/.test(-> xyz; return) then /\b_super\b/ else /.*/
    _super = parent.prototype

    ###
    The constructor function for the new subclass is either defined by you
    (the "constructor" property in your `extend` definition), or defaulted
    by us to simply call the parent's constructor.
    ###
    child =
      if protoProps and protoProps.hasOwnProperty('constructor')
      then protoProps.constructor else -> parent.apply(this, arguments)

    # Inherit class (static) properties from parent
    _.extend(child, parent)

    ###
    Instantiate a base class (but only create the instance,
    don't run the init constructor)
    ###
    initializing = true
    Class.prototype = parent.prototype
    child.prototype = new Class()
    initializing = false

    if protoProps
      _.extend(child.prototype, protoProps)

      # Copy the properties over onto the new prototype
      for key of protoProps

        # Check if we're overwriting an existing function
        child.prototype[key] =
          if typeof protoProps[key] == 'function' and
          typeof _super[key] == 'function' and
          fnTest.test(protoProps[key]) then ((name, fn) ->
            ->
              tmp = @_super

              ###
              Add a new ._super() method that is the same method
              but on the superclass
              ###
              @_super = _super[name]

              ###
              The method only needs to be bound temporarily, so we
              remove it when we're done executing
              ###
              ret = fn.apply(this, arguments)
              @_super = tmp
              ret
          )(key, protoProps[key]) else protoProps[key]

      # Add static properties to the constructor function, if supplied
      _.extend(child, classProps) if classProps

      # Enforce the constructor to be what we expect
      child.prototype.constructor = child

      # Set a convenience property in case the parent's prototype is needed later
      child.__super__ = parent.prototype

      return child

  # monkey patch View.extend to include mixins and super function
  Backbone.View.extend = (protoProps, classProps) ->
    child = sub(this, protoProps, classProps)
    mixin(child, child::traits) if _.has(child.prototype, 'traits')
    return child

  return Backbone.View
