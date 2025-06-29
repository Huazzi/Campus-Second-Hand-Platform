layui.use(['table', 'form'], function () {
    var table = layui.table,
        form = layui.form;

    table.render({
        elem: '#tb'
        , url: Base.ctxPath + '/users/users/queryList'
        , cols: [[
            {type: 'radio'},
                {field: 'id', title: 'ID'},
                {field: 'username', title: '用户名称'},
                {field: 'nickname', title: '用户昵称'},
                {field: 'head', title: '头像'},
                {field: 'mobilephone', title: '手机'},
                {field: 'address', title: '地址'},
                {field: 'sex', title: '性别'},
                {field: 'college', title: '大学'},
                {field: 'openid', title: '微信ID'},
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
                    username: $("#username").val(),
                    nickname: $("#nickname").val(),
                    head: $("#head").val(),
                    mobilephone: $("#mobilephone").val(),
                    address: $("#address").val(),
                    sex: $("#sex").val(),
                    college: $("#college").val(),
                    openid: $("#openid").val(),
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
        Base.open('添加', Base.ctxPath + '/users/users/add', '480', '400',true);
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
                Base.open('修改', Base.ctxPath + '/users/users/edit?id=' + data[0].id, '480', '400',true);
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
                url: Base.ctxPath + "/users/users/del",
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