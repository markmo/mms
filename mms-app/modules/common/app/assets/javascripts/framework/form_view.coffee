define [
  'underscore'
  'backbone'
  'backbone-forms'
], (_, Backbone, Form) ->

  Backbone.View.extend

    initialize: (options) ->
      id = options.id
      if id
        @item = @collection.get(id)
        @form = new Form
          model: @item
      else
        @item = new @model()
        @form = new Form
          schema: @item.createSchema || @item.schema
          data: @item.formDefaults || {}
      this.on 'ok', this.ok
      this.on 'cancel', this.cancel
      this.on 'shown', this.shown
      _.bindAll(this, 'onError', 'onSuccess')

    onError: (model, xhr, options) ->
      @item.set(@old)
      errorThrown = xhr.responseText
      if errorThrown.match(/^Models\\/)
        parts = errorThrown.split(':')
        errorThrown = parts[parts.length - 1].trim()
      formError = $('.modal form .form-error')
      if formError.length
        formError.text(errorThrown)
      else
        $('.modal form').prepend('<div class="form-error">' + errorThrown + '</div>')

    onSuccess: (model, response, options) ->
      this.cleanup()
      this.modal.close()

    ok: (modal) ->
      item = @item
      @modal = modal
      if errors = @form.validate()
        # do nothing
      else
        @old = item.toJSON()
        item.set(@form.getValue())
        if item.id
          item.save({}, {success: this.onSuccess, error: this.onError})
        else
          @collection.add(item)
          item.save({}, {success: this.onSuccess, error: this.onError})
      return false

    cancel: (modal) ->
      modal.close()
      this.cleanup()
      return false

    render: ->
      @form.render()
      @$el.html @form.el
      return this

    cleanup: ->
      this.off()
      @form.remove()
