layui.use([ 'form'], function () {
    var o = layui.$,
        form = layui.form;

    form.verify({});

    //监听提交表单
    form.on('submit(add)',
        function (data) {
            $.ajax({
                url: Base.ctxPath + "/cpp/testGen/save",
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