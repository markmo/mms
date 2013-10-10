define [
  'jquery'
  'backbone'
  'handlebars'
  'text!templates/revised_entities.html'
], ($, Backbone, Handlebars, revisedEntitiesPageTemplate) ->

  Backbone.View.extend
    el: '#page'

    compiled: Handlebars.compile revisedEntitiesPageTemplate

    render: (revisionId) ->
      $.ajax "/revisions/#{revisionId}",
        success: (data) =>
          @$el.html @compiled
            revision: {id: data.revisionId, date: data.revisionDate}
            modifiedEntities: data.modifiedEntities
      return this
