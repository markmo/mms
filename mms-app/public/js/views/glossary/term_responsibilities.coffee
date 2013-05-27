define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!views/glossary/responsibility_form'
    'text!templates/glossary/term_responsibilities.html'
], ($, Backbone, Handlebars, app, Vm, ResponsibilityForm, termResponsibilitiesPageTemplate) ->
    Backbone.View.extend
        el: '#responsibilities'

        events:
            'click #assign-responsibility': 'assign'
            'click #btnDelete': 'remove'

        compiled: Handlebars.compile termResponsibilitiesPageTemplate

        assign: ->
            responsibilityForm = Vm.create(this, 'ResponsibilityForm', ResponsibilityForm,
                termId: @termId
            )
            modal = new Backbone.BootstrapModal
                title: 'New Responsibility'
                content: responsibilityForm
                animate: true
            modal.open()
            return false

        remove: ->
            deletionIds = []
            $('.responsibility-delete').each ->
                deletionIds.push($(this).data('id')) if $(this).attr('checked')
            if deletionIds.length
                term = @term
                deleted = _.chain(term.get('people').models)
                    .filter((r) -> r.cid in deletionIds)
                    .map((r) ->
                        {
                            termId: term.id
                            personId: r.get('person').id
                            roleId: r.get('stakeholderRole').id
                        }
                    )
                    .value()
                $.ajax(
                    type: 'DELETE'
                    url: '/responsibilities'
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
                people = term.get('people')
                @term = term
                @$el.html @compiled
                    responsibilities: people.map (r) =>
                        {
                            cid: r.cid
                            role: r.get('stakeholderRole').get('name')
                            person: r.get('person').name()
                        }
            return this

        clean: ->
            @$el.html('')
