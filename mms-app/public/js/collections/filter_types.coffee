define [
    'jquery',
    'underscore',
    'backbone',
    'cs!models/filter_type'
], ($, _, Backbone, filterTypeModel) ->
    Backbone.Collection.extend
        url: ->
            '/filter-types'

        model: filterTypeModel

        comparator: (filterType) ->
            filterType.get('name')
