define [
    'backbone'
    'cs!models/filter_type'
], (Backbone, filterTypeModel) ->
    Backbone.Collection.extend
        url: ->
            '/filter-types'

        model: filterTypeModel

        comparator: (filterType) ->
            filterType.get('id')
