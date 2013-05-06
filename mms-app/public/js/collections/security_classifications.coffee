define [
    'backbone'
    'cs!models/security_classification'
], (Backbone, securityClassificationModel) ->
    Backbone.Collection.extend
        url: '/security-classifications'
        model: securityClassificationModel
