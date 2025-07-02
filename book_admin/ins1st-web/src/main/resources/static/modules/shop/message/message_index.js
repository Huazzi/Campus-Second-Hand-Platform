layui.use(['table', 'form'], function () {
    var table = layui.table,
        form = layui.form;

    table.render({
        elem: '#tb'
        , url: Base.ctxPath + '/message/message/queryList'
        , cols: [[
            {type: 'radio'},
                {field: 'id', title: 'ID'},
                {field: 'senduserid', title: '发送用户ID'},
                {field: 'reciveuserid', title: '接受用户ID'},
                {field: 'goodid', title: '物品'},
                {field: 'status', title: '状态'},
                {field: 'content', title: '内容'},
                {field: 'time', title: '发布日期'},
                {field: 'title', title: '主题'},
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
                    senduserid: $("#senduserid").val(),
                    reciveuserid: $("#reciveuserid").val(),
                    goodid: $("#goodid").val(),
                    status: $("#status").val(),
                    content: $("#content").val(),
                    time: $("#time").val(),
                    title: $("#title").val(),
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
        Base.open('添加', Base.ctxPath + '/message/message/add', '480', '400',true);
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
                Base.open('修改', Base.ctxPath + '/message/message/edit?id=' + data[0].id, '480', '400',true);
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
                url: Base.ctxPath + "/message/message/del",
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