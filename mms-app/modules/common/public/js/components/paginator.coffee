###*
*
* @module paginator
###
define [
  'jquery'
  'backbone'
  'cs!events'
], ($, Backbone, app) ->

  ###*
  * The paginator control.
  *
  * @class Paginator
  * @constructor
  * @extends Backbone.View
  * @author markmo
  ###
  Backbone.View.extend

    ###*
    * Uses Backbone.LayoutManager to manage the lifecycle of this view.
    *
    * @property manage
    * @type {Boolean}
    ###
    manage: true

    ###*
    * The location of the Handlebars template for this control.
    *
    * @property template
    * @type {String}
    ###
    template: 'components/paginator'

    ###*
    * The events hash used by Backbone.
    *
    * @property events
    * @type {Object}
    ###
    events:
      'click .prev a': 'previous'
      'click .next a': 'next'
      'click .first a': 'first'
      'click .last a': 'last'

    ###*
    * Fetches the previous page of the collection.
    *
    * @method previous
    * @param {Object} event
    ###
    previous: (event) ->
      event.preventDefault()
      spinner = app.spin()
      $.when(@collection.getPreviousPage()).done =>
        this.trigger('previous')
        spinner.stop()
        this.render()
      return false

    ###*
    * Fetches the next page of the collection.
    *
    * @method next
    * @param {Object} event
    ###
    next: (event) ->
      event.preventDefault()
      spinner = app.spin()
      $.when(@collection.getNextPage()).done =>
        this.trigger('next')
        spinner.stop()
        this.render()
      return false

    ###*
    * Fetches the first page of the collection.
    *
    * @method first
    * @param {Object} event
    ###
    first: (event) ->
      event.preventDefault()
      spinner = app.spin()
      $.when(@collection.getFirstPage()).done =>
        this.trigger('first')
        spinner.stop()
        this.render()
      return false

    ###*
    * Fetches the last page of the collection.
    *
    * @method last
    * @param {Object} event
    ###
    last: (event) ->
      event.preventDefault()
      spinner = app.spin()
      $.when(@collection.getLastPage()).done =>
        this.trigger('last')
        spinner.stop()
        this.render()
      return false

    initialize: (options) ->
      @longForm = !(options.shortForm)

    ###*
    * Returns the data object for use in the template.
    *
    * @method serialize
    * @return {Object} template variables
    ###
    serialize: ->
      collection = @collection
      state = collection.state
      start = (state.currentPage - 1) * state.pageSize + 1
      end = start + Math.min(state.pageSize, collection.length) - 1
      return {
        longForm: @longForm
        collection: collection
        start: start
        end: end
        totalRowCount: state.totalRecords #- Math.min(state.pageSize, @collection.length)
        # compensate for bug in backbone-pageable that is incrementing
        # the total record count as a result of an add event on sync,
        # which could be as a result of new behaviour in jquery
      }

    ###*
    * Run after the view is rendered.
    *
    * @method afterRender
    ###
    afterRender: ->
      this.trigger('shown')

    ###*
    * Initializes the paginator control. Expects a PageableCollection to be passed in
    * the options hash using the key 'collection'.
    *
    * @method initialize
    * @param {Object} options
    ###
