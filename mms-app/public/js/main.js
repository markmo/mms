require.config({
    baseUrl: "/assets/js",
    paths: {
        // Libraries
        jquery: 'lib/jquery/jquery-1.9.1',
        underscore: 'lib/underscore/underscore-min',
        backbone: 'lib/backbone/backbone-min',
        'backbone-forms': 'lib/backbone/backbone-forms.min',
        handlebars: 'lib/handlebars/handlebars-1.0.rc.1',
        bootstrap: 'lib/bootstrap/bootstrap.min',
        'jquery.ui.widget': 'lib/jquery-file-upload/vendor/jquery.ui.widget',
        tmpl: 'lib/jquery-file-upload/vendor/tmpl.min',
        'load-image': 'lib/jquery-file-upload/vendor/load-image.min',
        'canvas-to-blob': 'lib/jquery-file-upload/vendor/canvas-to-blob.min',

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
        handlebars: {
            exports: 'Handlebars'
        }
    }
});

require([
    'jquery',
    'backbone',
    'handlebars',
    'cs!views/app',
    'cs!router',
    'cs!vm',
    'cs!events',
    'cs!models/session',
    'lib/backbone/templates/bootstrap',
    'lib/jquery-file-upload/jquery.fileupload'
], function($, Backbone, Handlebars, AppView, Router, Vm, app, Session) {

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
        $('#spinner').html('');
        appView.render();

        location.href = '/#/revisions'; // TODO: is this the only way?
        //router.navigate('revisions', {trigger: true, replace: true}); // doesn't work - just appends #revisions to current url

    } else {
        Router.initialize({appView: appView});
        $('#spinner').html('');
        appView.render();
    }
});
