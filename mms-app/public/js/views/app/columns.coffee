define [
  'jquery'
  'underscore'
  'backbone'
  'handlebars'
  'text!templates/app/columns.html'
], ($, _, Backbone, Handlebars, columnsPageTemplate) ->

  Backbone.View.extend

    compiled: Handlebars.compile columnsPageTemplate

    initialize: (options) ->
      @el = options.el
      @columns = options.collection
      this.listenTo @collection, 'sync', this.render

    render: (filterTypes) ->
      filteredColumns = @columns.filterTypes(filterTypes)
      $el = @$el
      $ul = $el.find('ul.items')
      currentIdList = []
      if $el.is(':visible') and not $el.is(':empty')
        $ul.children().each (i, li) ->
          currentIdList.push($(li).find('a').data('id'))
        replacementIdList = filteredColumns.pluck('id')
        union = _.union(currentIdList, replacementIdList).sort (a, b) ->
          a - b
        j = 1
        for id in union
          if id in currentIdList
            unless id in replacementIdList
              removing = $ul.find("li:nth-child(#{j})")
              do (removing) ->
                removing.hide 'slow', ->
                  removing.remove()
                  j--
                  return
                return
            j++
          else
            column = filteredColumns.get(id)
            newLi = $("<li style=\"display: none;\"><a href=\"#/columns/#{id}\" class=\"item-title\" data-id=\"#{id}\">#{column.get('friendlyName')}</a></li>")
            if j < currentIdList.length
              $ul.find("li:nth-child(#{j})").before newLi
            else
              $ul.append newLi
            newLi.show 'slow'
      else
        content = $(@compiled
          columns: filteredColumns.toJSON()
        )
        $el.css('display', 'none')
        $el.html content
        $el.show 'slow'
      return this
