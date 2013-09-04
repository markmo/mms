define [
  'jquery'
  'backbone'
  'daterangepicker'
  'moment'
], ($, Backbone) ->

  Backbone.View.extend

    afterRender: ->
      collection = @collection
      queryParams = collection.queryParams
      @daterangepicker = $('#daterange').daterangepicker(
      {
        startDate: moment().subtract('days', 29)
        endDate: moment()
        minDate: '01/01/2008'
        maxDate: '31/12/2020'
        dateLimit: { days: 60 }
        showDropdowns: true
        showWeekNumbers: true
        timePicker: false
        timePickerIncrement: 1
        timePicker12Hour: true
        ranges:
          'Today': [moment(), moment()]
          'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)]
          'Last 7 Days': [moment().subtract('days', 6), moment()]
          'Last 30 Days': [moment().subtract('days', 29), moment()]
          'This Month': [moment().startOf('month'), moment().endOf('month')]
          'Last Month': [moment().subtract('month', 1).startOf('month'),
                         moment().subtract('month', 1).endOf('month')]
        opens: 'left'
        buttonClasses: ['btn-danger']
        applyClass: 'btn-small btn-success'
        clearClass: 'btn-small'
        format: 'DD/MM/YYYY'
        separator: ' to '
        locale:
          applyLabel: 'Submit'
          fromLabel: 'From'
          toLabel: 'To'
          customRangeLabel: 'Custom Range'
          daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr','Sa']
          monthNames: ['January', 'February', 'March', 'April',
                       'May', 'June', 'July', 'August', 'September',
                       'October', 'November', 'December']
          firstDay: 1
      },
      (start, end) ->
        $('#daterange span').html(start.format('MMMM D, YYYY') +
        ' - ' + end.format('MMMM D, YYYY'))
        start ?= moment('2008-01-01')
        end ?= moment()
        $.extend queryParams,
          from: start.format()
          to: end.format()
        collection.fetch()
      )

      ###
      Set the initial state of the picker label
      ###
      start = queryParams.from ? '2008-01-01'
      end = queryParams.to ? moment()
      $('#daterange span').html(
          moment().subtract('days', 29).format('MMMM D, YYYY') +
          ' - ' + moment(end).format('MMMM D, YYYY')
      )
