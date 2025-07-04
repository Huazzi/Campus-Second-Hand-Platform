@/*
标签参数 -
id : input框id
inputName ： 输入框前名称
required: 校验类型
type: 输入框类型
value : 默认值
@*/
<div class="layui-form-item">
    <label class="layui-form-label">${inputName}：</label>
    <div class="layui-input-inline">
        <input type="text" id="${id}" name="${id}"
               @if(isNotEmpty(required)){
               lay-verify="${required}"
               @}
               @if(isNotEmpty(value)){
               value="${value}"
               @}
               @if(isNotEmpty(type)){
               type="${type}"
               @}else{
               type="text"
               @}
               autocomplete="off"
               placeholder="请输入${inputName}"
               class="layui-input">
    </div>
</div>