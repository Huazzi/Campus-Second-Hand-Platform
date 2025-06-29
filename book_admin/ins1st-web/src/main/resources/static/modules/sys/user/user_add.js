layui.use([ 'form'], function () {
    var o = layui.$,
        form = layui.form;

    form.verify({});

    //监听提交表单
    form.on('submit(add)',
        function (data) {
            var arr = new Array();
            $("input:checkbox[name='roleIds']:checked").each(function (i) {
                arr[i] = $(this).val();
            });
            data.field.roleIds = arr.join(",");
            $.ajax({
                url: Base.ctxPath + "/sys/sysUser/save",
                type: "post",
                data: data.field,
                success: function (result) {
                    if (result.success) {
                        Base.success(result.message);
                    } else {
                        Base.fail(result.message);
                    }
                }
            });
            return false;
        });
})