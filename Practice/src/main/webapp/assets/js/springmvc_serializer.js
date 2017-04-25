//serialize({a:[ { b:[ {c:11} ] },{} ]})
function serialize(data) {
    if (data){
        var ret={};
        for(var prop in data) {
            var val = data[prop];
            if( val instanceof Array){
                for(var i =0;i<val.length;i++){
                    var json=val[i];
                    var sJson = serialize(json);

                    for(var jsonProp in sJson){
                        var name=prop+"["+i+"]."+jsonProp;
                        ret[name]=sJson[jsonProp];
                    }
                }
            }
            else if(typeof val === "object"){
                var sJson = serialize(val);
                for(var jsonProp in sJson) {
                    ret[prop+"."+jsonProp]=sJson[jsonProp];
                }
            }
            else {
                ret[prop]=val;
            }
        }
        return ret;
    }
    return data;
}