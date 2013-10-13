require.config({

  baseUrl: '/assets/js',

  paths: {
    'backbone': 'lib/backbone/backbone',
    'backbone-associations': 'lib/backbone-associations/backbone-associations',
    'backbone.bootstrap-modal': 'lib/backbone.bootstrap-modal/backbone.bootstrap-modal',
    'backbone.collectionsubset': 'lib/backbone.collectionsubset/backbone.collectionsubset',
    'backbone-forms': 'lib/backbone-forms/backbone-forms',
    'backbone.layoutmanager': 'lib/backbone.layoutmanager/backbone.layoutmanager',
    'backbone-pageable': 'lib/backbone-pageable/backbone-pageable',
    'bootstrap': 'lib/bootstrap/bootstrap',
    'bootstrap-switch': 'lib/bootstrap-switch/bootstrap-switch',
    'bootstrap-tagautocomplete': 'lib/tagautocomplete/bootstrap-tagautocomplete',
    'bootstrap-typeahead': 'lib/tagautocomplete/bootstrap-typeahead',
    'canvas-to-blob': 'lib/jquery-file-upload/vendor/canvas-to-blob.min',
    'caret-position': 'lib/tagautocomplete/caret-position',
    'chosen': 'lib/chosen/chosen.jquery.min',
    'daterangepicker': 'lib/daterangepicker/daterangepicker',
    'dotindicator': 'lib/dotindicator/dotindicator',
    'handlebars': 'lib/handlebars/handlebars',
    'jqtree': 'lib/jqtree/tree.jquery',
    'jquery': 'lib/jquery/jquery-1.9.1',
    'jquery.ba-resize': 'lib/jquery/jquery.ba-resize.min',
    'jquery.cookie': 'lib/jqtree/jquery.cookie',
    'jquery.facetview': 'lib/facetview/jquery.facetview',
    'jquery.linkify': 'lib/linkify/1.0/jquery.linkify-1.0-min',
    'jquery.ui.autocomplete': 'lib/jqueryui/jquery.ui.autocomplete',
    'jquery.ui.core': 'lib/jqueryui/jquery.ui.core',
    'jquery-ui.custom': 'lib/jquery-ui-1.8.18.custom/jquery-ui-1.8.18.custom.min',
    'jquery.ui.menu': 'lib/jqueryui/jquery.ui.menu',
    'jquery.ui.position': 'lib/jqueryui/jquery.ui.position',
    'jquery.ui.widget': 'lib/jquery-file-upload/vendor/jquery.ui.widget',
    'jsonform': 'lib/jsonform/jsonform',
    'load-image': 'lib/jquery-file-upload/vendor/load-image.min',
    'moment': 'lib/moment/moment',
    'rangy-core': 'lib/tagautocomplete/rangy-core',
    'select2': 'lib/select2/select2',
    'snap': 'lib/snap/snap.min',
    'tmpl': 'lib/jquery-file-upload/vendor/tmpl.min',
    'underscore': 'lib/underscore/underscore',
    'visualsearch': 'lib/visualsearch/visualsearch',

//    'annotator': 'lib/annotator/annotator-full.min',
//    'backbone.marionnette': 'lib/backbone.marionette/backbone.marionette',
//    'jquery.ui.widget': 'lib/jqueryui/jquery.ui.widget',

    // has relative dependencies
    //'jquery.fileupload-ui': 'lib/jquery-file-upload/jquery.fileupload-ui',
    //'jquery.fileupload': 'lib/jquery-file-upload/jquery.fileupload',

    // Require.js plugins
    cs: 'lib/require/cs',
    text: 'lib/require/text',

    // Put HTML outside the js dir
    templates: '../templates'
  },

  shim: {
    backbone: {
      deps: ['underscore', 'jquery'],
      exports: 'Backbone'
    },
    'backbone-associations': {
      deps: ['backbone']
    },
    'backbone.bootstrap-modal': {
      deps: ['jquery', 'underscore', 'backbone']
    },
    'backbone.collectionsubset': {
      deps: ['backbone']
    },
    'bootstrap': {
      deps: ['jquery']
    },
    'bootstrap-tagautocomplete': {
      deps: ['bootstrap-typeahead', 'rangy-core', 'caret-position']
    },
    'handlebars': {
      exports: 'Handlebars'
    },
    'jquery.facetview': {
      deps: ['jquery', 'jquery-ui.custom', 'linkify']
    },
    'jquery.ui.autocomplete': {
      deps: ['jquery.ui.menu']
    },
    'jquery.ui.core': {
      deps: ['jquery']
    },
    'jquery.ui.menu': {
      deps: ['jquery.ui.position', 'jquery.ui.widget']
    },
    'jquery.ui.position': {
      deps: ['jquery.ui.core']
    },
    underscore: {
      exports: '_'
    },
    'visualsearch': {
      deps: ['jquery.ui.autocomplete', 'underscore', 'backbone']
    }
  }
});
