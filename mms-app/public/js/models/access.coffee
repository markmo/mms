define [
    'backbone'
    'cs!models/term'
    'cs!models/user_group'
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

            access:
                type: 'Access'