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

      knownIssues:
        type: 'TextArea'
        editorClass: 'span5'
        editorAttrs:
          'data-snap-ignore': true

      pendingChanges:
        type: 'TextArea'
        editorClass: 'span5'
        editorAttrs:
          'data-snap-ignore': true

      datasource:
        type: 'ModelSelect'
        collection: 'datasources'

      vendor:
        type: 'ModelSelect'
        collection: 'vendors'
