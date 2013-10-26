define [
  'jquery'
  'underscore'
  'events'
  'framework/form_view'
], ($, _, app, FormView) ->

  FormView.extend

    ok: (modal) ->
      item = @item
      @modal = modal
      if errors = @form.validate()
        # do nothing
      else
        item.set(@form.getValue())
        data =
          termId: @termId
          personId: item.get('person').id
          roleId: item.get('role').id
        $.ajax
          url: '/responsibilities/patch'
          type: 'put'
          contentType: 'application/json'
          data: JSON.stringify(data)
          success: =>
#            this.render().done =>
#              app.resetCache('terms')
            $('#alert')
              .find('strong').html('Responsibilities have been successfully updated')
              .parent()
              .removeClass('alert-error')
              .addClass('alert-success in')
              .show()
            this.onSuccess()
            this.trigger('success')
          error: (jqXHR, textStatus, errorThrown) =>
            $('#alert')
              .find('strong').html(errorThrown)
              .parent()
              .removeClass('alert-success')
              .addClass('alert-error in')
              .show()
            this.trigger('error')
      return false

    initialize: (options) ->
      this._super(options)
      termId = @termId = options.termId
      term = options.terms.get(termId)
      @item.set('businessTerm', term)
