layui.config({
    base: Base.ctxPath + '/static/lib/treeTable/',
})
layui.use(['treeTable', 'layer', 'code', 'form'], function () {
    var o = layui.$,
        form = layui.form,
        layer = layui.layer,
        treeTable = layui.treeTable;

    var re = treeTable.render({
        elem: '#tree-table',
        url: Base.ctxPath + "/sys/sysMenu/queryMenus",
        icon_key: 'name',
        primary_key: 'id',
        parent_key: 'pId',
        is_checkbox: true,
        cols: [
            {
                key: 'id',
                title: 'ID',
                width: '10px',
                align: 'center',
            },

            {
                key: 'pId',
                title: '父ID',
                width: '10px',
                align: 'center',
            },
            {
                key: 'name',
                title: '菜单名称',
                width: '100px',
                template: function (item) {
                    return '<span style="color:green;">' + item.name + '</span>';
                }
            },
            {
                key: 'url',
                title: '菜单地址',
                width: '120px',
                template: function (item) {
                    return '<span style="color:green;">' + item.url + '</span>';
                }
            },
            {
                key: 'role',
                title: '菜单权限',
                width: '180px',
                template: function (item) {
                    return '<span style="color:green;">' + item.role + '</span>';
                }
            },
            {
                key: 'isMenu',
                title: '是否是菜单',
                width: '50px',
                template: function (item) {
                    if (item.isMenu == '0') {
                        return '<span style="color:green;">不是</span>';
                    } else {
                        return '<span style="color:green;">是</span>';
                    }
                }
            },
            {
                key: 'sort',
                title: '菜单排序',
                width: '50px',
                template: function (item) {
                    return '<span style="color:green;">' + item.sort + '</span>';
                }
            },
        ]
    });


    /**
     * 新增菜单
     */
    $("#addMenu").click(function () {
        Base.open('添加菜单', Base.ctxPath + '/sys/sysMenu/menu_add', '480', '400');
    });

    /**
     * 编辑菜单
     */
    $("#editMenu").click(function () {
        var ids = treeTable.checked(re).join(',');
        if (Base.isBlank(ids)) {
            Base.fail("请至少选择一个菜单");
            return false;
        }
        if (ids.split(",").length > 1) {
            Base.fail("只能选择一个菜单");
            return false;
        }
        Base.open('编辑菜单', Base.ctxPath + '/sys/sysMenu/menu_edit?id=' + ids.split(",")[0], '480', '400');
    });
    /**
     * 刷新菜单
     */
    $("#refresh").click(function () {
        treeTable.render(re);
    });
    /**
     * 删除菜单
     */
    $("#delMenu").click(function () {
        var ids = treeTable.checked(re).join(',');
        if (Base.isBlank(ids)) {
            Base.fail("请至少选择一个菜单");
            return false;
        }
        $.ajax({
            url: Base.ctxPath + "/sys/sysMenu/delMenu",
            type: "post",
            data: {
                "ids": ids,
            },
            success: function (result) {
                if (result.success) {
                    Base.alert(result.message);
                    treeTable.render(re);
                } else {
                    Base.fail(result.message);
                    treeTable.render(re);
                }
            }
        });
    });

})