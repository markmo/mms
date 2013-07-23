define [
    'jquery'
    'handlebars'
    'text!templates/components/paginator.html'
], ($, Handlebars, paginatorTemplate) ->
    Backbone.View.extend
        el: '#paginator'

        events:
            'click .prev a': 'previous'
            'click .next a': 'next'

        compiled: Handlebars.compile paginatorTemplate

        previous: (event) ->
            event.preventDefault()
            $.when(@pageableCollection.getPreviousPage()).done =>
                this.trigger('previous')
                this.render()

        next: (event) ->
            event.preventDefault()
            $.when(@pageableCollection.getNextPage()).done =>
                this.trigger('next')
                this.render()

        initialize: (options) ->
            @pageableCollection = options?.pageableCollection

        render: ->
            state = @pageableCollection.state
            start = (state.currentPage - 1) * state.pageSize + 1
            end = start + Math.min(state.pageSize, @pageableCollection.length) - 1
            @$el.html @compiled
                pageableCollection: @pageableCollection
                start: start
                end: end
                totalRowCount: state.totalRecords
            return this
