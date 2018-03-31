$.fn.extend({
    "preventScroll":function(){
        $(this).each(function(){
            var _this = this;
            var $this = $(this);
            if(navigator.userAgent.indexOf('Firefox') >= 0){   //firefox
                _this.addEventListener('DOMMouseScroll',function(e){
                    var t = e.detail > 0 ? 60 : -60;
                    _this.scrollTop += e.detail > 0 ? 60 : -60;
                    // $this.animate({"scrollTop":_this.scrollTop+t},_config.scroll_speed,_config.scroll_mode);
                    e.preventDefault();
                },false);
            }else{
                _this.onmousewheel = function(e){
                    e = e || window.event;
                    var t = e.wheelDelta > 0 ? -60 : 60;
                    _this.scrollTop += t;
                    // $this.animate({"scrollTop":_this.scrollTop+t},_config.scroll_speed,_config.scroll_mode);
                    return false;
                };
            }
        })
    }
});