define [
    'jquery'
    'backbone'
    'cs!events'
    'cs!models/vendor'
], ($, Backbone, app, Vendor) ->
    Backbone.View.extend

        initialize: (options) ->
            @vendorId = options?.vendorId
            this.on 'ok', =>
                @vendor.set(@form.getValue())
                app.vendors().done (vensors) =>
                    vensors.add(@vendor)
                    @vendor.save null,
                        success: => this.parent.render()

            this.on 'cancel', ->
                #alert 'cancel'

        render: ->
            if @vendorId
                app.vendors().done (vendors) =>
                    vendor = vendors.get(@vendorId)
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
