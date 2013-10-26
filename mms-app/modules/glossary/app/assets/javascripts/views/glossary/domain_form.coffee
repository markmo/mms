define [
  'framework/form_view'
  'models/domain'
], (FormView, Domain) ->

  FormView.extend

    model: Domain
