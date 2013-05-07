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
            id = $(event.target).attr('id')
            personForm = Vm.create(this, 'PersonForm', PersonForm, {personId: id})
            modal = new Backbone.BootstrapModal(
                title: 'Edit Person'
                content: personForm
                animate: true
            ).open()
            return false

        render: ->
            app.people().done (coll) =>
                @$el.html @compiled
                    people: coll.toJSON()
            return this

        clean: ->
            @$el.html('')
