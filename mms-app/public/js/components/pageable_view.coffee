define [
    'jquery'
    'underscore'
    'backbone'
    'cs!vm'
    'cs!components/paginator'
], ($, _, Backbone, Vm, Paginator) ->
    Backbone.View.extend
        events:
            'click a[data-sort]': 'sort'

        sort: (event) ->
            event.preventDefault()
            sortKey = $(event.target).data('sort')
            state = @pageableCollection.state
            @pageableCollection.setSorting(sortKey, state.order * -1)
                #https://github.com/wyuenho/backgrid/commit/21810e171e86ccbdb0a079435c7231a85f221291
                .fetch({reset: true}).done => this.render()

        preRender: ->
            if @paginator
                this.stopListening(@paginator)
                @paginator.clean?()

        # seems inefficient to recreate the subview each time but the
        # child container is being replaced in the html call above
        postRender: ->
            @paginator = paginator = Vm.create(this, 'Paginator', Paginator, {pageableCollection: @pageableCollection})
            paginator.render()
            this.listenTo paginator, 'previous next', _.bind(this.prePageChange, this) if _.isFunction(this.prePageChange)
            this.listenTo paginator, 'previous next', _.bind(this.render, this)
            return

        # can't create a subview before render as there will be no DOM elements
        # in the parent view at this stage to attach events or create selectors
        render: ->
            this.preRender()
            $.when(this.doRender()).then => this.postRender()
