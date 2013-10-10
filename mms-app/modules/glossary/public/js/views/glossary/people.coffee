define [
  'cs!framework/gridview'
  'cs!views/glossary/person_form'
  'cs!views/glossary/person_view'
  'cs!collections/people'
], (GridView, PersonForm, PersonView, People) ->

  GridView.extend

    template: 'glossary/people'

    initialize: (options = {}) ->
      options.form =
        name: 'Person'
        form: PersonForm
        readonly: PersonView
      unless options.collection
        collectionNotInjected = true
        options.collection = @collection = new People
      this._super(options)
      this.listenTo(@collection, 'sync', this.render)
      @collection.fetch() if collectionNotInjected
