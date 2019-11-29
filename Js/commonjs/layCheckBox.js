(function($){
    var defaults = {
        spreadToLine:true,
        lineSelector:"tr",
        allSelector:"#checkAll"
    }
    var innerOpt = {
        data_attr: "_layCheckBox_"
    };

    function Plugin(element, options,selector) {
        this.el = $(element);
        this.opt = options;
        this.selector = selector;
        this._initProcess();
    }

    Plugin.prototype = {
        _initProcess: function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;

            $plgThis._request();

            $plgThis._bindEvents();
        },
        _request: function () {
        },
        _bindEvents: function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;

            var selector = $plgThis.selector;

            var repaintCheckAll = function () {
                if(opt.allSelector){
                    var $checkAll = $(opt.allSelector).find("input:checkbox");

                    var totalSize = $(""+selector+"  .layui-form-checkbox:visible").size();
                    //列表为空时
                    if(totalSize == 0){
                        setCheckBoxVal($checkAll,false);
                        return ;
                    }


                    //列表不为空
                    var selectedSize = $plgThis.getSelected().size();
                    // var selectedSize =$("#assistantBody"  + "  .layui-form-checked:visible").parents("tr").size();
                    // console.log(selector);
                    // console.log(selectedSize);
                    // console.log($plgThis.getSelected());
                    if(selectedSize == totalSize){
                        setCheckBoxVal($checkAll,true);
                    } else {
                        setCheckBoxVal($checkAll,false);
                    }

                    layui.form.render("checkbox");
                }

            }

            if(opt.spreadToLine){
                //点击行
                $(document).on("click",""+selector+" "+opt.lineSelector,function (e) {
                    // e.stopPropagation();

                    var $obj = $(this).find("input:checkbox");

                    setCheckBoxVal($obj,!getCheckBoxVal($obj));
                    // setCheckBoxVal($obj,!getLayCheckBoxVal($(this)));

                    // setTimeout(repaintCheckAll, 100);
                    repaintCheckAll();

                });

                //点击 复选框
                $(document).on("click",""+selector+" "+opt.lineSelector+" .layui-icon",function (e) {
                    e.stopPropagation();

                    // setTimeout(repaintCheckAll, 100);
                    repaintCheckAll();

                });
            }

            if(opt.allSelector){
                //全选按钮
                $(opt.allSelector).click(function () {
                    var $this = $(this);

                    // console.log(getLayCheckBoxVal($this));
                    // console.log($(""+selector).find("input:checkbox"));
                    setCheckBoxVal($(""+selector).find("input:checkbox"),getLayCheckBoxVal($this));

                });
            }


        },
        clear:function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;

            var selector = $el.selector;

            //列表复选框
            setCheckBoxVal($("" + selector + " " + opt.lineSelector + " input:checkbox"),false);
            //全选
            setCheckBoxVal($("" + opt.allSelector + " input:checkbox"),false);

        },
        getSelected:function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;

            var selector = $plgThis.selector;
            return $("" + selector + "  .layui-form-checked:visible").parents(opt.lineSelector);
        }
    }

    $.fn.layCheckBox=function(params){
        var lists = this,
            retval = this;
        var args = arguments;
        var selector = $(this).selector;

        lists.each(function () {
            var plugin = $(this).data(innerOpt.data_attr);

            if (!plugin ) {
                if (typeof params === 'object') {
                    params = $.extend({}, defaults, params);
                    var nPlg = new Plugin(this, params,selector);
                    $(this).data(innerOpt.data_attr, nPlg);
                    retval = nPlg;
                }
            } else {
                if (typeof params === 'object') {
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
})(jQuery);

function getLayCheckBoxVal($obj) {
    return $obj.find(".layui-form-checked").size() > 0;
}

function getCheckBoxVal($obj) {
    var v = $obj.attr('checked');
    if(v) {
        return true;
    }else {
        return false;
    }
}

function setCheckBoxVal($obj,b) {
    if (b) {
        $obj.attr("checked", "checked");
    } else {
        $obj.removeAttr("checked");
    }
    layui.form.render("checkbox");
}