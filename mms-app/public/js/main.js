require.config({
    baseUrl: "/assets/js",
    paths: {
        // Libraries
        jquery: 'lib/jquery/jquery-1.9.1',
        underscore: 'lib/underscore/underscore-min',
        backbone: 'lib/backbone/backbone-min',
        'backbone-forms': 'lib/backbone/backbone-forms',
        'backbone-associations': 'lib/backbone/backbone-associations',
        'backbone-collectionsubset': 'lib/backbone/backbone.collectionsubset.min',
        handlebars: 'lib/handlebars/handlebars',
        bootstrap: 'lib/bootstrap/bootstrap.min',
        'jquery.ui.widget': 'lib/jquery-file-upload/vendor/jquery.ui.widget',
        'jquery-resize': 'lib/jquery/jquery.ba-resize.min',
        tmpl: 'lib/jquery-file-upload/vendor/tmpl.min',
        'load-image': 'lib/jquery-file-upload/vendor/load-image.min',
        'canvas-to-blob': 'lib/jquery-file-upload/vendor/canvas-to-blob.min',
        facetview: 'lib/facetview/jquery.facetview',
        linkify: 'lib/linkify/1.0/jquery.linkify-1.0-min',
        jqueryui_custom: 'lib/jquery-ui-1.8.18.custom/jquery-ui-1.8.18.custom.min',
        chosen: 'lib/chosen/chosen.jquery.min',
        select2: 'lib/select2/select2',
        snap: 'lib/snap/snap.min',
        jsonform: 'lib/jsonform/jsonform',
        tagautocomplete: 'lib/tagautocomplete/bootstrap-tagautocomplete',
        'bootstrap-typeahead': 'lib/tagautocomplete/bootstrap-typeahead',
        'caret-position': 'lib/tagautocomplete/caret-position',
        'rangy-core': 'lib/tagautocomplete/rangy-core',
        'annotator': 'lib/annotator/annotator-full.min',

        // Require.js plugins
        cs: 'lib/require/cs',
        text: 'lib/require/text',

        // Put HTML outside the js dir
        templates: '../templates'
    },
    shim: {
        underscore: {
            exports: '_'
        },
        backbone: {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'backbone-associations': {
            deps: ['backbone']
        },
        'backbone-collectionsubset': {
            deps: ['backbone']
        },
        handlebars: {
            exports: 'Handlebars'
        },
        facetview: {
            deps: ['jquery', 'jqueryui_custom', 'linkify']
        },
        tagautocomplete: {
            deps: ['bootstrap-typeahead', 'rangy-core', 'caret-position']
        }
    }
});

var v0 = function (fn) {
    fn();
    return void 0;
};

require([
    'jquery',
    'backbone',
    'handlebars',
    'cs!views/app',
    'cs!router',
    'cs!events',
    'cs!vm',
    'snap',
    'cs!models/session',
    'lib/backbone/templates/bootstrap',
    'lib/jquery-file-upload/jquery.fileupload'
], function($, Backbone, Handlebars, AppView, Router, app, Vm, Snap, Session) {

    require([
        'bootstrap',
        'lib/jquery/jquery-migrate-1.1.1.min',
        'lib/jquery/jquery.autogrow-textarea',
        'lib/bootstrap-growl/jquery.bootstrap-growl.min',
        'lib/jquery-file-upload/vendor/bootstrap-image-gallery.min',
        'lib/jquery-file-upload/jquery.fileupload-ui',
        'lib/jquery.equalHeights',
        'lib/backbone/backbone.bootstrap-modal'
    ]);

    var appView = Vm.create({}, 'AppView', AppView, {app: app});
    app.view = appView;

    Backbone.View.prototype.delay = function (ms, fn) {
        return setTimeout(fn, ms);
    };

    Handlebars.registerHelper('humanize', function (value) {
//        if (value instanceof Date) {
            return new Handlebars.SafeString(
                moment(value).fromNow()
            );
//        } else {
//            return value;
//        }
    });

    Handlebars.registerHelper('loop', function (context, options) {
        var fn = options.fn,
            inverse = options.inverse,
            ret = '';
        if (context && context.length > 0) {
            for (var i = 0, j = context.length; i < j; i += 1) {
                ret = ret + fn(_.extend({}, context[i], {i: i}));
            }
        } else {
            ret = inverse(this);
        }
        return ret;
    });

    Handlebars.registerHelper('foreach', function (arr, options) {
        if (options.inverse && !arr.length)
            return options.inverse(this);

        return arr.map(function (item, index) {
            item.$index = index;
            item.$first = index === 0;
            item.$last  = index === arr.length - 1;
            item.$more  = index < arr.length - 1;
            return options.fn(item);
        }).join('');
    });

    Handlebars.registerHelper('isRoleSelected', function (context, options) {
        var hash = options.hash;
        var roleId = context.id;
        var term = hash.term;
        var personId = hash.person.id;
        var isSelected = false;
        if (term.people) {
            for (var i = 0, n = term.people.length; i < n; i += 1) {
                var r = term.people[i];
                if (r.person.id === personId &&
                    r.stakeholderRole.id === roleId)
                {
                    isSelected = true;
                    break;
                }
            }
        }
        return isSelected ? "selected" : "";
    });

    // Sliding main window
    // https://github.com/jakiestfu/Snap.js/
    var snapper = new Snap({
        element: document.getElementById('main'),
        touchToDrag: false,
        minPosition: -398
    });
    $('.navbar-inner')
        .on('click', '#open-left', function (event) {
            event.preventDefault();
            if (snapper.state().state === 'left') snapper.close()
            else snapper.open('left');
        })
        .on('click', '#open-right', function (event) {
            event.preventDefault();
            if (snapper.state().state === 'right') snapper.close()
            else snapper.open('right');
        });

    var session = new Session();
    $.ajax('/session').done(function (data) {
        session.set('user', JSON.parse(data));
    });
    app.session = session;

    // TODO: is use of a global var the best way? may cause conflict with other libs
    // setting config using the require global var prior to loading require.js seems
    // to be overridden with calling config above
    if (global.start === 'revisions') {
        Router.initialize({appView: appView, silent: true});
        $('#spinner').html('').hide();
        appView.render();

        location.href = '/#/revisions'; // TODO: is this the only way?
        //router.navigate('revisions', {trigger: true, replace: true}); // doesn't work - just appends #revisions to current url

    } else if (global.start === 'glossary') {
        Router.initialize({appView: appView, silent: true});
        $('#spinner').html('').hide();
        appView.render();

        location.href = '/#/terms';

    } else {
        Router.initialize({appView: appView});
        $('#spinner').html('').hide();
        appView.render();
    }
});
