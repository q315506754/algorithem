var config = {
    canvas:true,
         group:true,
    //      canvas:false,
    // group:false,
    num:12 , //人数
//          changeSelection:"click",
    changeSelection:"mouseenter"
}

var records = [];//jingshang diyitian diyitian
//7v8 9v10 5x7 3+9-5>2 5
//shangjing:
//1.cai duitiao
//2.yan jinshui/chasha

//pk台 pk cmd
//table
//               1       2      3
//shouye yueliang          qipiao / beitou
//jingxuan  jinghui       tiao/up/down/chasha/jinshui/zibao
//piaoxing  jushou         qipiao / beitou
//siwang  kulou       back-kulou/shenhui

//diyibaitian taiyang
//piaoxing tuichuqu          qipiao / beitou /chuju/shenfen
//diyitianheiye  //piaoxing           qipiao / beitou
//siwang

//diyibaitian
//piaoxing
//diyitianheiye
//siwang

function validate() {
    $(".tile_name").css("color","red");

    $("[data-expected]").each(function (obj,ele) {
        var length = $(ele).children().length;
//              console.log(length);
        if(length==parseInt($(ele).attr("data-expected"))){
            $(ele).parent().prev(".tile_name").css("color","green");
        }
    });

    $("[data-max]").each(function (obj,ele) {
        var length = $(ele).find(".sort_placeholder").children().length;
        console.log($(ele).attr("id")+"-data-max:"+length);
        if(length==parseInt($(ele).attr("data-max"))){
            $(ele).children(".tile_name").css("color","green");
        }
    });

}

function getByShortKey(str) {
    var $obj = $(`[data-shortkey="${str}"]`);
    if($obj.length >0){
        return $obj;
    }
}

function compressCmd(str) {
    return str.replace(/\s+/g," ").trim();
}
function getCmdArray(str) {
    return str.split(" ");
}

$(function () {
    if(config.canvas) {
        $("#canvas").hackCanvas({
//                words:"wolf kill",
            words:"html java javascript c c++ python"
        });
    }
    if(!config.group) {
        $("#sortContainer").hide();
    }


    $("#cmd").keyShot({
        errorWhenShake:true,
        handle:function (str) {
            str=compressCmd(str);

            console.log("cmd:"+str);

            let cmdParser = new CmdParser(str);
            let cmdArr = cmdParser.getCmdArr();
            console.log(`getCmdArr:${cmdArr}`);
            let numArr = cmdParser.getContinuantNumberArrayForCmd(12);
            console.log(`numArr:${numArr}`);

            //[lcynsq]
            //move command selected
            if(/^[a-z]+$/.test(str)){
                console.log('selected身份定义指令');

                let [shortKey] = cmdArr;
                console.log(`shortKey:${shortKey}`);
                var $obj = getByShortKey(shortKey);

                if($obj){
                    $(".selected").appendTo($obj);
                    validate();
                    return;
                }
            }

            //1 3 5 4 [lcynsq]
            //move command
            if(/^(\d+\s*)+[a-z]+$/.test(str)){
                console.log('身份定义指令');

                let [shortKey] = cmdArr;
                console.log(`shortKey:${shortKey}`);
                var $obj = getByShortKey(shortKey);

                if($obj){
                    // console.log(numArr);
                    // console.log(numArr.length);

                    for(var i=0;i<numArr.length;i++) {
                        var tid=numArr[i];
                        // console.log(tid);

                        $(`[data-id="data${tid}"]`).appendTo($obj);
                    }
                    validate();
                    return;
                }
            }


            return false;
        }
    });

    //initial
    var num=config.num;
    for(var i=0;i<num;i++){
        //默认未知身份
        $("#group_unkown .sort_placeholder").append($(`<div class="number" data-id="data${i+1}">${i+1}</div>`));

        //默认弃票
        $("#electionContainer .policeRow .abstentionTd .electionResult").append($(`<div class="number" data-elecId="data${i+1}">${i+1}</div>`));
    }

    //selection
    $(document).on(config.changeSelection,".number[data-id]",function (e) {
        e.stopPropagation();
        $(this).toggleClass("selected");
    });
    $(document).on("click",function () {
        $(".selected.number[data-id]").removeClass("selected");
    });
    $(document).on("click",".sort_placeholder",function (e) {
        $(".selected.number[data-id]").appendTo($(this));
        validate();
    });

    $("#sortContainer .sort_placeholder").each(function (id, ele) {
        new Sortable(ele, {
            group: "name",  // or { name: "...", pull: [true, false, clone], put: [true, false, array] }
            sort: true,  // sorting inside list
            delay: 0, // time in milliseconds to define when the sorting should start
            animation: 150,  // ms, animation speed moving items when sorting, `0` — without animation
//              handle: ".number",  // Drag handle selector within list items
            filter: ".ignore-elements",  // Selectors that do not lead to dragging (String or Function)
            preventOnFilter: true, // Call `event.preventDefault()` when triggered `filter`
            draggable: ".number",  // Specifies which items inside the element should be draggable

            forceFallback: false,  // ignore the HTML5 DnD behaviour and force the fallback to kick in
            fallbackTolerance: 0, // Specify in pixels how far the mouse should move before it's considered as a drag.

            scroll: true, // or HTMLElement
            scrollSensitivity: 30, // px, how near the mouse must be to an edge to start scrolling.
            scrollSpeed: 10,// px

            // Element dragging ended
            onEnd: function (/**Event*/evt) {
                validate();
            },

        });
    });

    validate();
});