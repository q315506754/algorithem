function bindDebug(obj) {
    var opt = $.extend({},{
        times:3,
        onFire:function (k) {
            console.log("fired:"+k);
        }
    },obj);

    var _con_press_times = 0;
    var _con_press_prev;

    var clear = function () {
        _con_press_times = 0;
        _con_press_prev = null;
    }

    //进入debug模式
    $(document).on("keyup", "html", function (e) {
        // console.log(e.which);
        // console.log($(this));
        //连按alt+ctrl+` 3次触发debug模式
        if (!(e.ctrlKey && e.altKey)) {
            clear();
            return;
        }

        //continuous
        if(!!_con_press_prev && e.which!=_con_press_prev){
            clear();
            return;
        }

        _con_press_times++;

        //first time
        if(!_con_press_prev){
        } else {
            if(e.which!=_con_press_prev){
                clear();
                return;
            }
        }

        var tagName = e.target.tagName.toLowerCase();
        if (tagName != 'body') {
            clear();
            return;
        }

        //record
        _con_press_prev = e.which;

        if (_con_press_times>=opt.times){
            opt.onFire(e.which);

            clear();
        }


    });
}

