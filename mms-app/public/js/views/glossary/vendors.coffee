define [
    'jquery'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
    'cs!components/pageable_view'
    'cs!views/glossary/vendor_form'
    'text!templates/glossary/vendors.html'
], ($, Backbone, Handlebars, app, Vm, PageableView, VendorForm, vendorsPageTemplate) ->
    PageableView.extend
        el: '#page'

        events: ->
            _.extend {}, PageableView.prototype.events,
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
                        @pageableCollection.remove(@pageableCollection.get(id))
                    this.render()
            return false

        preRender: ->
            if @paginator
                this.stopListening(@paginator)
                @paginator.clean?()

        doRender: ->
            app.vendors().done (coll) =>
                @pageableCollection = coll
                @$el.html @compiled
                    # alternative to sending the pageableCollection
                    page:
                        list: coll.toJSON()
                        sortKey: coll.state.sortKey
                        sortOrder: if coll.state.order == -1 then 'headerSortUp' else 'headerSortDown'

        clean: ->
            this.stopListening()
            @$el.html('')
