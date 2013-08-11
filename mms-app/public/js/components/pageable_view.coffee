define [
  'backbone'
  'cs!vm'
  'cs!components/paginator'
], (Backbone, Vm, Paginator) ->

  Backbone.View.extend

    events:
      'click a[data-sort]': 'sort'

    sort: (event) ->
      event.preventDefault()
      sortKey = $(event.target).data('sort')
      state = @collection.state
      @collection
        .setSorting(sortKey, state.order * -1)
        .fetch({reset: true}) # https://github.com/wyuenho/backgrid/commit/21810e17
      return false

    initialize: (options) ->
      @collection = options.collection
      @paginator = paginator = Vm.create this, 'Paginator', Paginator,
        collection: @collection
      this.listenTo(paginator, 'previous next', this.prePageChange) if typeof this['prePageChange'] == 'function'
      this.listenTo(paginator, 'previous next', this.render)

    render: ->
      @paginator.setElement('#paginator').render()
