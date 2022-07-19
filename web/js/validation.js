//表单校验js
/**
 * 检车是否为空 onOff开关 表示是否显示错误信息
 * @param input 表单选择器
 * @param errSelector 错误提示选择器
 * @returns {boolean} true成功 false失败
 */
function checkEmpty(input,errSelector) {
    const val = $(input).val();
    //console.log("lzy="+val);
    if($.trim(val)==""){
        switchValid(false,input,errSelector,"请输入内容");
        return false;
    }else {
        switchValid(true,input,errSelector);
        return true;
    }

}

/**
 * 检查油画类型
 * @param input 表单输入选择器
 * @param errSelector  错误提示选择器
 * @returns {boolean} true 成功  false失败
 */
function checkCategory(input,errSelector) {
    const val = $(input).val();
    //console.log("lzy="+val);
    if(val==-1){
        switchValid(false,input,errSelector,"请输入油画类型");
        return false;
    }else {
        switchValid(true,input,errSelector);
        return true;
    }
}

/**
 * 检车价格
 * @param input 表单输入选择器
 * @param errSelector  错误提示选择器
 * @returns {boolean} true 成功  false失败
 */
function checkPrice(input,errSelector) {
    const val = $(input).val();
    //console.log("lzy="+val);
    const reg = /^[1-9][0-9]*$/;
    if(!reg.test(val)){
        switchValid(false,input,errSelector,"无效的价格");
        return false;
    }else {
        switchValid(true,input,errSelector);
        return true;
    }
}

/**
 * 检车描述说明是否为空
 * @param input 输入项选择器
 * @param errSelector 错误提示选择器
 * @returns {boolean} true成功  false失败
 */
function checkDescription(input,errSelector) {
    const val = $(input).val();
    //console.log("lzy="+val);
    if($.trim(val)==""){
        switchValid(false,input,errSelector,"请输入内容");
        return false;
    }else {
        switchValid(true,input,errSelector);
        return true;
    }
}

/**
 * 检车文件说明是否为空，是否为jpg png gif
 * @param input 输入项选择器
 * @param errSelector 错误提示选择器
 * @returns {boolean} true成功  false失败
 */
function checkFile(input,errSelector) {
    const flag = checkEmpty(input,errSelector);
    if(!flag)
        return false;
    const val = $(input).val().toLowerCase();
    //console.log("lzy="+val);
    if(val.length<4){
        switchValid(false,input,errSelector,"请选择有效的图片");
        return false;
    }

    const suffix = val.substring(val.length-3);
    if(suffix=="jpg"||suffix=="png"||suffix=="gif"){
        switchValid(true,input,errSelector);
        return true;
    } else {
        switchValid(false,input,errSelector,"请选择有效的图片");
        return false;
    }
}



/**
 * 隐藏 显示错误信息
 * @param onOff true 验证成功隐藏错误信息  false失败  显示错误信息
 * @param input 表单选择器
 * @param errSelector 错误提示选择器
 * @param message 错误提示信息
 */
function switchValid(onOff, input, errSelector, message) {
    if(!onOff){
        $(errSelector).text(message);
        $(input).addClass("error_input");
        $(errSelector).addClass("error_message");
    }else {
        $(errSelector).text("");
        $(input).removeClass("error_input");
        $(errSelector).removeClass("error_message");
    }
}