module.exports = (grunt) ->

  grunt.initConfig
    pkg: grunt.file.readJSON('package.json')

    clean:
      debug: ['dist/debug']
      release: ['dist/release']
      coverage: ['dist/coverage']

    jshint:
      options:
        scripturl: true
        eqeqeq: false
        eqnull: false
        undef: true
        globals:
          define: true
          require: true
          jquery: true
          alert: true
          console: true
          '_': true
          backbone: true
          window: true
          setTimeout: true
      checkApp: ['modules/**/target/scala_2.10/public/**/*.js']

    handlebars:
      compile:
        files:
          'dist/templates.js': [
            'modules/**/templates/**/*.html'
          ]
        options:
          wrapped: false

    requirejs:
      compile:
        options:
          mainConfigFile: '/app/assets/javascripts/config.js'
          out: 'dist/debug/require.js'
          name: 'config'
          wrap: false
          optimize: 'none'

    concat:
      compile:
        files:
          'dist/debug/require.js': [
            'public/js/libs/almond.js'
            'dist/debug/require.js'
            'dist/templates.js'
          ]

    cssmin:
      release:
        files: [
          expand: true
          cwd: 'dist/release/assets/css/'
          src: ['main.css']
          dest: 'dist/release/assets/css/'
        ]

  grunt.loadNpmTasks('grunt-contrib-clean')
  grunt.loadNpmTasks('grunt-contrib-jshint')
  grunt.loadNpmTasks('grunt-contrib-handlebars')
  grunt.loadNpmTasks('grunt-contrib-requirejs')
  grunt.loadNpmTasks('grunt-contrib-concat')
  grunt.loadNpmTasks('grunt-contrib-cssmin')

  grunt.registerTask('debug', 'debug', [
    'clean:debug', 'handlebars', 'requirejs', 'concat'
  ])
