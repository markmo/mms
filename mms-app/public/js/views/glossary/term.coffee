define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!utils/json-viewer',
    'text!templates/glossary/term.html'
    'annotator'
], ($, Backbone, Handlebars, app, JsonViewer, termPageTemplate) ->
    Backbone.View.extend

        el: '#term-form'

        compiled: Handlebars.compile termPageTemplate

        initialize: (options) ->
            app.loadCss '/assets/css/annotator/annotator.min.css'
            @termId = options?.termId

        render: ->
            app.terms().done (terms) =>
                term = terms.get(@termId)
                @$el.html @compiled
                    term: term.toJSON()
                @$el.addClass('readonly')
                $.ajax(
                    type: 'GET'
                    url: '/settings'
                ).done((schema) =>
                    JsonViewer.toHtml(this.getSchema(schema), term.get('customMetadata'), $('#custom-metadata'))
                    return
                ).fail((jqXHR, textStatus, errorThrown) ->
                    alert(errorThrown)
                )
                @$el.attr('data-snap-ignore', true)
                @$el.annotator()
            return this

        getSchema: (schema) ->
            if schema.hasOwnProperty('schema') then schema.schema
            else schema

        clean: ->
            app.unloadCss '/assets/css/annotator/annotator.min.css'
