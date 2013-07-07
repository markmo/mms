define [
    'jquery'
    'handlebars'
    'text!templates/components/paginator.html'
], ($, Handlebars, paginatorTemplate) ->
    Backbone.View.extend
        el: '#paginator'

        events:
            'click #previousPage': 'previous'
            'click #nextPage': 'next'

        compiled: Handlebars.compile paginatorTemplate

        previous: ->
            @pageableCollection.getPrevious()
            this.render()

        next: ->
            @pageableCollection.getNext()
            this.render()

        initialize: (options) ->
            @pageableCollection = options.pageableCollection

        render: ->
            @$el.html @compiled
                pageableCollection: @pageableCollection
