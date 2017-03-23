http://ifang.hnteacher.net/HTML/WorkShop/workshop-course.html?WorkShopID=0D4B8455-45FA-42A6-AF8F-62CD7099A3B9###

    b01

    < div
id = "VerifyDailogBox"
class
= "rz-box"
style = "border: 1px solid rgb(238, 238, 238); padding: 10px; font-size: 14px; text-align: center;" >
    < label > < strong > 请输入运算结果
：
<
span
style = "position: relative; top: 4px;" > < img
src = "/LearnTimer/GetVerifyPic.aspx?rdm=0.024009785418122043"
style = "width: 100px; height: 20px;" > < / span >
    < / strong > < / label > < input
type = "text"
style = "width: 30px;"
maxlength = "2" >
    & nbsp;
<
input
type = "button"
class
= "b01"
onclick = "myLearnTimerClient.DoVerify();"
value = "确定" >
    & nbsp;
<
a
href = "##"
style = "color: #336699;"
onclick = "myLearnTimerClient.RefVerifyPic();" > 换一个 < / a >
    < / div >


    <div id="pointDiv" class="mydiv" style="display: block;">
    <div class="TiTit">知识点测试<span id="closeBtu" onclick="closeDiv()"></span></div>
    <div id="questionInfoDiv" class="TiCont" style="overflow: auto;height: 260px;"><input id="pointID" value="13700" type="hidden">

    <h2>为了保证您听课准确的计时，请您输入正确答案！</h2>
<div>
<div style="overflow: auto;text-align: left; font-size: 14px; line-height: 35px;" id="videoPointContent">
    <div style="font-weight: bold;">单选题</div>
    <div style="padding-left: 20px;">
    <p class="MsoNormal">	<span style="font-family:宋体;">“营改增”后，对于酒店行业，年销售额在<span>500</span>万元以下，采用的增值税税率为（<span><span>&nbsp;&nbsp; </span></span>）。<span></span></span><br><input type="radio" name="useranswer" value="A">A.13%<br><input type="radio" name="useranswer" value="B">B.6%<br><input type="radio" name="useranswer" value="C">C.3%<br><input type="radio" name="useranswer" value="D">D.7%</p></div>
<div class="cbtn">
    <input name="questionid" id="questionID" value="54139" type="hidden">
    <input name="questionid" id="questionAnswer" value="C" type="hidden">
    </div>
    <div id="pointQuestionAnswer" style="display:none;">
    <div>正确答案:
C<br>
</div>
</div>
<ul>
<li class="TiAlign"><input type="button" class="Tibutton" value="确　　认" onclick="savePointAnswer()"></li>
    </ul>
    </div>
    </div>
    </div>
    </div>
////////////////////////////////////////////////////////
if(window._handle){
    window._handle.destroy();
}
var _config={
    intervalTs:3000
}
var _listener = {

}
function openNotify(title, duration) {
    var promise=new Promise((resolve,reject)=>{
        window.Notification.requestPermission(function (status) {
            if (status === 'granted') {
                var options = {};
                var notify = new Notification(title, options);
                resolve(notify);
            }else {
                reject("没有权限!");
            }
        });
    });
    promise.then((obj)=>{
        obj.onshow = function () {
            if (duration) {
                setTimeout(function () {
                    obj.close();
                }, duration);
            }
        };
    },(err)=>{
        alert(err);
    });
}
var _handle = {
    clock:null,
    tickTimes:0,
    statistics:{
        questions:{
            appearedTimes:0
        },
        restHints:{
            appearedTimes:0
        },
    },
    processQuestions: function () {
        if ($("#pointDiv:visible").length > 0) {
            this.statistics.questions.appearedTimes++;

            //get right answer
            var rightAnswer=$("#questionAnswer").val();
            //choose
            $("input:radio[name='useranswer'][value='"+rightAnswer+"']").attr("checked","checked");
            //save
            savePointAnswer();

            openNotify("[INFO]答题成功"+this.statistics.questions.appearedTimes, 9000);
        } else {
            console.log('没有发现题目..');
        }
    },
    processRestHint: function () {
        console.log('处理休息提示...');


    },
    init:function () {
        var $this = this;
        $this.clock=setInterval(function () {
            $this.tickTimes++;
            console.log('------------第'+$this.tickTimes+'次-----------');

            $this.processQuestions.apply($this);

            $this.processRestHint.apply($this);

        },_config.intervalTs);
    },
    destroy:function () {
        if (this.clock) {
            console.log('destroy successfully!');
            clearInterval(this.clock);
            this.clock=null;
        } else {
            console.log('destroy failed...');
        }
    },
    showStatistics:function () {
        console.log(this.statistics);
        let {
            questions:{
                appearedTimes:x
            },
            restHints:{
                appearedTimes:y
            }
        }=_handle.statistics;
        console.log("题目提醒次数"+x);
        console.log("休息提醒次数"+y);
    }
}
_handle.init();
// _handle.destroy();
// _handle.showStatistics();
// openNotify("test1",1000);
// openNotify("test3",3000);
//////////////////////////////////////////////////////////////////////

if (window.itv) {
    clearInterval(window.itv);
}
var intervalTs = 3000;
var dati_count = 0;
var cur_time_pointer = 0;
var itv = setInterval(function () {
    if ($("#VerifyDailogBox:visible").length > 0) {
        //alert('该答题了')
        openNotify("该答题了", 3000);
    } else {
        console.log('no 答题 found..' + dati_count++);
    }
    var t_pointer = readCurrentPointer();
    if (t_pointer != cur_time_pointer) {
        openNotify("该休息下了", 999999);
        cur_time_pointer = t_pointer;
    }
}, intervalTs);

var $al_time = $("#Div_LearningTimer dd:contains('您')").find(".yellow");
function openNotify(title, duration) {
    window.Notification.requestPermission(function (status) {
        var options = {};
        if (status === 'granted') {
            //如果开启了  则...
            var notify = new Notification(title, options);

            notify.onshow = function () {

                if (duration) {
                    setTimeout(function () {

                        notify.close();

                    }, duration);
                }
            };
        }
    });

}

function getLearningMin() {
    return parseInt($al_time.text());
}
function readCurrentPointer() {
    return parseInt(getLearningMin() / 20);
}
cur_time_pointer = readCurrentPointer();
//////////////////////////////////////////////////////////////////////

//验证码
http://120.76.26.252:89/LearnTimer/GetVerifyPic.aspx?rdm=0.024009785418122043


    openNotify('hahaha', 9000)
openNotify('hahaha')
clearInterval(itv);


in:

cost:40

com:80

好的牛肉干得80元左右最便宜的也要在六十左右

看价格以内蒙古的牛肉干为例
，
六七成干的风干牛肉需要2 - 3
斤新鲜牛肉才可出1斤牛肉干
，
超干的是3 - 4
斤出1斤
。
对比生牛肉的价格
，
自己算算成本
，
低于成本的基本不用考虑了
。

牛肉干分几个的
，
第一
，
形态
，
牛肉条
，
牛肉片
，
牛肉粒
，
牛肉梗
第二
，
看口味
，
风干牛肉
，
原味
，
咖喱
，
五香
，
麻辣
。。。。
第三
，
看干的程度
，
自然风干
，
烘烤
，
烘培
，
冷冻速干
第四
，
看品质
，
口感
，
风味
，
地方特色等
由于目前牛肉的时价在30 - 40
元一斤
，
牛肉干制作比例在一斤鲜牛肉制作0
.4 - 0.5
斤牛肉干
，
所以市面价格一般在50 - 80
元一斤左右
，
如我们这边咖喱牛肉粒就是55元一斤
，
蒙古风味手撕牛肉干就是78元一斤
