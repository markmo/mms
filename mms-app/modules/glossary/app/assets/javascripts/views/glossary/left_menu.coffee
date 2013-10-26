define [
  'backbone'
], (Backbone) ->

  Backbone.View.extend

    manage: true

    el: '#left-menu'

    template: 'glossary/left_menu'
