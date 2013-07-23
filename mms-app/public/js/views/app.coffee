define [
    #'require'
    'jquery'
    'backbone'
    'cs!vm'
    'cs!events'
    'text!templates/layout.html'
    'cs!views/header/header'
    'cs!views/footer/footer'
], (
    #require,
    $, Backbone, Vm, Events, layoutTemplate, HeaderView, FooterView) ->

    Backbone.View.extend
        el: '.content'

        initialize: (options) ->
            @app = options?.app
            return

        render: ->
            $(@el).html layoutTemplate
            #require ['cs!views/header/header'], (HeaderView) =>
            headerView = Vm.create(this, 'HeaderView', HeaderView)
            headerView.render()

            #require ['cs!views/footer/footer'], (FooterView) =>
            footerView = Vm.create(this, 'FooterView', FooterView, {appView: this})
            footerView.render()
            return this
