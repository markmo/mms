require(['config'], function () {
  require(['cs!framework/backbone_sub'], function () {
    require([
      'jquery',
      'underscore',
      'backbone',
      'handlebars',
      'cs!events',
      'cs!router',
      'cs!vm',
      'cs!views/app',
      'cs!models/session',
      'snap',
//  'text!templates/search_results.html',
      'views/helpers',
      'backbone-associations',
      'backbone.bootstrap-modal',
      'backbone.layoutmanager',
      'backbone.collectionsubset',
      'lib/backbone-forms/templates/bootstrap',
      'cs!components/form',
      'visualsearch',
      'bootstrap'
    ], function ($, _, Backbone, Handlebars, app, Router, Vm, AppView, Session, Snap, searchResultsTemplate) {

      require([
        'lib/bootstrap-growl/jquery.bootstrap-growl.min',
        'lib/jquery/jquery.autogrow-textarea',
        'lib/jquery.equalHeights',
        'lib/jquery-file-upload/vendor/bootstrap-image-gallery.min',
        'lib/jquery/jquery-migrate-1.1.1.min'
      ]);

      var appView = Vm.create({}, 'AppView', AppView, {app: app});
      app.view = appView;

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

      /**
       var facets = {};
       var facetLabels = {
    conceptType: 'concept',
    datasource: 'datasource',
    dataType: 'data type',
    domain: 'domain',
    objectType: 'obj. type',
    securityClassification: 'sec. class.',
    tags_name: 'tag'
  };
       var facetLabelReverseLookup = _.invert(facetLabels);
       var availFacets = [];
       $.ajax({
    type: 'POST',
    url: 'http://localhost:9200/mms/_search',
    data: '{"query":{"match_all":{}},"facets":{"objectType":{"terms":{"field":"objectType"}},"dataType":{"terms":{"field":"dataType"}},"tags.name":{"terms":{"field":"tags.name"},"nested":"tags"},"domain":{"terms":{"field":"domain"}},"securityClassification":{"terms":{"field":"securityClassification"}},"conceptType":{"terms":{"field":"conceptType"}},"datasource":{"terms":{"field":"datasource"}}}}',
    success: function (result) {
      var name, facet;
      var fs = result.facets;
      for (var key in fs) {
        name = key;
        facet = fs[key];
        if (name.indexOf('.') !== -1) {
          name = name.replace('.', '_');
        }
        facets[name] = _.map(facet.terms, function (term, i) {
          return {value: term.term, label: term.term};//{value: i + 1 + '', label: term.term};
        });
      }
      availFacets = _.chain(facets)
          .pairs()
          .filter(function (pair) {
            return pair[1].length;
          })
          .map(function (pair) {
            var key = pair[0];
            return facetLabels[key];
          })
          .value();
    }
  });

       var template = Handlebars.compile(searchResultsTemplate);
       $(document).ready(function () {
    var visualSearch = VS.init({
      container: $('.visual_search'),
      query: '',
      callbacks: {
        search: function (query, searchCollection) {
//          $('#search').removeClass('wide');
//          $('.nav-collapse').show();
          var qstart = '{"highlight":{"fields":{"de*":{}}},"query":{"bool":{"must":[';
          var qend = ']}}}';
          var qs = '';
          var n = searchCollection.length;
          for (var i = 0; i < n; i += 1) {
            var model = searchCollection.at(i);
            var t = model.toJSON();
            if (t.category === 'text') {
              qs += '{"query_string":{"query":"' + t.value + '","default_operator":"OR"}}';
            } else {
              var facet = facetLabelReverseLookup[t.category];
              var term = t.value;//facets[facet][t.value - 1];
              //qs += '{"term":{"' + facet + '":"' + term.label + '"}}';
              qs += '{"term":{"' + facet + '":"' + term + '"}}';
            }
            if (i < n - 1) qs += ',';
          }
          var data = qstart + qs + qend;
          $.ajax({
            type: 'POST',
            url: 'http://localhost:9200/mms/_search',
            data: data,
            success: function (result) {
              var hits = _.map(result.hits.hits, function (hit) {
                var ret = hit._source;
                var highlight = hit.highlight;
                if (highlight) {
                  var highlights = [];
                  for (var k in highlight) {
                    var text = '&hellip; ' + highlight[k];
                    highlights.push(text);
                  }
                  ret["_highlights"] = highlights.join('<br>');
                }
                return ret;
              });
              $('#shade')
                  .height($(document).height())
                  .addClass('on');
              $('#search-results').html(template({hits: hits}))
                  .animate({
                    top: '39px'
                  }, 250);
            }
          });
        },
        facetMatches: function (callback) {
          callback(_.map(availFacets, function (facet) {
            return {label: facet, category: 'facets'};
          }));
        },
        valueMatches: function (facet, searchTerm, callback) {
          switch (facet) {
            case 'concept':
              callback(facets.conceptType);
              break;
            case 'datasource':
              callback(facets.datasource);
              break;
            case 'data type':
              callback(facets.dataType);
              break;
            case 'domain':
//              app.domains().done(function (coll) {
//                var domain = _.map(coll.models, function (domain) {
//                  return {value: domain.id + '', label: domain.get('name')};
//                });
                callback(facets.domain);
//              });
              break;
            case 'obj. type':
//              callback([
//                {value: '1', label: 'Business Term'},
//                {value: '1', label: 'Dataset'},
//                {value: '1', label: 'Column'}
//              ]);
              callback(facets.objectType);
              break;
            case 'sec. class.':
              callback(facets.securityClassification);
              break;
            case 'tag':
//              app.tags().done(function (coll) {
//                var tags = _.map(coll.models, function (tag) {
//                  return {value: tag.id + '', label: tag.get('name')};
//                });
                callback(facets.tags_name);
//              });
              break;
          }
        }
      }
    });
    var closeSearchResults = function () {
      visualSearch.searchBox.value('');
      $('#search-results').animate({
        top: '-500px'
      }, 250);
      $('#search').removeClass('wide');
      $('.nav-collapse').show();
      $('#user-control').show();
      $('#shade').removeClass('on');
    };
    $('#search').on('focus', 'input', function (event) {
      $('.nav-collapse').hide();
      $('#user-control').hide();
      $(event.delegateTarget).addClass('wide');
    });
    $(document).on('click', function (event) {
      var target = event.target;
      var contained = false;
      $('#search,#search-results').each(function () {
        if ($.contains(this, target)) {
          contained = true;
          return false; // break out of loop
        }
        return true;
      });
      contained = contained || $(target).parents().filter('.VS-interface').length > 0;
      if (!contained) {
        $('#search').removeClass('wide');
        closeSearchResults();
      }
    });
    var baseUrl = '/';//'http://localhost:9000/';
    $('#search-results')
        .on('click', 'li', function (event) {
          event.preventDefault();
          var li = $(this);
          var uri = li.data('uri');
          location.href = baseUrl + '#/' + uri;
          // load the next page before hiding the search results
          setTimeout(function () {
            closeSearchResults();
          }, 1000);
          return false;
        })
        .on('click', 'button.close', function (event) {
          closeSearchResults();
        });
  });
       */

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
