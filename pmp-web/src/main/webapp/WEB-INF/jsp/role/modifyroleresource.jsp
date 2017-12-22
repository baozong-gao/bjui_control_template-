<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<head>
    <title>角色资源设置</title>
</head>
<body>
<div class="bjui-pageHeader">

    <div class="bjui-searchBar">
        <label>角 色：</label>
        <label><c:out value="${roleFuncForm.name}"/></label>&nbsp;
        <label>角色状态：</label>
        <label>${roleFuncForm.disableTag == '0'?"禁用":"启用"}</label>&nbsp;
    </div>
</div>
<div class="bjui-pageContent">
    <form class="nice-validator n-red" data-alertmsg="true"
               data-toggle="validate" action="${pageContext.request.contextPath}/role/authorization"
               novalidate="novalidate" data-callback="roleRefreshAndClose">
        <input type="hidden" name="roleId" id="roleId" value="${roleFuncForm.id}">
        <input type="hidden" name="functionIds" id="authorized_ids"/>
        <div>
            <div style="float:left;">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
        </div>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li>
            <button type="button" class="btn-close" >关闭</button>
        </li>
        <li>
            <button type="submit" class="btn-default" onclick="put_func_ids()">保存</button>
        </li>
    </ul>
</div>
<script type="text/javascript">
    var role_authorized_all = eval(${role_authorized_all});
    var zNodes = [];
    for (var index = 0; index < role_authorized_all.length; index++) {
        var authorize = role_authorized_all[index];
        var authorize_obj = {
            "id": authorize["id"],        <!-- 当前的id -->
            "pId": authorize["parentId"], <!-- 父级id -->
            name: authorize["name"],      <!-- 要显示的名称 -->
            checked:authorize["auth"],    <!-- 是否勾选 -->
            open:authorize["auth"]        <!-- 是否展开 -->
        };
        zNodes.push(authorize_obj);
    }
    var setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "ps" }
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });
    function put_func_ids() {
        var func_ids = [];
        var zTree = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
        for (var index = 0; index < zTree.length; index++) {
            var authorize = zTree[index]["id"];
            func_ids.push(authorize);
        }
        $("#authorized_ids").val(func_ids)
    }
</script>
</body>
</html>
