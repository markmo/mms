define [
  'backbone'
  'cs!events'
  'cs!views/glossary/domains'
  'cs!views/glossary/terms'
  'cs!collections/domains'
  'cs!collections/terms'
], (Backbone, app, DomainsView, TermsView, Domains, Terms) ->

  Backbone.View.extend

    manage: true

    initialize: (options = {}) ->
      unless options.domains
        domainsNotInjected = true
        options.domains = new Domains

      @domainsView = new DomainsView
        collection: options.domains

      unless options.terms
        termsNotInjected = true
        options.terms = new Terms
      this.listenTo(options.terms, 'sync', this.render)

      this.setView(new TermsView(options))

      options.domains.fetch() if domainsNotInjected
      options.terms.fetch() if termsNotInjected

    cleanup: ->
      domainsView = @domainsView
      domainsView.undelegateEvents()
      domainsView.$el.empty()
      domainsView.stopListening()
