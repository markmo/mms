define [
  'backbone'
  'models/term'
  'models/stakeholder_role'
  'models/person'
], (Backbone, Term, Role, Person) ->

  Backbone.AssociatedModel.extend

    relations: [
      {
        type: Backbone.One
        key: 'businessTerm'
        relatedModel: Term
      }
      {
        type: Backbone.One
        key: 'stakeholderRole'
        relatedModel: Role
      }
      {
        type: Backbone.One
        key: 'person'
        relatedModel: Person
      }
    ]

    schema:
      stakeholderRole:
        type: 'ModelSelect'
        collection: 'stakeholderRoles'
        validators: ['required']

      person:
        type: 'ModelSelect'
        collection: 'people'
        validators: ['required']
