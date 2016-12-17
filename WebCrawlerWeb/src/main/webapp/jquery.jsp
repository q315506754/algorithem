<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/29 0029
  Time: 16:31
  To change this template use File | Settings | File Templates.

    http://localhost:8080/jquery.jsp
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/no-cache-head.jsp"%>

<html>
<head>
    <title>jQuery</title>

    <link href="assets/css/common.css" rel="stylesheet"/>
    <script src="assets/js/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>

    <script>
        (function () {
            function Plugin() {
                this.a='aaa';
            }

            Plugin.prototype={
                funcA:function (msg) {
                    console.log('funcA:'+msg);
                    console.log(this);
                    this.funcB(msg);
                },
                funcB:function (msg) {
                    console.log('funcB:'+msg);
                    console.log(this);
                }
            }

            Plugin.funcC = function (msg) {
                console.log('funcC:'+msg);
                console.log(this);
            }

            var plg = new Plugin();
            plg.funcA();

            //plg.funcC();//xx
            Plugin.funcC('mmm');

        })();
        $(function () {
            console.log('loaded');
            $.ajax({
                url:"/autoc/get",
                dataType:"json"
            })
            $.ajax({
                url:"/data",
                dataType:"json"
            })
            $.ajax({
                url:"/autoc/get",
            })
            $.ajax({
                url:"/data",
            })
        })
    </script>
</head>


<body>
jQuery page
</body>
</html>
