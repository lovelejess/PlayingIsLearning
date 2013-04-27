$(function () {
    var AppRouter = Backbone.Router.extend({
        routes:{
            "tab/:id":"showTab",
            ":tab":"decodeTab"
        },

        showTab:function (id) {

            document.getElementById('adminTab' + id).click();

        },

        decodeTab:function (id) {

            document.getElementById('adminTab' + id.charAt(id.length-1)).click();

        }

    });

    var appRouter = new AppRouter();
    Backbone.history.start();
});