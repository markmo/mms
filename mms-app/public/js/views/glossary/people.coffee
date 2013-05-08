define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!views/glossary/person_form'
    'text!templates/glossary/people.html'
], ($, Backbone, Handlebars, app, Vm, PersonForm, peoplePageTemplate) ->
    Backbone.View.extend
        el: '#page'

        events:
            'click #create-person': 'create'
            'click .person-name': 'edit'
            'click .person-edit': 'edit'
            'click #btnDelete': 'remove'

        compiled: Handlebars.compile peoplePageTemplate

        create: ->
            personForm = Vm.create(this, 'PersonForm', PersonForm)
            modal = new Backbone.BootstrapModal(
                title: 'New Person'
                content: personForm
                animate: true
            ).open()
            return false

        edit: (event) ->
            id = $(event.currentTarget).data('id')
            personForm = Vm.create(this, 'PersonForm', PersonForm, {personId: id})
            modal = new Backbone.BootstrapModal(
                title: 'Edit Person'
                content: personForm
                animate: true
            ).open()
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
                        @people.remove(@people.get(id))
                    this.render()
            return false

        render: ->
            app.people().done (coll) =>
                @people = coll
                @$el.html @compiled
                    people: coll.toJSON()
            return this

        clean: ->
            @$el.html('')
