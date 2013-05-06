define [
    'jquery'
    'underscore'
    'backbone'
    'backbone-collectionsubset'
], ($, _, Backbone) ->

    # TODO add cache time to memo

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

        domains: (value, options) ->
            this.collections('domains', value, options)

        terms: (value, options) ->
            this.collections('terms', value, options)

        associations: (value, options) ->
            this.collections('associations', value, options)

        termsByDomain: (domainId, options) ->
            if domainId
                memo = '_terms_' + domainId
                dfd = $.Deferred()
                return dfd.resolve(@[memo]) if @[memo]

                this.terms(null, options).done (terms) =>
                    filtered = terms.subcollection
                        filter: (term) -> term.get('domain')?.id == domainId
                    @[memo] = filtered
                    dfd.resolve(filtered)
                    return

                return dfd

            else
                #dfd.reject('No domainId')
                this.terms(null, options)

        securityClassifications: (value, options) ->
            this.collections('security_classifications', value, options)

        tags: (value, options) ->
            this.collections('tags', value, options)

        collections: (name, value, options) ->
            memo = '_' + name
            if value and value instanceof Backbone.Collection
                @[memo] = value
                return this

            dfd = $.Deferred()

            return dfd.resolve(@[memo]) if @[memo]

            require ['cs!collections/' + name], (Collection) =>
                coll = new Collection(value, options)
                #coll.__guid = this.guid()
                coll.fetch
                    success: =>
                        @[memo] = coll
                        dfd.resolve(coll)
                        this.trigger("change:#{name}")
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

        loadCss: (url) ->
            link = document.createElement('link')
            link.type = 'text/css'
            link.rel = 'stylesheet'
            link.href = url
            document.getElementsByTagName('head')[0].appendChild(link)

        unloadCss: (url) ->
            $('link[href="' + url + '"]').remove()

        reconstructHierarchy: (coll) ->
            # reconstruct tree from just parent associations
            dict = {}
            dict[model.id] = model for model in coll.models
            for id, model of dict
                if model.has('parent')
                    parent = dict[model.get('parent').id]
                    parent.set('children', []) unless parent.has('children')
                    parent.get('children').push(model)
            coll

        convertCollectionToTree: (coll) ->
            dict = {}
            for model in coll.models
                dict[model.id] = {
                    id: model.id
                    label: model.toString()
                    children: []
                }
            for model in coll.models
                node = dict[model.id]
                if model.has('parent')
                    parentNode = dict[model.get('parent').id]
                    node.parent = parentNode
                    parentNode.children.push(node)
            # return just the top level nodes
            (node for id, node of dict when _.isNull(node.parent) or _.isUndefined(node.parent))

        baseUrl: 'http://localhost:9000/#'

    _.extend(new App, Backbone.Events)
