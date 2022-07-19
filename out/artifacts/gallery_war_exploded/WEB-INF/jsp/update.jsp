<%@page contentType="text/html;charset=utf-8" %>
<!-- 修改油画页面 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>作品更新</title>
    <link rel="stylesheet" type="text/css" href="css\create.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.5.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
    <script type="text/javascript">
        /**
         * 更新数据 表单校验  一个注意点文件是否修改通过隐藏input判断
         * @returns {boolean}
         */
        function checkSubmit() {
            const r1 = checkEmpty("#pname", "#errPname");
            const r2 = checkCategory('#category', '#errCategory');
            const r3 = checkPrice('#price', '#errPrice');
            let r4 ;
            const r5 = checkEmpty('#description', '#errDescription');
            if ($("#isPreviewModified") == "1") {
                r4 = checkFile('#input','#errPainting');
            }else {
                r4 = true;
            }
            if (r1 && r2 && r3 && r4 && r5) {
                return true;
            } else {
                return false;
            }
        };

        /**
         * 页面渲染完就会调用 相当于main
         */
        $(function () {
            $("#category").val(${painting.category});
        });

        /**
         * 文件input onchange事件  隐藏input的值要改变
         */
        function selectPreview() {
            checkFile("#painting", "#errPainting");
            //console.log("lzy");
            $("#preview").hide();
            $("#isPreviewModified").val(1);
        }
    </script>
</head>
<body>
<div class="container">
    <fieldset>
        <legend>作品名称</legend>
        <form action="/gallery/management?method=update" method="post"
              autocomplete="off" enctype="multipart/form-data"
              onsubmit="return checkSubmit()">
            <ul class="ulform">
                <li>
                    <span>油画名称</span>
                    <span id="errPname"></span>
                    <input id="pname" name="pname" onblur="checkEmpty('#pname','#errPname')" value="${painting.pname}"/>
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
                    <input id="price" value="${painting.price}" name="price" onblur="checkPrice('#price','#errPrice')"/>
                </li>
                <li>
                    <input type="hidden" id="isPreviewModified" name="isPreviewModified" value="0">
                    <span>作品预览</span><span id="errPainting"></span><br/>
                    <img id="preview" src="/gallery/${painting.preview}" style="width:361px;height:240px"/><br/>
                    <input id="painting" name="painting" type="file" style="padding-left:0px;" accept="image/*"
                           onchange="selectPreview()"/>
                </li>

                <li>
                    <span>详细描述</span>
                    <span id="errDescription"></span>
                    <textarea
                            id="description" name="description"
                            onblur="checkEmpty('#description','#errDescription')"
                    >${painting.description}</textarea>
                </li>
                <li style="text-align: center;">
                    <input type="hidden" id="id" name="id" value="${painting.id}">
                    <input type="hidden" id="page" name="page" value="${page}">
                    <button type="submit" class="btn-button">提交表单</button>
                </li>
            </ul>
        </form>
    </fieldset>
</div>

</body>
</html>
