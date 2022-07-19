<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>油画列表</title>
    <link rel="stylesheet" type="text/css" href="css\list.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.5.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/sweetalert2.all.js"></script>
    <script type="text/javascript">
        /**
         * 预览图片 要在标签中自定义属性 图片的地址  图片的名称 带过来显示
         * @param previewObj
         */
        function showPreview(previewObj) {
            const preview = $(previewObj).attr("data-preview");
            const pname = $(previewObj).attr("data-pname");
            Swal.fire({
                title: pname,
                html: "<img src='/gallery/" + preview + "' style='width:361px;height:240px'>",
                showCloseButton: true,
                showConfirmButton: false
            });
        };

        /**
         * html中 自定义属性  data-x形式  x为自定义名称
         * 删除油画 ajax请求 自定义属性 显示的数据 删除所需要的id
         * @param delObj
         */
        function del(delObj) {
            //获取自定义属性的值
            const id = $(delObj).attr("data-id");
            const pname = $(delObj).attr("data-pname");
            const preview = $(delObj).attr("data-preview");
            Swal.fire({
                title: "确定要删除[" + pname + "]油画吗?",
                html: "<img src='/gallery/" + preview + "' style='width:361px;height:240px'>",
                showCancelButton: true,
                confirmButtonText: "是",
                cancelButtonText: "否"
            }).then(function (result) {
                if (result.value) {
                    //点击了是
                    //ajax请求  字符串""包起来 type:get 日了狗
                    $.ajax({
                        url: "/gallery/management?method=delete&id=" + id,
                        type: "get",
                        dataType: "json",
                        success: function (json) {
                            //json对象  服务器返回的
                            //通过服务器json判断成功失败
                            if (json.result == "ok") {
                                window.location.reload();
                            } else {
                                Swal.fire({
                                    title: json.result
                                })
                            }
                        }
                    });
                }
            });
        }
    </script>
</head>
<body>
<div class="container">
    <fieldset>
        <legend>油画列表</legend>
        <div style="height: 40px">
            <a href="/gallery/management?method=show_create" class="btn-button">新增</a>
        </div>
        <!-- 油画列表 -->
        <table cellspacing="0px">
            <thead>
            <tr style="width: 150px;">
                <th style="width: 100px">分类</th>
                <th style="width: 150px;">名称</th>
                <th style="width: 100px;">价格</th>
                <th style="width: 400px">描述</th>
                <th style="width: 100px">操作</th>
            </tr>
            </thead>
            <c:forEach items="${pageModel.pageData}" var="painting">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${painting.category==1}">现实主义</c:when>
                            <c:when test="${painting.category==2}">抽象主义主义</c:when>
                            <c:otherwise>未知的类型</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${painting.pname}</td>
                    <td>
                        <fmt:formatNumber pattern="￥0.00" value="${painting.price}"></fmt:formatNumber>
                    </td>
                    <td>${painting.description}</td>
                    <td>
                        <a class="oplink" href="javascript:void(0)" onclick="showPreview(this)"
                           data-pname="${painting.pname}" data-preview="${painting.preview}">预览</a>
                        <a class="oplink"
                           href="/gallery/management?method=show_update&id=${painting.id}&page=${pageModel.page}">修改</a>
                        <a class="oplink" href="javascript:void(0)" data-id="${painting.id}" data-pname="${painting.pname}"
                           data-preview="${painting.preview}" onclick="del(this)">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <!-- 分页组件 -->
        <ul class="page">
            <li><a href="/gallery/management?method=list&p=1">首页</a></li>
            <li><a href="/gallery/management?method=list&p=${pageModel.hasPreviousPage?pageModel.page-1:1}">上页</a></li>

            <c:forEach begin="1" end="${pageModel.totalPages}" var="pno">
                <li ${pno==pageModel.page?"class='active'":""}>
                    <a href="/gallery/management?method=list&p=${pno}">${pno}</a></li>
            </c:forEach>
            <li>
                <a href="/gallery/management?method=list&p=${pageModel.hasNextPage?pageModel.page+1:pageModel.totalPages}">下页</a>
            </li>
            <li><a href="/gallery/management?method=list&p=${pageModel.totalPages}">尾页</a></li>
        </ul>
    </fieldset>
</div>

</body>
</html>
