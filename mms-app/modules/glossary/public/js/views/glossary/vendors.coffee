define [
  'cs!framework/gridview'
  'cs!views/glossary/vendor_form'
  'cs!views/glossary/vendor_view'
  'cs!collections/vendors'
], (GridView, VendorForm, VendorView, Vendors) ->

  GridView.extend

    template: 'glossary/vendors'

    initialize: (options = {}) ->
      options.form =
        name: 'Vendor'
        form: VendorForm
        readonly: VendorView
      unless options.collection
        collectionNotInjected = true
        options.collection = @collection = new Vendors
      this._super(options)
      this.listenTo(@collection, 'sync', this.render)
      @collection.fetch() if collectionNotInjected
