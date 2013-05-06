define [
    'jquery'
    'underscore'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!components/form'
    'cs!models/term'
    'chosen'
    'select2'
    'jquery-resize'
], ($, _, Backbone, Handlebars, app, Form, Term) ->
    Backbone.View.extend

        el: '#term-form'

        initialize: (options) ->
            @termId = options?.termId
            @parentTerm = options?.parentTerm

        submit: ->
            @term.set(@form.getValue())
            app.terms().done (terms) =>
                terms.add(@term)
                @term.save()
                this.parent.render()
            return

        cancel: ->
            this.clean();
            @$el.html('');
            this.trigger('closed')

        render: ->
            app.loadCss '/assets/css/chosen/chosen.css'
            app.loadCss '/assets/css/select2/select2.css'
            dfd = $.Deferred()
            app.terms().done (terms) =>
                if @termId?
                    @term = terms.get(@termId)
                    @term.set('parent', @parentTerm) if @parentTerm?
                else
                    @term = new Term({parent: @parentTerm})
                this.cleanForm() if @form?
                app.on 'change:domains', =>
                    @$el.find('select[name="domain"]').trigger('liszt:updated')
                app.on 'change:terms', =>
                    @$el.find('select[name="parent"]').trigger('liszt:updated')
                app.on 'change:tags', =>
                    @$el.find('select[name="tags"]').trigger('liszt:updated')
                app.on 'change:columns', =>
                    @$el.find('select[name="representations"]').trigger('liszt:updated')
                form = new Form(@term,
                    title: 'Define Term'
                    template: 'form_with_buttons'
                ).render()

                form.on 'cancel', _.bind(this.cancel, this)

                form.on 'submit', _.bind(this.submit, this)

                @form = form
                @$el.html form.el
                @$el.find('select[name="representations"]')
                    .attr('data-placeholder', 'Select a Column')
                    .chosen
                        no_results_text: 'No results matched'
                        allow_single_deselect: true

#                @$el.find('select[name="tags"]')
#                    .attr('data-placeholder', 'Type one or more tags')
#                    .attr('multiple', '')
#                    .chosen()
                app.tags().done (tags) =>
                    array = tags.map (model) ->
                        {id: model.id, text: model.toString()}
                    @$el.find('input[name="tags"]')
                        .attr('data-placeholder', 'Type one or more tags')
                        .attr('multiple', '')
                        .select2({tags: array})

                @$el.find('select[name="parent"]')
                    .attr('data-placeholder', 'Select the parent term')
                    .chosen
                        no_results_text: 'No results matched'
                        allow_single_deselect: true

                @$el.find('select[name="domain"]')
                    .attr('data-placeholder', 'Select the domain')
                    .chosen
                        no_results_text: 'No results matched'
                        allow_single_deselect: true

                @$el.find('textarea').css('overflow', 'hidden').autogrow()

                $('#term-form').resize ->
                    docHeight = $(document).height()
                    $('.drawers').css('height', docHeight)
                    $('#main').css('height', docHeight)

                dfd.resolve()

                return

            return dfd

        clean: ->
            this.cleanForm()
            app.off 'change:columns'
            app.off 'change:tags'
            app.off 'change:terms'
            app.off 'change:domains'
            $('#term-form').off()
            app.unloadCss '/assets/css/select2/select2.css'
            app.unloadCss '/assets/css/chosen/chosen.css'
            return

        cleanForm: ->
            @form.undelegateEvents()
            @form.remove()
