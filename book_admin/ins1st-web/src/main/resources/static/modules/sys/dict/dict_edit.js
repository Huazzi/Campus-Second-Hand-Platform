layui.use(['form'], function () {
    var o = layui.$,
        form = layui.form;

    form.verify({});

    form.on('submit(edit)',
        function (data) {
            $.ajax({
                url: Base.ctxPath + "/sys/sysDict/update",
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