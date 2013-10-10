###*
*
* @module grid_view
###
define [
  'backbone'
  'cs!framework/pageable_view'
  'cs!framework/editable_view'
  'cs!framework/searchable_view'
  'cs!framework/date_filterable_view'
  'cs!framework/downloadable_view'
], (Backbone, Pageable, Editable, Searchable, DateFilterable, Downloadable) ->

  ###*
  * The base object for all Grid Views. Grid Views display a table of items.
  * Traits can be mixed in to enable additional behaviour such as pagination,
  * editing, and search.
  *
  * @class GridView
  * @constructor
  * @extends Backbone.View
  * @author markmo
  ###
  Backbone.View.extend

    ###*
    * The list of traits to mix in.
    *
    * @property traits
    ###
    traits: [Pageable, Editable, DateFilterable, Downloadable]

    ###*
    * Uses Backbone.LayoutManager to manage the lifecycle of this view.
    *
    * @property manage
    * @type {Boolean}
    ###
    manage: true

    ###*
    * Initializes this view. Expects a PageableCollection to be passed in
    * the options hash using the key 'collection'.
    *
    * @method initialize
    * @param {Object} options
    ###
    initialize: (options) ->
      this.listenTo(@collection, 'sync', this.checkNoResults)

      # better to do explicitly in the subclass as need to be in complete
      # control of events to avoid unintended side-effects
#      this.listenTo(@collection, 'sync', this.render)

    checkNoResults: ->
      unless @collection.length
        setTimeout(->
          $('.well').text('None found')
        ,50)

    ###*
    * Returns the data object for use in the template.
    *
    * @method serialize
    * @return {Object} template variables
    ###
    serialize: ->
      return {pageableCollection: @collection}
