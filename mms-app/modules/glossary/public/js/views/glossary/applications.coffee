define [
  'cs!framework/gridview'
  'cs!views/glossary/application_form'
  'cs!views/glossary/application_view'
  'cs!collections/applications'
], (GridView, ApplicationForm, ApplicationView, Applications) ->

  GridView.extend

    template: 'glossary/applications'

    initialize: (options = {}) ->
      options.form =
        name: 'Application'
        form: ApplicationForm
        readonly: ApplicationView
      unless options.collection
        collectionNotInjected = true
        options.collection = @collection = new Applications
      this._super(options)
      this.listenTo(@collection, 'sync', this.render)
      @collection.fetch() if collectionNotInjected
