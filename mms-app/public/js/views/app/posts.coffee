define [
    'jquery'
    'underscore'
    'backbone'
    'handlebars'
    'cs!events'
    'cs!vm'
	'cs!collections/posts'
    'cs!models/post'
    'cs!views/app/profile'
    'cs!views/app/attachments'
    'text!templates/app/posts.html'
    'text!templates/app/post_reply.html'
    'cs!components/tooltips'
], ($, _, Backbone, Handlebars, app, Vm, PostsCollection, Post, ProfileSection, AttachmentsSection, postsPageTemplate, postReplyPageTemplate) ->
    Backbone.View.extend
        el: '#posts-section'

        events:
            'click #btn-submit': 'post'
            'click .btn-reply': 'showReplyForm'
            'click #btn-reply-submit': 'replySubmit'
            'click .popover .close': 'closePopover'
            'click #btn-attachments': 'addAttachments'

        compiled: Handlebars.compile postsPageTemplate

        replyCompiled: Handlebars.compile postReplyPageTemplate

        addAttachments: (event) ->
            event.preventDefault()
            $('#attachments').toggle()

        closePopover: (event) ->
            $el = $(event.target)
            popoverId = $el.data('popover-id')
            $("#posts-section .media > a[data-popover-id='#{popoverId}']").popover('hide')
            return

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
#                app.posts(null, {entityType: @entityType, entityId: @entityId}).done (posts) =>
#                    posts.add(post)
#                    post.save()
            return

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
#            app.posts(null, {entityType: @entityType, entityId: @entityId}).done (posts) ->
#                posts.add(post)
#                post.save()
            return

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
            return

        initialize: (options) ->
            @entityType = options.entityType
            @entityId = options.entityId
            @posts = new PostsCollection({},
                entityType: @entityType
                entityId: @entityId
            )
            this.listenTo @posts, 'sync', (model) =>
                this.render() if model instanceof Backbone.Model

            @typingSpinner = new Sonic
                width: 16
                height: 5

                stepsPerFrame: 1
                trailLength: 1
                pointDistance: .5
                fps: 3
                padding: 2
                #step: 'fader'

                fillColor: '#666'

                setup: ->
                    this._.lineWidth = 20

                path: [
                    ['line', 0, 4, 16, 4]
                    ['line', 16, 4, 0, 4]
                ]

            return this

        render: () ->
            @posts.fetch
                success: =>
                    this._render()
            return this

        _render: () ->
            dict = {}
            dict[post.id] = post.toJSON() for post in @posts.models
            for id, post of dict
                if post.parent?
                    parent = dict[post.parent.id]
                    parent.replies = [] unless parent.replies?
                    parent.replies.push(post)
            posts = (post for id, post of dict when post.parent == null)
            _.chain(dict)
                .values()
                .filter((post) -> !_.isEmpty(post.replies))
                .each((post) ->
                    post.replies = _.sortBy(post.replies, (reply) -> -reply.datetime)
                )
            setNestedIndex = (nodes, childrenProperty, levels = 2) ->
                _setNestedIndex = (children, start, level) ->
                    return unless children?
                    step = Math.pow(10, level)
                    _.each children, (node, i) ->
                        idx = start + step * (i + 1)
                        node.nestedIndex = idx
                        _setNestedIndex node[childrenProperty], idx, level -= 1
                        return
                _setNestedIndex nodes, 0, levels

            setNestedIndex(posts, 'replies', 2)

#            $('#side-buttons').append('<div id="social-toggle" class="side-button"><a href="#">Social</a></div>')

            posts = _.sortBy(posts, (post) -> -post.datetime)
            $(@el).html @compiled
                posts: posts
                user: app.session.authUser()

            #$('#side-buttons').affix()

#            updateSidePanelLocation = ->
#                if not $('#side-panel').hasClass('in')
#                    $('#side-panel').addClass('notransition')
#                    $('#side-panel').css('left', $(window).width() - $('#side-panel').position().left)
#                    $('#side-panel').removeClass('notransition')
#                return
#
#            updateSidePanelLocation()
#            $('#side-panel').css('display', 'block')
#            $(window).resize ->
#                updateSidePanelLocation()

            $('#side-panel .side-panel-content-border').css('height', (134 + 113.5 * posts.length) + 'px')

#            $('#social-toggle').click (e) ->
#                e.preventDefault()
#                $('#side-panel').toggleClass('in')
#                return

#            $('#social-toggle').hover((e) ->
#                $(this).animate({'right': -40 + 'px'}, 400)
#                $('#side-panel').css('left', ($(window).width() - 40) + 'px')
#                return
#            , (e) ->
#                $(this).animate({'right': 0}, 400)
#                $('#side-panel').css('left', $(window).width())
#                return
#            )

            $('.editable textarea').css('overflow', 'hidden').autogrow()
            $('#posts-section')
                .on 'focus', 'textarea', ->
                    $(this).parent().addClass('selected')
                    return
                .on 'blur', 'textarea', ->
                    ta = $(this)
                    ta.parent().removeClass('selected')
                    if ta.val().trim() == ''
                        ta.parent().removeClass('not-empty')
                    else
                        ta.parent().addClass('not-empty')
                    return

            self = this
            $('#posts-section .media > a').popover
                placement: 'left'
                trigger: 'click'
                container: 'body'
                delay:
                    show: 500
                    hide: 100
                content: ->
                    $target = $(this)
                    userId = $target.data('popover-id')
                    self.delay 0, -> # allow a repaint cycle
                        profileView = Vm.create(self, 'ProfileSection', ProfileSection,
                            userId: userId
                            onLoad: ->
                                targetHeight = $target.height()

                                # TODO not sure why $target.popover('tip') shouldn't work
                                $popover = $target.data('popover').tip()

                                popoverHeight = $popover.height()
                                if popoverHeight > targetHeight
                                    pct = Math.round(((targetHeight / 2) / popoverHeight) * 100)
                                    $popover.find('.arrow').css('top', pct + '%')

                                return
                        )
                        profileView.render()
                        return
                    return 'Loading...'

            $('#posts-section .media > a').popover().click (e) ->
                e.preventDefault()

            $('#btn-attachments').popover
                placement: 'bottom'
                container: 'body'
                trigger: 'click'
                delay:
                    show: 500
                    hide: 100
                content: ->
                    self.delay 0, -> # allow a repaint cycle
                        attachmentsView = Vm.create(self, 'AttachmentsSection', AttachmentsSection,
                            popoverId: 'fileupload'
                            entityType: @entityType
                            entityId: @entityId
                        )
                        attachmentsView.render()
                        return
                    return 'Loading...'

            WS = if window['MozWebSocket'] then MozWebSocket else WebSocket
            chatSocket = new WS('ws://localhost:9000/room/chat?username=' + app.session.authEmail())

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
                    return

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
                return

            infocus = false
            startedTyping = false

            handleReturnKey = (e) ->
                if e.charCode == 13 || e.keyCode == 13
                    e.preventDefault()
                    sendMessage()
                    $('#btn-submit').trigger 'click'
                else
                    unless startedTyping
                        startedTyping = true
                        chatSocket.send JSON.stringify
                            isTyping: true
                return

            # bind events
            messageBox = $('#message');
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
            return
