define [
    'backbone'
], (Backbone) ->
    Backbone.Model.extend
        schema:
            name:
                type: 'Text'
                editorClass: 'span5'
                editorAttrs:
                    'data-snap-ignore': true

            description:
                type: 'TextArea'
                editorClass: 'span5'
                editorAttrs:
                    'data-snap-ignore': true
#            description: 'Markdown'

        toString: -> this.get('name')
