define [
  'backbone'
  'backbone-forms'
], (Backbone, Form) ->

  Backbone.View.extend

    initialize: (options) ->
      @collection = options.collection
      id = options.id
      @item = if id then @collection.get(id) else new this.model()
      @form = new Form({model: @item})
      this.on 'ok', this.ok
      this.on 'cancel', this.cancel
      this.on 'shown', this.shown

    ok: (modal) ->
      item = @item
      if errors = @form.validate()
        modal.preventClose()
      else
        item.set(@form.getValue())
        if item.id
          item.save()
        else
          @collection.add(item)
          item.save()
        this.close()
        modal.close()
      return false

    cancel: (modal) ->
      modal.close()
      this.close()
      return false

    render: ->
      @form.render()
      @$el.html @form.el
      return this

    close: ->
      this.off()
      @form.remove()
