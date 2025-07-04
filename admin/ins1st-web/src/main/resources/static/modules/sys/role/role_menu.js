var zTree;
var setting = {
    view: {
        dblClickExpand: false,
        showLine: true,
        selectedMulti: false
    },
    check: {
        enable: true,
        chkStyle: "checkbox",
        chkboxType: {"Y": "ps", "N": "ps"}
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pId",
            rootPId: ""
        }
    },
    callback: {
        onClick: function (event, treeId, treeNode) {

        }
    }
};


$(document).ready(function () {
    $.ajax({
        url: Base.ctxPath + "/sys/sysRole/queryMenus4Role",
        type: "post",
        data: {
            "id": $("#id").val()
        },
        success: function (result) {
            var t = $("#tree");
            t = $.fn.zTree.init(t, setting, result);
        }
    });
    
    $("#save").click(function () {
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getCheckedNodes(true);
        var ids = "";
        for(var node in nodes){
            ids = ids + nodes[node].id + ","
        }
        $.ajax({
            url: Base.ctxPath + "/sys/sysRole/saveMenus4Role",
            type: "post",
            data: {
                "id": $("#id").val(),
                "ids" : ids
            },
            success: function (result) {
                Base.success(result.message);
            }
        });

    });

});