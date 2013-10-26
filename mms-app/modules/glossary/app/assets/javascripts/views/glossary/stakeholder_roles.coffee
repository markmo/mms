define [
  'framework/gridview'
  'views/glossary/stakeholder_role_form'
  'views/glossary/stakeholder_role_view'
  'collections/stakeholder_roles'
], (GridView, RoleForm, RoleView, Roles) ->

  GridView.extend

    template: 'glossary/stakeholder_roles'

    initialize: (options = {}) ->
      options.form =
        name: 'Stakeholder Role'
        form: RoleForm
        readonly: RoleView
      unless options.collection
        collectionNotInjected = true
        options.collection = @collection = new Roles
      this._super(options)
      this.listenTo(@collection, 'sync', this.render)
      @collection.fetch() if collectionNotInjected
