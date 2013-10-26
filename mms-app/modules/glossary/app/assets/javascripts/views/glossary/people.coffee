define [
  'framework/gridview'
  'views/glossary/person_form'
  'views/glossary/person_view'
  'collections/people'
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
