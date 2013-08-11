define [
  'jquery'
  'underscore'
  'backbone'
  'cs!events'
  'cs!vm'
  'cs!components/pageable_view'
  'cs!components/paginator'
  'text!templates/glossary/access.html'
  'text!templates/glossary/access_changes.html'
], ($, _, Backbone, Handlebars, app, Vm, PageableView, Paginator, accessTemplate, accessChangesTemplate) ->

  PageableView.extend

    events: ->
      _.extend {}, PageableView.prototype.events,
        'submit #accessForm': 'update'

    template: accessTemplate

    _findChanges: ->
      re = /access\[(\d+)\]\[(\d+)\]\['(c|r|u|d)'\]/
      vals = $('#accessForm').find('.btn.active').map((i, btn) ->
        match = re.exec(btn.name)
        termId = match[1]
        groupId = match[2]
        access = match[3]
        {
          termId: termId
          groupId: groupId
          access: access
        }
      ).get()
      data = _
        .chain(vals)
        .groupBy((a) ->
            a.termId + '|' + a.groupId
        )
        .pairs()
        .map((p) ->
          ids = p[0].split('|')
          access = _.pluck(p[1], 'access').join('')
          {
            termId: ids[0]
            groupId: ids[1]
            access: access
          }
        )
        .value()
      changes = _.object(_.map(data, (a) ->
        [a.termId + '|' + a.groupId, a])
      )
      _.extend(@changes, changes)

    prePageChange: ->
      this._findChanges()

    update: (event) ->
      event.preventDefault()
      this._findChanges()
      originalValues = _.clone(@originalValues)
      c1 = _
        .chain(@changes)
        .map((val, key) ->
          originalValue = originalValues[key]
          access = val.access.toUpperCase().split('')
          if originalValue
            originalAccess = originalValue.access.toUpperCase().split('')
            accessChanges = ['C', 'R', 'U', 'D'].map (a) ->
              if _.contains(originalAccess, a)
                if _.contains(access, a)
                  'X'
                else
                  a + '-'
              else
                if _.contains(access, a)
                  a + '-'
                else
                  'X'

            delete originalValues[key]
          else
            accessChanges = ['C', 'R', 'U', 'D'].map (a) ->
              if _.contains(access, a)
                a + '+'
              else
                'X'

          [key,
            termId: val.termId
            groupId: val.groupId
            accessChanges: accessChanges
          ]
        )
        .object()
        .value()
      c2 = _
        .chain(originalValues)
        .map((val, key) ->
          accessChanges = ['C', 'R', 'U', 'D'].map (a) ->
            if _.contains(val.access, a)
              a + '-'
            else
              'X'

          [key,
            termId: val.termId
            groupId: val.groupId
            accessChanges: accessChanges
          ]
        )
        .object()
        .value()
      combined = _.extend {}, c1, c2
      changes = _.map combined, (a) =>
        term: @pageableCollection.fullCollection.get(a.termId)
        group: @groups.get(a.groupId)
        access: a.accessChanges
      compiled = Handlebars.compile accessChangesTemplate
      modal = new Backbone.BootstrapModal
        title: 'Confirm Changes'
        content: compiled {changes: changes}
        animate: true
      modal.open()
      modal.on 'ok', _.bind(this.ok, this)
      @data = _.values @changes

    ok: ->
      $.ajax
        url: '/access-privileges'
        type: 'put'
        contentType: 'application/json'
        data: JSON.stringify(@data)
        success: =>
          this.render().done ->
            app.resetCache('terms')
            $('#alert')
              .find('strong').html('Access privileges have been successfully updated')
              .parent().removeClass('alert-error').addClass('alert-success in')
              .show()
        error: (jqXHR, textStatus, errorThrown) ->
          $('#alert')
            .find('strong').html(errorThrown)
            .parent().removeClass('alert-success').addClass('alert-error in')
            .show()
      return false

    initialize: (options) ->
      PageableView.prototype.initialize.call(this, options.terms)
      @originalValues = {}
      @changes = {}
      this.hasData(options, this.render)

    preRender: ->
      if @groupPaginator
        this.stopListening(@groupPaginator)
        @groupPaginator.clean?()

    doRender: ->
      @$el.html @compiled
        pageableCollection: @terms
        groups: @groups.toJSON()

      values = {}
      _.each @terms.models, (term) ->
        _.each term.get('accessPrivileges').models, (ap) ->
#          _.each ap.get('access').split(''), (a) ->
#            $("#access-#{term.id}-#{ap.get('userGroup').id}-#{a}").button('toggle')
          termId = term.id
          groupId = ap.get('userGroup').id
          values["#termId|#groupId"] =
            termId: termId
            groupId: groupId
            access: ap.get('access')

      _.extend(@originalValues, values)
      settings = _.extend({}, values, @changes)
      _.chain(settings).values().each (s) ->
        _.each s.access.split(''), (a) ->
          elem = $("#access-#{s.termId}-#{s.groupId}-#{a}")
          elem.button('toggle') if elem.length

    postRender: ->
      @groupPaginator = paginator = Vm.create(this, 'GroupPaginator', Paginator, {pageableCollection: @groups})
      paginator.setElement('#group-paginator')
      paginator.render()
      this.listenTo paginator, 'previous next', this.prePageChange
      this.listenTo paginator, 'previous next', this.render
