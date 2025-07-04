layui.use(['table', 'form'], function () {
    var table = layui.table,
        form = layui.form;

    table.render({
        elem: '#tb'
        , url: Base.ctxPath + '/table/genTable/queryList'
        , cols: [[
            {type: 'radio'},
            {field: 'id', title: 'ID'},
            {field: 'tableName', title: '数据表名'},
            {field: 'moduleName', title: '模块名'},
            {field: 'bizName', title: '业务名'},
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
                tableName: $("#tableName").val(),
                moduleName: $("#moduleName").val(),
                bizName: $("#bizName").val(),
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
        Base.open('添加', Base.ctxPath + '/table/genTable/add', '480', '400');
    });

    /**
     * 新增
     */
    $("#handle").click(function () {
        var data = Base.getSelected(table, 'tb');
        if (data.length == 0) {
            Base.fail("至少选择一行数据");
            return false;
        }
        Base.open('生成代码', Base.ctxPath + '/table/genTable/toGen?id=' + data[0].id, '', '', true);
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
                url: Base.ctxPath + "/table/genTable/del",
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