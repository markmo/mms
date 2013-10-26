define [
  'jquery'
  'events'
  'framework/base_view'
  'collections/terms'
  'collections/stakeholder_roles'
  'collections/people'
  'dotindicator'
], ($, app, BaseView, Terms, Roles, People) ->

  BaseView.extend

    manage: true

    template: 'glossary/responsibilities'

    events:
      'submit #responsibilitiesForm': 'update'

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
#          app.resetCache('terms')
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

    initialize: (options = {}) ->
      unless options.terms
        options.terms = new Terms
        options.terms.fetch()

      unless options.roles
        options.roles = new Roles
        options.roles.fetch()

      unless options.people
        options.people = new People
        options.people.fetch()

      this.hasData(options, this.render)

    serialize: ->
      terms: @terms.toJSON()
      roles: @roles.toJSON()
      people: @people.toJSON()

    afterRender: ->
      $('.grid').dotindicator
        target: '.dot-indicator'
