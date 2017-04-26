/**
 * Created by DELL-13 on 2017/4/24.
 */
Vue.component('Solution', {
    // 声明 props
//            props: ['obj','from','to','cur'],
    data(){
        return {
        }
    },
//        prop 是一个对象而不是字符串数组时，它包含验证要求：
    props:{
        preview:{
            type: Object,
            required: true
        },
        previewOrder:{
            type: Object,
            required: true
        },
        // p:{
        //     type: String,
        //     required: true
        // },
        // path:{
        //     type: String,
        //     required: true
        // },
        // cls:{
        //     type: String,
        //     default: "chosen"
        // }
    },
    computed:{
    },
    // 同样也可以在 vm 实例中像 "this.message" 这样使用
    template: `
               <div class="statistics">
                     <div class="dishesStats">
                         <div class="" v-if="preview.failed">
                             {{preview.failedReason}}
                         </div>
                         <div class="" v-else>
                             <ul>
                                 <li v-for="item in previewOrder.items">
                                     <span>{{item.name}}</span>
                                     x<span>{{item.num}}</span>
                                     <span class="price">￥{{item.num*item.money}}</span>
                                 </li>
                             </ul>
                         </div>
                     </div>
                     <div class="feesStats">
                         <div class="" v-if="preview.failed">
                             {{preview.failedReason}}
                         </div>
                         <div class="" v-else>
                             <ul>
                                 <li v-for="one in previewOrder.extraMoneyList">
                                     <span>{{one.name}}</span>
                                     <span class="price">￥{{one.price}}</span>
                                 </li>
                             </ul>
                             <ul>
                                 <li v-for="one in previewOrder.reducedMoneyList">
                                     <span>{{one.name}}</span>
                                     <span class="price">-￥{{one.price}}</span>
                                 </li>
                             </ul>
                             <div v-if="preview.failed!=undefined && !preview.failed">
                                合计<span class="price">￥{{previewOrder.price}}</span>
                             </div>
                         </div>
                     </div>
                 </div>`,
    methods: {
    }
});
