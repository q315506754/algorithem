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
                             <div class="comment">
                                 <span class="like" v-bind:class="{chosen:merchant.likeit>0}" @click="merchantLike(merchant)">↑</span>
                                 <span class="dislike" v-bind:class="{chosen:merchant.likeit<0}"  @click="merchantDislike(merchant)">↓</span>
                             </div>

                             <span class="merchant" @click="merchantSelect(merchant)" @contextmenu.prevent="merchantEdit(merchant)">{{merchant.name}}</span>
                         </li>
                     </ul>
                 </div>
                 <div class="btn">
                    <button @click="merchantCreate">create</button>
                 </div>
             </div>
             <div class="right-content">
                 <div class="btn">
                     <button @click="">create</button>
                 </div>
                 <div class="dishes">

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

  <script type="text/javascript">

        var vm = new Vue({
            el: '#mainContainer',
            data: {
                merchants:[{
                    id:1,
                    name:"啊啊啊啊1",
                    likeit:1,
                    isSelected:false
                },{
                    id:2,
                    name:"啊啊啊啊2",
                    likeit:0,
                    isSelected:false
                },{
                    id:3,
                    name:"啊啊啊啊3",
                    likeit:-1,
                    isSelected:false
                }]
            },
            methods: {
                setArrayProp(arr,prop,value){
                    for(let one of arr){
                        one[prop]=value;
                    }
                },
                merchantSelect(obj){
//                    console.log(arguments);
                    var prop="isSelected";
//                        console.log('Hello ' + obj.id + '!')
                    this.setArrayProp(this.merchants,prop,false);

                    obj[prop]=true;
//                    console.log(this.merchants);
//                    this.$set('merchants',this.merchants);
                },
                merchantEdit(obj) {

                },
                merchantCreate(){
//                    this.merchants.push({id:1,name:"asdas"});
                    console.log(this);
                    this.merchants=[];
                },
                merchantQuery(){
                    this.merchants.push({id:1,name:"asdas"});
                },
                merchantLike(obj){
                    obj.likeit=1;
                },
                merchantDislike(obj){
                    obj.likeit=-1;
                },
                doSeparate(){

                }
            },
            filters: {

            }
        });

        console.log(vm.$data);
        console.log(vm.$el);

        vm.$watch('merchants', function (newVal, oldVal) {
            // 这个回调将在 `vm.a`  改变后调用
            console.log(newVal);
            console.log(oldVal);
        })

    </script>
</html>
