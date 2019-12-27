/**
 * 首先需要信任证书
 *
 * https://118.25.100.74:8443
 */

var _g_n=0;
var _prev_first_msg='';
var _ts = setInterval(function () {
    if (checkMsgFeature()) {
        console.log('检测到未读消息');
        sendMsg('来消息了'+(_g_n++) + _prev_first_msg);
    }
}, 3000);

// clearInterval(_ts);

// function checkMsgFeature() {
//     var ret = false;
//     $(".badge.badge-red.js-unread-count").each(function (idx,ele) {
//         if ($(ele).css('visibility') == 'visible') {
//             ret = true;
//         }
//     });
//
//     return ret;
// }
function checkMsgFeature() {
    var str = $(".chat-detail.js-last-message:eq(0)").text();

    var ret = _prev_first_msg!='' && _prev_first_msg != str;
    _prev_first_msg = str;

    return ret;
}
checkMsgFeature();

function sendMsg(msg) {
    var prefix = "智齿";
    $.ajax({
        type: "POST",
        url:"https://118.25.100.74:8443/redirectJson?url=https%3A%2F%2Foapi.dingtalk.com%2Frobot%2Fsend%3Faccess_token%3D669df1c0c077fa8e62dc1b7bce4369f35ffdb965b541118db1cb018311f867d4",
        contentType:"application/json",
        data:JSON.stringify({
            "msgtype": "text",
            "text": {
                "content": prefix+msg
            }
        }),
        success: function (response) {
            console.log(response)
        }
    });
}
