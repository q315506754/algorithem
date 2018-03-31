//#opt 解决中文未选择词组就触发的问题 中文触发 事件
// onpropertychange / oninput
var cpLock = false;
$(document).delegate('#applyBind,#applyNotBind','compositionstart',function () {
    cpLock = true;
});
$(document).delegate('#applyBind,#applyNotBind','compositionend',function () {
    cpLock = false;
});
$(document).delegate('#applyBind','keyup',function () {
    if (!cpLock) {
        applyBind();
    }
});
$(document).delegate('#applyNotBind','keyup',function () {
    if (!cpLock) {
        applyNotBind();
    }
});