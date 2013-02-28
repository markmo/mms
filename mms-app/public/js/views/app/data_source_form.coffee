define [
    'jquery'
    'underscore'
    'backbone'
    'cs!components/form'
    'cs!events'
], ($, _, Backbone, Form, app) ->
    Backbone.View.extend
        el: '#page'

        render: (dataSourceId) ->
            app.dataSources().done (dataSources) =>
                dataSource = dataSources.get(dataSourceId)
                @clean() if @form?
                form = new Form(dataSource).render()

                form.on 'cancel', ->
                    alert 'cancel'

                form.on 'submit', ->
                    alert 'submit'

                @form = form
                $(@el).html form.el
                editor = new EpicEditor
                    basePath: '/assets/js/lib/epiceditor'
                    theme:
                        base: '/themes/base/epiceditor.css'
                        preview: '/themes/preview/preview-light.css'
                        editor: '/themes/editor/epic-light.css'
                editor.load()

            return this

        clean: ->
            @form.undelegateEvents()
            @form.remove()
            return
