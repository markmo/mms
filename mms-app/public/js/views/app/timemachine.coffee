define [
    'jquery'
    'underscore'
    'backbone'
    'cs!events'
    'text!templates/app/timemachine.html'
], ($, _, Backbone, app, timemachinePageTemplate) ->
    Backbone.View.extend
        el: '#page'

        compiled: timemachinePageTemplate

        render: () ->
            $.ajax '/revisions',
                success: (data) =>
                    revisions = {}
                    series = []
                    for datum in data
                        unixTime = moment(datum.revisionDate).unix()
                        revisions[unixTime] = {revisionId: datum.revisionId, revisionDate: datum.revisionDate}
                        series.push({x: unixTime, y: datum.numberEntitiesChanged})
                    $(@el).html @compiled
                    graph = new Rickshaw.Graph
                        element: $('#chart')[0]
                        width: 500
                        height: 200
                        renderer: 'line'
                        series: [
                            color: 'steelblue'
                            data: series
                        ]

                    graph.vis.on 'click', (d, i) ->
                        revisionId = _.pluck(revisions, 'revisionId')[i]
                        app.router.navigate "revisions/#{revisionId}",
                            trigger: true
                            replace: true
                        return

                    time = new Rickshaw.Fixtures.Time()
                    days = time.unit('day')
                    axes = new Rickshaw.Graph.Axis.Time({graph: graph, timeUnit: days})
                    graph.render()
                    hoverDetail = new Rickshaw.Graph.HoverDetail
                        graph: graph
                        formatter: (series, x, y) ->
                            date = "<span class=\"date\">#{revisions[x].revisionDate}</span>"
                            swatch = "<span class=\"detail_swatch\" style=\"background-color: #{series.color};\"></span>"
                            content = "#{swatch}Revision ID: #{revisions[x].revisionId}<br>#{date}"
                            return content
                    return
            return this
