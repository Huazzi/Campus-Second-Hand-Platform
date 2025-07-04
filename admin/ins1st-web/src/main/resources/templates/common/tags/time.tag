@/*
时间框参数 -
timeName : btName按钮名称
id
name
format：自定义格式
@*/
<div class="layui-form-item">
    <label class="layui-form-label">${timeName}：</label>
    <div class="layui-input-inline">
        <input class="layui-input"  autocomplete="off" placeholder="${timeName}" name="${name}" id="${id}">
    </div>
</div>
<script>
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#${id}',
            trigger: 'click'
        });
    });
</script>