define [
    'jquery',
    'underscore',
    'backbone',
    'text!templates/footer/footer.html'
], ($, _, Backbone, footerTemplate) ->
    Backbone.View.extend
        el: '.footer'

        render: ->
            $(@el).html(footerTemplate)
            return this
