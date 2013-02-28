define [
    'jquery',
    'underscore',
    'backbone',
    'text!templates/header/header.html'
], ($, _, Backbone, headerTemplate) ->
    Backbone.View.extend
        el: '#header'

        render: ->
            $(@el).html(headerTemplate)
            return this
