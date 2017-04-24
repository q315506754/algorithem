var vm = new Vue({
    el: '#mainContainer',
    data: {
        prop:{selected:"isSelected",choose:"chooseNum",like:"likeit"},
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

                if(arr.length>0){
                    $this.merchantSelect($this.merchants[0]);
                }

            } )
        },
        like(obj,val,path){
            if(obj.likeit==val){
                obj.likeit = 0;
            }else {
                obj.likeit=val;
            }

            $.ajax({url:`/${path}/like`,data:{id:obj.id,like:obj.likeit}});
        },
        dishQuery(){
            let $this = this;
            $.ajax({url:"/dish/list",data:{merchantId:this.merchantSelected}}).done(function(arr){
//                        console.log(arr);
                $this.setArrayProp(arr,$this.prop.selected,false);
                $this.setArrayProp(arr,$this.prop.choose,0);
                $this.dishes=arr;
//                        console.log($this.dishes);
            } )
        },
        incDishTimes(obj,times){
            $.ajax({url:"/dish/inc",data:{id:obj.id,inc:times}});
        },
        doSeparate(){

        }
    },
    filters: {

    }
//            components: {
//                'Choose': Child
//            }
});


vm.$watch('merchants', function (newVal, oldVal) {
    // 这个回调将在 `vm.a`  改变后调用
    console.log(newVal);
    console.log(oldVal);
})


vm.merchantQuery();