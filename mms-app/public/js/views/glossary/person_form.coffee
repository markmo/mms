define [
    'jquery'
    'backbone'
    'cs!events'
    'cs!models/person'
], ($, Backbone, app, Person) ->
    Backbone.View.extend

        initialize: (options) ->
            @personId = options?.personId
            this.on 'ok', =>
                @person.set(@form.getValue())
                app.people().done (people) =>
                    people.add(@person)
                    @person.save null,
                        success: => this.parent.render()

            this.on 'cancel', ->
                #alert 'cancel'

        render: ->
            if @personId
                app.people().done (people) =>
                    person = people.get(@personId)
                    this.renderForm(person)
            else this.renderForm(new Person)

        renderForm: (person) ->
            this.cleanForm() if @form
            @form = new Backbone.Form({model: person}).render()
            @person = person
            @$el.html @form.el
            return this

        cleanForm: ->
            @form.remove()
            return
