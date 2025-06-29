layui.use(['table', 'form'], function () {
    var table = layui.table,
        form = layui.form;

    table.render({
        elem: '#tb'
        , url: Base.ctxPath + '/sys/sysBizLog/queryList'
        , cols: [[
            {type: 'radio'},
                {field: 'id', title: 'ID'},
                {field: 'title', title: '日志标题'},
                {field: 'params', title: '记录参数'},
                {field: 'className', title: '执行类'},
                {field: 'method', title: '执行方法'},
                {field: 'createTime', title: '创建时间'},
        ]]
        , page: true
    });

    //查询
    function search() {
        table.reload('tb', {
            page: {
                curr: 1 //重新从第 1 页开始
            }
            , where: {
                    id: $("#id").val(),
                    title: $("#title").val(),
                    params: $("#params").val(),
                    className: $("#className").val(),
                    method: $("#method").val(),
                    createTime: $("#createTime").val(),
            }
        });
    }

    form.on('submit(search)',
        function (data) {
            search();
            return false;
        });

    /**
     * 新增
     */
    $("#add").click(function () {
        Base.open('添加', Base.ctxPath + '/sys/sysBizLog/add', '480', '400');
    });

    /**
     * 编辑
     */
    $("#edit").click(function () {
        var data = Base.getSelected(table, 'tb');
                if (data.length == 0) {
                    Base.fail("至少选择一行数据");
                    return false;
                }
                Base.open('修改', Base.ctxPath + '/sys/sysBizLog/edit?id=' + data[0].id, '480', '400');
    });

    /**
     * 删除
     */
    $("#del").click(function () {
        var data = Base.getSelected(table, 'tb');
        if (data.length == 0) {
           Base.fail("至少选择一行数据");
           return false;
        }
        Base.confirm("是否删除该记录？", function () {
            $.ajax({
                url: Base.ctxPath + "/sys/sysBizLog/del",
                type: "post",
                data: {
                    "id": data[0].id
                },
                success: function (result) {
                    if (result.success) {
                        Base.alert(result.message);
                        search();
                    } else {
                        Base.fail(result.message);
                    }
                }
            });
        });
    });

});