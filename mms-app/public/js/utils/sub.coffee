define [], ->

    initializing = false
    fnTest = if /xyz/.test(-> xyz; return) then /\b_super\b/ else /.*/

    # The base Class implementation (does nothing)
    this.Class = ->

    # Create a new Class that inherits from this class
    Class.sub = (prop) ->
        _super = @prototype

        # Instantiate a base class (but only create the instance,
        # don't run the init constructor)
        initializing = true
        prototype = new this()
        initializing = false

        # Copy the properties over onto the new prototype
        for key of prop

            # Check if we're overwriting an existing function
            prototype[key] =
                if typeof prop[key] == 'function' and
                    typeof _super[key] == 'function' and
                    fnTest.test(prop[key]) then ((key, fn) ->
                        ->
                            tmp = @_super

                            # Add a new ._super() method that is the same method
                            # but on the super-class
                            @_super = _super[key]

                            # The method only need to be bound temporarily, so we
                            # remove it when we're done executing
                            ret = fn.apply(this, arguments)
                            @_super = tmp
                            ret
                    )(key, prop[key])
                else prop[key]

        # The dummy class constructor
        Class = ->
            # All construction is actually done in the init method
            if !initializing and this.init
                this.init.apply(this, arguments)

        # Populate our constructed prototype object
        Class.prototype = prototype

        # Enforce the constructor to be what we expect
        Class.prototype.constructor = Class

        # And make this class extendable
        Class.sub = arguments.callee
        Class
