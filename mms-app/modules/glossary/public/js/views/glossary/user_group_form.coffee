define [
  'cs!framework/form_view'
  'cs!models/user_group'
], (FormView, UserGroupRole) ->

  FormView.extend

    model: UserGroupRole
