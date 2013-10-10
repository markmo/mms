define [
  'jquery'
  'backbone'
  'handlebars'
  'cs!collections/revisions'
  'text!templates/revisions.html'
], ($, Backbone, Handlebars, RevisionsCollection, revisionsPageTemplate) ->

  Backbone.View.extend
    el: '#page'

    compiled: Handlebars.compile revisionsPageTemplate

    render: (entityType, entityId) ->
      revisions = new RevisionsCollection {},
        entityType: entityType
        entityId: entityId
      revisions.fetch
        success: =>
          @$el.html @compiled
            entity: {id: entityId, type: entityType}
            revisions: revisions.toJSON()
      return this
