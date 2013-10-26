###*
* @module downloadable_view
###
define [
  'backbone'
], (Backbone) ->

  ###*
  * The Trait which enables downloading of lists and tables.
  *
  * @class Downloadable
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
      'click #download': 'download'

    ###*
    * Downloads the list or table.
    *
    * @method download
    * @param {Object} event
    ###
    download: (event) ->
      event.preventDefault()
      state = @collection.state
      if (state.totalRecords > 10000)
        modal = new Backbone.BootstrapModal
          title: 'Whoa!'
          content: "That's a lot of data. Try adding a search filter to reduce the size of the list"
          okText: 'Just give me the first 10,000 records then'
        modal.open()
        this.listentTo modal, 'ok', ->
          this._processDownload()
      else
        this._processDownload()
      return false

    _processDownload: ->
      collection = @collection
      queryParams = collection.queryParams
      state = collection.state
      params = []
      for own key, value of queryParams
        if key == 'pageSize'
          k = value
          v = state.totalRecords
          params.push(k + '=' + v)
        else if key in state
          k = value
          v = state[key]
          if v
            if key == 'order'
              v = queryParams.directions[v]
            params.push(k + '=' + v)
        else if key != 'directions'
          k = key
          v = value
          params.push(k + '=' + v)
      url = collection.url + '.xls?' + params.join('&')
      window.location.href = url

      ###
      Initializes this trait. Expects a PageableCollection to be passed in
      the options hash using the key 'collection'.

      @method initialize
      @param {Object} options
      ###
