!function (global) {
  'use strict';

  var defaults = {};

  var wrap = function ($) {

    $.fn.dotindicator = function (options) {
      options = $.extend(true, {}, defaults, options);

      return this.each(function () {
        var $this = $(this);
        var child = $this.children()[0];
        var $childWidth = $(child).width();
        var $thisWidth = $this.width();
        var width = $childWidth - $thisWidth;
        var n = Math.round($childWidth / $thisWidth);
        var $target = $(options.target);
        var createContent = false;
        var $container;
        if (options.target === '.dotindicator') {
          if ($target.is(':empty')) {
            createContent = true;
          }
          $container = $target;
        } else {
          $container = $target.find('.dotindicator');
          if (!$container.length) {
            $container = $('<div class="dotindicator">');
            $target.html($container);
            createContent = true;
          }
        }
        if (createContent) {
          var dots = [];
          for (var i = 0; i < n; i += 1) {
            if (i === 0) {
              dots.push('<div class="dot dot-1 active"></div>');
            } else {
              dots.push('<div class="dot dot-' + (i + 1) + '"></div>');
            }
          }
          $container.html(dots.join(''));
        }
        var dotno = 1;
        $this.scroll(function () {
          var pos = $this.scrollLeft();
          if (pos === 0)
            pos = 1;
          var page = pos / width;
          $container.find('.dot-' + dotno).removeClass('active');
          dotno = Math.ceil(page * n);
          $container.find('.dot-' + dotno).addClass('active');
        });
      });
    }
  };

  if (typeof define === 'function' && define.amd) {
    define(['jquery'], wrap);
  } else {
    wrap(global.jQuery);
  }
}(this);
