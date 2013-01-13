define [
    'jquery'
    'underscore'
    'backbone'
    'handlebars'
    'cs!events'
	'cs!collections/columns'
    'text!templates/app/columns.html'
], ($, _, Backbone, Handlebars, app, ColumnsCollection, columnsPageTemplate) ->
    Backbone.View.extend
        compiled: Handlebars.compile columnsPageTemplate

        initialize: (options) ->
            if options
                @el = options.el
                @tableId = options.tableId

        render: (filterTypes) ->
            if not @columns?
                @columns = new ColumnsCollection({}, {tableId: @tableId})
                app.columns = @columns
            @columns.fetch
                success: =>
                    filteredColumns = @columns.filterTypes(filterTypes)
                    $el = $(@el)
                    $ul = $el.find('ul.items')
                    currentIdList = []
                    if $el.is(':visible') and not $el.is(':empty')
                        $ul.children().each (i, li) ->
                            currentIdList.push($(li).find('a').data('id'))
                        replacementIdList = filteredColumns.pluck('id')
                        union = _.union(currentIdList, replacementIdList).sort((a, b) -> a - b)
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
                        content.css('display', 'none')
                        $el.html content
                        content.show 'slow'
                    return
            return this
