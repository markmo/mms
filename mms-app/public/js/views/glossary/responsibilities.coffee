define [
  'jquery'
  'cs!events'
  'cs!components/m_view'
  'text!templates/glossary/responsibilities.html'
], ($, app, MView, responsibilitiesTemplate) ->

  MView.extend

    events:
      'submit #responsibilitiesForm': 'update'

    template: responsibilitiesTemplate

    update: (event) ->
      event.preventDefault()
      values = $(event.target).serializeArray()
      re = /role\[(\d+)\]\[(\d+)\]/
      data = []
      for val in values
        match = re.exec(val.name)
        termId = match[1]
        personId = match[2]
        roleId = val.value
        if roleId
          data.push
            termId: termId
            personId: personId
            roleId: roleId
      $.ajax
        type: 'PUT'
        url: '/responsibilities'
        contentType: 'application/json'
        data: JSON.stringify(data)
        success: =>
          this.render()
          app.resetCache('terms')
          $('#alert')
            .find('strong').html('Responsibilities have been successfully updated')
            .parent().removeClass('alert-error').addClass('alert-success in')
            .show()
        error: (jqXHR, textStatus, errorThrown) ->
          $('#alert')
            .find('strong').html(errorThrown)
            .parent().removeClass('alert-success').addClass('alert-error in')
            .show()
      return false

    initialize: (options) ->
      MView.prototype.initialize.call(this)
      this.hasData(options, this.render)

    render: ->
      this.renderTemplate
        terms: @terms.toJSON()
        roles: @roles.toJSON()
        people: @people.toJSON()
