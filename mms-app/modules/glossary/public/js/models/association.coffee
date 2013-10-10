define [
    'backbone'
    'cs!models/term'
], (Backbone, Term) ->
    Backbone.AssociatedModel.extend
        relations: [
            {
                type: Backbone.One
                key: 'subject'
                relatedModel: Term
            }
            {
                type: Backbone.One
                key: 'object'
                relatedModel: Term
            }
        ]
