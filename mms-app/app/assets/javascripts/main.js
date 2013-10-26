define('global', [], function () {
  return window;
});

require(['config'], function () {
  require(['framework/backbone_sub'], function () {
    require([
      'jquery',
      'underscore',
      'backbone',
      'handlebars',
      'events',
      'router',
      'vm',
      'views/app',
      'models/session',
      'snap',
      'views/searchbox',
      'views/helpers',
      'backbone-associations',
      'backbone.bootstrap-modal',
      'backbone.layoutmanager',
      'backbone.collectionsubset',
      'lib/backbone-forms/templates/bootstrap',
      'components/form',
      'visualsearch',
      'bootstrap'
    ], function ($, _, Backbone, Handlebars, app, Router, Vm, AppView, Session, Snap, SearchboxView) {

      require([
        'lib/bootstrap-growl/jquery.bootstrap-growl.min',
        'lib/jquery/jquery.autogrow-textarea',
        'lib/jquery.equalHeights',
        'lib/jquery-file-upload/vendor/bootstrap-image-gallery.min',
        'lib/jquery/jquery-migrate-1.1.1.min'
      ]);

      var appView = new AppView();
      appView.render();
      app.view = appView;
      var searchbox = new SearchboxView();
      searchbox.render();

      Backbone.Collection.prototype.__fetched = false;
      var fetch = Backbone.Collection.prototype.fetch;
      Backbone.Collection.prototype.fetch = function (options) {
        this.__fetched = false;
        this.once('sync', function (collection) {
          collection.__fetched = true;
        });
        return fetch.call(this, options);
      };
      Backbone.Collection.prototype.isFetched = function () {
        return this.__fetched;
      };

      Backbone.View.prototype.delay = function (ms, fn) {
        return setTimeout(fn, ms);
      };

      // Sliding main window
      // https://github.com/jakiestfu/Snap.js/
      var snapper = new Snap({
        element: $('#main')[0],
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
  });
});

var v0 = function (fn) {
  fn();
  return void 0;
};
