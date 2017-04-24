Vue.component('Choose', {
    // 声明 props
//            props: ['obj','from','to','cur'],
    data(){
        return {
            r_cur:this.validateN(this.obj[this.p])
        }
    },
//        prop 是一个对象而不是字符串数组时，它包含验证要求：
    props:{
        obj:Object,
        from:{
            type: Number,
            default: 0
        },
        to:{
            type: Number,
            default: -1
        },
        cur:{
            type: Number,
            default: 0
        },
        p:{
            type: String,
            required: true
        }
    },
//            computed: {
//                cur: function () { return this.cur; }
//            },
    // 同样也可以在 vm 实例中像 "this.message" 这样使用
    template: `<span class="chooseNum" >
                 <button class="removeBtn"  @click="increment(-1)" tabindex="-1">-</button>
                <input type="text" v-model="obj[p]" @keydown.down="increment(-1)" @keydown.up="increment(1)" @keydown.left="increment(-1)" @keydown.right="increment(1)"/>
                <button class="addBtn" @click="increment(1)"  tabindex="-1">+</button>
                </span>`,
    methods: {
        increment: function (dir) {
            this.r_cur=this.validateN(this.r_cur+dir);

            this.obj[this.p]=this.r_cur;


            this.$emit('increment',this.obj,dir);
        },
        validateN: function (n) {
            if (n<this.from){
                n=this.from;
            }
            if(this.to>=this.from&&n>this.to){
                n=this.to;
            }
            return n;
        }
    }
});
