define [
  'jquery'
  'underscore'
  'backbone'
  'handlebars'
  'cs!events'
  'cs!collections/posts'
  'cs!models/post'
  'cs!views/profile'
  'cs!views/attachments'
  'text!templates/post_reply.html'
  'cs!components/tooltips'
], ($, _, Backbone, Handlebars, app, PostsCollection, Post, ProfileSection, AttachmentsSection, postReplyPageTemplate) ->

  Backbone.View.extend

    manage: true

    el: '#posts-section'

    template: 'posts'

    events:
      'click #btn-submit': 'post'
      'click .btn-reply': 'showReplyForm'
      'click #btn-reply-submit': 'replySubmit'
      'click .popover .close': 'closePopover'
      'click #btn-attachments': 'addAttachments'

    replyCompiled: Handlebars.compile postReplyPageTemplate

    addAttachments: (event) ->
      event.preventDefault()
      $('#attachments').toggle()

    closePopover: (event) ->
      $el = $(event.target)
      popoverId = $el.data('popover-id')
      $("#posts-section .media > a[data-popover-id='#{popoverId}']").popover('hide')

    post: (event) ->
      event.preventDefault()
      message = $('#message').val()
      if message and message.trim().length
        post = new Post
          entityType: @entityType
          entityId: @entityId
          subject: ''
          message: message.trim()
          datetime: new Date()
          userId: app.session.authId()
        @posts.add(post)
        post.save()
#        app.posts(null, {entityType: @entityType, entityId: @entityId}).done (posts) ->
#          posts.add(post)
#          post.save()
      return false

    replySubmit: (event) ->
      event.preventDefault()
      post = new Post
        entityType: @entityType
        entityId: @entityId
        subject: 'Test'
        message: $('#reply-message').val()
        parentId: $('#reply-to-post-id').val()
        datetime: new Date()
        userId: app.session.authId()
      @posts.add(post)
      post.save()
#      app.posts(null, {entityType: @entityType, entityId: @entityId}).done (posts) ->
#        posts.add(post)
#        post.save()
      return false

    showReplyForm: (event) ->
      event.preventDefault()
      $('.reply-body').hide 'slow', ->
        $(this).empty()
      target = $(event.target)
      postId = target.data('id')
      container = target.parent().next()
      replyForm = @replyCompiled
        postId: postId
      container.html replyForm
      container.show 'slow'
      $('.reply-body textarea').css('overflow', 'hidden').autogrow()
      this.delegateEvents()
      return false

    initialize: (options) ->
      @entityType = options.entityType
      @entityId = options.entityId
      unless options.posts
        options.posts = new PostsCollection {},
          entityType: @entityType
          entityId: @entityId
      posts = @posts = options.posts
      this.listenTo(posts, 'sync', this.render)
#      this.listenTo posts, 'sync', (model) =>
#        this.render() if model instanceof Backbone.Model
      posts.fetch()

      @typingSpinner = new Sonic
        width: 16
        height: 5
        stepsPerFrame: 1
        trailLength: 1
        pointDistance: .5
        fps: 3
        padding: 2
#        step: 'fader'
        fillColor: '#666'
        setup: ->
          this._.lineWidth = 20
        path: [
          ['line', 0, 4, 16, 4]
          ['line', 16, 4, 0, 4]
        ]

    beforeRender: ->
      dict = {}
      dict[post.id] = post.toJSON() for post in @posts.models
      for id, post of dict
        if post.parent
          parent = dict[post.parent.id]
          parent.replies = [] unless parent.replies
          parent.replies.push(post)
      posts = @posts = (post for id, post of dict when post.parent == null)

      _.chain(dict).values()
        .filter((post) ->
          !_.isEmpty(post.replies)
        )
        .each((post) ->
          post.replies = _.sortBy(post.replies, (reply) ->
            -reply.datetime
          )
        )
      setNestedIndex = (nodes, childrenProperty, levels = 2) ->
        _setNestedIndex = (children, start, level) ->
          return unless children
          step = Math.pow(10, level)
          _.each children, (node, i) ->
            idx = start + step * (i + 1)
            node.nestedIndex = idx
            _setNestedIndex node[childrenProperty], idx, level -= 1
        _setNestedIndex nodes, 0, levels
      setNestedIndex(posts, 'replies', 2)

#      $('#side-buttons').append('<div id="social-toggle" class="side-button"><a href="#">Social</a></div>')

      posts = _.sortBy(posts, (post) ->
        -post.datetime
      )

    serialize: ->
      posts: @posts
      user: app.session.authUser()

    afterRender: ->
#      $('#side-buttons').affix()

#      updateSidePanelLocation = ->
#        unless $('#side-panel').hasClass('in')
#          $('#side-panel').addClass('notransition')
#          $('#side-panel').css('left', $(window).width() - $('#side-panel').position().left)
#          $('#side-panel').removeClass('notransition')

#      updateSidePanelLocation()
#      $('#side-panel').css('display', 'block')
#      $(window).resize ->
#        updateSidePanelLocation()

      $('#side-panel .side-panel-content-border').css('height', (134 + 113.5 * @posts.length) + 'px')

#      $('#social-toggle').click (e) ->
#        e.preventDefault()
#        $('#side-panel').toggleClass('in')
#        return false

#      $('#social-toggle').hover((e) ->
#        $(this).animate({'right': -40 + 'px'}, 400)
#        $('#side-panel').css('left', ($(window).width() - 40) + 'px')
#        return false
#      , (e) ->
#        $(this).animate({'right': 0}, 400)
#        $('#side-panel').css('left', $(window).width())
#        return false
#      )

      $('.editable textarea').css('overflow', 'hidden').autogrow()
      $('#posts-section')
      .on 'focus', 'textarea', ->
        $(this).parent().addClass('selected')
        return false
      .on 'blur', 'textarea', ->
        ta = $(this)
        ta.parent().removeClass('selected')
        if ta.val().trim() == ''
          ta.parent().removeClass('not-empty')
        else
          ta.parent().addClass('not-empty')
        return false

      $('#posts-section .media > a').popover
        placement: 'left'
        trigger: 'click'
        container: 'body'
        delay:
          show: 500
          hide: 100
        content: =>
          $target = $(this)
          userId = $target.data('popover-id')
          setTimeout(=>
            profileView = this.setView '.popover.in .popover-content', new ProfileSection,
              userId: userId
              onLoad: ->
                targetHeight = $target.height()
                # TODO not sure why $target.popover('tip') shouldn't work
                $popover = $target.data('popover').tip()
                popoverHeight = $popover.height()
                if popoverHeight > targetHeight
                  pct = Math.round(((targetHeight / 2) / popoverHeight) * 100)
                  $popover.find('.arrow').css('top', pct + '%')
            profileView.render()
          ,0) # allow a repaint cycle
          return 'Loading...'

      $('#posts-section .media > a').popover().click (event) ->
        event.preventDefault()

      $('#btn-attachments').popover
        placement: 'bottom'
        container: 'body'
        trigger: 'click'
        delay:
          show: 500
          hide: 100
        content: =>
          setTimeout(=>
            attachmentsView = this.setView '.popover.in .popover-content', new AttachmentsSection,
              popoverId: 'fileupload'
              entityType: @entityType
              entityId: @entityId
            attachmentsView.render()
          ,0) # allow a repaint cycle
          return 'Loading...'

      WS = if window['MozWebSocket'] then MozWebSocket else WebSocket
      chatSocket = new WS('ws://localhost:9000/social/room/chat?username=' + app.session.authEmail())

      Backbone.history.once 'route', ->
        chatSocket.close()

      sendMessage = ->
        chatSocket.send JSON.stringify
          text: $('#message').val()
        #$('#message').val('')

      receiveEvent = (event) =>
        data = JSON.parse(event.data)

        # handle errors
        if data.error
          chatSocket.close()
          $('#onError span').text data.error
          $('#onError').show()
          return
        else
          $('#onChat').show()

        # Update the members list
        $('#members').toggle(data.members.length > 1)
        $('#members-list').empty()
        $(data.members).each ->
          if this.valueOf() != app.session.authEmail()
            $('#members-list').append '<li>' + this + '</li>'

        if data.kind == 'talk'
          # Create the message element
          el = $('<div class="message"><span></span><p></p></div>')
          $('span', el).text data.user
          $('p', el).text data.message
          $(el).addClass data.kind
          $(el).addClass 'me' if data.user == '@username'
          $('#messages').append el

        else if data.kind in ['join', 'quit']
          if data.user != app.session.authEmail()
            $.bootstrapGrowl "#{data.user} #{data.message}",
              type: 'info'
              delay: 4000

        else if data.kind == 'typing'
          if data.user != app.session.authEmail()
            if data.message == 'is typing'
              @typingSpinner.play()
              document.getElementById('typing-spinner').appendChild(@typingSpinner.canvas)
              $('#typing-spinner').append('&nbsp;<small>' + data.user + ' is typing</small>')
            else
              $('#typing-spinner').empty()
              @typingSpinner.stop()

      infocus = false
      startedTyping = false

      handleReturnKey = (event) ->
        if event.charCode == 13 || event.keyCode == 13
          event.preventDefault()
          sendMessage()
          $('#btn-submit').trigger 'click'
        else
          unless startedTyping
            startedTyping = true
            chatSocket.send JSON.stringify
              isTyping: true

      # bind events
      messageBox = @messageBox = $('#message');
      messageBox.keypress(handleReturnKey)

      messageBox.focus ->
        infocus = true
        return

      messageBox.blur ->
        infocus = false
        startedTyping = false
        chatSocket.send JSON.stringify
          isTyping: false
        return

      chatSocket.onmessage = receiveEvent

    cleanup: ->
      @messageBox.unbind()
      $('#posts-section').off()
