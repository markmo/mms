define [
    'jquery'
    'underscore'
    'backbone'
    'cs!events'
    'cs!vm'
    'cs!views/attachments'
    'cs!views/sandbox_form'
    'text!templates/home/main.html'
], ($, _, Backbone, app, Vm, AttachmentsSection, SandboxForm, homePageTemplate) ->
    Backbone.View.extend
        el: '#page'

        events:
            'click #create-sandbox': 'createSandbox'
            'click #btn-attachments': 'addAttachments'

        compiled: Handlebars.compile homePageTemplate

        addAttachments: (event) ->
            event.preventDefault()
            $('#attachments').toggle()

        createSandbox: ->
            sandboxForm = Vm.create(this, 'SandboxForm', SandboxForm)

            modal = new Backbone.BootstrapModal(
                title: 'New Sandbox'
                content: sandboxForm
                animate: true
            ).open()
            return false

        render: ->
            app.sandboxes().done (sandboxes) =>
                $(@el).html @compiled
                    sandboxes: sandboxes.toJSON()

                $('#home-features').equalHeights()

                self = this
                $('#btn-attachments').popover
                    placement: 'bottom'
                    trigger: 'click'
                    delay:
                        show: 500
                        hide: 100
                    content: ->
                        self.delay 0, -> # allow a repaint cycle
                            attachmentsView = Vm.create(self, 'AttachmentsSection', AttachmentsSection,
                                popoverId: 'fileupload'
                                entityType: 'sandbox'
                                entityId: app.selectedSandbox()?.id
                            )
                            attachmentsView.render()
                            return
                        return 'Loading...'

            return this
