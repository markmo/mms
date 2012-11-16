define [
    'jquery',
    'underscore',
    'backbone',
    'text!templates/home/main.html'
], ($, _, Backbone, homePageTemplate) ->
    Backbone.View.extend
        el: '.page',

        render: ->
            $(@el).html homePageTemplate
            return this
