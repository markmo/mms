define [
  'framework/form_view'
  'models/person'
], (FormView, Person) ->

  FormView.extend

    model: Person
