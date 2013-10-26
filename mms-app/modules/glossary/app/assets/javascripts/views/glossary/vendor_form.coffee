define [
  'framework/form_view'
  'models/vendor'
], (FormView, Vendor) ->

  FormView.extend

    model: Vendor
