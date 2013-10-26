define [
  'backbone'
  'models/post'
], (Backbone, Post) ->

  Backbone.Collection.extend

    url: ->
      '/social/posts/' + @entityType + '/' + @entityId

    model: Post

    comparator: (post) ->
      -post.get('datetime')

    initialize: (models, options) ->
      @entityType = options.entityType
      @entityId = options.entityId
