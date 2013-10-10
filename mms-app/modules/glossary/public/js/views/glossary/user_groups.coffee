define [
  'cs!framework/gridview'
  'cs!views/glossary/user_group_form'
  'cs!views/glossary/user_group_view'
  'cs!collections/user_groups'
], (GridView, GroupForm, GroupView, Groups) ->

  GridView.extend

    template: 'glossary/user_groups'

    initialize: (options = {}) ->
      options.form =
        name: 'User Group'
        form: GroupForm
        readonly: GroupView
      unless options.collection
        collectionNotInjected = true
        options.collection = @collection = new Groups
      this._super(options)
      this.listenTo(@collection, 'sync', this.render)
      @collection.fetch() if collectionNotInjected
