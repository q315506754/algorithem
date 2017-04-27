<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html >
  <head>
    <title >index</title>
      <%@include file="common/head.jsp"%>
  </head>
  <script>
      
  </script>
  <body>
  <div class="container">
      <div id="mainContainer" class="mainContainer">
         <div class="nav_bar">
             <span class="config">配置</span>
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
                     <button @click="show.merchantCreate=true">create</button>
                     <%--<a href="/merchant/create">create</a>--%>
                     <%--<router-link to="/merchant/create">create</router-link>--%>
                     <button @click="merchantQuery">refresh</button>
                 </div>
             </div>
             <div class="right-content">
                 <div class="btn">
                     <button @click="show.dishCreate=true">create</button>
                     <button @click="dishQuery">refresh</button>
                 </div>
                 <div class="dishes">
                    <ul>
                        <li v-for="dish in dishes">

                            <Likeit v-bind:obj="dish" v-bind:p="prop.like" v-bind:cls="'chosen'" v-bind:path="'dish'"></Likeit>

                            <span class="dish"  v-bind:class="{burning:dish.chooseNum>0}">{{dish.name}}<br/><span class="price" >￥{{dish.money}}</span></span>

                            <Choose v-bind:obj="dish" v-bind:p="prop.choose" @increment="incDishTimes"  ></Choose>

                            <span class="price" >￥{{dish.chooseNum*dish.money}}</span>
                        </li>
                    </ul>
                 </div>

                 <Solution v-bind:preview="preview" v-bind:preview-order="previeworder" ></Solution>


                 <div class="action">
                     <button @click="doSeparateDialog">doSeparate</button>
                 </div>

             </div>
         </div>

        <dialog-x  v-bind:p="'merchantCreate'" @save="merchantSave">
            <h2 slot="head">创建商户</h2>
            <div>
                <div class="row">
                        商户名称 <input type="text"  v-model="merchantCreate.name"/>
                </div>
                <div class="row">
                        起送价格 <Choose v-bind:obj="merchantCreate" v-bind:p="'baseMoney'"  ></Choose>
                </div>
                <div class="row">
                        打包价格 <Choose v-bind:obj="merchantCreate" v-bind:p="'distributionMoney'"  ></Choose>
                </div>
            </div>
        </dialog-x>

        <dialog-x  v-bind:p="'dishCreate'" @save="dishSave">
            <h2 slot="head">创建菜类</h2>
            <div>
                <div class="row">
                        菜名 <input type="text"  v-model="dishCreate.name"/>
                </div>
                <div class="row">
                        价格 <Choose v-bind:obj="dishCreate" v-bind:p="'money'"  ></Choose>
                </div>
                <div class="row">
                        打包价格 <Choose v-bind:obj="dishCreate" v-bind:p="'packageMoney'"  ></Choose>
                </div>
            </div>
        </dialog-x>

        <dialog-x  v-bind:p="'separate'" @save="doSeparateProcess">
            <h2 slot="head">拆</h2>
            <div>
                <div class="row">
                    是否使用会员<input type="checkbox"  v-model="separateParam.isVip"/>
                </div>
                <div class="row">
                      最小单数 <Choose v-bind:obj="separateParam" v-bind:p="'minOrder'"  ></Choose>
                </div>
                <div class="row">
                     最大单数 <Choose v-bind:obj="separateParam" v-bind:p="'maxOrder'"  ></Choose>
                </div>
                <div class="row">
                     最多使用红包数 <Choose v-bind:obj="separateParam" v-bind:p="'maxRedEnvelopeChosen'"  ></Choose>
                </div>
                <div class="row">
                    参与计算的红包
                    <ul class="redEnvelope">
                        <li v-for="one in redEnvelope" v-bind:class="{burning:one.isEnable==1}" @click="one.isEnable=1-one.isEnable">
                            满{{one.reach}}减{{one.reduce}}
                        </li>
                    </ul>
                </div>
            </div>
        </dialog-x>

      </div>

    </div>
  </body>

  <%@include file="common/head_js.jsp"%>
  <script src="${basePath}/assets/js/pl_solution.js" ></script>
  <script src="${basePath}/assets/js/pl_choose.js" ></script>
  <script src="${basePath}/assets/js/pl_likeit.js" ></script>
  <script src="${basePath}/assets/js/pl_dialog.js" ></script>
  <%--<script src="${basePath}/assets/js/vue/vue-router.js" ></script>--%>

  <%--<script src="${basePath}/assets/js/route.js" ></script>--%>
  <script src="${basePath}/assets/js/main.js" ></script>

</html>
