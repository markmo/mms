define [
    'jquery'
    'underscore'
    'backbone'
    'handlebars'
    'cs!events'
    'text!templates/glossary/access.html'
], ($, _, Backbone, Handlebars, app, accessPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        events:
            'submit #accessForm': 'update'

        compiled: Handlebars.compile accessPageTemplate

        update: (event) ->
            event.preventDefault()
            re = /access\[(\d+)\]\[(\d+)\]\['(c|r|u|d)'\]/
            vals = $(event.target).find('.btn.active').map((i, btn) ->
                match = re.exec(btn.name)
                termId = match[1]
                groupId = match[2]
                access = match[3]
                {
                    termId: termId
                    groupId: groupId
                    access: access
                }
            ).get()
            data = _.chain(vals)
                .groupBy((a) -> a.termId + "|" + a.groupId)
                .pairs()
                .map((p) ->
                    ids = p[0].split('|')
                    access = _.pluck(p[1], 'access').join('')
                    {
                        termId: ids[0]
                        groupId: ids[1]
                        access: access

                    }
                )
                .value()
            $.ajax
                url: '/access-privileges'
                type: 'put'
                contentType: 'application/json'
                data: JSON.stringify(data)
                success: =>
                    this.render().done ->
                        $('#alert')
                            .find('strong').html('Access privileges have been successfully updated')
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
                app.userGroups().done (groups) =>
                    @$el.html @compiled
                        terms: terms.toJSON()
                        groups: groups.toJSON()
                    _.each terms.models, (term) ->
                        _.each term.get('accessPrivileges'), (ap) ->
                            _.each ap.access.split(''), (a) ->
                                $("#access-#{term.id}-#{ap.userGroup.id}-#{a}").button('toggle')
                    dfd.resolve()
            return dfd
