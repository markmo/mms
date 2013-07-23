define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!components/pageable_view'
    'cs!views/glossary/application_form'
    'text!templates/glossary/applications.html'
], ($, Backbone, Handlebars, app, Vm, PageableView, ApplicationForm, applicationsPageTemplate) ->
    PageableView.extend
        el: '#page'

        events: ->
            _.extend {}, PageableView.prototype.events,
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
                        @pageableCollection.remove(@pageableCollection.get(id))
                    this.render()
            return false

        doRender: ->
            app.applications().done (coll) =>
                @pageableCollection = coll
                @$el.html @compiled
                    pageableCollection: coll

        clean: ->
            this.stopListening()
            @$el.html('')
