define [
  'jquery'
  'underscore'
  'cs!events'
  'cs!framework/form_view'
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
          groupId: @access.get('userGroup').id
          access: @access.get('access')
        $.ajax
          url: '/access-privileges/patch'
          type: 'put'
          contentType: 'application/json'
          data: JSON.stringify(data)
          success: =>
#            this.render().done =>
#              app.resetCache('terms')
            $('#alert')
              .find('strong').html('Access privileges have been successfully updated')
              .parent().removeClass('alert-error').addClass('alert-success in')
              .show()
            this.onSuccess()
            this.trigger('success')
          error: (jqXHR, textStatus, errorThrown) =>
            $('#alert')
              .find('strong').html(errorThrown)
              .parent().removeClass('alert-success').addClass('alert-error in')
              .show()
            this.trigger('error')
      return false

    initialize: (options) ->
      this._super(options)
      termId = @termId = options.termId
      term = options.terms.get(termId)
      @item.set('businessTerm', term)
