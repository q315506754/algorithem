/////////////////////////////////////////////
if (typeof ts != 'undefined'){
    console.log('清除ts');
    clearInterval(ts)
}

var ts;
var loopTimes=0;
var lastPlaySeconds=0;
var opened=false;
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

function openNotify(title, duration) {
    var daxiao = "http://video.g2s.cn/zhs_yanfa_150820/ablecommons/demo/201812/fb4432aa0be04e3b8f93eb5947731e59.mp3";
    var daxiao = new Audio(daxiao);
    daxiao.load();
    // daxiao.src =
    daxiao .play(); //播放
//暂停
//     daxiao.pause();


    $(daxiao).bind("ended", function() {
        daxiao.play();
    });

    var $btn = $(`
        <button>停止音乐</button>
        `);

    $btn.click(function () {
        console.log('暂停');
        daxiao.pause();
    });

    $('body').append($btn);
}
// openNotify()

function startMain() {
    ts = setInterval(function () {
        if(checkEx("#popDiv")){
            record("需要动鼠标，点击弹框")
            closeDiv();
        }


        if(loopTimes%_config.printLoopTimes == 0){
            record("定时器",_config.printLoopTimes)
        }

        loopTimes++

        var oldSec = lastPlaySeconds
        lastPlaySeconds = parseInt(getPlayingNowTime())

        if(oldSec == lastPlaySeconds){
            var totalTimes = record("播放截止")
            if (totalTimes > 2 && !opened) {
                openNotify()
                opened = true
            }

        }
    },_config.intervalTs);

    console.log('started:'+ts);
}

startMain();
/////////////////////////////////////////////
//openNotify('haha')



