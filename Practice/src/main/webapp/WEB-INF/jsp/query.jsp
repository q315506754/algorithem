<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html >
  <head>
    <title >query</title>
      <%@include file="common/head.jsp"%>
  </head>
  <script>
      var queryId="${queryId}";
  </script>
  <body>
  <div class="qcontainer">
      <div id="queryContainer" class="queryContainer">
          <div class="nav_bar">
              <div v-if="queryRs.code==-1">
                  请求的结果已经不存在
              </div>
              <div v-else-if="queryRs.code==1">
                  执行还在准备中
              </div>
              <div v-else-if="queryRs.code==0">
                  执行已经完成
              </div>
              <div v-else-if="queryRs.code==2">
                  执行在运行中
              </div>
              <div v-else-if="queryRs.code==3">
                  执行已经取消
              </div>
          </div>

          <div class="solutionArea">
              <div class="solutionContainer" v-for="i in detail.maxOrder" v-if="queryRs.code>=0">
                  <div class="solution">
                      <div class="num">拆{{getSolution(i).orderNum}}单</div>
                      <div class="price">￥{{getSolution(i).price}}</div>
                      <div class=""  v-if="getSolution(i).failed">{{getSolution(i).failedReason}}</div>
                      <div class=""  v-if="queryRs.code==2&&detail.curOrder==i">{{dt.percent}}</div>

                      <div class="" v-for="ordex in getSolution(i).orders" v-if="">
                          <Solution v-bind:preview="getSolution(i)" v-bind:preview-order="ordex" ></Solution>
                      </div>
                    <%--{{getSolution(i)}}--%>
                  </div>
              </div>
          </div>
      </div>
  </div>
  </body>

  <%@include file="common/head_js.jsp"%>
  <script src="${basePath}/assets/js/pl_solution.js" ></script>
  <%--<script src="${basePath}/assets/js/pl_choose.js" ></script>--%>
  <%--<script src="${basePath}/assets/js/pl_likeit.js" ></script>--%>
  <%--<script src="${basePath}/assets/js/pl_dialog.js" ></script>--%>
  <%--<script src="${basePath}/assets/js/vue/vue-router.js" ></script>--%>

  <%--<script src="${basePath}/assets/js/route.js" ></script>--%>
  <script src="${basePath}/assets/js/query.js" ></script>

</html>
