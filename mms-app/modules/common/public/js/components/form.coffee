###*
* A form editor is paired with an HTML form field to control how information
* is input and how it is displayed.
*
* @module form
###
define [
  'jquery'
  'underscore'
  'backbone'
  'backbone-forms'
  'cs!events'
  'cs!models/tag'
  'text!templates/components/form.html'
  'select2'
  'bootstrap-switch'
  'jsonform'
], ($, _, Backbone, Form, app, Tag, formTemplate) ->

  Form.editors.Access = Form.editors.Base.extend

    tagName: 'div'

    initialize: (options) ->
      Form.editors.Base.prototype.initialize.call(this, options)
      template = """
                 <div class="btn-group" data-toggle="buttons-checkbox">
                 <button id="c" type="button" class="btn btn-mini create">C</button>
                 <button id="r" type="button" class="btn btn-mini read">R</button>
                 <button id="u" type="button" class="btn btn-mini update">U</button>
                 <button id="d" type="button" class="btn btn-mini delete">D</button>
                 </div>"""

      @$el.html(template)
      this.setValue(@value)
      return

    getValue: ->
      @$el.find('.btn.active').map((i, btn) ->
        btn.id
      ).get().join('')

    setValue: (value) ->
      if value and value.length
        rights = value.toLowerCase().split('')
        _.each rights, (r) =>
          @$el.find('#' + r).button('toggle')


  Form.editors.Columns = Form.editors.Select.extend

    initialize: (options) ->
      Form.editors.Base.prototype.initialize.call(this, options)
      app.columns().done (columns) =>
        @columns = columns

    getValue: ->
      id = @$el.val()
      if id?
        column = @columns.get(id)
        column or null
      else null

    setValue: (value) ->
      if value instanceof Backbone.Model
        @$el.val(value.id)


  Form.editors.CustomMetadata = Form.editors.Base.extend

    tagName: 'div'

    initialize: (options) ->
      Form.editors.Base.prototype.initialize.call(this, options)
      @$el.attr('id', 'custom-form')
      .attr('data-snap-ignore', true)
      .addClass('custom form-vertical')
      this.render()
      $.ajax(
        type: 'GET'
        url: '/glossary/settings'
      ).done((schema) =>
        $('#custom-form').jsonForm(this.getSchema(schema, @value))
        return
      ).fail((jqXHR, textStatus, errorThrown) ->
        alert(errorThrown)
      )
      return this

    getValue: ->
      data = $('#custom-form').data('jsonform-tree')
      if data then JSON.stringify(data.root.getFormValues()) else null

    setValue: (value) ->

    getSchema: (schema, value) ->
      vo = JSON.parse(value)
      if schema['schema']
        _.extend(schema, {value: vo})
      else
        schema: schema
        value: vo
        form: []


  Form.editors.Domains = Form.editors.Select.extend

    initialize: (options) ->
      Form.editors.Base.prototype.initialize.call(this, options)
      app.domains().done (domains) =>
        @domains = domains

    getValue: ->
      id = @$el.val()
      if id?
        domain = @domains.get(id)
        domain or null
      else null

    setValue: (value) ->
      if value instanceof Backbone.Model
        @$el.val(value.id)


  ###*
  * The ModelSelect editor populates a dropdown with a list of options from a collection
  * whereby the option value is the id of the mocel object, and the display name is the
  * output of the model's toString function. It expects the following properties on the
  * field's schema object:
  * <ul>
  * <li>collection - the collection key in the collection hash (currently in the router)
  * <li>placeholder - the placeholder text to display when no value has been selected
  * </ul>
  * The options list is loaded asynchronously.
  *
  * The ModelSelect editor can also be configured for multiple select fields. The following
  * additional properties are required on the field's schema object:
  * <ul>
  * <li>editorAttrs: {multiple: ''}
  * </ul>
  *
  * @class Form.editors.ModelSelect
  * @constructor
  * @extends Form.editors.Select
  * @author markmo
  ###
  Form.editors.ModelSelect = Form.editors.Select.extend

    ###*
    * Initializes the editor.
    *
    * @method initialize
    * @param {Object} options
    ###
    initialize: (options) ->
      collectionName = options.schema.collection
      if (collectionName)
        options.schema.options = (callback) =>
          dfd = app[collectionName].call app, null,
            state: {pageSize: 99}
            queryParams: options.schema.filterParams
          dfd.done (coll) =>
            @collection = coll
            array = coll.map (model) ->
              {val: model.id, label: model.toString()}
            if options.schema.editorAttrs?.multiple
              model = options.model
              if model
                url = "#{model.collection.url}/#{model.id}/#{options.key}"
                $.ajax(url).done =>
                  @value = value
                  callback(array)
                  setTimeout(=>
                    @$el.select2
                      placeholder: options.schema.placeholder
                      maximumSelectionSize: options.schema.maximumSelectionSize || 99
                  ,50)
              else
                callback(array)
                setTimeout(=>
                  @$el.select2
                    placeholder: options.schema.placeholder
                    maximumSelectionSize: options.schema.maximumSelectionSize || 99
                ,250)
            else
              array.unshift({val: null, label: ''})
              callback(array)
              setTimeout(=>
                @$el.select2
                  placeholder: options.schema.placeholder
              ,250)
      else
        options.schema.options = []
      Form.editors.Base.prototype.initialize.call(this, options)

    ###*
    * Returns the selected Backbone.Model.
    *
    * @method getValue
    * @return the selected Backbone.Model
    ###
    getValue: ->
      id = @$el.val()
      if id
        if _.isArray(id) then id else @collection.get(id)
      else null

    ###*
    * Sets the value. The value can be a Backbone.Model object, an array of models,
    * or an array of model ids.
    *
    * @method setValue
    * @param {Backbone.Model|Array[Backbone.Model]|Array[int]} value
    ###
    setValue: (value) ->
      value = '' unless value
      if _.isArray(value) and value.length
        if value[0] instanceof Backbone.Model
          val = _(value).map (model) ->
            model.id
        else
          val = value
      else
        if value instanceof Backbone.Model
          val = value.id
        else
          val = value
      @$el.val(val).trigger('change')

    ###*
    * Turns off event listeners when this field is removed.
    *
    * @method remove
    ###
    remove: ->
      this.off()


  ###*
  * The SearchSelect editor is for dropdowns with very long lists, which require
  * a search interface to narrow the list range. It expects the following properties
  * on the field's schema object:
  * <ul>
  * <li>collection - the collection key in the collection hash (currently in the router)
  * <li>placeholder - the placeholder text to display when no value has been selected
  * <li>searchKey - the model property to search on
  * </ul>
  *
  * @class Form.editors.SearchSelect
  * @constructor
  * @extends Form.editors.Base
  * @author markmo
  ###
  Form.editors.SearchSelect = Form.editors.Base.extend

    ###*
    * The HTML element to use as the basis of the form field.
    *
    * @property {String} tagName
    ###
    tagName: 'input'

    ###*
    * Initializes the editor.
    *
    * @method initialise
    * @param {Object} options
    ###
    initialize: (options) ->
      Form.editors.Hidden.prototype.initialize.call(this, options)
      collectionName = options.schema.collection
      setTimeout(=>
        @$el.select2
          placeholder: options.schema.placeholder
          minimumInputLength: 2
          query: (options) =>
            dfd = app[collectionName].call app, null,
              state: {pageSize: 999}
            dfd.done (coll) =>
              coll.queryParams[options.schema.searchKey] = "%#{options.term}%"
              array = coll.map (model) ->
                {val: model.id, label: model.toString()}
              data = {results: array}
              options.callback(data)
        @$el.select2('data', @value)
      ,250)

    ###*
    * Returns the selected Backbone.Model.
    *
    * @method getValue
    * @return the selected Backbone.Model
    ###
    getValue: ->
      @$el.val()
#      id = @$el.val()
#      if id
#        if _.isArray(id) then id else @collection.get(id)
#      else null

    ###*
    * Sets the value. The value can be a Backbone.Model object, an array of models,
    * or an array of model ids.
    *
    * @method setValue
    * @param {Backbone.Model|Array[Backbone.Model]|Array[int]} value
    ###
    setValue: (value) ->
      value = '' unless value
      if _.isArray(value) and value.length
        if value[0] instanceof Backbone.Model
          val = _(value).map (model) ->
            model.id
        else
          val = value
      else
        if value instanceof Backbone.Model
          val = value.id
        else
          val = value
      @$el.select2('data', val)

    ###*
    * Turns off event listeners when this field is removed.
    *
    * @method remove
    ###
    remove: ->
      this.off()


  ###*
  * The Switch editor displays a sliding toggle switch instead of a checkbox.
  *
  * @class Form.editors.Switch
  * @constructor
  * @extends Form.editors.Base
  * @author markmo
  ###
  Form.editors.Switch = Form.editors.Base.extend({

    ###*
    * Initializes the editor.
    *
    * @method initialise
    * @param {Object} options
    ###
    initialize: (options) ->
      template = options.template || @constructor.template
      $el = $(template())
      $el.bootstrapSwitch()
      this.setElement($el)
      @input = $el.find('input[type="checkbox"]')
      Form.editors.Base.prototype.initialize.call(this, options)

    ###*
    * Returns a boolean value to indicate if the field has been checked.
    *
    * @method getValue
    * @return {Boolean} value
    ###
    getValue: ->
      @input.prop('checked')

    ###*
    * Sets the boolean value of the field.
    *
    * @method setValue
    * @param {Boolean} value
    ###
    setValue: (value) ->
      @input.prop('checked', true) if value

  }, {
    ###*
    * The HTML template for the switch.
    *
    * @property template
    * @static
    ###
    template: _.template("<div class=\"make-switch\" data-on-label=\"Yes\" data-off-label=\"No\" data-on=\"info\">\n	<input type=\"checkbox\">\n</div>", null, Form.templateSettings)
  })


  Form.editors.Readonly = Form.editors.Base.extend

    tagName: 'label'

    className: 'readonly'

    render: ->
      this.setValue(@value)
      return this

    ###*
    * Returns the immutable value.
    *
    * @method getValue
    * @return {String} value
    ###
    getValue: ->
      @$el.text()

    ###*
    * Sets the immutable value of the field.
    *
    * @method setValue
    * @param {String} value
    ###
    setValue: (value) ->
      @$el.text(value) if value


  Form.editors.SecurityClassifications = Form.editors.Select.extend

    initialize: (options) ->
      Form.editors.Base.prototype.initialize.call(this, options)
      app.securityClassifications().done (securityClassifications) =>
        @securityClassifications = securityClassifications

    getValue: ->
      id = @$el.val()
      if id?
        securityClassification = @securityClassifications.get(id)
        securityClassification or null
      else null

    setValue: (value) ->
      if value instanceof Backbone.Model
        @$el.val(value.id)


  Form.editors.Tags = Form.editors.Text.extend

    initialize: (options) ->
      Form.editors.Base.prototype.initialize.call(this, options)
      app.tags().done (tags) =>
        @tags = tags

    getValue: ->
      @$el.val().split(',').map (idOrName) =>
        if /^\d+$/.test(idOrName)
          tag = @tags.get(idOrName)
        unless tag
          tag = @tags.findWhere({name: idOrName})
        tag or new Tag({name: idOrName})

    setValue: (value) ->
      if value instanceof Backbone.Collection
        namesArray = value.map (model) ->
          model.get('name')
        @$el.val(namesArray.join())
      return


  Form.editors.TaggableContent = Form.editors.Base.extend

    tagName: 'div'

    initialize: (options) ->
      Form.editors.Base.prototype.initialize.call(this, options)
      @$el.attr('contenteditable', true).addClass('autotag')
      app.terms().done (terms) =>
        @terms = terms
        tags = terms.map (term) ->
          ('#' + term.toString())
        @$el.tagautocomplete
          source: tags
          character: '#'
          updater: (val) ->
            if /\s/g.test(val)
              if /"/g.test(val)
                '#{' + val.substring(1) + '}'
              else '#"' + val.substring(1) + '"'
            else val
        this.setValue(@value) if @value
        return

    getValue: ->
      @$el.text()

    setValue: (value) ->
      @$el.text(value)


  Form.editors.Terms = Form.editors.Select.extend

    initialize: (options) ->
      Form.editors.Base.prototype.initialize.call(this, options)
      app.terms().done (terms) =>
        @terms = terms

    getValue: ->
      id = @$el.val()
      if id?
        term = @terms.get(id)
        term or null
      else null

    setValue: (value) ->
      if value instanceof Backbone.Model
        @$el.val(value.id)


  Form.editors.Markdown = Form.editors.Base.extend

    events:
      change: ->
        this.trigger('change', this)

      focus: ->
        this.trigger('focus', this)

      blur: ->
        this.trigger('blur', this)

    render: ->
      @$el.html '<div id="epiceditor"></div>'
      return this

    getValue: ->
      @$el.val()

    setValue: (value) ->
      @$el.val(value)
      return this

    focus: ->
      return if @hasFocus

    blur: ->
      return unless @hasFocus


  Form.setTemplates
    form_with_buttons: formTemplate

  Form.extend

    events:
      'click #btnCancel': 'cancel'
      'click #btnSubmit': 'submit'

    initialize: (model, options) ->
      unless options?.nolegend
        legend = model.get('friendlyName') || model.get('name') || model.get('description') || options?.title
      if model instanceof Backbone.Model
        opts = {
          fieldsets: [
            legend: legend
            fields: _.keys(model.schema)
          ]
          model: model
          template: options?.template || 'form'
        }
      else
        opts = model
      Form.prototype.initialize.call(this, opts)

    cancel: (event) ->
      event.preventDefault()
      this.trigger 'cancel'

    submit: (event) ->
      event.preventDefault()
      this.trigger 'submit'
