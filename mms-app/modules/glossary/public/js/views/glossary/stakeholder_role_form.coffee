define [
  'cs!framework/form_view'
  'cs!models/stakeholder_role'
], (FormView, StakeholderRole) ->

  FormView.extend

    model: StakeholderRole
