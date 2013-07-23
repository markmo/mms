define [
    'jquery',
    'backbone',
    'text!templates/header/header.html'
], ($, Backbone, headerTemplate) ->
    Backbone.View.extend
        el: '#header'

        render: ->
            @$el.html(headerTemplate)
            return this
