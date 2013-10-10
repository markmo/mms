define [
    'backbone'
    'cs!models/upload'
], (Backbone, uploadModel) ->
    Backbone.Collection.extend
        url: '/files'
        model: uploadModel
