define [
    'backbone'
], (Backbone) ->
    Backbone.Model.extend

        initialize: ->
            @notifications = {}

        authUser: ->
            return this.get('user')

        authEmail: ->
            return this.get('user')?.email

        authId: ->
            return this.get('user')?.id

        notifiedOf: (notification) ->
            return true if @notifications[notification]
            @notifications[notification] = true
            return false
