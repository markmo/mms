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


#    doRender: ->
#      state = @vendors.state
#      @$el.html @compiled
#        page: # alternative to sending the pageableCollection
#          list: @vendors.toJSON()
#          sortKey: state.sortKey
#          order: if state.order == -1 then 'headerSortUp' else 'headerSortDown'
