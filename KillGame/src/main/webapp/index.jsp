<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/29 0029
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="true" %>
<html>
  <head>
    <title>index</title>
  </head>
  <link href="assets/css/common.css" rel="stylesheet"/>
  <link href="assets/css/glowinput/glowinputpure.css" rel="stylesheet"/>
  <%--<link href="assets/css/weather/dynamicWeather.css" rel="stylesheet"/>--%>
  <%--<link href="assets/css/flowborder/flowborder.css" rel="stylesheet"/>--%>
    <style type="text/css">
    </style>

  <script src="assets/js/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
  <script src="assets/js/hackCanvas.js" type="text/javascript"></script>
  <script src="assets/js/keyshot.js" type="text/javascript"></script>
  <script src="assets/js/sortable.js" type="text/javascript"></script>
  <script src="assets/js/shake.js" type="text/javascript"></script>
  <script src="assets/js/main.js" type="text/javascript"></script>
  <script src="assets/js/cmdParser.js" type="text/javascript"></script>
  <script>

  </script>
  <body>
        <%--命令栏--%>
      <div class="webdesigntuts-workshop">
           <input id="cmd" type="search" placeholder="Input your command">
      </div>

        <%--<div class="selected number" data-id="data1">ad</div>--%>
        <%--<div class="selected number" data-id="data1">ad</div>--%>
        <%--<div class="selected number" data-id="data1">ad</div>--%>
        <%--<div class="selected number" data-id="data1">ad</div>--%>

        <%--<div class="container" style="background-color:  #00BBFF">--%>
            <%--<div class="sunny"></div>--%>
            <%--<div class="cloudy"></div>--%>
            <%--<div class="rainy"></div>--%>
            <%--<div class="snowy"></div>--%>
            <%--<div class="rainbow"></div>--%>
            <%--<div class="starry"></div>--%>
            <%--<div class="stormy"></div>--%>
        <%--</div>--%>
        <%--<div class="starry"></div>--%>

        <table>
            <thead>
                <tr>
                    <th>ord</th>
                    <th>c</th>
                    <th>c</th>
                    <th>c</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td rowspan="4">asd</td>
                    <td ><div class="sunnyContainer"><div class="sunny"></div></div></td>
                    <td ></td>
                    <td >aaaa</td>
                </tr>
                <tr>
                    <td >bbbb</td>
                    <td >aaaa</td>
                    <td >aaaa</td>
                </tr>
                <tr>
                    <td >bbbb</td>
                    <td ></td>
                    <td >aaaa</td>
                </tr>
                <tr>
                    <td >bbbb</td>
                    <td >aaaa</td>
                    <td >aaaa</td>
                </tr>
            </tbody>
        </table>



        <div id="sortContainer">
            <div id="group_unkown" class="layer tile"  style="display: block">
                <div class="tile_name">未知u</div>
                <div class="tile_list">
                    <div class="sort_placeholder" data-expected="0"  data-shortkey="u">

                    </div>
                </div>
            </div>

            <div  id="group_haoren" class="layer tile" style="display: block" data-max="8">
                <div class="tile_name">好人hr</div>
                <div class="tile_list">
                    <span class="sort_placeholder"   data-shortkey="hr">
                    </span>

                    <div class="layer tile">
                        <div class="tile_name">预言家y</div>
                        <div class="tile_list">
                            <span class="sort_placeholder"  data-expected="1"   data-shortkey="y">
                            </span>
                        </div>
                    </div>

                    <div class="layer tile"   id="group_qiangshen" data-max="3">
                        <div class="tile_name">强神qs</div>
                        <div class="tile_list">
                            <span class="sort_placeholder"    data-shortkey="qs" >
                            </span>

                            <div class="layer tile">
                                <div class="tile_name">女巫n</div>
                                <div class="tile_list">
                                    <span class="sort_placeholder"  data-expected="1"   data-shortkey="n">
                                    </span>
                                </div>
                            </div>
                            <div class="layer tile">
                                <div class="tile_name">守卫s</div>
                                <div class="tile_list">
                                    <span class="sort_placeholder"  data-expected="1"   data-shortkey="s">
                                    </span>
                                </div>
                            </div>
                            <div class="layer tile">
                                <div class="tile_name">猎人q</div>
                                <div class="tile_list">
                                    <span class="sort_placeholder"  data-expected="1"   data-shortkey="q">
                                    </span>
                                </div>
                            </div>


                        </div>
                    </div>

                    <div class="layer tile" id="group_cunmin">
                        <div class="tile_name">村民c</div>
                        <div class="tile_list">
                            <span class="sort_placeholder"  data-expected="4"   data-shortkey="c">
                            </span>
                        </div>
                    </div>

                </div>
            </div>



            <div  id="group_langren" class="layer tile "  style="display: block">
                <div class="tile_name">狼人l</div>
                <div class="tile_list">
                    <span class="sort_placeholder "  data-expected="4"   data-shortkey="l">
                    </span>
                </div>
            </div>
        </div>


      <canvas id="canvas"></canvas>
  </body>
</html>
