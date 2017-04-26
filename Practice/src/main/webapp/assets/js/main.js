var vm = new Vue({
    el: '#mainContainer',
    data: {
        prop:{selected:"isSelected",choose:"chooseNum",like:"likeit"},
        show:{merchantCreate:false,dishCreate:false,separate:false},
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
        ],
        merchantCreate: {
            name: "",
            baseMoney: 5,
            distributionMoney: 5
        },
        dishCreate: {
            name: "",
            money: 5,
            packageMoney: 1,
            merchantId: null,
        },
        preview:{

        },
        defaults:{

        },
        separateParam:{
            maxOrder:-1,
            minOrder:-1,
            maxRedEnvelopeChosen:-1,
            isVip:true,
            merchantId:-1
        },
        redEnvelope:[]
    },
    computed: {
        previewOrder: function () {
            if(this.preview && this.preview.orders){
                return this.preview.orders[0];
            }
            return {};
        }
    },
    methods: {
        setArrayProp(arr,prop,value){
            for(let one of arr){
                one[prop]=value;
            }
        },
        getArrayProp(arr,prop,value){
            let ret = [];
            for(let one of arr){
                if (one[prop]=value){
                    ret.push[one];
                }
            }
            return ret;
        },
        merchantSelect(obj){
//                    console.log(arguments);
            this.setArrayProp(this.merchants,this.prop.selected,false);

            obj[this.prop.selected]=true;
//                    console.log(this.merchants);
//                    this.$set('merchants',this.merchants);

            this.merchantSelected=obj.id;
            this.dishCreate.merchantId=obj.id;

            this.dishQuery();
        },
        merchantEdit(obj) {

        },
        merchantSave() {
            var $this =this;
            console.log(this.merchantCreate);

            $.ajax({url:`${basePath}/merchant/save`,data:this.merchantCreate}).done(function(arr){
                $this.merchantQuery();
            } )
        },
        dishSave() {
            var $this =this;
            console.log(this.dishCreate);

            $.ajax({url:`${basePath}/dish/save`,data:this.dishCreate}).done(function(arr){
                $this.dishQuery();
            } )
        },
        merchantQuery(){
//                    this.merchants.push({id:1,name:"asdas"});
            let $this = this;
            $.ajax(`${basePath}/merchant/list`).done(function(arr){
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

            $.ajax({url:`${basePath}/${path}/like`,data:{id:obj.id,like:obj.likeit}});
        },
        dishQuery(){
            let $this = this;
            $.ajax({url:`${basePath}/dish/list`,data:{merchantId:this.merchantSelected}}).done(function(arr){
//                        console.log(arr);
                $this.setArrayProp(arr,$this.prop.selected,false);
                $this.setArrayProp(arr,$this.prop.choose,0);
                $this.dishes=arr;
                $this.preview={};
//                        console.log($this.dishes);
            } )
        },
        queryDefaults(){
            let $this = this;
            $.ajax(`${basePath}/defaults`).done(function(data){
                $this.defaults=data;
                console.log($this.defaults);
            });

            let name="asd";
            let money=33;
            let packageMoney=1;
            let num=2;

            let obj = {name,money,packageMoney,num};
            let items = [obj,obj,obj];
            console.log(items);

            let iArr=[5,3,2,7];
            $.ajax({url:`${basePath}/calc/listtest`,data:serialize({items,itemsArr:items,items2:items,iArr,iListArr:iArr,iListArr2:iArr}),type:"POST"}).done(function(data){

            });
            // $.ajax({url:"${basePath}/calc/listtest2",data:{items5:items},type:"POST"}).done(function(data){
            //
            // });
            $.ajax({url:`${basePath}/calc/listtest2`,data:serialize({items5:items}),type:"POST"}).done(function(data){

            });
        },
        getSelectedItems(){
            let ret = [];
            for(let one of this.dishes){
                if (one[this.prop.choose]>0){
                    let t = {};
                    t.num=one[this.prop.choose];
                    t.name=one.name;
                    t.money=one.money;
                    t.packageMoney=one.packageMoney;
                    ret.push(t);
                }
            }
            return ret;
        },
        incDishTimes(obj,times){
            $.ajax({url:`${basePath}/dish/inc`,data:{id:obj.id,inc:times}});

            let $this = this;
            let items = this.getSelectedItems();
            let merchantId=this.merchantSelected;

            if(items.length>0){
                $.ajax({url:`${basePath}/calc/preview`,data:serialize({items,merchantId}),type:"POST"}).done(function(data){
                    $this.preview=data;
                });
            }else {
                $this.preview = {};
            }

        },
        doSeparateDialog(){
            let $this = this;
            let items = this.getSelectedItems();
            let merchantId=this.merchantSelected;


            if(true){
            // if($this.preview.failed!=undefined && !$this.preview.failed){
                //default
                this.separateParam.maxOrder=this.defaults.maxOrder;
                this.separateParam.minOrder=this.defaults.minOrder;
                this.separateParam.maxRedEnvelopeChosen=this.defaults.maxRedEnvelopeChosen;
                this.separateParam.isVip=this.defaults.isVip;
                this.separateParam.merchantId=this.merchantSelected;


                $.ajax({url:`${basePath}/rede/list`,type:"POST"}).done(function(arr){
                    $this.redEnvelope=arr;
                    // console.log(arr);

                    //open
                    $this.show.separate=true;
                });
            }

        },
        doSeparateProcess(){
            let $this = this;
            // console.log($this.separateParam);
            let items = this.getSelectedItems();
            let cart = {items};
            $this.separateParam.cart=cart;
            // console.log(serialize($this.separateParam));

            //
            // this.separateParam.cart=;
            // console.log(this.redEnvelope);
            var arr = [];
            for(let on of  this.redEnvelope) {
                // console.log(on);
                if(on.isEnable==1){
                    arr.push(on);
                }
            }
            this.separateParam.redEnvelope=arr;


            $.ajax({url:`${basePath}/calc/caculate`,data:serialize($this.separateParam),type:"POST"}).done(function(dt){
                console.log(dt);

                window.open(`${basePath}/query/?queryId=${dt}`);
            });
        }
    },
    filters: {

    },
    // router
//            components: {
//                'Choose': Child
//            }
});


vm.$watch('merchants', function (newVal, oldVal) {
    // 这个回调将在 `vm.a`  改变后调用
    console.log(newVal);
    console.log(oldVal);
})


vm.queryDefaults();
vm.merchantQuery();


