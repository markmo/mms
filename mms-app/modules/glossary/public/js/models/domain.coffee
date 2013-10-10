define [
  'backbone'
  'cs!events'
], (Backbone, app) ->

  Domain = Backbone.AssociatedModel
  Domain.extend
    relations: [
      {
        type: Backbone.One
        key: 'parent'
        relatedModel: Domain
      }
    ]
    schema:
      name: 'Text'
      parent:
        type: 'Domains'
        options: (callback) ->
          app.domains().done (domains) ->
            array = domains.map (model) ->
              {val: model.id, label: model.toString()}
            array.unshift({val: null, label: ''})
            callback(array)

    toString: ->
      this.get('name')
