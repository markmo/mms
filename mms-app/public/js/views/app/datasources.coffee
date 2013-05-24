define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!views/app/datasource_form'
    'text!templates/app/datasources.html'
], ($, Backbone, Handlebars, app, Vm, DatasourceForm, datasourcesPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        events:
            'click #create-datasource': 'create'
            'click .datasource-name': 'edit'
            'click .datasource-edit': 'edit'
            'click #btnDelete': 'remove'

        compiled: Handlebars.compile datasourcesPageTemplate

        create: ->
            datasourceForm = Vm.create(this, 'DatasourceForm', DatasourceForm)
            modal = new Backbone.BootstrapModal
                title: 'New Datasource'
                content: datasourceForm
                animate: true
            modal.$el.css('width', '590px')
            modal.open()
            return false

        edit: (event) ->
            id = $(event.currentTarget).data('id')
            datasourceForm = Vm.create(this, 'DatasourceForm', DatasourceForm, {datasourceId: id})
            modal = new Backbone.BootstrapModal
                title: 'Edit Datasource'
                content: datasourceForm
                animate: true
            modal.$el.css('width', '590px')
            modal.open()
            return false

        render: () ->
            app.datasources().done (datasources) =>
                $(@el).html @compiled
                    datasources: datasources.toJSON()
            return this
