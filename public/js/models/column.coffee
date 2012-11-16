define [
    'underscore',
    'backbone'
], (_, Backbone) ->
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
