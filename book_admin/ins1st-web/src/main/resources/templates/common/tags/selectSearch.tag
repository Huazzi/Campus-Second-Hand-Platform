@/*
标签参数 -
    id : input框id
    selectName ： 输入框前名称
    list:数据集合
    optionName: 多选框显示名称
    optionValue: 多选框值
    value: 默认选中的值
    dictType: 字典值
@*/
<div class="layui-inline layui-show-xs-block">
    <select id="${id}" name="${id}" class="layui-select">
        @if(isNotEmpty(list)&&isNotEmpty(optionName)&&isNotEmpty(optionValue)){
            <option value="">${selectName}</option>
            @for(item in list){
                <option value="${item[optionValue]}">${item[optionName]}</option>
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
            <option value="">${selectName}</option>
        @}
    </select>
</div>