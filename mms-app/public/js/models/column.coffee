define [
    'backbone'
], (Backbone) ->
    Backbone.Model.extend
        schema:
            columnIndex: 'Text'
            isAutoinc:
                dataType: 'checkbox'
            isNullable:
                dataType: 'checkbox'
            isUnique:
                dataType: 'checkbox'
            precision: 'Text'
            scale: 'Text'
            defaultValue: 'Text'

        toString: -> this.get('friendlyName') or this.get('name')
