define [], ->
    (($) ->
        $.fn.tooltips = (el) ->

            # ensure chaining works
            @each (i, el) ->
                $el = $(el).attr('data-tooltip', i)

                # make div and append to page
                $tooltip = $("""
                             <div class="tooltip" data-tooltip="#{i}">#{$el.attr('title')}<div class="arrow"></div></div>
                             """).appendTo('body')

                # position immediately, so first appearance is smooth
                linkPosition = $el.position()

                $tooltip.css
                    top: linkPosition.top - $tooltip.outerHeight() - 13
                    left: linkPosition.left - ($tooltip.width() / 2)

                $el
                    # get rid of yellow box popup
                    .removeAttr('title')

                    # mouseenter
                    .hover(->
                        $el = $(this)
                        $tooltip = $("div[data-tooltip=#{$el.data('tooltip')}]")

                        # reposition tooltip in case of page movement e.g. screen resize
                        linkPosition = $el.position()

                        $tooltip.css
                            top: linkPosition.top - $tooltip.outerHeight() - 13
                            left: linkPosition.left - ($tooltip.width() / 2)

                        # adding class handles animation through CSS
                        $tooltip.addClass('active')

                        return

                    # mouseleave
                    , ->
                        $el = $(this)

                        # temporary class for same-direction fadeout
                        $tooltip = $("div[data-tooltip=#{$el.data('tooltip')}]").addClass('out')

                        # remove all classes
                        setTimeout(->
                            $tooltip.removeClass('active').removeClass('out')
                        , 300)
                    )

    )(jQuery)
