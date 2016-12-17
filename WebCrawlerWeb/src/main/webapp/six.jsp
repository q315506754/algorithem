<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/29 0029
  Time: 16:31
  To change this template use File | Settings | File Templates.

    http://localhost:8080/jquery.jsp
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/no-cache-head.jsp" %>

<html>
<head>
    <title>jQuery</title>

    <link href="assets/css/common.css" rel="stylesheet"/>

    <!-- CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/form.css" rel="stylesheet">
    <link href="css/calendar.css" rel="stylesheet">
    <link href="css/media-player.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link href="css/icons.css" rel="stylesheet">
    <link href="css/generics.css" rel="stylesheet">

    <%--<link href="css/jquery.dataTables.css" rel="stylesheet">--%>
    <link href="css/dataTables.bootstrap.css" rel="stylesheet">


</head>


<body id="skin-blur-violate">
<header id="header" class="media">
    <a href="" id="menu-toggle"></a>
    <a class="logo pull-left" href="index.html">SUPER ADMIN 1.0</a>

    <div class="media-body">
        <div class="media" id="top-menu">
            <div class="pull-left tm-icon">
                <a data-drawer="messages" class="drawer-toggle" href="">
                    <i class="sa-top-message"></i>
                    <i class="n-count animated">5</i>
                    <span>Messages</span>
                </a>
            </div>
            <div class="pull-left tm-icon">
                <a data-drawer="notifications" class="drawer-toggle" href="">
                    <i class="sa-top-updates"></i>
                    <i class="n-count animated">9</i>
                    <span>Updates</span>
                </a>
            </div>


            <div id="time" class="pull-right">
                <span id="hours"></span>
                :
                <span id="min"></span>
                :
                <span id="sec"></span>
            </div>

            <div class="media-body">
                <input type="text" class="main-search">
            </div>
        </div>
    </div>
</header>

<div class="clearfix"></div>

<section id="main" class="p-relative" role="main">

    <!-- Sidebar -->
    <%--<aside id="sidebar">--%>

    <%--<!-- Sidbar Widgets -->--%>
    <%--<div class="side-widgets overflow">--%>
    <%--<!-- Profile Menu -->--%>
    <%--<div class="text-center s-widget m-b-25 dropdown" id="profile-menu">--%>
    <%--<a href="" data-toggle="dropdown">--%>
    <%--<img class="profile-pic animated" src="img/profile-pic.jpg" alt="">--%>
    <%--</a>--%>
    <%--<ul class="dropdown-menu profile-menu">--%>
    <%--<li><a href="">My Profile</a> <i class="icon left">&#61903;</i><i class="icon right">&#61815;</i></li>--%>
    <%--<li><a href="">Messages</a> <i class="icon left">&#61903;</i><i class="icon right">&#61815;</i></li>--%>
    <%--<li><a href="">Settings</a> <i class="icon left">&#61903;</i><i class="icon right">&#61815;</i></li>--%>
    <%--<li><a href="">Sign Out</a> <i class="icon left">&#61903;</i><i class="icon right">&#61815;</i></li>--%>
    <%--</ul>--%>
    <%--<h4 class="m-0">Malinda Hollaway</h4>--%>
    <%--@malinda-h--%>
    <%--</div>--%>

    <%--<!-- Calendar -->--%>
    <%--<div class="s-widget m-b-25">--%>
    <%--<div id="sidebar-calendar"></div>--%>
    <%--</div>--%>

    <%--<!-- Feeds -->--%>
    <%--<div class="s-widget m-b-25">--%>
    <%--<h2 class="tile-title">--%>
    <%--News Feeds--%>
    <%--</h2>--%>

    <%--<div class="s-widget-body">--%>
    <%--<div id="news-feed"></div>--%>
    <%--</div>--%>
    <%--</div>--%>

    <%--<!-- Projects -->--%>
    <%--<div class="s-widget m-b-25">--%>
    <%--<h2 class="tile-title">--%>
    <%--Projects on going--%>
    <%--</h2>--%>

    <%--<div class="s-widget-body">--%>
    <%--<div class="side-border">--%>
    <%--<small>Joomla Website</small>--%>
    <%--<div class="progress progress-small">--%>
    <%--<a href="#" data-toggle="tooltip" title="" class="progress-bar tooltips progress-bar-danger" style="width: 60%;" data-original-title="60%">--%>
    <%--<span class="sr-only">60% Complete</span>--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--<div class="side-border">--%>
    <%--<small>Opencart E-Commerce Website</small>--%>
    <%--<div class="progress progress-small">--%>
    <%--<a href="#" data-toggle="tooltip" title="" class="tooltips progress-bar progress-bar-info" style="width: 43%;" data-original-title="43%">--%>
    <%--<span class="sr-only">43% Complete</span>--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--<div class="side-border">--%>
    <%--<small>Social Media API</small>--%>
    <%--<div class="progress progress-small">--%>
    <%--<a href="#" data-toggle="tooltip" title="" class="tooltips progress-bar progress-bar-warning" style="width: 81%;" data-original-title="81%">--%>
    <%--<span class="sr-only">81% Complete</span>--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--<div class="side-border">--%>
    <%--<small>VB.Net Software Package</small>--%>
    <%--<div class="progress progress-small">--%>
    <%--<a href="#" data-toggle="tooltip" title="" class="tooltips progress-bar progress-bar-success" style="width: 10%;" data-original-title="10%">--%>
    <%--<span class="sr-only">10% Complete</span>--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--<div class="side-border">--%>
    <%--<small>Chrome Extension</small>--%>
    <%--<div class="progress progress-small">--%>
    <%--<a href="#" data-toggle="tooltip" title="" class="tooltips progress-bar progress-bar-success" style="width: 95%;" data-original-title="95%">--%>
    <%--<span class="sr-only">95% Complete</span>--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>

    <%--<!-- Side Menu -->--%>
    <%--<ul class="list-unstyled side-menu">--%>
    <%--<li>--%>
    <%--<a class="sa-side-home" href="index.html">--%>
    <%--<span class="menu-item">Dashboard</span>--%>
    <%--</a>--%>
    <%--</li>--%>
    <%--<li>--%>
    <%--<a class="sa-side-typography" href="typography.html">--%>
    <%--<span class="menu-item">Typography</span>--%>
    <%--</a>--%>
    <%--</li>--%>
    <%--<li>--%>
    <%--<a class="sa-side-widget" href="content-widgets.html">--%>
    <%--<span class="menu-item">Widgets</span>--%>
    <%--</a>--%>
    <%--</li>--%>
    <%--<li class="active">--%>
    <%--<a class="sa-side-table" href="tables.html">--%>
    <%--<span class="menu-item">Tables</span>--%>
    <%--</a>--%>
    <%--</li>--%>
    <%--<li class="dropdown">--%>
    <%--<a class="sa-side-form" href="">--%>
    <%--<span class="menu-item">Form</span>--%>
    <%--</a>--%>
    <%--<ul class="list-unstyled menu-item">--%>
    <%--<li><a href="form-elements.html">Basic Form Elements</a></li>--%>
    <%--<li><a href="form-components.html">Form Components</a></li>--%>
    <%--<li><a href="form-examples.html">Form Examples</a></li>--%>
    <%--<li><a href="form-validation.html">Form Validation</a></li>--%>
    <%--</ul>--%>
    <%--</li>--%>
    <%--<li class="dropdown">--%>
    <%--<a class="sa-side-ui" href="">--%>
    <%--<span class="menu-item">User Interface</span>--%>
    <%--</a>--%>
    <%--<ul class="list-unstyled menu-item">--%>
    <%--<li><a href="buttons.html">Buttons</a></li>--%>
    <%--<li><a href="labels.html">Labels</a></li>--%>
    <%--<li><a href="images-icons.html">Images &amp; Icons</a></li>--%>
    <%--<li><a href="alerts.html">Alerts</a></li>--%>
    <%--<li><a href="media.html">Media</a></li>--%>
    <%--<li><a href="components.html">Components</a></li>--%>
    <%--<li><a href="other-components.html">Others</a></li>--%>
    <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
    <%--<a class="sa-side-chart" href="charts.html">--%>
    <%--<span class="menu-item">Charts</span>--%>
    <%--</a>--%>
    <%--</li>--%>
    <%--<li>--%>
    <%--<a class="sa-side-folder" href="file-manager.html">--%>
    <%--<span class="menu-item">File Manager</span>--%>
    <%--</a>--%>
    <%--</li>--%>
    <%--<li>--%>
    <%--<a class="sa-side-calendar" href="calendar.html">--%>
    <%--<span class="menu-item">Calendar</span>--%>
    <%--</a>--%>
    <%--</li>--%>
    <%--<li class="dropdown">--%>
    <%--<a class="sa-side-page" href="">--%>
    <%--<span class="menu-item">Pages</span>--%>
    <%--</a>--%>
    <%--<ul class="list-unstyled menu-item">--%>
    <%--<li><a href="list-view.html">List View</a></li>--%>
    <%--<li><a href="profile-page.html">Profile Page</a></li>--%>
    <%--<li><a href="messages.html">Messages</a></li>--%>
    <%--<li><a href="login.html">Login</a></li>--%>
    <%--<li><a href="404.html">404 Error</a></li>--%>
    <%--</ul>--%>
    <%--</li>--%>
    <%--</ul>--%>

    <%--</aside>--%>

    <!-- Content -->
    <section id="content" class="container" style="margin-left: 0px">


        <!-- Breadcrumb -->
        <ol class="breadcrumb hidden-xs">
            <li><a href="#">Home</a></li>
            <li><a href="#">Library</a></li>
            <li class="active">Data</li>
        </ol>

        <h4 class="page-title">TABLES</h4>


        <!-- Table Striped -->
        <div class="block-area">
            <h3 class="block-title">Table Striped</h3>
            <div class="table-responsive overflow">
                <table id="tableStriped" class="tile table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>sid</th>
                        <th>id</th>
                        <th>type</th>
                        <th>matchKey</th>
                        <th>title</th>
                        <th>createTime</th>
                        <th>operation</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </section>
    <br/><br/>
</section>
</body>

<script src="assets/js/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<%--<script src="assets/js/jquery.js" type="text/javascript"></script>--%>
<script src="assets/js/jquery.dataTables.js" type="text/javascript"></script>

<script>
    (function () {
        function Plugin() {
            this.a = 'aaa';
        }

        Plugin.prototype = {
            funcA: function (msg) {
                console.log('funcA:' + msg);
                console.log(this);
                this.funcB(msg);
            },
            funcB: function (msg) {
                console.log('funcB:' + msg);
                console.log(this);
            }
        }

        Plugin.funcC = function (msg) {
            console.log('funcC:' + msg);
            console.log(this);
        }

        var plg = new Plugin();
        plg.funcA();

        //plg.funcC();//xx
        Plugin.funcC('mmm');

    })();

    function mark(sid,marked) {
        $.ajax({
            url: "/mark",
            type: "post",
            data:{sid:sid,marked:marked},
            success:function () {

            }
        });
    }

    $(function () {

//        $.ajax({
//            url:"/data",
//            dataType:"json"
//        })
        $.ajax({
            url: "/data",
            success: function (arr) {
//                console.log('data loaded');
//                console.log(arr);
//                console.log(typeof arr);
//                console.log(arr.length);

                $('#tableStriped').DataTable({
                    data: arr,
                    "order": [[ 5, 'desc' ]],
                    "pageLength": 30,
                    columns: [
                        {data: 'sid'},
                        {data: 'id'},
                        {data: 'type'},
                        {data: 'matchKey'},
                        {data: 'title'},
                        {data: 'createTime'},
//                        {name: 'operation'}
                    ],
                    "rowCallback": function( row, data, index ) {
//                        console.log(arguments);
                        var $like = $(row).find("td:last  a:eq(0)");
                        var $dislike = $(row).find("td:last  a:eq(1)");
                        var $dislikeAll = $(row).find("td:last  a:eq(2)");

                        var $link = $(row).find("td:eq(4)");
//                        console.log($oper);
                        $dislike.click(function () {
//                            mark(data.sid,1);
                            mark(data.sid,1);
                            $(row).css('background-color','red');
                        });
                        $like.click(function () {
//                            mark(data.sid,1);
                            mark(data.sid,10);
                            $(row).removeClass("prev_visited");
                            $(row).removeClass("prev_selected");
                            $(row).css('background-color','#24718D');
                        });
                        $dislikeAll.click(function () {
                            $(".prev_visited").find("td:last  a:eq(1)").click();
                            $(".prev_selected").find("td:last  a:eq(1)").click();
                        });


                        $link.click(function () {
//                            mark(data.sid,1);
//                            $(row).css('background-color','#288d73');prev_visited
                            $(".prev_selected").removeClass("prev_selected").addClass("prev_visited");
                            $(row).addClass("prev_selected");
                        });
                    },
                    "columnDefs": [
                        {
                            "targets": 4,
                            "render": function (data, type, full, meta) {
                                return '<a href="' + full.url + '"  target="_blank">' + data + '</a>';
                            }
                        },
                        {
                            "targets": 5,
                        },
                        {
//                            name: 'operation',
                            "targets": 6,
                            "render": function (data, type, full, meta) {

//                                return '<a href="javascript:mark(\''+full.sid+'\',1)"  >oper</a>';
                                return '<a href="javascript:void(0)"  >like</a>&nbsp;&nbsp;<a href="javascript:void(0)"  >dislike</a>&nbsp;&nbsp;<a href="javascript:void(0)"  >dislikeAll</a>';
//                                return $ret;
                            }
                        }

                    ]
                });
            }
        })
    })
</script>

</html>
