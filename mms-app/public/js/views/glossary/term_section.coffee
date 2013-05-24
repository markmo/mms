define [
    'jquery'
    'underscore'
    'backbone'
    'cs!events'
    'cs!vm'
    'cs!views/glossary/term'
    'cs!views/glossary/term_form'
    'cs!views/glossary/associations'
    'text!templates/glossary/term_section.html'
], ($, _, Backbone, app, Vm, TermView, TermForm, AssociationsSection, termSectionTemplate) ->
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
                this.showTerm()
            return

        render: ->
            @$el.html termSectionTemplate
            $('ul.nav').on 'shown', 'a[data-toggle="tab"]', (event) =>
                if $(event.target).attr('href') == '#relationships'
                    this.showRelationships()
                return true
            return this

        clean: ->
            @associationsSection.clean() if @associationsSection
            @$el.html('')
