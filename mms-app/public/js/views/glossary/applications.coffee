define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!views/glossary/application_form'
    'text!templates/glossary/applications.html'
], ($, Backbone, Handlebars, app, Vm, ApplicationForm, applicationsPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        events:
            'click #create-application': 'create'
            'click .application-name': 'edit'
            'click .application-edit': 'edit'
            'click #btnDelete': 'remove'

        compiled: Handlebars.compile applicationsPageTemplate

        create: ->
            applicationForm = Vm.create(this, 'ApplicationForm', ApplicationForm)
            modal = new Backbone.BootstrapModal
                title: 'New Application'
                content: applicationForm
                animate: true
            modal.$el.css('width', '590px')
            modal.open()
            return false

        edit: (event) ->
            id = $(event.currentTarget).data('id')
            applicationForm = Vm.create(this, 'ApplicationForm', ApplicationForm, {applicationId: id})
            modal = new Backbone.BootstrapModal
                title: 'Edit Application'
                content: applicationForm
                animate: true
            modal.$el.css('width', '590px')
            modal.open()
            return false

        remove: ->
            deletions = []
            $('.application-delete').each ->
                deletions.push($(this).data('id')) if $(this).attr('checked')
            if deletions.length
                $.ajax(
                    type: 'DELETE'
                    url: '/applications'
                    data: {id: deletions}
                ).done =>
                    _.each deletions, (id) =>
                        @applications.remove(@applications.get(id))
                    this.render()
            return false

        render: ->
            app.applications().done (coll) =>
                @applications = coll
                @$el.html @compiled
                    applications: coll.toJSON()
            return this

        clean: ->
            @$el.html('')
