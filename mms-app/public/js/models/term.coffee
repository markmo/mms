define [
    'backbone'
    'cs!events'
    'cs!collections/tags'
    'cs!models/domain'
    'cs!models/security_classification'
    'cs!models/tag'
    'backbone-associations'
], (Backbone, app, TagsCollection, Domain, SecurityClassification, Tag) ->
    Term = Backbone.AssociatedModel
    Term.extend
        relations: [
            {
                type: Backbone.One
                key: 'domain'
                relatedModel: Domain
            }
            {
                type: Backbone.One
                key: 'parent'
                relatedModel: Term
            }
            {
                type: Backbone.One
                key: 'securityClassification'
                relatedModel: SecurityClassification
            }
            {
                type: Backbone.Many
                key: 'tags'
                relatedModel: Tag
                collectionType: TagsCollection
            }
        ]
        schema:
            name:
                type: 'Text'
                editorClass: 'span5'
                editorAttrs:
                    'data-snap-ignore': true

            definition:
                type: 'TaggableContent'
                editorAttrs:
                    'data-snap-ignore': true

            description:
                type: 'TextArea'
                editorClass: 'span5'
                editorAttrs:
                    'data-snap-ignore': true

            domain:
                type: 'Domains'
                options: (callback) ->
                    app.domains().done (domains) ->
                        array = domains.map (model) ->
                            {val: model.id, label: model.toString()}
                        array.unshift({val: null, label: ''})
                        callback(array)

            parent:
                type: 'Terms'
                options: (callback) ->
                    app.terms().done (terms) ->
                        array = terms.map (model) ->
                            {val: model.id, label: model.toString()}
                        array.unshift({val: null, label: ''})
                        callback(array)

            securityClassification:
                type: 'SecurityClassifications'
                options: (callback) ->
                    app.securityClassifications().done (securityClassifications) ->
                        callback(securityClassifications)

            tags:
                type: 'Tags'

            representations:
                type: 'Columns'
                options: (callback) ->
                    app.columns().done (columns) ->
                        array = columns.map (model) ->
                            {val: model.id, label: model.toString()}
                        array.unshift({val: null, label: ''})
                        callback(array)

            customMetadata:
                type: 'CustomMetadata'

        toString: -> this.get('name')
