define [
    'backbone'
    'cs!models/catalog'
], (Backbone, catalogModel) ->
    Backbone.Collection.extend
        url: -> 'datasources/' + @datasourceId + '/catalogs'

        model: catalogModel

        initialize: (models, options) ->
            Backbone.Model.prototype.initialize.apply(this, arguments)
            @datasourceId = options.datasourceId
            return
