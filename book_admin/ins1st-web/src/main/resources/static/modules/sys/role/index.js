layui.use(['table', 'form'], function () {
    var table = layui.table,
        form = layui.form;

    table.render({
        elem: '#tb'
        , url: Base.ctxPath + '/sys/sysRole/queryList'
        , cols: [[
            {type: 'radio'},
            {field: 'id', title: 'ID', sort: true}
            , {field: 'name', title: '角色名称'}
            , {field: 'keyword', title: '角色标识', sort: true}
            , {field: 'status', title: '角色状态', templet: menuStatus}
            , {field: 'sort', title: '角色排序'}
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
                name: $("#name").val(),
                keyword: $("#keyword").val(),
            }
        });
    }

    //监听查询表单
    form.on('submit(sreach)',
        function (data) {
            search();
            return false;
        });
    /**
     * 新增
     */
    $("#add").click(function () {
        Base.open('添加角色', Base.ctxPath + '/sys/sysRole/role_add', '480', '400');
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
        Base.open('修改角色', Base.ctxPath + '/sys/sysRole/role_edit?id=' + data[0].id, '480', '400');

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
                url: Base.ctxPath + "/sys/sysRole/delRole",
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

    /**
     * 配置权限
     */
    $("#setMenus").click(function () {
        var data = Base.getSelected(table, 'tb');
        if (data.length == 0) {
            Base.fail("至少选择一行数据");
            return false;
        }
        Base.open('角色分配菜单', Base.ctxPath + '/sys/sysRole/role_menu?id=' + data[0].id, '300', '400')
    });

});