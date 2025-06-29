layui.use(['table', 'form'], function () {
    var table = layui.table,
        form = layui.form;

    table.render({
        elem: '#tb'
        , url: Base.ctxPath + '/cpp/testGen/queryList'
        , cols: [[
            {type: 'radio'},
                {field: 'id', title: 'ID'},
                {field: 'columnOne', title: '字段1'},
                {field: 'columnTwo', title: '字段2'},
                {field: 'columnThree', title: '字段3'},
                {field: 'columnFour', title: '字段4'},
                {field: 'columnFive', title: '字段5'},
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
                    columnOne: $("#columnOne").val(),
                    columnTwo: $("#columnTwo").val(),
                    columnThree: $("#columnThree").val(),
                    columnFour: $("#columnFour").val(),
                    columnFive: $("#columnFive").val(),
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
        Base.open('添加', Base.ctxPath + '/cpp/testGen/add', '480', '400',true);
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
                Base.open('修改', Base.ctxPath + '/cpp/testGen/edit?id=' + data[0].id, '480', '400',true);
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
                url: Base.ctxPath + "/cpp/testGen/del",
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