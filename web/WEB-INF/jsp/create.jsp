<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 新增油画页面 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>新增油画</title>
    <link rel="stylesheet" type="text/css" href="css\create.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.5.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>

</head>

<body>
<script type="text/javascript">
    /**
     * 检查所有输入项 全部符合才表单提交
     * @returns {boolean}
     */
     function checkSubmit(){
        const r1 = checkFile('#painting', '#errPainting');
        const r2 = checkEmpty('#pname', '#errPname');
        const r3 = checkCategory('#category', '#errCategory');
        const r4 = checkPrice('#price', '#errPrice');
        const r5 = checkDescription('#description', '#errDescription');
        if (r1 && r2 && r3 && r4 & r5) {
            return true;
        }
        return false;
    };
</script>
<div class="container">
    <fieldset>
        <legend>新增油画</legend>
        <form onsubmit="return checkSubmit()" action="/gallery/management?method=create" method="post" enctype="multipart/form-data"
              autocomplete="off">
            <ul class="ulform">
                <li>
                    <span>油画名称</span>
                    <span id="errPname"></span>
                    <input id="pname" name="pname" onblur="checkEmpty('#pname','#errPname')"/>
                </li>
                <li>
                    <span>油画类型</span>
                    <span id="errCategory"></span>
                    <select id="category" name="category" onchange="checkCategory('#category','#errCategory')">
                        <option value="-1">请选择油画类型</option>
                        <option value="1">现实主义</option>
                        <option value="2">抽象主义</option>
                    </select>
                </li>
                <li>
                    <span>油画价格</span>
                    <span id="errPrice"></span>
                    <input id="price" name="price" onblur="checkPrice('#price','#errPrice')"/>
                </li>
                <li>
                    <span>作品预览</span>
                    <span id="errPainting"></span>
                    <input id="painting" name="painting" type="file"
                           style="padding-left: 0px;" accept="image/*"
                           onchange="checkFile('#painting','#errPainting')"/>
                </li>

                <li>
                    <span>详细描述</span>
                    <span id="errDescription"></span>
                    <textarea
                            id="description" name="description"
                            onblur="checkDescription('#description','#errDescription')"></textarea>
                </li>
                <li style="text-align: center;">
                    <button type="submit" class="btn-button">提交表单</button>
                </li>
            </ul>
        </form>
    </fieldset>
</div>

</body>
</html>
