define [
  'backbone'
], (Backbone) ->

  Backbone.View.extend

    manage: true

    template: 'glossary/associations'

    _getDefinitionMarkup: (val, terms) ->
      if val and val.length
        re = /#\w+|#["}][^"}]+["}]/g
        p = /^#["}]?([^"}]+)["}]?$/
        matches = val.match(re)
        if matches
          for match in matches
            tag = p.exec(match)[1]
            term = terms.findWhere({name: tag})
            if term
              link = '<a href="/#/terms/' + term.id + '">' + term + '</a>'
              val = val.replace(match, link)
      return val

    initialize: (options) ->
      @terms = options.terms
      @associations = options.associations
      this.listenTo @associations, 'sync', this.render

    serialize: ->
      associations: @associations.map (a) =>
        pred = this._getDefinitionMarkup(a.get('predicate'), @terms)
        {
          object: a.get('object').toString()
          predicate: pred
        }
