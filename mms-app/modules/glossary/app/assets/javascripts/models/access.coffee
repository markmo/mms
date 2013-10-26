define [
  'backbone'
  'models/term'
  'models/user_group'
], (Backbone, Term, UserGroup) ->

  Backbone.AssociatedModel.extend

    relations: [
      {
        type: Backbone.One
        key: 'businessTerm'
        relatedModel: Term
      }
      {
        type: Backbone.One
        key: 'userGroup'
        relatedModel: UserGroup
      }
    ]

    schema:
      userGroup:
        type: 'ModelSelect'
        collection: 'userGroups'
        validators: ['required']

      access:
        type: 'Access'
