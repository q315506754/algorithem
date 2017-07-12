class ArrayUtil{
    static setArrayProp(arr,prop,value){
        for(let one of arr){
            one[prop]=value;
        }
    }

    static getArrayProp(arr,prop,value){
        let ret = [];
        for(let one of arr){
            if (one[prop]=value){
                ret.push[one];
            }
        }
        return ret;
    }

    //ArrayUtil.copyArrayProp([{a:1,b:2,c:3}],'b','c');
    static copyArrayProp(arr,...props){
        let ret = [];
        for(let one of arr){
            let nOne = {};
            for(let p of props){
                nOne[p]=one[p]
            }
            ret.push(nOne);
        }
        return ret;
    }

    static populateArrayProp(arr,propsArr,keyP){
        for(let pone of propsArr){
            for(let aone of arr){
                if(aone[keyP]==pone[keyP]){

                    for(let pp in pone){
                        aone[pp]=pone[pp];
                    }

                    break;
                }
            }
        }
    }
}


var vm = new Vue({
    el: '#mainContainer',
    data: {
        prop:{selected:"isSelected",choose:"chooseNum",like:"likeit"},
        show:{merchantCreate:false,merchantUpdate:false,dishCreate:false,separate:false,config:false,dishUpdate:false},
        merchants:[
        ],
        merchantSelected:-1,
        dishes:[
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
        redeCreate: {
            reach: 0,
            reduce: 0,
        },
        preview:{

        },
        defaults:{

        },
        separateParam:{
            maxOrder:-1,
            minOrder:-1,
            maxRedEnvelopeChosen:-1,
            isVip:false,
            merchantId:-1
        },
        redEnvelope:[]
        ,
        dishUpdateModel:{},
        merchantUpdateModel:{},
        merchantUpdateRulesModel:[],
        merchantUpdateRuleCreateModel: {
            reach: 0,
            reduce: 0,
            merchantId:-1
        }
    },
    computed: {
        previeworder: function () {
            if(this.preview && this.preview.orders){
                return this.preview.orders[0] || {};
            }
            return {};
        }
    },
    methods: {
        merchantSelect(obj){
//                    console.log(arguments);
            ArrayUtil.setArrayProp(this.merchants,this.prop.selected,false);

            obj[this.prop.selected]=true;
//                    console.log(this.merchants);
//                    this.$set('merchants',this.merchants);

            this.merchantSelected=obj.id;
            this.dishCreate.merchantId=obj.id;

            this.dishQuery();
        },
        merchantEditDialog(obj) {
            var $this =this;

            //规则的商户id
            $this.merchantUpdateRuleCreateModel.merchantId=obj.id;

            $.ajax({url:`${basePath}/merchant/find`,data:{id:obj.id}}).done(function(dt){
                $this.merchantUpdateModel=dt;

            } ).done($this.merchantUpdateRuleQuery(()=>$this.show.merchantUpdate=true));

        },
        dishUpdate(obj) {
            var $this =this;

            $.ajax({url:`${basePath}/dish/save`,data:this.dishUpdateModel,type:"POST"})
                .done($this.dishQuery);
        },
        dishEditDialog(obj) {
            var $this =this;

            //规则的商户id
            $.ajax({url:`${basePath}/dish/find`,data:{id:obj.id}}).done(function(dt){
                $this.dishUpdateModel=dt;

            } ).done($this.merchantUpdateRuleQuery(()=>$this.show.dishUpdate=true));
        },
        merchantUpdateRuleQuery(cb) {
            var $this =this;
            $.ajax({url:`${basePath}/rule/findList`,data:{merchantId: $this.merchantUpdateRuleCreateModel.merchantId}}).done(function(arr){
                $this.merchantUpdateRulesModel=arr;
            }).done(cb);
        },
        merchantUpdateRuleSave() {
            var $this =this;
            $.ajax({url:`${basePath}/rule/save`,data: $this.merchantUpdateRuleCreateModel,type:"POST"})
                .done($this.merchantUpdateRuleQuery);
        },
        merchantUpdateRuleRemove(one) {
            var $this =this;
            $.ajax({url:`${basePath}/rule/remove`,data:{id:one.id}})
                .done($this.merchantUpdateRuleQuery);
        },
        merchantSave() {
            var $this =this;
            console.log(this.merchantCreate);

            $.ajax({url:`${basePath}/merchant/save`,data:this.merchantCreate,type:"POST"}).done(function(arr){
                $this.merchantQuery();
            } )
        },
        merchantUpdate() {
            var $this =this;

            $.ajax({url:`${basePath}/merchant/save`,data:this.merchantUpdateModel,type:"POST"});
        },
        dishSave() {
            var $this =this;
            console.log(this.dishCreate);

            $.ajax({url:`${basePath}/dish/save`,data:this.dishCreate,type:"POST"}).done(function(arr){
                $this.dishQuery();
            } )
        },
        dishFocus($event) {
            $($event.target).find("input").focus();
        },
        merchantQuery(){
//                    this.merchants.push({id:1,name:"asdas"});
            let $this = this;
            $.ajax(`${basePath}/merchant/list`).done(function(arr){
                ArrayUtil.setArrayProp(arr,$this.prop.selected,false);
                $this.merchants=arr;

                if(arr.length>0){
                    $this.merchantSelect($this.merchants[0]);
                }

            } )
        },
        dishQuery(){
            let $this = this;

            let recordChoose= ArrayUtil.copyArrayProp($this.dishes,'id',$this.prop.choose);
            // console.log(recordChoose);

            $.ajax({url:`${basePath}/dish/list`,data:{merchantId:this.merchantSelected}}).done(function(arr){
//                        console.log(arr);
                ArrayUtil.setArrayProp(arr,$this.prop.selected,false);
                ArrayUtil.setArrayProp(arr,$this.prop.choose,0);

                ArrayUtil.populateArrayProp(arr,recordChoose,"id");
                console.log(arr);

                $this.dishes=arr;

                $this.calcPreview();
//                        console.log($this.dishes);
            } )
        },
        dishClear(){
            let $this = this;
            ArrayUtil.setArrayProp($this.dishes,$this.prop.choose,0);

            $this.calcPreview();
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

            $this.calcPreview();
        },
        calcPreview(){
            let $this = this;
            let items = this.getSelectedItems();
            let merchantId = this.merchantSelected;

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

            if(true){
            // if($this.preview.failed!=undefined && !$this.preview.failed){
                //default
                this.separateParam.maxOrder=this.defaults.maxOrder;
                this.separateParam.minOrder=this.defaults.minOrder;
                this.separateParam.maxRedEnvelopeChosen=this.defaults.maxRedEnvelopeChosen;
                this.separateParam.isVip=this.defaults.isVip;
                this.separateParam.merchantId=this.merchantSelected;


                this.redEnvelopQuery(()=>$this.show.separate=true);
            }

        },
        configDialog(){
            let $this = this;

            this.redEnvelopQuery(()=>$this.show.config=true,true);
        },
        redEnvelopQuery(cb,isAll){
            let $this = this;

            let last = isAll?'listAll':'list';

            $.ajax({url:`${basePath}/rede/${last}`,type:"POST"}).done(function(arr){
                $this.redEnvelope=arr;
                // console.log(arr);

                //open
                // $this.show.separate=true;
            }).done(cb);
        },
        redEnvelopEnable(one){
            one.isEnable=1-one.isEnable;

            $.ajax({url:`${basePath}/rede/enable`,data:{id:one.id,isEnable:one.isEnable},type:"POST"});
        },
        redEnvelopRemove(one){
            let $this = this;
            $.ajax({url:`${basePath}/rede/remove`,data:{id:one.id},type:"POST"}).done($this.configDialog);
        },
        redEnvelopSave(){
            let $this = this;
            $.ajax({url:`${basePath}/rede/save`,data:$this.redeCreate,type:"POST"}).done($this.configDialog);
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


