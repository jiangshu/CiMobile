$(function(){


    var mobileInfo;
    var index = 1;
    /*
     * 拼接一个mobile实例的html
     * */
    function joinHtml(mobileInstance){
        function getBrowser(){
            var browser = "<table width='90%' align='center'><tr><td width='40%'>";
            $.each(mobileInstance.browser,function(key,val){
                browser+= "<input type='radio' value='"+val+"' name='browser'/>"+val;
            });
            browser+= "</td><td width='50%'>";
            browser+= "<input class='url' type='text'/>";
            browser+= "</td><td width='10%'>";
            browser+= "<input class='start' type='button' value='启动' data_id='"+mobileInstance.id+"'/>";
            browser+= "</td></tr></table>";
            return browser;
        }

        function getPackage(){
            var package = "<select>";
            $.each(mobileInstance.package,function(key,val){
                package = package+"<option>"+val+"</option>"
            });
            package+= "<input class='install' type='button' value='安装'/>";
            package+= "<input class='uninstall' type='button' value='卸载'/>";
            package+= "</select>";
            return package;
        }

       var html = "" +
           "<tr class='mobile_item'>" +
               "<td>"+ index +"</td>" +
               "<td>"+mobileInstance.name+"</td>" +
               "<td>"+mobileInstance.sysVersion+"</td>" +
               "<td>"+mobileInstance.alias+"</td>" +
               "<td>"+getBrowser()+"</td> " +
//               "<td>"+getPackage()+"</td>" +
           "</tr>"
        return html;
    }

    function update(){
        $.ajax({
           type:"get",
            dataType:"json",
            url:"./MobiInfo.php?type=1",
            success:function(data){
                if(data.length>0){
                    mobileInfo = data;
                    index = 1;
                    for(i=0;i<data.length;i++){
                        var mobileInstance = data[i];
                        $("#mobileList").append(joinHtml(mobileInstance));
                        index ++;
                    }

                    $(".start").on("click",function(el){
                        var id = $(this).attr("data_id");
                        var browser = "";
                        var url = $(this).parent().parent().find(".url").val();

                        $.each($(this).parent().parent().children().eq(0).children(),function(key,el){
                             if($(el).attr("checked")){
                                 if(browser!=""){
                                     browser+="@";
                                 }
                                 browser+=$(el).val()
                             }
                        });

                        $.post("./action.php",{
                            id:id,
                            browser:browser,
                            url:url
                        },function(data){
                               console.log(data);
                        });
                    });
                }
            },
            error:function(msg){
                alert(msg);
            }
        });
    }
    update();
    $(".update").on("click",function(){
        $(".mobile_item").remove();
        update();
    });
});