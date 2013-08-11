define [
  'jquery'
  'underscore'
  'backbone'
  'cs!events'
  'cs!models/access'
], ($, _, Backbone, app, Access) ->

  Backbone.View.extend

    ok: ->
      @access.set(@form.getValue())
      data = [
        termId: @termId
        groupId: @access.get('userGroup').id
        access: @access.get('access')
      ]
      $.ajax
        url: '/access-privileges/patch'
        type: 'put'
        contentType: 'application/json'
        data: JSON.stringify(data)
        success: =>
          this.render().done =>
            app.resetCache('terms')
            $('#alert')
              .find('strong').html('Access privileges have been successfully updated')
              .parent().removeClass('alert-error').addClass('alert-success in')
              .show()
            this.parent.render()
        error: (jqXHR, textStatus, errorThrown) =>
          $('#alert')
            .find('strong').html(errorThrown)
            .parent().removeClass('alert-success').addClass('alert-error in')
            .show()
          this.parent.render()
      return false

    cancel: ->

    initialize: (options) ->
      @termId = options.termId
      this.on 'ok', _.bind(this.ok, this)
      this.on 'cancel', _.bind(this.cancel, this)

    render: ->
      app.terms().done (terms) =>
        term = terms.get(@termId)
        access = new Access
        access.set('businessTerm', term)
        this.renderForm(access)

    renderForm: (access) ->
      this.cleanForm() if @form
      @form = new Backbone.Form({model: access}).render()
      @access = access
      @$el.html @form.el
      return this

    cleanForm: ->
      @form.remove()
      return
