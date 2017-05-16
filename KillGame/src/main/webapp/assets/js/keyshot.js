(function ($) {
    $.fn.keyShot = function(options) {
        var opt = $.extend({}, defaults, options);


        var $this = $(this[0]);
        // console.log($this);
        $this.hide();

        $(document).on("keyup","body",function (e) {
            // console.log(e);
            $this.show();
            if(e.key.length==1) {
                $this.val(e.key);
            }
            $this.focus();
        });

        $this.keyup(function (e) {
            // console.log(e);
            e.stopPropagation();

            //escape
            if(e.keyCode==27){
                $(this).blur();
            }
            //enter
            else if(e.keyCode==13){
                var txt = $(this).val();

                var ret = true;
                if(opt.handle){
                    ret=opt.handle(txt);

                    if(ret == false && opt.errorWhenShake){
                        $this.shake(10, 3, 50);
                    }
                }

                if(ret != false){
                    $(this).blur();
                }
            }
        });


        $this.blur(function (e) {
            $(this).val("");
            $this.hide();
        });
    }

    var defaults = {
        //need shake.js
        errorWhenShake:false
    };
})(jQuery);