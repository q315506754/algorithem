<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html >
  <head>
    <title >index</title>
      <%@include file="common/head.jsp"%>
  </head>
  <script>
      
  </script>
  <body>
      <div id="mainContainer" class="mainContainer">
         <div class="nav_bar">
             <span class="config">*</span>
         </div>


         <div class="content">
             <div class="left-content">
                 <div class="search">
                     <input type="text"  @keyup.enter="merchantQuery"/>
                 </div>
                 <div class="table">
                     <ul>
                         <li v-for="merchant in merchants" v-bind:class="{selected:merchant.isSelected==true}" >

                             <Likeit v-bind:obj="merchant" v-bind:p="prop.like" v-bind:cls="'chosen'" v-bind:path="'merchant'"></Likeit>


                             <span class="merchant" @click="merchantSelect(merchant)" @contextmenu.prevent="merchantEdit(merchant)">{{merchant.name}}</span>
                         </li>
                     </ul>
                 </div>

                 <%--<router-view  name="merchantCreate"></router-view>--%>
                 <div class="btn">
                     <button @click="merchantCreate">create</button>
                     <%--<a href="/merchant/create">create</a>--%>
                     <%--<router-link to="/merchant/create">create</router-link>--%>
                     <button @click="merchantQuery">refresh</button>
                 </div>
             </div>
             <div class="right-content">
                 <div class="btn">
                     <button @click="">create</button>
                     <button @click="dishQuery">refresh</button>
                 </div>
                 <div class="dishes">
                    <ul>
                        <li v-for="dish in dishes">

                            <Likeit v-bind:obj="dish" v-bind:p="prop.like" v-bind:cls="'chosen'" v-bind:path="'dish'"></Likeit>

                            <span class="dish" >{{dish.name}}<br/><span class="price" >￥{{dish.money}}</span></span>

                            <Choose v-bind:obj="dish" v-bind:p="prop.choose" @increment="incDishTimes"  ></Choose>

                            <span class="price" >￥{{dish.chooseNum*dish.money}}</span>
                        </li>
                    </ul>
                 </div>
                 <div class="money">

                 </div>
                 <div class="action">
                     <button @click="doSeparate">doSeparate</button>
                 </div>
             </div>
         </div>
      </div>

      <%--<script type="text/x-template" id="createMerchantTemp">--%>
          <%--<router-link to="/index">cancel</router-link>--%>
      <%--</script>--%>
      <%--<script type="text/x-template" id="createMerchantView">--%>
          <%--<div class="btn">--%>
              <%--<button @click="merchantCreate">create</button>--%>
                  <%--&lt;%&ndash;<a href="/merchant/create">create</a>&ndash;%&gt;--%>
              <%--&lt;%&ndash;<router-link to="/merchant/create">create</router-link>&ndash;%&gt;--%>
              <%--<button @click="merchantQuery">refresh</button>--%>
          <%--</div>--%>
      <%--</script>--%>

      <%--<router-link :to="href"></router-link>--%>
  </body>

  <%@include file="common/head_js.jsp"%>
  <%--<script src="/assets/js/vue/vue-router.js" ></script>--%>

  <%--<script src="/assets/js/route.js" ></script>--%>
  <script src="/assets/js/main.js" ></script>
  <script src="/assets/js/pl_choose.js" ></script>
  <script src="/assets/js/pl_likeit.js" ></script>

</html>
