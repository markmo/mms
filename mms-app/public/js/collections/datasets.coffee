define [
    'backbone'
    'cs!models/dataset'
], (Backbone, datasetModel) ->
    Backbone.Collection.extend
        url: ->
            if @namespaceId?
                '/namespaces/' + @namespaceId + '/datasets'
            else
                '/datasets'

        model: datasetModel

        comparator: (dataset) ->
            dataset.get('name')

        initialize: (models, options) ->
            @namespaceId = options.namespaceId
