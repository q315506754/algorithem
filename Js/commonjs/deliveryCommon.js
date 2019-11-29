var C = {
    message: {
        error: function (msg, callback) {
            Message.errorAuto(msg, callback);
        }
    }
    , common: {
        getFirstAssistantName: function (array) {
            if (array.length > 1) {
                var userName = array[0].userName;
                if (userName == null || userName == "null") {
                    return "...";
                } else {
                    return userName + "...";
                }
            } else if (array.length == 1) {
                var userName = array[0].userName;
                if (userName == null || userName == "null") {
                    return "";
                } else {
                    return userName;
                }
            } else {
                return '';
            }
        },
        getAllAssistantName: function (array) {
            var userName = "";
            if (array.length > 0) {
                for (var i = 0; i < array.length; i++) {
                    userName += (array[i].userName);
                    if (i < array.length - 1) {
                        userName += "\r\n";
                    }
                }
                return userName;
            } else {
                return userName;
            }
        }
    }
}


// common
function getObjOfForm(formId, name) {
    return $("#" + formId).find("[name='" + name + "']");
}
function findObjOfForm(formId, str) {
    return $("#" + formId).find(str);
}
function clearForm(formId) {
    $("#" + formId).find("[name]").not("[fixed]").val("");
    $("#" + formId).find("img[name]").not("[fixed]").removeAttr("src");

    //select
    $("#" + formId).find("select[name]").not("[fixed]").each(function (idx,ele) {
        var fv = $(ele).children("option:eq(0)").val();
        $(ele).val(fv);
    });

    //checkbox
    $("#" + formId).find("input:checkbox").not("[fixed]").each(function (idx,ele) {
        $(ele).removeAttr("checked");
        // $(ele).next(".layui-form-checkbox").removeClass("layui-form-checked");
    });

    layui.use(['form'], function () {
        layui.form.render('select');
        layui.form.render('checkbox');
    });
}
function injectForm(formId, obj,mp) {
    mp = mp || {};

    for (let objKey in obj) {
        var realName = mp[objKey] || objKey;
        getObjOfForm(formId,realName).not("[fixed]").val(obj[objKey]);
    }

    layui.use(['form'], function () {
        layui.form.render('select');
    });
}
function getFormData(formId) {
    var ret = {};
    $("#" + formId).find("[name]").not(":disabled").each(function (idx,ele) {
        var key = $(ele).attr("name");
        var v = $(ele).val();
        ret[key] = v;
    });
    return ret;
}
var MODE_CREATE = 1;
var MODE_EDIT = 2;

// flatObj({a:{aa:22,cc:33}, b:1},"a") -> {_a_aa:2,_a_cc:33,b:1}
function flatObj( obj,prop) {
    if (obj) {
        for (var i in obj[prop]){
            obj["_" + prop + "_" + i] = obj[prop][i];
        }
        // delete obj[prop];
        return obj;
    }
}
function flatObj2( obj,prop,newProp) {
    if (obj) {
        for (var i in obj[prop]){
            obj["_" + newProp + "_" + i] = obj[prop][i];
        }
        delete obj[prop];
        return obj;
    }
}

// flatObjThis({a:{aa:22,cc:33}, b:1},"uc") -> {a: {…}, b: 1, _uc_b: 1}
function flatObjThis( obj,newProp) {
    if (obj) {
        var one = {};

        for (var i in obj){
            var v = obj[i];
            if (typeof v !== 'object') {
                one["_" + newProp + "_" + i] = v;
            }
        }

        for (var i in one){
            obj[i] = one[i];
        }

        return obj;
    }
}
// unflatObj({_a_aa:2,_a_cc:33,b:1},"a") -> {a:{aa:22,cc:33}, b:1}
function unflatObj(obj,prop) {
    if (obj) {
        var jx = {};
        for (var i in obj){
            let searchString = "_" + prop + "_";
            if (i.startsWith(searchString)) {
                var rprop = i.substring(searchString.length);
                jx[rprop] = obj[i];

                delete obj[i];
            }
        }

        obj[prop] = jx;
        return obj;
    }
}
// flatToDotObj({_a_aa:2,_a_cc:33,b:1},"a") -> {b: 1, a.aa: 2, a.cc: 33}
function flatToDotObj(obj,prop) {
    if (obj) {
        for (var i in obj){
            let searchString = "_" + prop + "_";
            if (i.startsWith(searchString)) {
                var rprop = i.substring(searchString.length);
                obj[prop+"."+rprop] = obj[i];

                if (rprop.indexOf("_") >= 0 ) {
                    var mprop =  rprop.replace("_",".")
                    obj[prop+"."+mprop] = obj[i];
                }

                delete obj[i];
            }
        }

        return obj;
    }
}

//模式 1:create 2:edit
function clearOrInjectForm(formId, obj,mp) {
    if (!obj || typeof  obj != 'object' ) {
        clearForm(formId);

        return MODE_CREATE;
    } else {
        clearForm(formId);
        injectForm(formId,obj,mp);

        return MODE_EDIT;
    }
}

function setCheckBox($obj,t) {
    if (t) {
        $obj.attr("checked","checked");
    } else {
        $obj.removeAttr("checked");
    }

    layui.use(['form'], function () {
        layui.form.render('checkbox');
    });
}


function appendEleData(divId,arr,eachFunc) {
    arr = arr || [];
    var $content = $("<div></div>");
    for (var i = 0; i < arr.length; i++) {
        var one = arr[i];

        var $str = eachFunc(one,i);

        // console.log(this.);
        $content.append($str);
    }

    if(arr.length == 0){
        $content.append($("<tr><td style='text-align: center;' colspan='"+$("#"+divId).prev("thead").children().children("th").size()+"'>暂无数据</td></tr>"));
    }

    $("#"+divId).html($content.children());
}

function downloadFile(name,url) {
    window.location.href = basePath + "/crm/downloadFile?name=" + name+"&fileUrl="+
        url;
}


var commonClear=function($obj) {
    $obj.nextAll(".error-tips").text("");
    $obj.removeClass("errorBorder");
}
var commonError=function($obj,str) {
    $obj.nextAll(".error-tips").text(str);
    $obj.addClass("errorBorder");
}


function parseParams(data) {
    try {
        var tempArr = [];
        for (var i in data) {
            var key = encodeURIComponent(i);
            var value = encodeURIComponent(data[i]);
            tempArr.push(key + '=' + value);
        }
        var urlParamsStr = tempArr.join('&');
        return urlParamsStr;
    } catch (err) {
        return '';
    }
}

var snpic=function(url,sx) {
    return url.substring(0,url.lastIndexOf("."))+"_"+sx+url.substring(url.lastIndexOf("."))
}
var s1pic=function(url) {
    return snpic(url,"s1")
}
var s2pic=function(url) {
    return snpic(url,"s2")
}
var s3pic=function(url) {
    return snpic(url,"s3")
}

//请求url 将typeMap 放入select选项中
function injectSelectOptions(url,$objArrFunc,hasAllFunc) {
    var $list_arr = $objArrFunc();
    var hasAllList = hasAllFunc();

    var realLogic = function(dt){

        for(var idx=0;idx<$list_arr.length;idx++){
            var $obj = $list_arr[idx];
            $obj.empty();

            if (hasAllList.length > idx && hasAllList[idx]) {
                $obj.append(` <option value="">全部</option>`);
            }

            for (var i in dt) {
                $obj.append(` <option value="${i}">${dt[i]}</option>`);
            }
        }
    }


    $.ajax({
        url: url,
        type: "POST",
        success: function (rs) {
            realLogic(rs);
        }
    });

}

$(function () {
    //#opt ESC=关闭layer
    $(document).delegate('body','keyup',function (e) {
        if (e.which == 27) {
            //如果你想关闭最新弹出的层，直接获取layer.index即可
            // layer.close(layer.index); //它获取的始终是最新弹出的某个层，值是由layer内部动态递增计算的

            layer.closeAll();
        }
    });

    //优化重复请求
    _global_ajax_record = {};
    $.ajaxNoDup  = function (opt,prop) {
        prop = prop|| 'default';
        if ($.checkLock(prop)) {
            return ;
        }

        _global_ajax_record[prop] = true;

        opt.complete=function () {
            _global_ajax_record[prop] = false;
        }
        $.ajax(opt);
    };

    $.checkLock = function (prop) {
        prop = prop|| 'default';
        var locked = !!_global_ajax_record[prop];
        return locked;
    }
});