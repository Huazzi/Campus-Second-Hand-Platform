layui.use(['table', 'form'], function () {
    var table = layui.table,
        form = layui.form;

    table.render({
        elem: '#tb'
        , url: Base.ctxPath + '/sys/sysUser/queryList'
        , cols: [[
            {type: 'radio'},
                {field: 'id', title: 'ID'},
                {field: 'userName', title: '用户名称'},
                {field: 'userNick', title: '用户昵称'},
                {field: 'userEmail', title: '用户邮箱'},
                {field: 'userMobile', title: '用户电话'},
                {field: 'userSex', title: '用户性别'},
                {field: 'status', title: '用户状态'},
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
                    userName: $("#userName").val(),
                    userNick: $("#userNick").val(),
                    userSex: $("#userSex").val(),
                    status: $("#status").val(),
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
        Base.open('添加', Base.ctxPath + '/sys/sysUser/add', '480', '400');
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
                Base.open('修改', Base.ctxPath + '/sys/sysUser/edit?id=' + data[0].id, '480', '400');
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
                url: Base.ctxPath + "/sys/sysUser/del",
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