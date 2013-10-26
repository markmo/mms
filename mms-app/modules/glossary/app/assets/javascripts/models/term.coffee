define [
  'backbone'
  'collections/tags'
  'models/access'
  'models/domain'
  'models/responsibility'
  'models/security_classification'
  'models/tag'
], (Backbone, Tags, Access, Domain, Responsibility, SecurityClassification, Tag) ->

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
        collectionType: Tags
      }
      {
        type: Backbone.Many
        key: 'people'
        relatedModel: Responsibility
      }
      {
        type: Backbone.Many
        key: 'accessPrivileges'
        relatedModel: Access
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
          require ['collections/domains'], (Domains) ->
            domains = new Domains
            domains.fetch().done (domains) ->
              array = domains.map (domain) ->
                {val: domain.id, label: domain.toString()}
              array.unshift({val: null, label: ''})
              callback(array)

      parent:
        type: 'Terms'
        options: (callback) ->
          require ['collections/terms'], (Terms) ->
            terms = new Terms
            terms.fetch().done (terms) ->
              array = terms.map (term) ->
                {val: term.id, label: term.toString()}
              array.unshift({val: null, label: ''})
              callback(array)

      securityClassification:
        type: 'SecurityClassifications'
        options: (callback) ->
          require ['collections/security_classifications'], (SecurityClassifications) ->
            securityClassifications = new SecurityClassifications
            securityClassifications.fetch().done (securityClassifications) ->
              callback(securityClassifications)

      tags:
        type: 'Tags'

#      representations:
#        type: 'Columns'
#        options: (callback) ->
#          @columns.fetch().done (columns) ->
#            array = columns.map (column) ->
#              {val: column.id, label: column.toString()}
#            array.unshift({val: null, label: ''})
#            callback(array)

      customMetadata:
        type: 'CustomMetadata'

    initialize: ->
#      @columns = new Columns

    toString: ->
      this.get('name')
