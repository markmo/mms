define [
  'jquery'
  'underscore'
  'backbone'
  'cs!events'
  'cs!models/access'
  'cs!views/glossary/access_form'
], ($, _, Backbone, app, Access, AccessForm) ->

  Backbone.View.extend

    manage: true

    template: 'glossary/term_access'

    events:
      'click #assign-access': 'assign'
      'click #btnDelete': 'remove'

    assign: ->
      form = new AccessForm
        terms: @collection
        termId: @termId
        model: Access
      modal = new Backbone.BootstrapModal
        title: 'Assign Access Privileges'
        content: form
        animate: true
        okCloses: false
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
        .filter((r) ->
            r.cid in deletionIds)
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
#          app.resetCache('terms')
          this.render()
      return false

    initialize: (options) ->
      @termId = options.termId

    serialize: ->
      term = @term = @collection.get(@termId)
      privileges = term.get('accessPrivileges')
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
