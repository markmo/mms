define [
    'backbone'
], (Backbone) ->
    Backbone.Model.extend
        schema:
            name: 'Text'
            description: 'TextArea'
            url:
                title: 'URL'
                type: 'Text'
            type:
                type: 'Select'
                options: [
                    {val: 'FIL', label: 'File'}
                    {val: 'TAB', label: 'Table'}
                ]

        initialize: ->
            this.on 'change:type', (model, type) ->
                entityType = null
                if type == 'TAB'
                    entityType = 'Table'
                else if type == 'FIL'
                    entityType = 'FlatFile'
                model.set('entityType', entityType)
                return
