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
            app.people().done (people) =>
                if @personId
                    @person = people.get(@personId)
                else
                    @person = new Person
                @clean() if @form?
                @form = new Backbone.Form(
                    model: @person
                ).render()
                $(@el).html @form.el
            return this

        clean: ->
            @form.undelegateEvents()
            @form.remove()
            return
