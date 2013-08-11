define [
  'jquery'
  'handlebars'
  'text!templates/components/paginator.html'
], ($, Handlebars, paginatorTemplate) ->

  Backbone.View.extend

    events:
      'click .prev a': 'previous'
      'click .next a': 'next'

    compiled: Handlebars.compile paginatorTemplate

    previous: ->
      $.when(@pageableCollection.getPreviousPage()).done =>
        this.trigger('previous')
        this.render()
      return false

    next: ->
      $.when(@pageableCollection.getNextPage()).done =>
        this.trigger('next')
        this.render()
      return false

    initialize: (options) ->
      @collection = options.collection

    render: ->
      state = @collection.state
      start = (state.currentPage - 1) * state.pageSize + 1
      end = start + Math.min(state.pageSize, @collection.length) - 1
      @$el.html @compiled
        pageableCollection: @collection
        start: start
        end: end
        totalRowCount: state.totalRecords #- Math.min(state.pageSize, @collection.length)
        # compensate for bug in backbone-pageable that is incrementing
        # the total record count as a result of an add event on sync,
        # which could be as a result of new behaviour in jquery
      return this
