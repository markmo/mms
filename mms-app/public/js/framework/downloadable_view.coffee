define [
  'backbone'
], (Backbone) ->

  Backbone.View.extend

    manage: true

    events:
      'click #download': 'download'

    download: (event) ->
      event.preventDefault()
      state = @collection.state
      if (state.totalRecords > 10000)
        modal = new Backbone.BootstrapModal
          title: 'Whoa!'
          content: "That's a lot of data. Try adding a search filter to reduce the size of the list"
          okText: "Just give me the first 10,000 records then"
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

    initialize: (options) ->
      @collection = options.collection
