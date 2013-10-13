define [
  'jquery'
  'backbone'
  'handlebars'
  'cs!events'
  'text!templates/profile.html'
], ($, Backbone, Handlebars, app, profilePageTemplate) ->

  Backbone.View.extend

#    el: '.popover.in .popover-content'

    compiled: Handlebars.compile profilePageTemplate

    initialize: (options) ->
      @userId = options.userId
      @onLoad = options.onLoad
      return this

    render: () ->
      $.ajax "/profile/#{@userId}",
        success: (data) =>
          user = JSON.parse(data)
          @$el.html @compiled
            user: user
            emailHash: md5(user.email)
          if typeof @onLoad == 'function'
            @onLoad.call()
          return
      return this
