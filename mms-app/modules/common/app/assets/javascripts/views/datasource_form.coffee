define [
  'jquery'
  'underscore'
  'backbone'
  'events'
  'models/datasource'
], ($, _, Backbone, app, Datasource) ->
  Backbone.View.extend

    ok: ->
      @datasource.set(@form.getValue())
      app.datasources().done (datasources) =>
        datasources.add(@datasource)
        @datasource.save null,
          success: =>
            this.parent.render()
      return

    cancel: ->

    initialize: (options) ->
      @datasourceId = options?.datasourceId
      this.on 'ok', _.bind(this.ok, this)
      this.on 'cancel', _.bind(this.cancel, this)

    render: ->
      if @datasourceId
        app.datasources().done (datasources) =>
          datasource = datasources.get(@datasourceId)
          this.renderForm(datasource)
      else this.renderForm(new Datasource)

    renderForm: (datasource) ->
      this.cleanForm() if @form
      @form = new Backbone.Form({model: datasource}).render()
      @datasource = datasource
      @$el.html @form.el
      #            editor = new EpicEditor
      #                basePath: '/assets/js/lib/epiceditor'
      #                theme:
      #                    base: '/themes/base/epiceditor.css'
      #                    preview: '/themes/preview/github.css'
      #                    editor: '/themes/editor/epic-light.css'
      #            editor.load()
      return this

    cleanForm: ->
      @form.remove()
      return
