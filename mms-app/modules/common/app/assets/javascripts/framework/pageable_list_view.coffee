define [
  'framework/pageable_view'
], (Pageable) ->

  Pageable.extend

    serialize: ->
      return {collection: @collection.toJSON()}
