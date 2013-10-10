define [
  'jquery'
  'underscore'
  'backbone'
  'cs!events'
  'cs!models/responsibility'
  'cs!views/glossary/responsibility_form'
], ($, _, Backbone, app, Responsibility, ResponsibilityForm) ->

  Backbone.View.extend

    manage: true

    template: 'glossary/term_responsibilities'

    events:
      'click #assign-responsibility': 'assign'
      'click #btnDelete': 'remove'

    assign: ->
      form = new ResponsibilityForm
        terms: @collection
        termId: @termId
        model: Responsibility
      modal = new Backbone.BootstrapModal
        title: 'New Responsibility'
        content: form
        animate: true
        okCloses: false
      modal.open()
      return false

    remove: ->
      deletionIds = []
      $('.responsibility-delete').each ->
        deletionIds.push($(this).data('id')) if $(this).attr('checked')
      if deletionIds.length
        term = @term
        deleted = _.chain(term.get('people').models)
          .filter((r) ->
            r.cid in deletionIds)
          .map((r) ->
            termId: term.id
            personId: r.get('person').id
            roleId: r.get('stakeholderRole').id
          )
          .value()
        req = $.ajax
          type: 'DELETE'
          url: '/responsibilities'
          contentType: 'application/json'
          data: JSON.stringify(deleted)
#        req.done =>
#          app.resetCache('terms')
#          this.render()
        req.done(this.render)
      return false

    initialize: (options) ->
      @termId = options.termId

    serialize: ->
      term = @term = @collection.get(@termId)
      people = term.get('people')
      responsibilities: people.map (r) =>
        cid: r.cid
        role: r.get('stakeholderRole').get('name')
        person: r.get('person').name()
