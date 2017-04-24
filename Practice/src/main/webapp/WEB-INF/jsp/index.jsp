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
                 <div class="btn">
                    <button @click="merchantCreate">create</button>
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
  </body>

  <%@include file="common/head_js.jsp"%>

  <script src="/assets/js/main.js" ></script>
  <script src="/assets/js/pl_choose.js" ></script>
  <script src="/assets/js/pl_likeit.js" ></script>

</html>
