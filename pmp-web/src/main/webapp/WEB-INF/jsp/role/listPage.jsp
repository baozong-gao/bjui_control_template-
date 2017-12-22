<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLEncoder" %>
<html>
<head>
    <title>角色列表</title>
</head>
<body>
<div class="bjui-pageHeader">
    <form:form id="pagerForm" name="transRateCountForm"
               data-toggle="ajaxsearch"
               action="${pageContext.request.contextPath}/role/search" method="post"
               modelAttribute="rout">
        <input type="hidden" name="pageSize"
               value="${transRateCountForm.pageSize}"/>
        <input type="hidden" name="pageCurrent"
               value="${transRateCountForm.pageCurrent}"/>
        <div class="bjui-searchBar">
            <table>
                <tr>
                    <td>ID：</td>
                    <td><form:input path="id" class="form-control" size="15"/></td>
                    <td>名称：</td>
                    <td><form:input path="name" class="form-control" size="15"/></td>
                    <td>状态 ：</td>
                    <td><form:select path="disableTag" data-toggle="selectpicker">
                        <form:option value="">请选择</form:option>
                        <form:option value="1">启用</form:option>
                        <form:option value="0">禁用</form:option>
                    </form:select></td>
                </tr>
                <tr>
                    <td><a href="javascript:;" class="btn btn-default" data-toggle="reloadsearch"
                           data-clear-query="true">重置</a></td>
                    <td>
                        <button type="submit" class="btn-default" data-icon="search">查询</button>
                        <a class="btn btn-default" data-title="增加" data-id="form"
                           data-toggle="dialog" data-width="800" data-height="400" data-id="user-add"
                           href="role/addpage">增加</a></td>
                    </td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </div>
    </form:form>
</div>
<div class="bjui-pageContent tableContent">
    <table class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" data-width="100%"
           data-nowrap="true">
        <thead>
        <tr>
            <th align="center">ID</th>
            <th align="center">名称</th>
            <th align="center">状态</th>
            <th align="center">备注</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="record" items="${pageUser.list}" varStatus="status">
            <tr>
                <td align="center"><c:out value="${record.id}"/></td>
                <td align="center"><c:out value="${record.name}"/></td>
                <td align="center"><c:out
                        value="${record.disableTag == '1'?'启用':''}${record.disableTag == '0'?'禁用':''}"/></td>
                <td align="center"><c:out value="${record.remark}"/></td>

                <td align="center">
                    <a class="btn btn-green" data-title="修改" data-id="form"
                       data-toggle="dialog" data-width="800" data-height="400" data-id="user-update"
                       href="role/updatepage/${record.id}">修改</a>
                    <a class="btn btn-red" data-confirm-msg="确定要删除吗？" data-toggle="doajax"
                       href="role/delete/${record.id}">删除</a>
                    <a href="role/authpage/${record.id}"
                       class="btn btn-green" data-toggle="dialog" data-width="400" data-height="400"
                       data-id="dialog-normal" data-title="角色资源组信息">授权</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="bjui-pageFooter" id="pageFooter">
    <div class="pages">
        <span>每页 </span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize">
                <option value="10">10</option>
                <option value="30">30</option>
                <option value="60">60</option>
                <option value="100">100</option>
            </select>
        </div>
        <span> 条，共 <c:out value="${pageUser.itemCount}"/> 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="<c:out value="${pageUser.itemCount}"/>"
         data-page-size="<c:out value="${pageUser.pageSize}"/>"
         data-page-current="<c:out value="${pageUser.pageIndex}"/>">
    </div>
</div>
</body>
</html>
