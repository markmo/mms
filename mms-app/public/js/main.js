require.config({
    baseUrl: "/assets/js",
    paths: {
        // Libraries
        jquery: 'lib/jquery/jquery-1.8.3.min',
        underscore: 'lib/underscore/underscore-min',
        backbone: 'lib/backbone/backbone-min',
        'backbone-forms': 'lib/backbone/backbone-forms.min',
        handlebars: 'lib/handlebars/handlebars-1.0.rc.1',
        bootstrap: 'lib/bootstrap/bootstrap.min',

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
    'handlebars',
    'cs!views/app',
    'cs!router',
    'cs!vm',
    'cs!events',
    'cs!collections/data_sources',
    'lib/backbone/templates/bootstrap'
], function($, Handlebars, AppView, Router, Vm, app, DataSourcesCollection) {

    require(['bootstrap', 'lib/jquery/jquery.autogrow-textarea']);

    var appView = Vm.create({}, 'AppView', AppView, {app: app});


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

    // TODO: is use of a global var the best way? may cause conflict with other libs
    // setting config using the require global var prior to loading require.js seems
    // to be overridden with calling config above
    if (global.start === 'revisions') {
        var router = Router.initialize({appView: appView, silent: true});
        $('#spinner').html('');
        appView.render();

        location.href = '/#/revisions'; // TODO: is this the only way?
        //router.navigate('revisions', {trigger: true, replace: true}); // doesn't work - just appends #revisions to current url

    } else {
        var dataSources = new DataSourcesCollection();
        app.dataSources = dataSources;
        dataSources.fetch({
            success: function () {
                Router.initialize({appView: appView});
                $('#spinner').html('');
                appView.render();
            }
        });
    }
});
