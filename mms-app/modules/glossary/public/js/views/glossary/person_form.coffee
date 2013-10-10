define [
  'cs!framework/form_view'
  'cs!models/person'
], (FormView, Person) ->

  FormView.extend

    model: Person
