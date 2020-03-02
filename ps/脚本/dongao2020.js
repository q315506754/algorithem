/////////////////////////////////////////////
if (typeof ts != 'undefined'){
    console.log('清除ts');
    clearInterval(ts)
}

var ts;
var loopTimes=0;
var _config={
    intervalTs:3000
    ,printLoopTimes:40
}
var dt = {

}
function record(msg,args) {
    dt[msg] =  dt[msg] || 0
    dt[msg]++
    args = args || 'none'
    console.log(`${msg}(${args}):${dt[msg]}`);
    return dt[msg];
}
function checkEx(selector) {
    return $(selector).filter(":visible").length > 0;
}

// openNotify()

function startMain() {
    ts = setInterval(function () {
        if(checkEx(".question")){
            record("需要动鼠标，点击弹框")
            $(".box-sure").click();
        }


        if(loopTimes%_config.printLoopTimes == 0){
            record("定时器",_config.printLoopTimes)
        }

        loopTimes++

    },_config.intervalTs);

    console.log('started:'+ts);
}

startMain();
/////////////////////////////////////////////
//openNotify('haha')



