define(['jquery'], function ($) {
    function importJS(src, look_for, onload) {
        var existingScripts = document.getElementsByTagName('script');
        for (i=0; i < existingScripts.length; i++) {
            var existingSrc = existingScripts[i].getAttribute('src');
            if (src == existingSrc) {
                // script is already loaded
                if (onload) {
                    onload();
                }
                return;
            }
        }

        var s = document.createElement('script');
        s.setAttribute('type', 'text/javascript');
        s.setAttribute('src', src);
        if (onload) {
            wait_for_script_load(look_for, onload);
        }
        var head = document.getElementsByTagName('head')[0];
        if (head) {
            head.appendChild(s);
        } else {
            document.body.appendChild(s);
        }
    }

    function importCSS(href, look_for, onload) {
        var existingLinks = document.getElementsByTagName('link');
        for (i=0; i < existingLinks.length; i++) {
            var existingHref = existingLinks[i].getAttribute('href');
            if (href == existingHref) {
                // stylesheet is already loaded
                if (onload) {
                    onload();
                }
                return;
            }
        }

        var s = document.createElement('link');
        s.setAttribute('rel', 'stylesheet');
        s.setAttribute('type', 'text/css');
        s.setAttribute('media', 'screen');
        s.setAttribute('href', href);
        if (onload) {
            wait_for_script_load(look_for, onload);
        }
        var head = document.getElementsByTagName('head')[0];
        if (head) {
            head.appendChild(s);
        } else {
            document.body.appendChild(s);
        }
    }

    function wait_for_script_load(look_for, callback) {
        if (look_for == null) {
            callback();
            return;
        }
        var interval = setInterval(function() {
            try {
                if (eval("typeof " + look_for) != 'undefined') {
                    clearInterval(interval);
                    callback();
                }
            } catch (err) {
                // swallow the exception - the look_for was not yet reachable
            }
        }, 50);
    }

    var apply = function() {
        var cdnBaseUrl;
        if (location.protocol == "https:") {
            cdnBaseUrl = "https://code.jquery.com/";
        } else {
            cdnBaseUrl = "http://code.jquery.com/";
        }

    //	var relativeBaseUrl = "http://eobjects.org/resources/datacleaner-html-rendering/";
        var relativeBaseUrl = "/assets/stylesheets/analysis-result/";
        var existingScripts = document.getElementsByTagName('script');
        for (i=0; i < existingScripts.length; i++) {
            var existingSrc = existingScripts[i].getAttribute('src');
            if (existingSrc != null) {
                var offset = existingSrc.length - 'analysis-result.js'.length;
                if (existingSrc > 0 && existingSrc.indexOf('analysis-result.js', offset) !== -1) {
                    // script src ends with 'analysis-result.js'
                    relativeBaseUrl = existingSrc.substring(0, offset);
                    break;
                }
            }
        }

    //	importJS(cdnBaseUrl + "jquery/1.7.2/jquery.min.js", 'jQuery', function() {
            importCSS(cdnBaseUrl + "ui/1.10.2/themes/smoothness/jquery-ui.css");
            importJS(cdnBaseUrl + "ui/1.10.2/jquery-ui.min.js", 'jQuery.ui', function() {
                loadTabs();
                importCSS(relativeBaseUrl + "analysis-result.min.css?load=script");
            });
    //	});
    };

    function drillToDetails(elementId) {
        var elem = $('#' + elementId);

        var wWidth = $(window).width();
        var dWidth = wWidth * 0.85;
        var wHeight = $(window).height();
        var dHeight = wHeight * 0.79;
        elem.dialog({
            modal : true,
            width : dWidth,
            height : dHeight
        });
    }

    function loadTabs() {
        $(function() {
            $(".analysisResultContainer").tabs({
                tabTemplate : "<li><a href=\"#{href}\">#{label}</a></li>"
            });
        });
    }

    return {
        apply: apply,
        drillToDetails: drillToDetails
    }
});
