define [
    'backbone'
    'cs!models/post'
], (Backbone, postModel) ->
    Backbone.Collection.extend
        url: ->
            '/posts/' + @entityType + '/' + @entityId

        model: postModel

        comparator: (post) ->
            -post.get('datetime')

        initialize: (models, options) ->
            @entityType = options.entityType
            @entityId = options.entityId
