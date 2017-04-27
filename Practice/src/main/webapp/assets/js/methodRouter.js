
class MethodRouter{
    constructor(config){
        this.config = config;
    }

    process(path){
        var args = path.split(".");
        var r = this.config;
        for(let prop of args) {
            r = r[prop];
        }
        if(typeof r=="function"){
            r.apply(this,...[[...arguments].slice(1)]);
        }
    }

    getf(path){
        var args = path.split(".");
        var r = this.config;
        for(let prop of args) {
            r = r[prop];
        }
        return r;
    }

}

//_methodsRouter.process("dialog.separate",3223);
var _methodsRouter = new MethodRouter(
    {
        dialog:{
            separate(a){
                console.log(a);
                console.log(this);
                console.log('separate');

            }
        }
    }
);