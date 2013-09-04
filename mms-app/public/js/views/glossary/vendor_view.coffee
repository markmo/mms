define [
  'cs!framework/readonly_view'
  'text!templates/glossary/vendor_view.html'
], (ReadonlyView, vendorTemplate) ->
  ReadonlyView.extend

    template: 'vendorTemplate'
