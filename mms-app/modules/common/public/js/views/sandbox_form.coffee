define [
  'cs!framework/form_view'
  'cs!models/sandbox'
], (FormView, Sandbox) ->

  FormView.extend

    model: Sandbox
