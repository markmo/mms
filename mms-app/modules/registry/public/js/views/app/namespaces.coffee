define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
	'cs!collections/namespaces'
    'cs!views/app/posts'
    'text!templates/app/namespaces.html'
], ($, Backbone, Handlebars, app, Vm, NamespacesCollection, PostsSection, namespacesPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        compiled: Handlebars.compile namespacesPageTemplate

        initialize: (options) ->
            @context = options.context

        render: (contextId) ->
            @context.id = contextId
            if not @namespaces?
                @namespaces = new NamespacesCollection({}, {context: @context})
                app.namespaces(@namespaces)
            if @context.name == 'catalog'
                isCatalog = true
                dfd = app.catalogs()
            else
                dfd = app.sandboxes()
            dfd.done (coll) =>
                ctx = coll.get(contextId)
                if isCatalog
                    datasource = ctx.get('datasource')
                @namespaces.fetch
                    success: =>
                        content = $('<div class="page-content clearfix"></div>')
                        content.html @compiled
                            context: ctx.toJSON()
                            isCatalog: isCatalog
                            namespaces: @namespaces.toJSON()
                            datasource: datasource
                        $(@el).html content
                        content.animate({top: 0}, 'fast')
                        if isCatalog
                            postsSection = Vm.create(this, 'PostsSection', PostsSection,
                                entityType: 'catalog'
                                entityId: contextId
                            )
                            postsSection.render()
                        return
                return
            return this
