$(function () {
    var AppRouter = Backbone.Router.extend({
        routes:{
            ":tab":"decodeTab"
        },

        decodeTab:function (id) {
            document.getElementById('entrance').style.display = 'none';
            document.getElementById('terms').style.display = 'none';
            document.getElementById('register').style.display = 'none';
            document.getElementById(id).style.display = 'block';
        }
    });

    var appRouter = new AppRouter();
    Backbone.history.start();
});