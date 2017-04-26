var vm = new Vue({
    el: '#queryContainer',
    data: {
        queryRs:{

        }
    },
    computed:{
        dt(){
            if(this.queryRs.data){
                return this.queryRs.data;
            }
            return {};
        },
        detail(){
            if(this.dt.detail){
                return this.dt.detail;
            }
            return {};
        }
    },
    methods: {
        query(){
            var $this= this;
            $.ajax({url:`${basePath}/query/process`,data:{queryId},type:"POST"}).done(function(dt){
                console.log(dt);
                $this.queryRs=dt;
                // $this.detail=dt.detail;

                if(!(dt.code==0||dt.code==3)){
                    // clearInterval(_ts);
                    setTimeout($this.query,1000);
                }
            });
        },
        getSolution(i){
            if(this.detail && this.detail.solutions){
                // console.log(this.detail.solutions);
                // console.log(i);
                if(i-1<this.detail.solutions.length){
                    return this.detail.solutions[i-1];
                }
            }
            return {};
        }
    }
});

vm.query();

// var _ts=setInterval(()=>vm.query(),1000);


