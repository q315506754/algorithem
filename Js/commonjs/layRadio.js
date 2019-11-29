(function($){
    var defaults = {
        spreadToLine:true,
        lineSelector:"tr"
    }
    var innerOpt = {
        data_attr: "_layRadio_"
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

            // console.log($(this));
            if(opt.spreadToLine){
                $(document).on("click",""+selector+" "+opt.lineSelector,function (e) {
                    // e.stopPropagation();

                    var $obj = $(this).find("input:radio");
                    setRadioVal($obj,true);

                    layui.form.render("radio");
                });

                $(document).on("click",""+selector+" "+opt.lineSelector+" .layui-anim",function (e) {
                    e.stopPropagation();
                });
            }
        },
        clear:function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;

            var selector = $el.selector;

            $("" + selector + " " + opt.lineSelector + " input:radio").removeAttr("checked");
            layui.form.render("radio");
        }
    }

    $.fn.layRadio=function(params){
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


function getRadioVal($obj) {
    var v = $obj.attr('checked');
    if(v) {
        return true;
    }else {
        return false;
    }
}
function setRadioVal($obj,b) {
    if (b) {
        $obj.attr("checked", "checked");
    } else {
        $obj.removeAttr("checked");
    }
}