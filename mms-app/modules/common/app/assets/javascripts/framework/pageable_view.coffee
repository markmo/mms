###*
*
* @module pageable_view
###
define [
  'backbone'
  'vm'
  'components/paginator'
], (Backbone, Vm, Paginator) ->

  ###*
  * The Trait which enables pagination of lists and tables.
  *
  * @class Pageable
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
    * The events hash used by Backbone.
    *
    * @property events
    * @type {Object}
    ###
    events:
      'click a[data-sort]': 'sort'

    ###*
    * Sorts the list or table by the value in the selected column. The sort order
    * is toggled between asc and desc.
    *
    * @method sort
    * @param {Object} event
    ###
    sort: (event) ->
      event.preventDefault()
      sortKey = $(event.target).data('sort')
      state = @collection.state
      @collection
      .setSorting(sortKey, state.order * -1)
      .fetch({reset: true}) # https://github.com/wyuenho/backgrid/commit/21810e1
      return false

    ###*
    * Initializes this trait. Expects a PageableCollection to be passed in
    * the options hash using the key 'collection'.
    *
    * @method initialize
    * @param {Object} options
    ###
    initialize: (options) ->
      collection = @collection = options.collection

      # better to do explicitly in the subclass as need to be in complete
      # control of events to avoid unintended side-effects
      #      this.listenTo(collection, 'sync', this.render)

      paginator = new Paginator
        collection: collection
        shortForm: options.shortPaginatorForm
      this.setView('#paginator', paginator)
      this.listenTo(paginator, 'previous next first last', this.beforePageChange) if typeof this['beforePageChange'] == 'function'
      this.listenTo(paginator, 'previous next first last', this.render)
