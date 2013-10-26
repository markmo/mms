define [
  'jquery'
  'backbone'
  'events'
  'collections/associations'
  'collections/tags'
  'views/glossary/term'
  'views/glossary/term_form'
  'views/glossary/associations'
  'views/glossary/term_responsibilities'
  'views/glossary/term_access'
  'collections/terms'
], ($, Backbone, app, Associations, Tags, TermView, TermForm, AssociationsView, TermResponsibilitiesView, TermAccessView, Terms) ->

  Backbone.View.extend

    manage: true

    template: 'glossary/term_section'

    initialize: (options) ->
      @termId = options.termId
      @parentTerm = options.parentTerm

      terms = options.terms
      unless terms
        terms = new Terms
        terms.fetch()
      @terms = terms

    showTerm: ->
      term = @terms.get(@termId)
      termView = this.setView '#attributes', new TermView
        collection: @terms
        model: term
      termView.render()
      this.listenTo(termView, 'edit', this.editTerm)

    showRelationships: ->
      associations = new Associations(null, {termId: @termId})
      this.setView '#relationships', new AssociationsView
        terms: @terms
        associations: associations
      associations.fetch()

    showResponsibilities: ->
      responsibilitiesView = this.setView '#responsibilities', new TermResponsibilitiesView
        collection: @terms
        termId: @termId
      responsibilitiesView.render()

    showAccess: ->
      accessView = this.setView '#access', new TermAccessView
        collection: @terms
        termId: @termId
      accessView.render()

    createTerm: ->
      tags = new Tags
      form = @form = new TermForm
        collection: @terms
        tags: tags
      tags.fetch().done ->
        form.render()
      $('ul.nav a[href="#relationships"]').hide()
      this.listenTo form, 'closed', (id) ->
        if id
          @termId = id
          this.showTerm()
      return false

    editTerm: ->
      term = @terms.get(@termId)
      tags = new Tags
      form = @form = new TermForm
        collection: @terms
        model: term
        parentTerm: @parentTerm
        tags: tags
      tags.fetch().done ->
        form.render()
#      this.listenTo form, 'closed', ->
#        app.resetCache('associations', {subjectId: @termId})
#        this.showTerm()
      this.listenTo(form, 'closed', this.showTerm)
      return false

    beforeRender: ->
      $('ul.nav').off()

    afterRender: ->
      $('ul.nav').on 'shown', 'a[data-toggle="tab"]', (event) =>
        switch $(event.target).attr('href')
          when '#relationships' then this.showRelationships()
          when '#responsibilities' then this.showResponsibilities()
          when '#access' then this.showAccess()

    cleanup: ->
      @form?.remove()
      $('ul.nav').off()
