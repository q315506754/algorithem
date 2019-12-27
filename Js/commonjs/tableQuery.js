(function($){
    var defaults = {
        loadData:function (f) {

        },
        drawRow:function (rowData,i,gid) {
            return "<tr></tr>";
        },
        emptyContent:function () {
            return "<tr></tr>";
        },
        onPostDrawn:function () {
        },
        onPostEachRowDrawn:function ($str,one) {
        },
        onPostKeyup:function ($ele) {

        },
        onBeforeRequest:function ($ele) {

        },
        afterQuery:function (count) {

        },
        frontQuery:true,//是否需要前端搜索
        filters:[{
            ele:"",
            prop:"courseName",
            addClear:{
                paddingRight:"20px",
                right : "8px",
            }
        }]
    };
    var innerOpt = {
        data_attr: "_tableQuery_",
        no_data_cls :"no-data-span"
    };
    function Plugin(element, options) {
        this.el = $(element);
        this.opt = options;
        this.datalist = [];
        this._initProcess();
    }

    //产生随机数函数
    function RndNum(n){
        var rnd="";
        for(var i=0;i<n;i++)
            rnd+=Math.floor(Math.random()*10);
        return rnd;
    }
    function generateId(){
        return RndNum(5)+"-"+RndNum(4)+"-"+RndNum(8);
    }

    Plugin.prototype = {
        _initProcess: function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;

            $plgThis._request();

            $plgThis._bindEvents();
        },
        _bindEvents:function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;

            if(opt.filters) {
                for(var i=0;i<opt.filters.length;i++){
                    var one = opt.filters[i];

                    var $ele;
                    if(typeof one.ele == "string"){
                        $ele =  $(one.ele);
                    }

                    if($ele.prop("tagName") == "INPUT" && $ele.prop("type") == "text" ) {
                        //#双击 选中
                        $ele.dblclick(function () {
                            $(this).select();
                        });

                        var cpLock = false;
                        $(document).delegate(one.ele,'compositionstart',function () {
                            cpLock = true;
                        });
                        $(document).delegate(one.ele,'compositionend',function () {
                            cpLock = false;
                        });
                        $(document).delegate(one.ele,'keyup',function () {
                            if (!cpLock) {
                                $plgThis.refresh();

                                opt.onPostKeyup($ele);
                            }
                        });


                        //addClear
                        if(one.addClear && "addClear" in $.fn){
                            var addClearOpt = one.addClear;
                            addClearOpt.onClear = function () {
                                $plgThis.refresh();

                                opt.onPostKeyup($ele);
                            }

                            $ele.addClear(addClearOpt);
                        }
                    }

                    // if(one.onInit){
                    //     one.onInit($ele);
                    // }
                }
            }
        },
        _request:function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;

            if(opt.onBeforeRequest){
                opt.onBeforeRequest();
            }

            opt.loadData(function (arr) {
                arr = arr || [];
                $plgThis.datalist = arr;

                var $total=$("<div></div>");

                for(var i=0;i<arr.length;i++) {
                    var one = arr[i];
                    one._id = generateId();

                    var $str = opt.drawRow(one,i,one._id);
                    // console.log($str);
                    // console.log(typeof $str);
                    if(typeof $str === "string") {
                        $str = $($str);
                    }

                    $str.attr("id", one._id);

                    $total.append($str);

                    if(opt.onPostEachRowDrawn){
                        opt.onPostEachRowDrawn($str,one);
                    }

                }

                //no data
                var $nodata = $(opt.emptyContent());
                $nodata.addClass(innerOpt.no_data_cls);
                if(arr.length == 0) {
                    $nodata.show();
                } else {
                    $nodata.hide();
                }
                $total.append($nodata);

                $el.html($total.children());

                if(opt.onPostDrawn){
                    opt.onPostDrawn();
                }

                //刷新一次
                //应用搜索
                $plgThis.refresh($el);
            });

        },
        reload:function(field,cb,cbForOver) {
            // console.log(arguments);
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;
            var datalist = $plgThis.datalist;

            if(opt.onBeforeRequest){
                opt.onBeforeRequest();
            }

            opt.loadData(function (arr) {
                arr = arr || [];
                for(var i=0;i<arr.length;i++) {
                    var one = arr[i];


                    //find in inner list
                    for(var j=0;j<datalist.length;j++) {
                        var i_one = datalist[j];

                        if(one[field] === i_one[field]){

                            if(cb){
                                cb.apply($plgThis,[$("#"+i_one._id),one,i_one]);
                            }
                            break;
                        }
                    }
                }


                if(cbForOver){
                    cbForOver.apply($plgThis);
                }

            });

        },
        removeById:function(_id) {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;
            var datalist = $plgThis.datalist;

            for(var i=0;i<datalist.length;i++) {
                var one = datalist[i];
                if (one._id==_id){
                    datalist.splice(i,1);

                    $plgThis.refresh();

                    return;
                }
            }
        },
        refresh:function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;
            var datalist = $plgThis.datalist;
            //无需前端搜索

            var frontQuery = false;

            if(typeof  opt.frontQuery=='function') {
                frontQuery = opt.frontQuery();
            } else {
                frontQuery= opt.frontQuery;
            }

            if(!frontQuery) {
                return;
            }

            var data= {};

            if(opt.filters) {
                for (var i = 0; i < opt.filters.length; i++) {
                    var one = opt.filters[i];

                    var $ele;
                    if(typeof one.ele == "string"){
                        $ele =  $(one.ele);
                    }

                    //待过滤值
                    data[one.prop] = $ele.val();

                    //重新绘制 addClear
                    if($ele.prop("tagName") == "INPUT" && $ele.prop("type") == "text" ) {
                        //addClear
                        if(one.addClear && "addClear" in $.fn){
                            $ele.trigger("addClearRefresh");
                        }
                    }
                }
            }

            // console.log(data);
            $plgThis.query(data);
        },
        _match:function (dt,queryJson) {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;

            for(var key in queryJson){
                // console.log(key + queryJson[key]);

                var mode="=";

                if(opt.filters) {
                    for (var i = 0; i < opt.filters.length; i++) {
                        var one = opt.filters[i];

                        if(one.prop == key){
                            mode = one.mode;
                        }
                    }
                }

                if(typeof mode=="string") {
                    if(mode=="="){
                        if(dt[key] != queryJson[key]){
                            return false;
                        }
                    } else if(mode=="*"){
                        var str = $.trim(queryJson[key]);

                        //查询  无字段
                        if(str.length > 0 && !dt[key]){
                            return false;
                        }

                        //查询  找不到
                        if(str.length > 0 && dt[key].indexOf(str) < 0){
                            return false;
                        }
                    }
                } else {
                    //function
                    if(mode != undefined && !mode(key,dt,queryJson)){
                        return false;
                    }
                }

            }

            return true;
        },
        query:function (fieldAndValueObj) {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;
            var datalist = $plgThis.datalist;

            var count = 0;
            for(var i=0;i<datalist.length;i++){
                var one = datalist[i];

                if($plgThis._match(one,fieldAndValueObj)){
                    $("#"+one._id).show();
                    count++;
                } else {
                    $("#"+one._id).hide();
                }
            }

            if(count == 0) {
                $el.find("."+innerOpt.no_data_cls).show();
            } else {
                $el.find("."+innerOpt.no_data_cls).hide();
            }

            if(opt.afterQuery) {
                opt.afterQuery(count);
            }

        },
        //额外增加一个
        append:function (one) {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;
            var datalist = $plgThis.datalist;

            one._id = generateId();
            datalist.push(one);

            var $str = opt.drawRow(one,datalist.length,one._id);

            if(typeof $str === "string") {
                $str = $($str);
            }

            $str.attr("id", one._id);

            if(opt.onPostEachRowDrawn){
                opt.onPostEachRowDrawn($str,one);
            }
            $el.append($str);

            //刷新一次
            //应用搜索
            $plgThis.refresh($el);
        }
    }

    $.fn.tableQuery=function(params){
        var lists = this,
            retval = this;
        var args = arguments;
        lists.each(function () {
            var plugin = $(this).data(innerOpt.data_attr);

            if (!plugin ) {
                if (typeof params === 'object') {
                    params = $.extend({}, defaults, params);
                    var nPlg = new Plugin(this, params);
                    $(this).data(innerOpt.data_attr, nPlg);
                    retval = nPlg;
                }
            } else {
                if (typeof params === 'object') {
                    params = $.extend({}, defaults, params);

                    //重新设置
                    plugin.opt=params;

                    plugin._request();
                } else {
                    if (typeof params === 'string' && typeof plugin[params] === 'function') {
                        retval = plugin[params].apply(plugin, [].slice.call(args, 1));
                    } else {
                        retval = plugin;
                    }
                }
            }
        });

        return retval || lists;
    }

    $.fn.serializeJSON=function(params){
        var data = $(this).serializeArray();

        var ret = {};

        for(var i=0;i<data.length;i++){
            var one = data[i];

            ret[one.name]=one.value;
        }

        return ret;
    }

})(jQuery);

//[1,2] -> [{userId:1,userId:2}]
function listToArray(list,prop) {
    var ret = [];
    for(var i=0;i<list.length;i++){
        var one = list[i];

        var each = {};
        each[prop] = one;

        ret.push(each);
    }
    return ret;
}

//  [{userId:1,userId:2}] -> [1,2]
function arrayToList(list,prop) {
    var ret = [];
    for(var i=0;i<list.length;i++){
        var one = list[i];

        var each = one[prop];

        ret.push(each);
    }
    return ret;
}

function injectInputData($container,data,map) {
    for(var k in map) {
        // console.log(getInputDiv($container,map[k]));
        getInputDiv($container,map[k]).val(data[k]);
        getSelectDiv($container,map[k]).val(data[k]);
        // $container.find("input[name='"+map[k]+"']").val(data[k]);
    }
}
function getInputDiv($container,name) {
    return $container.find("input[name='"+name+"']");
}
function getSelectDiv($container,name) {
    return $container.find("select[name='"+name+"']");
}
function removeDataInArray(arr,prop,val) {
    for(var i=0;i<arr.length;i++){
        var one = arr[i];

        if(one[prop] == val){
            arr.splice(i,1);

            removeDataInArray(arr,prop,val);

            return;
        }
    }
}