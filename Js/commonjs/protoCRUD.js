function RoleManageCRUD(opt) {
    var bodyId = opt.bodyId || 'crudBody';//渲染列表的id
    var $add_clicker = opt.add_clicker;//绑定点击事件的jquery对象
    var $refresh_clicker = opt.refresh_clicker;//绑定点击事件的jquery对象
    var $reset_clicker = opt.reset_clicker;//绑定点击事件的jquery对象
    var createform = 'createForm';
    var queryForm = 'queryForm';
    var pageDivId = 'pageDivId';
    var moduleCn = '角色';
    var relaModulePath = '/usermanage/account';

    //初始化分页参数
    var PageData = {
        "pageSize": 10,
        "pageIndex": 1
    }

    //function
    //新增报告点击弹窗
    $add_clicker.click(function () {
        saveTr();
    });
    $refresh_clicker.click(function () {
        PageData.pageIndex = 1;
        refreshList();
    });
    $reset_clicker.click(function () {
        PageData.pageIndex = 1;

        clearOrInjectForm(queryForm);

        //默认选择全部 会触发refreshList
        $(".treeFromOther span:eq(0)").click();
        // refreshList();
    });

    //验证
    var validator = new Validator({
        container: $("#" + createform + ""),
        rules: [{
            type: "text",
            name: "_user_name",
            // selector: "#answerContent",
            whenEmpty: function ($obj) {
                commonError($obj, "请输入姓名");
            },
            clear: commonClear
        }]
    });

    //初始化刷新一次
    refreshList();

    //inner function
    function saveTr(dto) {
        validator.clearAll();

        var mode = clearOrInjectForm(createform, dto);
        // console.log(mode);

        if (mode == MODE_EDIT) {
        } else {
        }

        //确认弹窗
        layer.open({
            type: 1,
            title: dto ? "更新"+moduleCn : "创建"+moduleCn,
            area: ['600px'],
            content: $('.add-account-win'),
            shade: 0.3,
            btn: ['确认', '取消'],
            btnAlign: 'c',
            success: function () {
            },
            yes: function (index) {
                if (!validator.validate()) {
                    return;
                }

                var dt = getFormData(createform);
                console.log(dt);

                // return;
                if (mode == MODE_EDIT) {
                    $.ajax({
                        url: basePath + relaModulePath +"/doUpdate",
                        type: "POST",
                        data: dt,
                        success: function (rs) {
                            console.log(rs);

                            Message.successTips('更新成功')

                            layer.close(index);

                            refreshList();
                        }, error: function (a) {
                            // console.log(arguments);
                            Message.error(a.responseText);
                        }
                    });
                } else {
                    $.ajax({
                        url: basePath + relaModulePath + "/doSave",
                        type: "POST",
                        data: dt,
                        success: function (rs) {
                            console.log(rs);

                            Message.successTips('创建成功')

                            layer.close(index);

                            refreshList();

                        }, error: function (a) {
                            // console.log(arguments);
                            Message.error(a.responseText);
                        }
                    });
                }
            }
        });
    }

    function deleteTr(dto) {
        layui.use(['layer','form'], function () {
            var layer = layui.layer;
            var form = layui.form;
            layer.alert(`您确定将账号“${dto.name}”从企业中移除吗？`, {
                title: "提示",
                icon: 3,
                btn: ["确定", "取消"],
                btnAlign: "c",
                move: false,
                yes: function (index) {
                    $.ajax({
                        url: basePath + relaModulePath +  "/doDelete",
                        type: "POST",
                        data: {
                            "id": dto.id
                        },
                        success: function () {
                            Message.successTips('删除成功')

                            layer.close(index);

                            refreshList();
                        }
                    });
                }
            })
        });
    }

    function refreshList() {
        var dt = getFormData(queryForm);
        dt.pageIndex = PageData.pageIndex - 1;
        dt.pageSize = PageData.pageSize;

        $.ajax({
            url: basePath + relaModulePath + "/doQueryList",
            type: "POST",
            data: dt,
            beforeSend: function () {
                // $('#crudBody').html('<tr><td style="text-align: center;" colspan="7">数据加载中...</td></tr>')
            },
            success: function (obj) {
                console.log(obj);

                var data = obj.records;

                appendEleData(bodyId, data, function (one) {
                    one.userDto = one.userDto || {};

                    var $str = $(`
                    <tr>
                        <td>
                            <div class="minWidth80">${one.userDto.name || ""}</div>
                        </td>
                        <td>
                            <div class="dataOperation">
                                <span class="editTr operat-sp">编辑</span>
                                <span class="deleteTr operat-sp">删除</span>
                            </div>
                        </td>
                    </tr>`);

                    //删除
                    $str.find(".deleteTr").data("_data", one);
                    $str.find(".deleteTr").click(function () {
                        var dt = $(this).data("_data");
                        // deleteTr(dt.id, one.userDto.name);
                        deleteTr(dt.uscId, one.userDto.name);
                    });

                    //编辑
                    $str.find(".editTr").data("_data", one);
                    $str.find(".editTr").click(function () {
                        var dt = $(this).data("_data");

                        saveTr(dt);
                    });

                    return $str;
                });
                //分页
                layui.use(['laypage', 'layer'], function () {
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: pageDivId
                        , count: obj.totalRecords
                        , curr: PageData.pageIndex
                        , limit: PageData.pageSize
                        , theme: '#1890ff'
                        , layout: ['count', 'prev', 'page', 'next']
                        , jump: function (obj, first) {
                            if (!first) {
                                var pageIndex = obj.curr;
                                PageData.pageSize = obj.limit;
                                PageData.pageIndex = pageIndex;
                                refreshList();
                            }
                        }
                    });
                });

            },
            error: function () {
                appendEleData(bodyId, [])
            }
        });
    }
}