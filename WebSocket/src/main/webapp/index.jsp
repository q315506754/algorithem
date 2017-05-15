<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/29 0029
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>web socket</title>
  </head>

  <script src="assets/js/jquery/jquery-1.8.3.min.js"></script>

  <script>
      var webSocket = null;
      var tryTime = 0;

      function p(msg) {
          $("#console").html($("#console").html()+msg+"<br/>");
      }
      $(function () {
          initSocket();

          window.onbeforeunload = function () {
              //离开页面时的其他操作
          };

          $("button").click(function () {
//              webSocket.send($("#text").val());

//              由于WebSocket支援的Binary数据形式有Blob和ArrayBuffer两种，所以必须要手动指定要用哪种类型的数据。

//              webSocket.binaryType = "arraybuffer" ;
              webSocket.send(window);

          });
      });

      /**
       * 初始化websocket，建立连接
       */
      function initSocket() {
          if (!window.WebSocket) {
              alert("您的浏览器不支持websocket！");
              return false;
          }

          //JSR 356 WebSocket 1.1 implementation
//          webSocket = new WebSocket(((window.location.protocol == 'http:')? 'ws': 'wss') +"://localhost:8080/websocketpath.ws");
          webSocket = new WebSocket(((window.location.protocol == 'http:')? 'ws': 'wss') +"://localhost:8080/chatroom.ws");

          //legacy servlet
//          webSocket = new WebSocket(((window.location.protocol == 'http:')? 'ws': 'wss') +"://localhost:8080/hellowebsocket.ws");

          //spring webSocket
//          webSocket = new WebSocket(((window.location.protocol == 'http:')? 'ws': 'wss') +"://localhost:8080/springws");

          // 收到服务端消息
//          webSocket.onmessage = function (msg) {
//              console.log(msg);
//              p(msg);
//          };


          var handler = function (msg) {
              console.log(msg);
              p(msg);
          }

          webSocket.onmessage = function(evt) {
              if(typeof(evt.data)=="string"){
                  handler(evt.data);
              }else{
                  var reader = new FileReader();
                  reader.onload = function(evt){
                      if(evt.target.readyState == FileReader.DONE){
                          var data = new Uint8Array(evt.target.result);
                          handler(data);
                      }
                  }
                  reader.readAsArrayBuffer(evt.data);
              }
          };

          // 异常
          webSocket.onerror = function (event) {
              console.log(event);
          };

          // 建立连接
          webSocket.onopen = function (event) {
              console.log(event);
          };

          // 断线重连
          webSocket.onclose = function () {
              // 重试10次，每次之间间隔10秒
              if (tryTime < 10) {
                  setTimeout(function () {
                      webSocket = null;
                      tryTime++;
                      initSocket();
                  }, 500);
              } else {
                  tryTime = 0;
              }
          };
      }
  </script>

  <body>
    <input id="text" type="text" value="msg"/><br/>
    <button>send</button>

   <div id="console"></div>
  </body>
</html>
