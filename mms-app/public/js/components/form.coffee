define [
    'underscore'
    'backbone'
    'text!templates/components/form.html'
    'backbone-forms'
], (_, Backbone, formTemplate) ->

    Backbone.Form.editors.Markdown = Backbone.Form.editors.Base.extend

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


    Backbone.Form.setTemplates
        form: formTemplate

    Backbone.Form.extend

        events:
            'click #btnCancel': 'cancel'
            'click #btnSubmit': 'submit'

        initialize: (model) ->
            if model instanceof Backbone.Model
                options = {
                    fieldsets: [
                        legend: model.get('friendlyName') || model.get('name') || model.get('description')
                        fields: _.keys(model.schema)
                    ]
                    model: model
                }
            else
                options = model
            Backbone.Form.prototype.initialize.call(this, options)

        cancel: (event) ->
            event.preventDefault()
            @trigger 'cancel'

        submit: (event) ->
            event.preventDefault()
            @trigger 'submit'
