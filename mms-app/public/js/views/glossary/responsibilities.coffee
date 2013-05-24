define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/glossary/responsibilities.html'
], ($, Backbone, Handlebars, app, responsibilitiesPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        events:
            'submit #responsibilitiesForm': 'update'

        compiled: Handlebars.compile responsibilitiesPageTemplate

        update: (event) ->
            event.preventDefault()
            values = $(event.target).serializeArray()
            re = /role\[(\d+)\]\[(\d+)\]/
            data = []
            for val in values
                match = re.exec(val.name)
                termId = match[1]
                personId = match[2]
                roleId = val.value
                if roleId
                    data.push
                        termId: termId
                        personId: personId
                        roleId: roleId
            $.ajax
                url: '/responsibilities'
                type: 'put'
                contentType: 'application/json'
                data: JSON.stringify(data)
                success: =>
                    this.render().done ->
                        $('#alert')
                            .find('strong').html('Responsibilities have been successfully updated')
                            .parent()
                            .removeClass('alert-error')
                            .addClass('alert-success in')
                            .show()
                error: (jqXHR, textStatus, errorThrown) ->
                    $('#alert')
                        .find('strong').html(errorThrown)
                        .parent()
                        .removeClass('alert-success')
                        .addClass('alert-error in')
                        .show()
            return false

        render: ->
            dfd = $.Deferred()
            app.terms(null, {refresh: true}).done (terms) =>
                app.stakeholderRoles().done (roles) =>
                    app.people().done (people) =>
                        @$el.html @compiled
                            terms: terms.toJSON()
                            roles: roles.toJSON()
                            people: people.toJSON()
                        dfd.resolve()
            return dfd
