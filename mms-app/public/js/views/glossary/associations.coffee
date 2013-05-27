define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/glossary/associations.html'
], ($, Backbone, Handlebars, app, associationsPageTemplate) ->
    Backbone.View.extend
        el: '#relationships'

        compiled: Handlebars.compile associationsPageTemplate

        initialize: (options) ->
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
                app.associations(null, {subjectId: @termId}).done (coll) =>
                    @$el.html @compiled
                        associations: coll.map (a) =>
                            pred = this.getDefinitionMarkup(a.get('predicate'), terms)
                            {
                                object: a.get('object').toString()
                                predicate: pred
                            }
            return this

        clean: ->
            @$el.html('')
