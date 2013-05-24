define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!views/glossary/vendor_form'
    'text!templates/glossary/vendors.html'
], ($, Backbone, Handlebars, app, Vm, VendorForm, vendorsPageTemplate) ->
    Backbone.View.extend
        el: '#page'

        events:
            'click #create-vendor': 'create'
            'click .vendor-name': 'edit'
            'click .vendor-edit': 'edit'
            'click #btnDelete': 'remove'

        compiled: Handlebars.compile vendorsPageTemplate

        create: ->
            vendorForm = Vm.create(this, 'VendorForm', VendorForm)
            modal = new Backbone.BootstrapModal
                title: 'New Vendor'
                content: vendorForm
                animate: true
            modal.open()
            return false

        edit: (event) ->
            id = $(event.currentTarget).data('id')
            vendorForm = Vm.create(this, 'VendorForm', VendorForm, {vendorId: id})
            modal = new Backbone.BootstrapModal
                title: 'Edit Vendor'
                content: vendorForm
                animate: true
            modal.open()
            return false

        remove: ->
            deletions = []
            $('.vendor-delete').each ->
                deletions.push($(this).data('id')) if $(this).attr('checked')
            if deletions.length
                $.ajax(
                    type: 'DELETE'
                    url: '/vendors'
                    data: {id: deletions}
                ).done =>
                    _.each deletions, (id) =>
                        @vendors.remove(@vendors.get(id))
                    this.render()
            return false

        render: ->
            app.vendors().done (coll) =>
                @vendors = coll
                @$el.html @compiled
                    vendors: coll.toJSON()
            return this

        clean: ->
            @$el.html('')
