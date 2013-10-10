define [
  'backbone'
], (Backbone) ->

  Backbone.AssociatedModel.extend

    schema:
      firstName:
        type: 'Text'
        validators: ['required']
      lastName:
        type: 'Text'
        validators: ['required']
      title:
        type: 'Text'
        validators: ['required']
      email:
        type: 'Text'
        validators: ['required', 'email']
      phone:
        type: 'Text'

    initialize: ->
      Backbone.Model.prototype.initialize.apply(this, arguments)
      this.on 'change', (model, options) ->
        this.set({name: this.name()}, {silent: true})

    get: (attr) ->
      if typeof this[attr] == 'function'
        this[attr]()
      else
        Backbone.Model.prototype.get.call(this, attr)

    name: (value) ->
      if value?
        parts = value.split(/\s+/)
        this.set('firstName', parts[0])
        if parts.length > 1
          this.set('lastName', parts[1])
      else
        name = ''
        if this.has('firstName')
          name += this.get('firstName')
        if this.has('lastName')
          if this.has('firstName')
            name += ' '
          name += this.get('lastName')
        name

    toString: ->
      this.name()
