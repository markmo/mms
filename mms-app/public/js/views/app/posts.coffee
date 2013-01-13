define [
    'jquery'
    'underscore'
    'backbone'
    'handlebars'
    'cs!events'
	'cs!collections/posts'
    'cs!models/post'
    'text!templates/app/posts.html'
    'text!templates/app/post_reply.html'
    'cs!components/tooltips'
], ($, _, Backbone, Handlebars, app, PostsCollection, Post, postsPageTemplate, postReplyPageTemplate) ->
    Backbone.View.extend
        el: '#posts-section'

        events:
            'click #btn-submit': 'post'
            'click .btn-reply': 'showReplyForm'
            'click #btn-reply-submit': 'replySubmit'

        compiled: Handlebars.compile postsPageTemplate

        replyCompiled: Handlebars.compile postReplyPageTemplate

        post: (event) ->
            event.preventDefault()
            post = new Post
                entityType: 'datasource'
                entityId: 1
                subject: 'Test'
                message: $('#message').val()
                userId: 1
            app.posts.add(post)
            post.save()
            return

        replySubmit: (event) ->
            event.preventDefault()
            post = new Post
                entityType: 'datasource'
                entityId: 1
                subject: 'Test'
                message: $('#reply-message').val()
                parentId: $('#reply-to-post-id').val()
                userId: 1
            app.posts.add(post)
            post.save()
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

        initialize: ->
            @posts = new PostsCollection({},
                entityType: 'datasource'
                entityId: 1
            )
            this.listenTo @posts, 'change', this.render
            app.posts = @posts

        render: () ->
            @posts.fetch
                success: =>
                    dict = {}
                    dict[post.id] = post.toJSON() for post in @posts.models when post.id?
                    for id, post of dict
                        if post.parent?
                            parent = dict[post.parent.id]
                            parent.replies = [] unless parent.replies?
                            parent.replies.push(post)
                    posts = (post for id, post of dict when post.parent is null)

#                    setNestedIndex = (nodes, childrenProperty, levels = 2) ->
#                        _setNestedIndex = (children, start, level) ->
#                            return unless children?
#                            step = Math.pow(10, level)
#                            for node, i in children
#                                do (node, i, start, step, level) ->
#                                    idx = start + step * (i + 1)
#                                    node.nestedIndex = idx
#                                    _setNestedIndex node[childrenProperty], idx, level -= 1
#                        _setNestedIndex nodes, 0, levels
#
#                    setNestedIndex(posts, 'replies', 2)

                    posts = _.sortBy(posts, (post) -> -post.datetime)
                    $(@el).html @compiled
                        posts: posts
                    $('.editable textarea').css('overflow', 'hidden').autogrow()
                    $('#posts-section')
                        .on 'focus', 'textarea', ->
                            $(this).parent().toggleClass('selected')
                            return
                        .on 'blur', 'textarea', ->
                            ta = $(this)
                            ta.parent().toggleClass('selected')
                            if ta.val().trim() == ''
                                ta.parent().removeClass('not-empty')
                            else
                                ta.parent().addClass('not-empty')
                            return
                    $('#posts-section .media-object').tooltips()
                    return
            return this
