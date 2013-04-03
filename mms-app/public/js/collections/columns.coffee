define [
    'underscore'
    'backbone'
    'cs!models/column'
    'lib/datascience/StringUtils'
], (_, Backbone, columnModel, stringutils) ->
    ColumnsCollection = Backbone.Collection.extend
        url: ->
            '/datasets/' + @datasetId + '/columns'

        model: columnModel

        comparator: (column) ->
            column.get('id')

        initialize: (models, options) ->
            @datasetId = options.datasetId

        filterTypes: (filterTypes) ->
            return this unless filterTypes and filterTypes.length

            return new ColumnsCollection(this.filter (data) ->
                # ~ is the bitwise NOT operator, which inverts the bits of it's
                # operand. In practice it equates to -x-1. Here it works on the
                # basis that we want to check for an index greater than -1, and
                # -(-1)-1 == 0 evaluates to false
                return not filterTypes.some (word) -> ~data.get('name').toLowerCase().indexOf word.toLowerCase()
            )

        fetch: (options) ->
            options || (options = {})
            success = options.success
            options.success = (args...) =>
                _.each(@models, (column) ->
                    column.set('friendlyName', stringutils.convertCamelCaseToSpaced(column.get('name')))
                )
                if success and typeof success == 'function'
                    success.apply(args)
                return
            return Backbone.Collection.prototype.fetch.call(this, options)
    return ColumnsCollection
