define [
  'backbone'
  'cs!views/glossary/domains'
  'cs!views/glossary/left_menu'
  'cs!views/glossary/terms'
  'cs!views/posts'
  'cs!collections/domains'
#  'cs!collections/terms'
#  'cs!collections/stakeholder_roles'
#  'cs!collections/people'
], (Backbone, DomainsView, LeftMenuView, TermsView, PostsView, Domains
  , Terms, Roles, People
) ->

  Backbone.View.extend

    manage: true

    initialize: (options = {}) ->
      unless options.domains
#        domainsNotInjected = true
        options.domains = new Domains
        options.domains.fetch()
#      unless options.terms
#        termsNotInjected = true
#        options.terms = new Terms
#      unless options.roles
#        rolesNotInjected = true
#        options.roles = new Roles
#      unless options.people
#        peopleNotInjected = true
#        options.people = new People

      this.setView '#left-drawer', new DomainsView
        collection: options.domains

      this.setView '#left-menu', new LeftMenuView

      this.setView new TermsView(options)

#      this.setView '', new PostsView
#        entityType: 'term'
#        entityId: @termId

#      options.domains.fetch() if domainsNotInjected
#      options.terms.fetch() if termsNotInjected
#      options.roles.fetch() if rolesNotInjected
#      options.people.fetch() if peopleNotInjected
