define [
    'backbone'
    'cs!models/stakeholder_role'
], (Backbone, roleModel) ->
    Backbone.Collection.extend
        url: 'stakeholder-roles'
        model: roleModel
