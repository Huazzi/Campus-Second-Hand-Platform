layui.use(['table', 'form'], function () {
    var table = layui.table,
        form = layui.form;

    table.render({
        elem: '#tb'
        , url: Base.ctxPath + '/sys/sysDict/queryList'
        , cols: [[
            {type: 'radio'},
            {field: 'id', title: 'ID'},
            {field: 'pid', title: '父级字典'},
            {field: 'name', title: '名称'},
            {field: 'code', title: '值'},
            {field: 'num', title: '排序'},
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
                num: $("#num").val(),
                pid: $("#pid").val(),
                name: $("#name").val(),
                code: $("#code").val(),
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
        Base.open('添加', Base.ctxPath + '/sys/sysDict/add', '480', '400');
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
        Base.open('修改', Base.ctxPath + '/sys/sysDict/edit?id=' + data[0].id, '480', '400');
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
                url: Base.ctxPath + "/sys/sysDict/del",
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