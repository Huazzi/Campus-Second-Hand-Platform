layui.use(['table', 'form'], function () {
    var table = layui.table,
        form = layui.form;

    table.render({
        elem: '#tb'
        , url: Base.ctxPath + '/goods/goods/queryList'
        , cols: [[
            {type: 'radio'},
                {field: 'id', title: 'ID'},
                {field: 'goodname', title: '物品名称'},
                {field: 'goodownerid', title: '用户ID'},
                //{field: 'price', title: ''},
                {field: 'description', title: '描述'},
                {field: 'image', title: '图片路径'},
                {field: 'num', title: '数量'},
                {field: 'catagory', title: '分类'},
                {field: 'time', title: '发布时间'},
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
                    goodname: $("#goodname").val(),
                    goodownerid: $("#goodownerid").val(),
                    price: $("#price").val(),
                    description: $("#description").val(),
                    image: $("#image").val(),
                    num: $("#num").val(),
                    catagory: $("#catagory").val(),
                    time: $("#time").val(),
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
        Base.open('添加', Base.ctxPath + '/goods/goods/add', '480', '400',true);
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
                Base.open('修改', Base.ctxPath + '/goods/goods/edit?id=' + data[0].id, '480', '400',true);
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
                url: Base.ctxPath + "/goods/goods/del",
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