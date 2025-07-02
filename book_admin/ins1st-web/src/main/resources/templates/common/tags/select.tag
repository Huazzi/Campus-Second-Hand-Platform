@/*
标签参数 -
    id : input框id
    selectName ： 输入框前名称
    list:数据集合
    optionName: 多选框显示名称
    optionValue: 多选框值
    value: 默认选中的值
    required : 校验
    dictType: 字典值
@*/
<div class="layui-form-item">
    <label class="layui-form-label">${selectName}：</label>
    <div class="layui-input-inline">
        <select id="${id}" name="${id}"
                @if(isNotEmpty(required)){
                    lay-verify="${required}"
                @}
                class="layui-select">
            @if(isNotEmpty(list)&&isNotEmpty(optionName)&&isNotEmpty(optionValue)){
                <option value="">请选择</option>
                @for(item in list){
                    @if(isNotEmpty(value)&&item[optionValue] == value){
                        <option selected="selected" value="${item[optionValue]}">${item[optionName]}</option>
                    @}else{
                        <option value="${item[optionValue]}">${item[optionName]}</option>
                    @}
                @}
            @}else if(isNotEmpty(dictType)){
                <option value="">请选择</option>
                @if(dict.hasDict(dictType)&&isNotEmpty(dict.queryDictsByParent(dictType))){
                    @for(sysDict in dict.queryDictsByParent(dictType)){
                        @if(isNotEmpty(value)&&sysDict['code'] == value){
                            <option selected="selected" value="${sysDict['code']}">${sysDict['name']}</option>
                        @}else{
                            <option value="${sysDict['code']}">${sysDict['name']}</option>
                        @}

                    @}
                @}
            @}else{
                <option value="">请选择</option>
            @}
        </select>
    </div>
</div>