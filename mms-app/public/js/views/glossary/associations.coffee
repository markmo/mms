define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/glossary/associations.html'
], ($, Backbone, Handlebars, app, associationsPageTemplate) ->
    Backbone.View.extend
        el: '#term-associations'

        compiled: Handlebars.compile associationsPageTemplate

        initialize: (options) ->
            @termId = options?.termId

        render: ->
            app.associations(null, {subjectId: @termId}).done (coll) =>
                @$el.html @compiled
                    associations: coll.map (a) ->
                        object: a.get('object').toString()
                        predicate: a.get('predicate')
            return this

        clean: ->
            @$el.html('')
