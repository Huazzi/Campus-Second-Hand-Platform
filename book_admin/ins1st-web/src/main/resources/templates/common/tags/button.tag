@/*
按钮参数 -
btName : btName按钮名称
filter: 按钮事件
@*/
<div class="layui-form-item">
    <div class="layui-input-block">
        <button class="layui-btn" lay-submit="" lay-filter="${filter}">${btName}</button>
        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
</div>