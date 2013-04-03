define [
    'jquery'
    'underscore'
    'backbone'
], ($, _, Backbone) ->

    class App

        datasources: (value, options) ->
            this.collections('datasources', value, options)

        sandboxes: (value, options) ->
            this.collections('sandboxes', value, options)

        namespaces: (value, options) ->
            this.collections('namespaces', value, options)

        datasets: (value, options) ->
            this.collections('datasets', value, options)

        columns: (value, options) ->
            this.collections('columns', value, options)

        files: (value, options) ->
            this.collections('files', value, options)

        collections: (name, value, options) ->
            memo = '_' + name
            if value? and value instanceof Backbone.Collection
                @[memo] = value
                return this

            dfd = $.Deferred()

            return dfd.resolve(@[memo]) if @[memo]?

            require ['cs!collections/' + name], (Collection) =>
                coll = new Collection(value, options)
                #coll.__guid = this.guid()
                coll.fetch
                    success: =>
                        @[memo] = coll
                        dfd.resolve(coll)
                        return
            return dfd

        selectedSandbox: (value) ->
            if value? and value instanceof Sandbox
                @_selectedSandbox = value
                return this

            return @_selectedSandbox if @_selectedSandbox?

            return @_sandboxes.at(0) if @_sandboxes?.length

            return null

        guid: ->
            'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace /[xy]/g, (c) ->
                r = Math.random()*16|0
                v = if c == 'x' then r else (r&0x3|0x8)
                v.toString(16)

    _.extend(new App, Backbone.Events)
