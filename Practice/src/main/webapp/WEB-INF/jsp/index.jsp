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
                            <div class="comment">
                                <span class="like"  v-bind:class="{chosen:dish.likeit>0}" @click="dishLike(dish)">↑</span>
                                <span class="dislike"  v-bind:class="{chosen:dish.likeit<0}" @click="dishDislike(dish)">↓</span>
                            </div>

                            <span class="dish" >{{dish.name}}<br/><span class="price" >￥{{dish.money}}</span></span>

                            <span class="chooseNum" >
                                 <button class="addBtn" @click="dishRem(dish)">-</button>
                                 <input type="text" v-model="dish.chooseNum" @keyup.left="dishRem(dish)" @keyup.right="dishAdd(dish)" @keyup.down="dishRem(dish)" @keyup.up="dishAdd(dish)"/>
                                 <button class="removeBtn" @click="dishAdd(dish)">+</button>
                            </span>

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

  <script type="text/javascript">

        var vm = new Vue({
            el: '#mainContainer',
            data: {
                prop:{selected:"isSelected"},
                merchants:[
//                        {
//                    id:1,
//                    name:"啊啊啊啊1",
//                    likeit:0,
//                    isSelected:false
//                }
                ],
                merchantSelected:-1,
                dishes:[
//                        {
//                    merchantId: 1,
//                    money:18,
//                    name:"蒜苗炒香肠",
//                    packageMoney:1,
//                    isSelected:false,
//                    chooseNum:0,
//                    likeit:0
//                }
                ]
            },
            methods: {
                setArrayProp(arr,prop,value){
                    for(let one of arr){
                        one[prop]=value;
                    }
                },
                merchantSelect(obj){
//                    console.log(arguments);
                    this.setArrayProp(this.merchants,this.prop.selected,false);

                    obj[this.prop.selected]=true;
//                    console.log(this.merchants);
//                    this.$set('merchants',this.merchants);

                    this.merchantSelected=obj.id;

                    this.dishQuery();
                },
                merchantEdit(obj) {

                },
                merchantCreate(){
//                    this.merchants.push({id:1,name:"asdas"});
                    console.log(this);
                    this.merchants=[];
                },
                merchantQuery(){
//                    this.merchants.push({id:1,name:"asdas"});
                    let $this = this;
                    $.ajax("/merchant/list").done(function(arr){
                        $this.setArrayProp(arr,$this.prop.selected,false);
                        $this.merchants=arr;
                    } )
                },
                merchantLike(obj){
                    this.like(obj,1,"merchant");
                },
                merchantDislike(obj){
                    this.like(obj,-1,"merchant");
                },
                like(obj,val,path){
                    if(obj.likeit==val){
                        obj.likeit = 0;
                    }else {
                        obj.likeit=val;
                    }

                    $.ajax({url:"/"+path+"/like",data:{id:obj.id,like:obj.likeit}});
                },
                dishQuery(){
                    let $this = this;
                    $.ajax({url:"/dish/list",data:{merchantId:this.merchantSelected}}).done(function(arr){
//                        console.log(arr);
                        $this.setArrayProp(arr,$this.prop.selected,false);
                        $this.setArrayProp(arr,"chooseNum",0);
                        $this.dishes=arr;
//                        console.log($this.dishes);
                    } )
                },
                dishAdd(dish){
                    dish.chooseNum++;
                    this.incDishTimes(dish,1);
                },
                dishRem(dish){
                    dish.chooseNum>0?dish.chooseNum--:0;
                    this.incDishTimes(dish,-1);
                },
                incDishTimes(obj,times){
                    $.ajax({url:"/dish/inc",data:{id:obj.id,inc:times}});
                },
                dishLike(obj){
                    this.like(obj,1,"dish");
                },
                dishDislike(obj){
                    this.like(obj,-1,"dish");
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

        vm.merchantQuery();
    </script>
</html>
