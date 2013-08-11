define [
  'cs!views/gridview'
  'cs!views/glossary/vendor_form'
  'text!templates/glossary/vendors.html'
], (GridView, VendorForm, vendorsTemplate) ->

  GridView.extend

    template: vendorsTemplate

    initialize: (options) ->
      this._super(options)
      this.hasData(options, this.render)
      options.collection = options.vendors
      options.form =
        name: 'Vendor'
        form: VendorForm
        url: '/vendors'

    doRender: ->
      state = @vendors.state
      @$el.html @compiled
        page: # alternative to sending the pageableCollection
          list: @vendors.toJSON()
          sortKey: state.sortKey
          order: if state.order == -1 then 'headerSortUp' else 'headerSortDown'
