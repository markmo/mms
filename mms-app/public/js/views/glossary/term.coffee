define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!utils/json-viewer',
    'text!templates/glossary/term_view.html'
#    'annotator'
], ($, Backbone, Handlebars, app, JsonViewer, termPageTemplate) ->
    Backbone.View.extend

        el: '#attributes'

        events:
            'click #btnCancel': 'cancel'
            'click #btnEdit': 'edit'

        compiled: Handlebars.compile termPageTemplate

        edit: ->
            this.clean();
            this.trigger('edit')

        cancel: ->
            this.clean();
            this.trigger('closed')

        initialize: (options) ->
            app.loadCss '/assets/css/annotator/annotator.min.css'
            @termId = options?.termId

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
            val

        render: ->
            app.terms().done (terms) =>
                term = terms.get(@termId)
                defn = this.getDefinitionMarkup(term.get('definition'), terms)
                @$el.html @compiled
                    term: term.toJSON()
                    defn: defn
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
#                @$el.annotator()
            return this

        getSchema: (schema) ->
            if schema.hasOwnProperty('schema') then schema.schema
            else schema

        clean: ->
            @$el.html('');
            app.unloadCss '/assets/css/annotator/annotator.min.css'
