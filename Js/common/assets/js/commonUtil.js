//object
var _o = {
    isNull:function (obj){
        if( null==obj || undefined == obj  )
            return true;
        else
            return false;
    },
    isNotNull:function (obj){
        return !this.isNull(obj);
    },
    isObject:function (obj) {
        if(this.isNotNull(obj) && typeof obj==='object' && !this.isArray(obj)){
            return true;
        }
        return false;
    },
    isArray:function (obj) {
        if(this.isNotNull(obj) && obj instanceof Array){
            return true;
        }
        return false;
    },
    isString:function (obj) {
        if(this.isNotNull(obj) && typeof obj==='string'){
            return true;
        }
        return false;
    },
    isFunction:function (obj) {
        if(this.isNotNull(obj) && typeof obj==='function'){
            return true;
        }
        return false;
    },
    isRequestSuccess:function (json) {
        if (this.isObject(json)) {
            return json.errorCode == 0 && json.success === true;
        }
        return false;
    },
    equal:function (json1,json2) {
        return this.contains(json1,json2) && this.contains(json2,json1);
    },
    //json1 contains json2
    contains:function (json1,json2) {
        if(this.isObject(json1) && this.isObject(json2)){
            for(var a in json2){
                if(json1[a] !== json2[a]){
                    return false;
                }
            }

            return true;
        }
        return false;
    }
}


//convertor 转换器
var _c={

}

//String
var _s = {
    trim:function (str){
        if(_o.isNull(str))
            return str;
        return str.replace(/(^\s*)|(\s*$)/g, "");
    },
    isEmpty:function (str){
        if( null==str || 'null'==str || undefined == str || 'undefined'==str ||
            ''==str || str.length ==0  || str.match(/^\s*$/))
            return true;
        else
            return false;
    },
    isNotEmpty:function (str){
        return !this.isEmpty(str);
    }
}

//array
var _a = {
    //在数组里查找prop=val的那条json数据
    findObjByPropAndVal:function (arr, prop, val) {
        if(_o.isArray(arr) && _o.isString(prop)){
            for (var i = 0; i < arr.length; i++) {
                var fileData = arr[i];

                if (fileData && fileData[prop] === val) {
                    return fileData;
                }
            }
        }

        return null;
    },
    //在数组里查找prop=val的那些json数据
    findArrayByPropAndVal:function (arr, prop, val) {
        var ret = [];
        for (var i = 0; i < arr.length; i++) {
            var fileData = arr[i];

            if (fileData && fileData[prop] == val) {
                ret.push(fileData);
            }
        }
        return ret;
    },
    findIdxByPropAndVal :function (arr, prop, val) {
        for (var i = 0; i < arr.length; i++) {
            var fileData = arr[i];

            if (fileData && fileData[prop] == val) {
                return i;
            }
        }
        return -1;
    },
    //截取json数组的每条数据的某个属性，添加到数组里返回
    cutProp:function (arr, prop) {
        var ret = [];
        for (var i = 0; i < arr.length; i++) {
            var fileData = arr[i];

            if(fileData[prop] != undefined)
                ret.push(fileData[prop]);
        }
        return ret;
    },
    //相当于java里的map.keySet()
    mapKeys:function (obj) {
        var ret = [];
        for (var key in obj) {
            ret.push(key);
        }
        return ret;
    },
    //两数组完全相同，包括顺序
    arrayEqual:function (arr1,arr2) {
        // var minLen = Math.min(arr1.length,arr2.length);
        if(arr1 && arr2){
            if (arr1.length != arr2.length) {
                return false;
            }
            for (var i = 0; i < arr1.length; i++) {
                if (arr1[i] != arr2[i] ) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

}



//浏览器对象,同时返回版本
//_n.ie ,_n.chrome,...
//_n.version TODO
var _n = function () {
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
        (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
            (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
                (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
                    (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
    Sys.version = ua.replace(/[^0-9.]/ig,"");
    return Sys;
}();