define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/file/browser.html'
    'lib/jqtree/jquery.cookie'
    'lib/jqtree/tree.jquery'
], ($, Backbone, Handlebars, app, browserPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        compiled: Handlebars.compile(
            """
            [
            {{#foreach files}}
                {
                    label:'{{name}}',
                    id: '{{id}}'{{#if load_on_demand}},
                    load_on_demand: true
                    {{/if}}
                }{{#if $more}},{{/if}}
            {{/foreach}}
            ]
            """
        )

        initialize: (options) ->
            return this

        render: () ->
            $(@el).html(browserPageTemplate)
            app.files().done (files) =>
                data = eval(@compiled {files: files.toJSON()})
                $('#tree1').tree
                    data: data
                return
