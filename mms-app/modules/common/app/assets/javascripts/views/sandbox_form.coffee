define [
  'framework/form_view'
  'models/sandbox'
], (FormView, Sandbox) ->

  FormView.extend

    model: Sandbox
