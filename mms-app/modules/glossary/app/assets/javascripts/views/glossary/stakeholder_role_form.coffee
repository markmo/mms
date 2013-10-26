define [
  'framework/form_view'
  'models/stakeholder_role'
], (FormView, StakeholderRole) ->

  FormView.extend

    model: StakeholderRole
