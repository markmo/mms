define [
  'jquery'
  'underscore'
  'backbone'
  'cs!events'
  'cs!vm'
  'cs!collections/associations'
  'cs!views/glossary/term'
  'cs!views/glossary/term_form'
  'cs!views/glossary/associations'
  'cs!views/glossary/term_responsibilities'
  'cs!views/glossary/term_access'
  'text!templates/glossary/term_section.html'
], ($, _, Backbone, app, Vm, Associations, TermView, TermForm, AssociationsView, TermResponsibilitiesView, TermAccessSection, termSectionTemplate) ->

  Backbone.View.extend

    el: '#term-section'

    initialize: (options) ->
      @termId = options.termId
      @parentTerm = options.parentTerm
      @terms = options.terms
      @roles = options.roles
      @people = options.people

    showTerm: ->
      termView = Vm.create this, 'TermView', TermView,
        termId: @termId
      termView.render()
      termView.once 'edit', _.bind(this.editTerm, this)
      termView.once 'closed', this.clean
      return false

    showRelationships: ->
      associations = new Associations(null, {termId: @termId})
      @associationsSection = Vm.create this, 'AssociationsView', AssociationsView,
        terms: @terms
        associations: associations
      associations.fetch()

    showResponsibilities: ->
      @responsibilitiesSection = Vm.create this, 'TermResponsibilitiesView', TermResponsibilitiesView,
        terms: @terms
        roles: @roles
        people: @people

    showAccess: ->
      @accessSection = Vm.create this, 'TermAccessSection', TermAccessSection,
        termId: @termId
      @accessSection.render()

    createTerm: ->
      termForm = Vm.create this, 'TermForm', TermForm
      termForm.render()
      $('ul.nav a[href="#relationships"]').hide()
      termForm.once 'closed', (id) =>
        if id
          @termId = id
          this.showTerm()
        else this.clean()
      return

    editTerm: ->
      termForm = Vm.create this, 'TermForm', TermForm,
        termId: @termId
        parentTerm: @parentTerm
      termForm.render()
      termForm.once 'closed', =>
        app.resetCache('associations', {subjectId: @termId})
        this.showTerm()
      return false

    render: ->
      @$el.html termSectionTemplate
      $('ul.nav').on 'shown', 'a[data-toggle="tab"]', (event) =>
        if $(event.target).attr('href') == '#relationships'
          this.showRelationships()
        else if $(event.target).attr('href') == '#responsibilities'
          this.showResponsibilities()
        else if $(event.target).attr('href') == '#access'
          this.showAccess()
        return true
      return this

    clean: ->
      @associationsSection.clean() if @associationsSection
      @responsibilitiesSection.clean() if @responsibilitiesSection
      @$el.html('')
