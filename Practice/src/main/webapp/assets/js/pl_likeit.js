/**
 * Created by DELL-13 on 2017/4/24.
 */
Vue.component('Likeit', {
    // 声明 props
//            props: ['obj','from','to','cur'],
    data(){
        return {
        }
    },
//        prop 是一个对象而不是字符串数组时，它包含验证要求：
    props:{
        obj:Object,
        p:{
            type: String,
            required: true
        },
        path:{
            type: String,
            required: true
        },
        cls:{
            type: String,
            default: "chosen"
        }
    },
    computed:{
        chosenLikeCls(){
            let $this= this;
            return {
                [$this.cls]:$this.obj[$this.p]>0
            }
        },
        chosenDisLikeCls(){
            let $this= this;
            return {
                [$this.cls]:$this.obj[$this.p]<0
            }
        },
    },
    // 同样也可以在 vm 实例中像 "this.message" 这样使用
    template: `
                <div class="comment">
                    <span class="like"  v-bind:class="chosenLikeCls" @click="like(1)">↑</span>
                    <span class="dislike"  v-bind:class="chosenDisLikeCls" @click="like(-1)">↓</span>
                </div>`,
    methods: {
        like(val){
            if(this.obj[this.p]==val){
                this.obj[this.p] = 0;
            }else {
                this.obj[this.p]=val;
            }

            var id=this.obj.id;
            var like=this.obj[this.p];
            $.ajax({url:`/${this.path}/like`,data:{id,like}});
            // this.$emit('increment',this.obj,dir);
        }
    }
});
