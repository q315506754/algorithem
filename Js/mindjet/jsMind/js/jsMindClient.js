var _jm = null;//jsmind实例

var _meta={
    placeholder:"请输入结点名称",
    enableShortCut:true,        // 是否启用快捷键
//        enableShortCut:false,        // 是否启用快捷键
    enableDrag:true,        // 是否启用拖动排序
       // enableDrag:false,        // 是否启用拖动排序
}

//console
var _noop = function(){};
var console = (typeof console === 'undefined')?{
    log:_noop, debug:_noop, error:_noop, warn:_noop, info:_noop
}:console;

//String
var _s = {
    isEmpty:function (str){
        if( null==str || 'null'==str || undefined == str || 'undefined'==str ||
            ''==str || str.length ==0  || str.match(/^\s*$/))
            return true;
        else
            return false;
    },
    isNotEmpty:function (str){
        return !this.isEmpty(str);
    }
}

var _placeHolder={
    getOrgText:function (str) {
        if(str==_meta.placeholder){
            return "";
        }
        return str;
    },
    getDisplayText:function (str) {
        if(_s.isEmpty(str)){
            return _meta.placeholder;
        }
        return str;
    },
}


///////   DOM ////////
var getNodeId = function($obj){
    var $node = $obj.parents("jmnode")
    return $node.attr("nodeid")
}
var getNode = function($obj){
    return _jm.get_node(getNodeId($obj))
}
var createTopicHtml = function(str){
    var s = str==undefined?_meta.placeholder:str;
    return "<div class='nodeText'><span class='text'>"+s+"</span></div><input type='text' class='nodeTextInput'/><button class='deleteNode'>x</button><button class='addSiblingNode'>+</button><button class='addChildNode'>+</button>";
}
var focusNodeEdit = function (node,append) {
    var editInput = getEditDisplay(node);
    if(!editInput)
        return;
    editInput.trigger("dblclick",append)
}
var getEditDisplay = function (node) {
    var $node = getEditNode(node);
    if($node){
        return $node.find(".nodeText .text");
    }
}
var getEditInput = function (node) {
    var $node = getEditNode(node);
    if($node){
        return $node.find(".nodeTextInput");
    }
}
var getEditNode = function (node) {
    if(!node)
        return;
    var selector = "jmnode[nodeid='"+node.id+"']";
    return $(selector);
}
///////   DOM ////////


///////   UI ////////
var resizePainter = function(){
    $("#jsmind_container").css({
        "width":_jm.view.e_canvas.width+"px",
        "height":_jm.view.e_canvas.height+"px"
    });
}
///////   UI ////////


///////   jsMind  ////////
var addSiblingNode = function (node) {
    if(node.isroot){
        return;
    }
    var newNode = _jm.insert_node_after(node, "node"+new Date().getTime(),createTopicHtml(), {})
    recalculateData(newNode);

    var sort =  _jm.get_sort(newNode);
    console.log("sort");
    console.log(sort);
    console.log("pid");
    console.log(newNode.parent.id)

    focusNodeEdit(newNode);

    return newNode;
}

var addChildNode = function (node) {
    var newNode = _jm.add_node(node, "node"+new Date().getTime(),createTopicHtml(), {})
    recalculateData(newNode);

    var sort =  _jm.get_sort(newNode);
    console.log("sort");
    console.log(sort);
    console.log("pid");
    console.log(newNode.parent.id);

    focusNodeEdit(newNode);
    return newNode;
}

var removeNode = function (node) {
    if(node.isroot){
        return;
    }
    console.log(node.data);
    var pid=node.data.parentid;
    var st=node.data.sort;

    _jm.remove_node(node);

    var affectedData =  recalculateDataAfterSort(pid,st);

    console.log("affectedData:");
    console.log(affectedData);
}
///////   jsMind  ////////



///////   业务  ////////
var recalculateData = function (node) {
    var newSort = _jm.get_sort(node);
    var parentChanged = node.data.parentid!=node.parent.id;
    node.data.parentid=node.parent.id;
    var sortChanged = node.data.sort!=newSort;
    node.data.sort=newSort;
    node.index=newSort;
    var p = {};
    p.id=node.id;
    if(parentChanged) {
        p.parentId=node.parent.id;
    }
    if(sortChanged) {
        p.sort=newSort;
    }
    return parentChanged || sortChanged?p:null;
}

var recalculateDataAfterSort = function (parentId,sort) {
    var ret = []
    var parent = _jm.get_node(parentId);
    if(parent.children){
        for(var i=0;i<parent.children.length;i++) {
            var cOne = parent.children[i];
            if(cOne.data.sort<sort){
                continue;
            }

            var p =recalculateData(cOne);
            if(p) {
                ret.push(p);
            }
        }
    }
    return ret;
}
///////   业务  ////////

$(function () {
    $(document).on("dblclick","jmnode .nodeText",function (e,append) {
        e.stopPropagation();
        var node = getNode($(this));
        var nodeDisplay=getEditDisplay(node);
        var nodeInput=getEditInput(node);

        var txt = _placeHolder.getOrgText(nodeDisplay.text());
        nodeDisplay.hide();

        nodeInput.show().val(txt).focus();

        if(append!=true){
            nodeInput.select();
        }
    });

    $(document).on("blur","jmnode .nodeTextInput",function (e) {
        e.stopPropagation();

        var node = getNode($(this));
        var nodeDisplay=getEditDisplay(node);
        var nodeInput=getEditInput(node);

        var newTxt = _placeHolder.getOrgText(nodeInput.val());
        var oldTxt = _placeHolder.getOrgText(nodeDisplay.text());


        if(_s.isNotEmpty(newTxt) && oldTxt!=newTxt){
            console.log('text changed to:'+newTxt);
        }

        nodeDisplay.text(_placeHolder.getDisplayText(newTxt));
        nodeDisplay.show();
        nodeInput.hide();
    });

    $("body").on("keydown","jmnode .nodeTextInput",function (e) {
        e.stopPropagation();
        console.log('ccc');

        //enter
        if(e.keyCode == 13){
            $(this).blur();
        }

        //tab
        if(e.keyCode == 9){
            e.preventDefault();
        }
    });

    $(document).on("click","button.deleteNode",function (e) {
        e.stopPropagation();
        console.log('deleteNode');
        var node = getNode($(this));
        console.log(node);
        console.log(node.data.sort);

        removeNode(node);
    });
    $(document).on("click","button.addSiblingNode",function (e) {
        e.stopPropagation();
        console.log('addSiblingNode');

        var node = getNode($(this));

        var newNode = addSiblingNode(node);

        ;
    });
    $(document).on("click","button.addChildNode",function (e) {
        e.stopPropagation();
        console.log('addChildNode');

        var node = getNode($(this));

        var newNode = addChildNode(node);
    });

});

function load_jsmind(dataArray){
    var mind = {
        meta:{
            name:'demo',
            author:'hizzgdev@163.com',
            version:'0.2'
        },
        format:'node_array',
        //"node_tree"-mongo,   "node_array"-rdb,   "freemind"-xml,
        data:dataArray
    };
    var options = {
        container:'jsmind_container',// [必选] 容器的ID
//            editable : false,       // 是否启用编辑
        editable : true,       // 是否启用编辑
        draggable:_meta.enableDrag,   //ext 扩展
        mode :'side',           // 显示模式
//            jsMind 现支持两种显示模式:
//        full - 子节点动态分布在根节点两侧 [默认值]
//        side - 子节点只分布在根节点右侧
//            theme:'clouds',//自定义主题
        theme:'custom1',
        //可选项
//            primary
//            warning
//            danger
//            success
//            info
//            greensea
//            nephrite
//            belizehole
//            wisteria
//            asphalt
//            orange
//            pumpkin
//            pomegranate
//            clouds
//            asbestos


        view:{
            hmargin:100,        // 思维导图距容器外框的最小水平距离
            vmargin:50,         // 思维导图距容器外框的最小垂直距离
            line_width:2,       // 思维导图线条的粗细
            line_color:'#ececec'   // 思维导图线条的颜色
        },
        layout:{
            hspace:50,          // 节点之间的水平间距
            vspace:30,          // 节点之间的垂直间距
            pspace:0           // 节点收缩/展开控制器的尺寸
        },
        default_event_handle:{
            enable_mousedown_handle:true,
            enable_click_handle:true,
            enable_dblclick_handle:false
        },
        shortcut:{
            enable:_meta.enableShortCut,        // 是否启用快捷键
//                enable:false,        // 是否启用快捷键
            blockingCode:function () {
                console.log("length:"+$(".nodeTextInput:visible").length);
                return $(".nodeTextInput:visible").length > 0
            },
            otherKeyCode:function (j,e,kc) {
                if((kc>=65&&kc<=90) || (kc>=48&&kc<=57)){
                    e.preventDefault();
                    console.log('otherKeyCode a-z 0-9');
                    focusNodeEdit(_jm.get_selected_node(),true);
                }
            },
            //自定义按键
            handles:{
                k_addchild:function(j,e){
//                        console.log(e);
//                        console.log(j);
//                     e.preventDefault();
                    addChildNode(_jm.get_selected_node());
                },
                k_addsibling:function(j,e){
//                        console.log(e);
//                        console.log(j);
                    addSiblingNode(_jm.get_selected_node());
                },
                k_delete:function(j,e){
//                        console.log(e);
//                        console.log(j);
                    removeNode(_jm.get_selected_node());
                },
                k_edit:function(j,e){
//                        console.log(e);
//                        console.log(j);
                    e.preventDefault();
                    focusNodeEdit(_jm.get_selected_node());
                },
            },
            mapping:{
                k_addsibling : 13, // Enter
                k_delete : 46, // Delete
                k_addchild : [45,9], // Insert,Tab
                k_edit : 32, // Space
                left       : 37, // Left
                up         : 38, // Up
                right      : 39, // Right
                down       : 40, // Down
            }
        }
    }
    //方法一
//        _jm = jsMind.show(options,mind);

    //方法二
    _jm = new jsMind(options);
    _jm.show(mind);

    //事件监听
    _jm.add_event_listener(function (tp,data) {

        if(tp==jsMind.event_type.resize){
            resizePainter();
        }

        //拖动排序
        if(data.evt=="move_node"){
            var node=_jm.get_node(data.node);
//                    console.log(node.data.parentid);
//                    console.log(node.parent.id);
            var oldPid=node.data.parentid;
            var oldSort=node.data.sort;
            var newPid=node.parent.id;
            var newSort = _jm.get_sort(node);

            var p = recalculateData(node);

            if(p){
                console.log("really move ..");

                console.log("thisChange:");
                console.log(p);

                var orgAffected = recalculateDataAfterSort(oldPid,oldSort);
                console.log("orgAffected:");
                console.log(orgAffected);

                var newAffected = recalculateDataAfterSort(newPid,newSort);
                console.log("newAffected:");
                console.log(newAffected);
            }
        }
    });

    resizePainter();
}

