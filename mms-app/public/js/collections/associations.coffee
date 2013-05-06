define [
    'backbone'
    'cs!models/association'
], (Backbone, associationModel) ->
    Backbone.Collection.extend
        url: ->
            '/associations/' + @subjectId

        model: associationModel

        initialize: (models, options) ->
            @subjectId = options?.subjectId
