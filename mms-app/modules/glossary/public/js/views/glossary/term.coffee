define [
  'jquery'
  'backbone'
  'cs!events'
  'cs!utils/json-viewer'
], ($, Backbone, app, JsonViewer) ->

  Backbone.View.extend

    manage: true

    template: 'glossary/term_view'

    events:
      'click #btnCancel': 'cancel'
      'click #btnEdit': 'edit'

    edit: ->
      this.trigger('edit')

    cancel: ->
      this.trigger('closed')

#    initialize: (options) ->
#      app.loadCss '/assets/css/annotator/annotator.min.css'

    getDefinitionMarkup: (val, terms) ->
      if val and val.length
        re = /#\w+|#["}][^"}]+["}]/g
        p = /^#["}]?([^"}]+)["}]?$/
        matches = val.match(re)
        if matches
          for match in matches
            tag = p.exec(match)[1]
            term = terms.findWhere({name: tag})
            if term
              link = '<a href="/#/terms/' + term.id + '">' + term + '</a>'
              val = val.replace(match, link)
      return val

    serialize: ->
      defn = this.getDefinitionMarkup(@model.get('definition'), @collection)
      return {
        term: @model.toJSON()
        defn: defn
      }

    afterRender: ->
      @$el.addClass('readonly')
      @$el.attr('data-snap-ignore', true)
      $.get('/glossary/settings').done((schema) =>
        JsonViewer.toHtml(this.getSchema(schema), @model.get('customMetadata'), $('#custom-metadata'))
      ).fail((jqXHR, textStatus, errorThrown) ->
        alert(errorThrown)
      )

    getSchema: (schema) ->
      if schema.hasOwnProperty('schema') then schema.schema
      else schema

#    cleanup: ->
#      app.unloadCss '/assets/css/annotator/annotator.min.css'
