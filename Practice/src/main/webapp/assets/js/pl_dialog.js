/**
 * Created by DELL-13 on 2017/4/24.
 */
Vue.component('dialog-x', {
    // 声明 props
    //        props: ['p','from','to','cur'],
    data(){
        return {
        }
    },
//        prop 是一个对象而不是字符串数组时，它包含验证要求：
    props:{
        // obj:Object,
        // p:{
        //     type: String,
        //     required: true
        // },
        // path:{
        //     type: String,
        //     required: true
        // },
        // show:{
        //     type: Boolean,
        //     default: false
        // },
        p:{
            type: String,
            required: true
        }
    },
    computed:{
        getShow(){
            // console.log(this.$parent.show);
            // console.log(this.$parent.$data);
            // console.log(this.$parent.show);
            // console.log(this.$parent.$data);
            return this.$parent.show;
        },

    },
    // 同样也可以在 vm 实例中像 "this.message" 这样使用
    template: `
        <div  @keydown.esc="cancel">
        <div  class="dialogLayer" v-show="getShow[p]">
        </div>
         <div  class="dialog" v-show="getShow[p]">
              <div class="dialogHead">
                <slot name="head"></slot>
              </div>
              <hr/>
              <div class="dialogBody">
                    <slot></slot>
              </div>
              <hr/>
              <div class="dialogFoot">
                    <button @click="save">save</button>
                    <button @click="cancel">cancel</button>
                       <slot name="foot"></slot>
              </div>
          </div>
    </div>
`,
    methods: {
        save(){
            this.cancel();

            // $.ajax({url:`/${this.path}/like`,data:{id,like}});
            this.$emit('save');
        },
        cancel(){
            this.getShow[this.p]=false;
        }
    }

});
