;(function () {
    document.createStyleSheet().cssText="" +
    ".console_line{"+
    "    margin: 1px;"+
    "}";

    var oldFunc = window.console;
    var el = document.getElementById("console");
    el.innerHTML = "";
    window.console = {
        log:function (msg) {
            this._print(msg);
            oldFunc.log.apply(this,arguments);
        },
        warn:function (msg) {
            this._print(msg,'yellow');
            oldFunc.warn.apply(this,arguments);
        },
        _print:function (msg,cls) {
            if (msg == null || msg == undefined) {
                cls = 'gray';
            }
            if(msg instanceof Array) {
                msg = "["+msg+"]";
            } else {
                if(typeof msg == "object") {
                    msg = JSON.stringify(msg);
                }
                if(typeof msg == "function") {
                    msg = "function "+msg.name+"(...){...}";
                }
                if(typeof msg == "number") {
                    cls = "blue"
                }
                if(typeof msg == "string") {
                    msg = "\""+msg+"\"";
                }
            }

            el.innerHTML = el.innerHTML+"><span class='console_line' style='color:"+cls+"'>"+msg+"</span><br/>";
        }
    }
})();