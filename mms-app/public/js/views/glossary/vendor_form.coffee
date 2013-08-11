define [
  'jquery'
  'backbone'
  'cs!events'
  'cs!models/vendor'
], ($, Backbone, app, Vendor) ->

  Backbone.View.extend

    initialize: (options) ->
      @vendors = options.collection
      @vendorId = options.id
      this.on 'ok', =>
        @vendor.set(@form.getValue())
        @vendors.add(@vendor)
        @vendor.save null,
          success: => this.parentView.render

    render: ->
      if @vendorId
        vendor = @vendors.get(@vendorId)
        this.renderForm(vendor)
      else this.renderForm(new Vendor)

    renderForm: (vendor) ->
      this.cleanForm() if @form
      @form = new Backbone.Form({model: vendor}).render()
      @vendor = vendor
      @$el.html @form.el
      return this

    cleanForm: ->
      @form.remove()
      return
