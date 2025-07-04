layui.use(['laydate', 'form'], function () {
    var laydate = layui.laydate;
    var form = layui.form;

    form.on('checkbox(ck)', function (data) {
        data.value = "1";
    });
});


function gen() {
    var arr = [];
    $('#genTable tbody tr').each(function (i) {
        var data = new Object();
        $(this).children('td').each(function (j) {
            switch (j) {
                case 0:
                    data.name = $(this).text();
                    break;
                case 1:
                    data.comment = $(this).text();
                    break;
                case 2:
                    data.dbType = $(this).text();
                    break;
                case 3:
                    data.javaType = $(this).text();
                    break;
                case 4:
                    if ($(this).text().trim() == "是") {
                        data.keyFlag = true;
                    } else {
                        data.keyFlag = false;
                    }
                    break;
                case 5:
                    if ($(this).find("div[class='layui-unselect layui-form-checkbox layui-form-checked']").length > 0) {
                        data.isInsert = true;
                    } else {
                        data.isInsert = false;
                    }
                    break;
                case 6:
                    if ($(this).find("div[class='layui-unselect layui-form-checkbox layui-form-checked']").length > 0) {
                        data.isEdit = true;
                    } else {
                        data.isEdit = false;
                    }
                    break;
                case 7:
                    if ($(this).find("div[class='layui-unselect layui-form-checkbox layui-form-checked']").length > 0) {
                        data.isSelect = true;
                    } else {
                        data.isSelect = false;
                    }
                    break;
                case 8:
                    if ($(this).find("div[class='layui-unselect layui-form-checkbox layui-form-checked']").length > 0) {
                        data.isList = true;
                    } else {
                        data.isList = false;
                    }
                    break;
                case 9:
                    if ($(this).find("div[class='layui-unselect layui-form-checkbox layui-form-checked']").length > 0) {
                        data.isRequired = true;
                    } else {
                        data.isList = false;
                    }
                    break;
                case 10:
                    data.htmlType = $(this).find("select").val();
                    break;
                case 11:
                    data.dictType = $(this).find("input").val();
                    break;

            }
        });
        arr.push(data);
    });

    $.ajax({
        url: Base.ctxPath + "/table/genTable/start",
        type: "post",
        data: {
            "arr": JSON.stringify(arr),
            "tableId": $("#tableId").val()
        },
        success: function (result) {
            if (result.success) {
                Base.success(result.message);
            } else {
                Base.fail(result.message);
            }
        }
    });
}