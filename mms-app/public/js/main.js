require.config({
    baseUrl: "/assets/js",
    paths: {
        // Major libraries
        backbone: 'lib/backbone/backbone',
        'backbone-forms': 'lib/backbone/backbone-forms',
        bootstrap: 'lib/bootstrap/bootstrap.min',
        jquery: 'lib/jquery/jquery-1.8.2',
        underscore: 'lib/underscore/underscore',

        // Require.js plugins
        cs: 'lib/require/cs',
        text: 'lib/require/text',

        // Put HTML outside the js dir
        templates: '../templates'
    }
});

require([
    'jquery',
    'cs!views/app',
    'cs!router',
    'cs!vm',
    'cs!events',
    'cs!collections/data_sources',
    'backbone',
    'lib/backbone/templates/bootstrap'
], function($, AppView, Router, Vm, app, DataSourcesCollection, Backbone) {
    require(['bootstrap']);
    var dataSources = new DataSourcesCollection();
    app.dataSources = dataSources;
    var appView = Vm.create({}, 'AppView', AppView, {app: app});
    //setTimeout(function () {
    dataSources.fetch({
        success: function () {
            Router.initialize({appView: appView});
            $('#spinner').html('');
            appView.render();
        }
    });
    //},2000);
});
