$.ajaxSetup({
    type: "POST",
    error: function (XHR, textStatus, errorThrown) {

    },
    success: function (data) {

    },
    complete: function (XHR, TS) {
        var res = $.parseJSON(XHR.responseText);
        if (res.code != null && res.code == 500) {
            Base.fail(res.message + ",请联系管理员");
            return;
        } else if (res.code != null && res.code == 501) {
            layer.alert(res.message, {
                icon: 2,
            }, function () {
                window.location.href = Base.ctxPath + "/login";
            })
        }
    }
});
var Base = {
    ctxPath: '',
    addCtx: function (ctx) {
        this.ctxPath = ctx;
    },
    isBlank: function (str) {
        if (str == null || str == "" || str == undefined) {
            return true;
        }
        return false;
    },
    isNotBlank: function (str) {
        if (str != null || str != "" || str != undefined) {
            return true;
        }
        return false;
    },
    alert: function (msg) {
        layer.alert(msg, {
            icon: 1,
        })
    },
    success: function (msg) {
        layer.alert(msg, {
            icon: 1,
        }, function () {
            xadmin.close();
            xadmin.father_reload();
        })
    },
    fail: function (msg) {
        layer.alert(msg, {
            icon: 2,
        })
    },
    warn: function (msg) {
        layer.alert(msg, {
            icon: 0,
            time: 2000
        })
    },
    confirm: function (title, func1) {
        layer.confirm(title, {
            btn: ['确认', '取消'] //按钮
        }, function () {
            func1();
        });
    },
    message: function (msg) {
        layer.msg(msg)
    },
    open: function (title, url, w, h, full) {
        if (title == null || title == '') {
            var title = false;
        }
        ;
        if (url == null || url == '') {
            var url = "404.html";
        }
        ;
        if (w == null || w == '') {
            var w = ($(window).width() * 0.9);
        }
        ;
        if (h == null || h == '') {
            var h = ($(window).height() - 50);
        }
        ;
        var index = layer.open({
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false, //不固定
            maxmin: true,
            shadeClose: true,
            shade: 0.4,
            title: title,
            content: url
        });
        if (full) {
            layer.full(index);
        }
    },
    getSelected: function (table, tableId) {
        var checkStatus = table.checkStatus(tableId);
        var data = checkStatus.data;
        return data;
    },

};