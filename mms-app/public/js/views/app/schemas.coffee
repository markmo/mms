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
        el: '.page'

        compiled: Handlebars.compile schemasPageTemplate

        render: (dataSourceId) ->
            dataSource = app.dataSources.get(dataSourceId)
            schemas = new SchemasCollection({}, {dataSourceId: dataSourceId})
            app.schemas = schemas
            schemas.fetch
                success: =>
                    content = $('<div class="page-content clearfix"></div>')
                    content.html @compiled
                        dataSource: dataSource.toJSON()
                        schemas: schemas.toJSON()
                    $(@el).html content
                    content.animate({top: 0}, 'fast')
                    postsSection = Vm.create(this, 'PostsSection', PostsSection)
                    postsSection.render()
                    return
            return this
