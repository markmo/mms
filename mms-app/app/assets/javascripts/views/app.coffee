define [
  'backbone'
  'views/header/header'
  'views/footer/footer'
], (Backbone, HeaderView, FooterView) ->

  Backbone.View.extend

    manage: true

    el: '.content'

    template: 'layout'

    initialize: (options) ->
      this.setView('#header', new HeaderView)
      this.setView('#footer', new FooterView)
