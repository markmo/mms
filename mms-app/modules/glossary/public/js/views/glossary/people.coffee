define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!components/pageable_view'
    'cs!views/glossary/person_form'
    'text!templates/glossary/people.html'
], ($, Backbone, Handlebars, app, Vm, PageableView, PersonForm, peoplePageTemplate) ->
    PageableView.extend
        el: '#page'

        events: ->
            _.extend {}, PageableView.prototype.events,
                'click #create-person': 'create'
                'click .person-name': 'edit'
                'click .person-edit': 'edit'
                'click #btnDelete': 'remove'

        compiled: Handlebars.compile peoplePageTemplate

        create: ->
            personForm = Vm.create(this, 'PersonForm', PersonForm)
            modal = new Backbone.BootstrapModal
                title: 'New Person'
                content: personForm
                animate: true
            modal.open()
            return false

        edit: (event) ->
            id = $(event.currentTarget).data('id')
            personForm = Vm.create(this, 'PersonForm', PersonForm, {personId: id})
            modal = new Backbone.BootstrapModal
                title: 'Edit Person'
                content: personForm
                animate: true
            modal.open()
            return false

        remove: ->
            deletions = []
            $('.person-delete').each ->
                deletions.push($(this).data('id')) if $(this).attr('checked')
            if deletions.length
                $.ajax(
                    type: 'DELETE'
                    url: '/people'
                    data: {id: deletions}
                ).done =>
                    _.each deletions, (id) =>
                        @pageableCollection.remove(@pageableCollection.get(id))
                    this.render()
            return false

        initialize: (options) ->
            @pageableCollection = options.collection
            this.listenTo @pageableCollection, 'sync', this.render

        doRender: ->
            @$el.html @compiled
                pageableCollection: @pageableCollection
            return this

        clean: ->
            this.stopListening()
            @$el.html('')
