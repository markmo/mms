define [
  'jquery'
  'underscore'
  'backbone'
  'handlebars'
  'backbone.layoutmanager'
], ($, _, Backbone, Handlebars, LayoutManager) ->

  root = '/'

  # TODO add cache time to memo
  class App

    root: root

    filter: {}

    queryParams: {}

    searchMode: {}

    JST = window.JST = window.JST || {}

    LayoutManager.configure

      prefix: 'assets/templates/'

      fetchTemplate: (path) ->
        path = path + '.html'
        if JST[path]
          if !JST[path].__compiled__
            JST[path] = Handlebars.template(JST[path])
            JST[path].__compiled__ = true
          return JST[path]
        done = this.async()
        $.get(root + path, (contents) ->
          done(Handlebars.compile(contents))
        ,'text')

    setView: (selector, view) ->
      $('.main').html(
        '<div style="text-align: center;"><img src="/assets/img/throbber.gif" style="margin-top: 100px;"></div>'
      )
      return this.layout.setView(selector, view)

    hasView: (selector) ->
      return !!(this.layout.getView(selector))

    useLayout: (name, options) ->
      # Enable variable arity by allowing the first argument to be the options
      # object and omitting the name argument.
      options = name if _.isObject(name)

      # Ensure options is an object.
      options = options || {}

      # If a name property was specified use that as the template.
      options.template = name if _.isString(name)

      # Check if a layout already exists. If so, then update the template.
      if @layout
        @layout.template = options.template
      else
        # Create a new Layout with options.
        @layout = new Backbone.Layout(_.extend
          el: '#content'
        ,options)

      # Cache the reference.
      return @layout

    applications: (value, options) ->
      this.collections('applications', value, options)

    associations: (value, options) ->
      this.collections('associations', value, options)

    catalogs: (value, options) ->
      this.collections('catalogs', value, options)

    columns: (value, options) ->
      this.collections('columns', value, options)

    datasets: (value, options) ->
      this.collections('datasets', value, options)

    datasources: (value, options) ->
      this.collections('datasources', value, options)

    domains: (value, options) ->
      this.collections('domains', value, options)

    files: (value, options) ->
      this.collections('files', value, options)

    namespaces: (value, options) ->
      this.collections('namespaces', value, options)

    people: (value, options) ->
      this.collections('people', value, options)

    posts: (value, options) ->
      this.collections('posts', value, options)

    sandboxes: (value, options) ->
      this.collections('sandboxes', value, options)

    securityClassifications: (value, options) ->
      this.collections('security_classifications', value, options)

    stakeholderRoles: (value, options) ->
      this.collections('stakeholder_roles', value, options)

    tags: (value, options) ->
      this.collections('tags', value, options)

    terms: (value, options) ->
      this.collections('terms', value, options)

    termsByDomain: (domainId, options) ->
      if domainId
        memo = '_terms_' + domainId
        dfd = $.Deferred()
        return dfd.resolve(@[memo]) if @[memo] and not options?.refresh

        this.terms(null, options).done (terms) =>
          filtered = terms.subcollection
            filter: (term) ->
              term.get('domain')?.id == domainId
          @[memo] = filtered
          dfd.resolve(filtered)
          return

        return dfd

      else
        #dfd.reject('No domainId')
        this.terms(null, options)

    userGroups: (value, options) ->
      this.collections('user_groups', value, options)

    vendors: (value, options) ->
      this.collections('vendors', value, options)

    resetCache: (name, options) ->
      if name
        memo = '_' + name
        if options and !_.isEmpty(options)
          memo += '_' + _.chain(options).pairs().flatten().value().join('_')
        delete @[memo]

    collections: (name, value, options) ->
      memo = '_' + name
      if options and !_.isEmpty(options)
        memo += '_' + _.chain(options).pairs().flatten().value().join('_')
      if value and value instanceof Backbone.Collection
        @[memo] = value
        return this

      dfd = $.Deferred()

      return dfd.resolve(@[memo]) if @[memo] and not options?.refresh

      require ['collections/' + name], (Collection) =>
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
        r = Math.random() * 16 | 0
        v = if c == 'x' then r else (r & 0x3 | 0x8)
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
          parentNode.children.push(node) if parentNode

      # return just the top level nodes
      (node for id, node of dict when _.isNull(node.parent) or _.isUndefined(node.parent))

    baseUrl: 'http://localhost:9000/#'

  _.extend(new App, Backbone.Events)
