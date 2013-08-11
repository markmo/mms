define [
  'jquery'
  'backbone'
  'handlebars'
  'cs!events'
  'cs!vm'
  'cs!views/glossary/access_form'
  'text!templates/glossary/term_access.html'
], ($, Backbone, Handlebars, app, Vm, AccessForm, termAccessPageTemplate) ->

  Backbone.View.extend

    el: '#access'

    events:
      'click #assign-access': 'assign'
      'click #btnDelete': 'remove'

    compiled: Handlebars.compile termAccessPageTemplate

    assign: ->
      accessForm = Vm.create this, 'AccessForm', AccessForm,
        termId: @termId
      modal = new Backbone.BootstrapModal
        title: 'Assign Access Privileges'
        content: accessForm
        animate: true
      modal.open()
      return false

    remove: ->
      deletionIds = []
      $('.access-delete').each ->
        deletionIds.push($(this).data('id')) if $(this).attr('checked')
      if deletionIds.length
        term = @term
        deleted = _
          .chain(term.get('accessPrivileges').models)
          .filter((r) -> r.cid in deletionIds)
          .map((r) ->
              termId: term.id
              groupId: r.get('userGroup').id
          )
          .value()
        $.ajax(
          type: 'DELETE'
          url: '/access-privileges'
          contentType: 'application/json'
          data: JSON.stringify(deleted)
        ).done =>
          app.resetCache('terms')
          this.render()
      return false

    initialize: (options) ->
      @termId = options.termId

    render: ->
      app.terms().done (terms) =>
        term = terms.get(@termId)
        privileges = term.get('accessPrivileges')
        @term = term
        @$el.html @compiled
          privileges: privileges.map (a) =>
            access = a.get('access')
            if access and access.length
              access = access.toUpperCase().split('')
              accessChanges = ['C', 'R', 'U', 'D'].map (a) ->
                if _.contains(access, a) then a else 'X'
            {
              cid: a.cid
              group: a.get('userGroup').get('name')
              access: accessChanges
            }
      return this

    clean: ->
      @$el.html('')
