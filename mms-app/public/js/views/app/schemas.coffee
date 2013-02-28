define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
	'cs!collections/schemas'
    'cs!views/app/posts'
    'text!templates/app/schemas.html'
], ($, Backbone, Handlebars, app, Vm, SchemasCollection, PostsSection, schemasPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        compiled: Handlebars.compile schemasPageTemplate

        initialize: (options) ->
            @context = options.context

        render: (contextId) ->
            @context.id = contextId
            if not @schemas?
                @schemas = new SchemasCollection({}, {context: @context})
                app.schemas(@schemas)
            if @context.name == 'dataSource'
                isDataSource = true
                dfd = app.dataSources()
            else
                dfd = app.sandboxes()
            dfd.done (coll) =>
                ctx = coll.get(contextId)
                @schemas.fetch
                    success: =>
                        content = $('<div class="page-content clearfix"></div>')
                        content.html @compiled
                            context: ctx.toJSON()
                            isDataSource: isDataSource
                            schemas: @schemas.toJSON()
                        $(@el).html content
                        content.animate({top: 0}, 'fast')
                        if isDataSource
                            postsSection = Vm.create(this, 'PostsSection', PostsSection,
                                entityType: 'dataSource'
                                entityId: contextId
                            )
                            postsSection.render()
                        return
                return
            return this
