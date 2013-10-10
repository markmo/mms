define [
  'cs!framework/form_view'
  'cs!models/vendor'
], (FormView, Vendor) ->

  FormView.extend

    model: Vendor
