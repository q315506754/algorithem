//填写表单
$("form:eq(0)").find("[name]").each(function (idx,ele) {
    // console.log(ele.tagName);
    var $obj = $(ele);
    if($obj.is("[fixed]")){
        return;
    }
    let tagName = ele.tagName;
    let name = ele.name || 'unknown';
    let type = ele.type || 'none';

    if (name == 'id') {
        return;
    }

    if (tagName == 'INPUT') {
        console.log(type);
        if (matchEx(name,"num") || matchEx(name,"id")) {
            return setId($obj);
        }
        if (matchEx(name,"logo")) {
            return setImg($obj);
        }

        if (type == 'hidden') {

        }else if (type == 'text') {
            $obj.val("text内容");
        }else if (type == 'file') {

        }
    } else  if(tagName == 'SELECT') {
        console.log($obj);

        var firstV;
        $obj.find("option").each(function (idx, ele) {
            if (!firstV) {
                firstV = $(ele).val();
            }
        });
        $obj.val(firstV);
        $obj.trigger("change");
        // $obj[0].selectedIndex = 0;
        //$("select[name='cityId0']").val(0)
        layui.use(['form'], function(){
            var form = layui.form;
            form.render('select');
        });
    } else  if(tagName == 'TEXTAREA') {
        $obj.val("TEXTAREA内容");
    }else  if(tagName == 'IMG') {
        setImg($obj);
    }
});
function matchEx(n,str) {
    return n.endsWith(str) || n.endsWith(str.toLowerCase()) || n.endsWith(str.toUpperCase()) || n.endsWith(str[0].toUpperCase()+str.substring(1).toLowerCase())
}
function setImg($obj) {
    let png = "http://image.g2s.cn/zhs_yanfa_150820/ablecommons/demo/201809/db1157b1744e444199400baaf3ae9289.png";
    $obj.attr("src",png);
    $obj.val(png);
}
function setId($obj) {
    $obj.val(1);
}