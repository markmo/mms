define [
  'cs!components/m_view'
  'cs!components/pageable_view'
  'cs!components/crud_view'
  'cs!views/glossary/user_group_form'
  'text!templates/glossary/user_groups.html'
], (MView, Pageable, Crud, GroupForm, groupsTemplate) ->

  MView.extend
    mixins: [Pageable, Crud]

    template: groupsTemplate

    initialize: (options) ->
      this._super(options)
      this.hasData(options, this.render)
      options.collection = options.groups
      options.form =
        name: 'User Group'
        form: GroupForm
        url: '/usergroups'

    render: ->
      @$el.html @compiled
        pageableCollection: @applications
      return this
