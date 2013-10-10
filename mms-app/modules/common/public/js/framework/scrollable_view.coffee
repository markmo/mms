define [
  'jquery'
  'backbone'
  'infinite-scroll'
], ($, Backbone) ->

  Backbone.View.extend

    afterRender: ->
      setTimeout(->
        $('.table tbody').infinitescroll
          loading:
            finishedMsg: "<em>That's the whole list</em>"
            msg: $('<tr id="infscr-loading"><td colspan="7"><img alt="Loading&hellip;" src="/img/throbber.gif"><div>Loading&hellip;</div></td></tr>')
          nextSelector: '.next a',
          navSelector: '.pagination',
          itemSelector: 'tbody tr',
          behavior: 'custom'

        $.extend $.infinitescroll.prototype,
          _showdonemsg_custom: ->
            opts = this.options

            # perform the default behaviour
            opts.loading.msg
              .find('img').hide().parent()
              .find('div').html(opts.loading.finishedMsg).animate {opacity: 1}, 2000, ->
                $(this).parent().fadeOut(opts.loading.speed)

            # And also hide the scroll message at bottom
            $('.infscr-msg').hide()

            # user provided callback when done
            opts.errorCallback.call($(opts.contentSelector)[0], 'done')
      ,0)
