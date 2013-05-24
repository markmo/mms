define [
    'jquery'
    'backbone'
    'handlebars'
    'text!templates/glossary/left_menu.html'
], ($, Backbone, Handlebars, leftMenuPageTemplate) ->
    Backbone.View.extend
        el: '#left-menu'

        compiled: Handlebars.compile leftMenuPageTemplate

        render: ->
            @$el.html @compiled()
            return this
