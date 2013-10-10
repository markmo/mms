define [
  'cs!framework/form_view'
  'cs!models/application'
], (FormView, Application) ->

  FormView.extend

    model: Application
