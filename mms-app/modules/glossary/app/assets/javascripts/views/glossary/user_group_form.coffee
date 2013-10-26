define [
  'framework/form_view'
  'models/user_group'
], (FormView, UserGroupRole) ->

  FormView.extend

    model: UserGroupRole
