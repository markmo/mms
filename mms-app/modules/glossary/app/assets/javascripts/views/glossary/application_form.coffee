define [
  'framework/form_view'
  'models/application'
], (FormView, Application) ->

  FormView.extend

    model: Application
