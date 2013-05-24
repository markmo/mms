define [
    'backbone'
    'cs!models/user_group'
], (Backbone, groupModel) ->
    Backbone.Collection.extend
        url: 'usergroups'
        model: groupModel
