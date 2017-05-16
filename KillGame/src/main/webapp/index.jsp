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
  <%--<link href="assets/css/flowborder/flowborder.css" rel="stylesheet"/>--%>
    <style type="text/css">
    </style>

  <script src="assets/js/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
  <script src="assets/js/hackCanvas.js" type="text/javascript"></script>
  <script src="assets/js/keyshot.js" type="text/javascript"></script>
  <script src="assets/js/sortable.js" type="text/javascript"></script>
  <script src="assets/js/shake.js" type="text/javascript"></script>
  <script>
      var config = {
          canvas:true,
//          canvas:false,
          num:12  //人数
      }

      var records = [];//jingshang diyitian diyitian
      //7v8 9v10 5x7 3+9-5>2 5
      //shangjing:
      //1.cai duitiao
      //2.yan jinshui/chasha

      function validate() {
          $(".tile_name").css("color","red");

          $("[data-expected]").each(function (obj,ele) {
              var length = $(ele).children().length;
//              console.log(length);
              if(length==parseInt($(ele).attr("data-expected"))){
                  $(ele).parent().prev(".tile_name").css("color","green");
              }
          });

          $("[data-max]").each(function (obj,ele) {
              var length = $(ele).find(".sort_placeholder").children().length;
              console.log($(ele).attr("id")+"-data-max:"+length);
              if(length==parseInt($(ele).attr("data-max"))){
                  $(ele).children(".tile_name").css("color","green");
              }
          });

      }

      function getByShortKey(str) {
          var $obj = $(`[data-shortkey="${str}"]`);
          if($obj.length >0){
              return $obj;
          }
      }

      function compressCmd(str) {
          return str.replace(/\s+/g," ").trim();
      }
      function getCmdArray(str) {
          return str.split(" ");
      }

      $(function () {
          if(config.canvas) {
            $("#canvas").hackCanvas({
                words:"wolf kill"
            });
          }

          $("#cmd").keyShot({
              errorWhenShake:true,
              handle:function (str) {
                  str=compressCmd(str);

                  console.log("cmd:"+str);

                  //[lcynsq]
                  //move command selected
                  if(/^[a-z]+$/.test(str)){
                      var $obj = getByShortKey(str);
                      if($obj){
                          $(".selected").appendTo($obj);
                          validate();
                          return;
                      }
                  }

                  //1 3 5 4 [lcynsq]
                  //move command
                  if(/^(\d+\s*)+[a-z]+$/.test(str)){
                      var arr =getCmdArray(str);
                      $obj = getByShortKey(arr[arr.length-1]);
                      if($obj){
                          for(var i=0;i<arr.length-1;i++) {
                              var tid=arr[i];

                              $(`[data-id="data${tid}"]`).appendTo($obj);
                          }
                          validate();
                          return;
                      }
                  }

                  return false;
              }
          });

          //initial
          var num=config.num;
          for(var i=0;i<num;i++){
             $("#group_unkown .sort_placeholder").append($(`<div class="number" data-id="data${i+1}">${i+1}</div>`));
          }

          //selection
          $(document).on("mouseover",".number[data-id]",function () {
              $(this).toggleClass("selected");
          });
          $(document).on("click",function () {
              $(".selected").removeClass("selected");
          });

          $("#sortContainer .sort_placeholder").each(function (id, ele) {
              new Sortable(ele, {
                  group: "name",  // or { name: "...", pull: [true, false, clone], put: [true, false, array] }
                  sort: true,  // sorting inside list
                  delay: 0, // time in milliseconds to define when the sorting should start
                  animation: 150,  // ms, animation speed moving items when sorting, `0` — without animation
//              handle: ".number",  // Drag handle selector within list items
                  filter: ".ignore-elements",  // Selectors that do not lead to dragging (String or Function)
                  preventOnFilter: true, // Call `event.preventDefault()` when triggered `filter`
                  draggable: ".number",  // Specifies which items inside the element should be draggable

                  forceFallback: false,  // ignore the HTML5 DnD behaviour and force the fallback to kick in
                  fallbackTolerance: 0, // Specify in pixels how far the mouse should move before it's considered as a drag.

                  scroll: true, // or HTMLElement
                  scrollSensitivity: 30, // px, how near the mouse must be to an edge to start scrolling.
                  scrollSpeed: 10,// px

                  // Element dragging ended
                  onEnd: function (/**Event*/evt) {
                      validate();
                  },

              });
          });

          validate();
      });
  </script>
  <body>
        <%--命令栏--%>
      <div class="webdesigntuts-workshop">
           <input id="cmd" type="search" placeholder="Input your command">
      </div>


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
