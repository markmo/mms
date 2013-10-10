define [
    'backbone'
    'cs!models/security_classification'
], (Backbone, securityClassificationModel) ->
    Backbone.Collection.extend
        url: '/glossary/security-classifications'
        model: securityClassificationModel
