define [
    'jquery',
    'underscore',
    'backbone',
	'cs!collections/schemas',
    'text!templates/tables/schemas.html'
], ($, _, Backbone, SchemasCollection, schemasPageTemplate) ->
    Backbone.View.extend
        el: '.page'

        compiled: _.template schemasPageTemplate

        render: (dataSourceId) ->
            schemas = new SchemasCollection(dataSourceId)
            schemas.fetch
                success: =>
                    $(@el).html @compiled
                        schemas: schemas.toJSON()
                    return
            return this
