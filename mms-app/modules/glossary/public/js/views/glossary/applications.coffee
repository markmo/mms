define [
  'cs!framework/gridview'
  'cs!views/glossary/application_form'
  'cs!views/glossary/application_view'
], (GridView, ApplicationForm, ApplicationView) ->

  GridView.extend

    template: 'glossary/applications'

    initialize: (options) ->
      options.form =
        name: 'Application'
        form: ApplicationForm
        readonly: ApplicationView
      this._super(options)
