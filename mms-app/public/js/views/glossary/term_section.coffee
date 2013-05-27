define [
    'jquery'
    'underscore'
    'backbone'
    'cs!events'
    'cs!vm'
    'cs!views/glossary/term'
    'cs!views/glossary/term_form'
    'cs!views/glossary/associations'
    'cs!views/glossary/term_responsibilities'
    'cs!views/glossary/term_access'
    'text!templates/glossary/term_section.html'
], ($, _, Backbone, app, Vm, TermView, TermForm, AssociationsSection, TermResponsibilitiesSection, TermAccessSection, termSectionTemplate) ->
    Backbone.View.extend

        el: '#term-section'

        initialize: (options) ->
            @termId = options?.termId

        showTerm: ->
            termView = Vm.create(this, 'TermView', TermView,
                termId: @termId)
            termView.render()
            termView.once 'edit', _.bind(this.editTerm, this)
            termView.once 'closed', =>
                this.clean()
            return

        showRelationships: ->
            @associationsSection = Vm.create(this, 'AssociationsSection', AssociationsSection,
                termId: @termId
            )
            @associationsSection.render()

        showResponsibilities: ->
            @responsibilitiesSection = Vm.create(this, 'TermResponsibilitiesSection', TermResponsibilitiesSection,
                termId: @termId
            )
            @responsibilitiesSection.render()

        showAccess: ->
            @accessSection = Vm.create(this, 'TermAccessSection', TermAccessSection,
                termId: @termId
            )
            @accessSection.render()

        createTerm: ->
            termForm = Vm.create(this, 'TermForm', TermForm)
            termForm.render()
            $('ul.nav a[href="#relationships"]').hide()
            termForm.once 'closed', =>
                this.showTerm()
            return

        editTerm: ->
            termForm = Vm.create(this, 'TermForm', TermForm,
                termId: @termId
                parentTerm: @parentTerm)
            termForm.render()
            termForm.once 'closed', =>
                app.resetCache('associations', {subjectId: @termId})
                this.showTerm()
            return

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
