define [
  'backbone'
], (Backbone) ->

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
          require ['collections/domains'], (Domains) ->
            domains = new Domains
            domains.fetch().done (domains) ->
              array = domains.map (domain) ->
                {val: domain.id, label: domain.toString()}
              array.unshift({val: null, label: ''})
              callback(array)

    toString: ->
      this.get('name')
