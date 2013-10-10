define [
  'jquery'
  'backbone'
  'cs!events'
  'cs!components/form'
  'cs!models/term'
  'chosen'
  'bootstrap-tagautocomplete'
], ($, Backbone, app, Form, Term) ->

  Backbone.View.extend

    el: '#attributes'

    initialize: (options) ->
      term = options.model
      parentTerm = options.parentTerm
      @tags = options.tags
      if term
        @old = term.toJSON()
        term.set('parent', parentTerm) if parentTerm
      else
        term = new Term
          parent: parentTerm
      form = @form = new Form(term,
        title: 'Define Term'
        template: 'form_with_buttons'
      )
      form.on 'cancel', this.cancel
      form.on 'submit', this.submit
      $el = @$el
      app.on 'change:domains', ->
        $el.find('select[name="domain"]').trigger('liszt:updated')
      app.on 'change:terms', ->
        $el.find('select[name="parent"]').trigger('liszt:updated')
      app.on 'change:tags', ->
        $el.find('select[name="tags"]').trigger('liszt:updated')
      app.on 'change:columns', ->
        $el.find('select[name="representations"]').trigger('liszt:updated')

    submit: ->
      term = @model
      if errors = @form.validate()
        # do nothing
      else
        term.set(@form.getValue())
        if term.id
          term.save({}, {success: this.onSuccess, error: this.onError})
        else
          @collection.add(term)
          term.save({}, {success: this.onSuccess, error: this.onError})
      return false

    onError: (model, xhr, options) ->
      @model.set(@old)
      errorThrown = xhr.responseText
      formError = $('.modal form .form-error')
      if formError.length
        formError.text(errorThrown)
      else
        $('.modal form').prepend('<div class="form-error">' + errorThrown + '</div>')

    onSuccess: (model, response, options) ->
      this.trigger('closed', model.id)

    cancel: ->
      this.trigger('closed')

    render: ->
      app.loadCss '/assets/css/chosen/chosen.css'
      app.loadCss '/assets/css/select2/select2.css'
      @$el.html @form.render().el
      this.afterRender()

    afterRender: ->
#      @$el.find('select[name="representations"]')
#        .attr('data-placeholder', 'Select a Column')
#        .chosen
#            no_results_text: 'No results matched'
#            allow_single_deselect: true

#      @$el.find('select[name="tags"]')
#          .attr('data-placeholder', 'Type one or more tags')
#          .attr('multiple', '')
#          .chosen()

      array = @tags.map (model) ->
        id: model.id
        text: model.toString()
      @$el.find('input[name="tags"]')
        .attr('data-placeholder', 'Type one or more tags')
        .attr('multiple', '')
        .select2({tags: array, width: '380px'})

      @$el.find('select[name="parent"]')
        .attr('data-placeholder', 'Select the parent term')
        .chosen
          no_results_text: 'No results matched'
          allow_single_deselect: true

      @$el.find('select[name="domain"]')
        .attr('data-placeholder', 'Select the domain')
        .chosen
          no_results_text: 'No results matched'
          allow_single_deselect: true

      @$el.find('textarea').css('overflow', 'hidden').autogrow()

      $('#attributes').resize ->
        docHeight = $(document).height()
        $('.drawers').css('height', docHeight)
        $('#main').css('height', docHeight)

    close: ->
      app.off 'change:columns'
      app.off 'change:tags'
      app.off 'change:terms'
      app.off 'change:domains'
      @form.remove()
      app.unloadCss '/assets/css/select2/select2.css'
      app.unloadCss '/assets/css/chosen/chosen.css'
      return
