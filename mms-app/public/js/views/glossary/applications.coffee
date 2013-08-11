define [
  'cs!views/gridview'
  'cs!views/glossary/application_form'
  'text!templates/glossary/applications.html'
], (GridView, ApplicationForm, applicationsTemplate) ->

  GridView.extend

    template: applicationsTemplate

    initialize: (options) ->
      this._super(options)
      this.hasData(options, this.render)
      options.collection = options.applications
      options.form =
        name: 'Application'
        form: ApplicationForm
        url: '/applications'

    render: ->
      @$el.html @compiled
        pageableCollection: @applications
      return this
