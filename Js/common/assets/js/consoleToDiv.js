;(function () {
    var consoleId = "console_"+new Date().getTime();
    function createStyleSheet() {
        var link = document.createElement("style");
        link.rel = "stylesheet";
        link.type = "text/css";
        document.getElementsByTagName("head")[0].appendChild(link);
        return link;
    }
    function createConsole() {
        mainDiv = document.createElement("div");
        mainDiv.id = consoleId;
        mainDiv.className = "floatConsole";
        document.getElementsByTagName("body")[0].appendChild(mainDiv);

         consoleTitle = document.createElement("div");
        consoleTitle.className = "consoleTitle";
        consoleTitle.innerHTML="Console"

         container = document.createElement("div");
        container.className = "consoleContainer";

        mainDiv.appendChild(consoleTitle);
        mainDiv.appendChild(container);

        return mainDiv;
    }

    function scrollBottom() {
        container.scrollTop=container.scrollHeight;
    }

    try{

    createStyleSheet().innerHTML="" +
    ".console_line{"+
    "    margin: 0px;"+
    "    color: RGB(169,183,198);"+
    "}"+
    ".floatConsole{"+
    "    margin: 0px;"+
    "    position: fixed;"+
    "    width: 50%;"+
    "    height:  40%;"+
    "    left: 25%;"+
    "}"+
    ".floatConsole .consoleContainer{"+
    "    margin: 0px;"+
    "    width: 100%;"+
    "    height:  100%;"+
    "    overflow-x: hidden;"+
    "    background:RGB(43,43,43)"+
    "}"+
    ".floatConsole .consoleTitle{"+
    "    width: 97%;"+
    "    height:  20px;"+
    "    color: RGB(0,0,0);"+
    "    padding: 3px;"+
    "    background:RGB(163,191,229)"+
    "}";
    }catch(e){}


    var oldFunc = window.console;
    if(!"console" in window){
        var emptyFunc = function () {
        }
        oldFunc={log:emptyFunc,warn:emptyFunc,error:emptyFunc};
    }
    var mainDiv ;
    var container;
    var consoleTitle;

    createConsole();
    
    consoleTitle.onclick=function () {
        if (container.style.display !== "none"){
            container.style.display = "none";
        }else {
            container.style.display = "block";
        }
    };


    window.console = {
        log:function (msg) {
            window.console._print(msg);
            try{oldFunc.log.apply(this,arguments);}
            catch (e){
                oldFunc.log(arguments);
            }
        },
        warn:function (msg) {
            this._print(msg,'yellow');
            try{oldFunc.warn.apply(this,arguments);}
            catch (e){
                oldFunc.warn(arguments);
            }
        },
        _print:function (msg,cls) {
            try{
                if (msg == null || msg == undefined) {
                    cls = 'gray';
                }else if(msg instanceof Array) {
                    msg = "["+msg+"]";
                    cls="RGB(97,116,56);"
                } else {
                    if(typeof msg == "object") {
                        msg = JSON.stringify(msg);
                        cls=" RGB(186,180,40);"
                    }
                    if(typeof msg == "function") {
                        msg = "function "+msg.name+"(...){...}";
                        cls=" RGB(204,120,50);"
                    }
                    if(typeof msg == "number") {
                        cls = "RGB(135,117,130)"
                    }
                    if(typeof msg == "string") {
                        msg = "\""+msg+"\"";
                    }
                }

                container.innerHTML = container.innerHTML+"><span class='console_line' style='color:"+cls+"'>"+msg+"</span><br/>";
                scrollBottom();
            }catch(e){}
        }
    }

    console.log("FloatConsole Loading success! :)");
})();