package controllers;

import play.mvc.*;
import utils.StatisticUtil;
import views.html.*;

public class Admin extends MasterController {

    public static Result index() {
        return ok(admin.render());
    }

//    public Integer getPlayImportance;
//    public Integer getPlayingRelatedToLearningKnowledge;
//    public Integer getMotivatedToLearnAboutPlay;
//    public Integer getAge;
//    public Integer getZip;
//    public Integer getChildrenInCare;

//    <script type="text/javascript">
//    var data = null;
//    $("button").click(function(){
//        $.post("admin",
//                { },
//                function(dataFromServer){
//            alert("Data: " + dataFromServer);
//            data = dataFromServer;
//        });
//    });
//
//    var chart = d3.select("body").append("div").attr("class", "chart");
//
//    chart.selectAll("div")
//            .data(data)
//    .enter().append("div")
//    .style("width", function(d) { return d * 10 + "px"; })
//            .text(function(d) { return d; });
//    </script>

}
