define [
    'underscore'
    'backbone'
    'cs!events'
    'cs!models/tag'
    'text!templates/components/form.html'
    'backbone-forms'
    'jsonform'
    'tagautocomplete'
], (_, Backbone, app, Tag, formTemplate) ->

    Backbone.Form.editors.Access = Backbone.Form.editors.Base.extend

        tagName: 'div'

        initialize: (options) ->
            Backbone.Form.editors.Base.prototype.initialize.call(this, options)
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


    Backbone.Form.editors.Columns = Backbone.Form.editors.Select.extend

        initialize: (options) ->
            Backbone.Form.editors.Base.prototype.initialize.call(this, options)
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


    Backbone.Form.editors.CustomMetadata = Backbone.Form.editors.Base.extend

        tagName: 'div'

        initialize: (options) ->
            Backbone.Form.editors.Base.prototype.initialize.call(this, options)
            @$el.attr('id', 'custom-form')
                .attr('data-snap-ignore', true)
                .addClass('custom form-vertical')
            this.render()
            $.ajax(
                type: 'GET'
                url: '/settings'
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


    Backbone.Form.editors.Domains = Backbone.Form.editors.Select.extend

        initialize: (options) ->
            Backbone.Form.editors.Base.prototype.initialize.call(this, options)
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


    Backbone.Form.editors.ModelSelect = Backbone.Form.editors.Select.extend

        initialize: (options) ->
            collectionName = options.schema.collection
            options.schema.options = (callback) ->
                app[collectionName].call(app).done (coll) ->
                    array = coll.map (model) ->
                        {val: model.id, label: model.toString()}
                    array.unshift({val: null, label: ''})
                    callback(array)
            Backbone.Form.editors.Base.prototype.initialize.call(this, options)
            app[collectionName].call(app).done (coll) =>
                @coll = coll

        getValue: ->
            id = @$el.val()
            if id?
                model = @coll.get(id)
                model or null
            else null

        setValue: (value) ->
            if value instanceof Backbone.Model
                @$el.val(value.id)


    Backbone.Form.editors.SecurityClassifications = Backbone.Form.editors.Select.extend

        initialize: (options) ->
            Backbone.Form.editors.Base.prototype.initialize.call(this, options)
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


    Backbone.Form.editors.Tags = Backbone.Form.editors.Text.extend

        initialize: (options) ->
            Backbone.Form.editors.Base.prototype.initialize.call(this, options)
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
                namesArray = value.map (model) -> model.get('name')
                @$el.val(namesArray.join())
            return


    Backbone.Form.editors.TaggableContent = Backbone.Form.editors.Base.extend

        tagName: 'div'

        initialize: (options) ->
            Backbone.Form.editors.Base.prototype.initialize.call(this, options)
            @$el.attr('contenteditable', true).addClass('autotag')
            app.terms().done (terms) =>
                @terms = terms
                tags = terms.map (term) -> ('#' + term.toString())
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


    Backbone.Form.editors.Terms = Backbone.Form.editors.Select.extend

        initialize: (options) ->
            Backbone.Form.editors.Base.prototype.initialize.call(this, options)
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
        form_with_buttons: formTemplate

    Backbone.Form.extend

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
            Backbone.Form.prototype.initialize.call(this, opts)

        cancel: (event) ->
            event.preventDefault()
            this.trigger 'cancel'

        submit: (event) ->
            event.preventDefault()
            this.trigger 'submit'
