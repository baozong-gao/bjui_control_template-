<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLEncoder" %>
<html>
<head>
    <title>权限列表</title>
</head>
<body>
<div class="bjui-pageHeader">
    <form:form id="pagerForm" name="transRateCountForm"
               data-toggle="ajaxsearch"
               action="${pageContext.request.contextPath}/fun/search" method="post"
               modelAttribute="rout">
        <input type="hidden" name="pageSize"
               value="${transRateCountForm.pageSize}"/>
        <input type="hidden" name="pageCurrent"
               value="${transRateCountForm.pageCurrent}"/>
        <div class="bjui-searchBar">
            <table style="width:100%">
                <tr>
                    <td>ID：</td>
                    <td><form:input path="id" class="form-control" size="15"/></td>
                    <td>名称：</td>
                    <td><form:input path="name" class="form-control" size="15"/></td>
                    <td>父级ID：</td>
                    <td><form:input path="parentId" class="form-control" size="15"/></td>
                    <td>权限码：</td>
                    <td><form:input path="code" class="form-control" size="15"/></td>
                    <td>等级：</td>
                    <td><form:select path="grade" data-toggle="selectpicker">
                        <form:option value="">请选择</form:option>
                        <form:option value="A">一级</form:option>
                        <form:option value="B">二级</form:option>
                        <form:option value="C">三级</form:option>
                    </form:select></td>
                    <td>状态 ：</td>
                    <td><form:select path="status" data-toggle="selectpicker">
                        <form:option value="">请选择</form:option>
                        <form:option value="O">启用</form:option>
                        <form:option value="C">禁用</form:option>
                    </form:select></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><a href="javascript:;" class="btn btn-default" data-toggle="reloadsearch"
                           data-clear-query="true">重置</a>
                        <button type="submit" class="btn-default" data-icon="search">查询</button>
                    </td>
                    <td><a class="btn btn-default" data-title="增加" data-id="form"
                           data-toggle="dialog" data-width="800" data-height="400" data-id="user-add"
                           href="fun/addpage">增加</a></td>
                    <td></td>
                    <td></td>
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
            <th align="center">父级ID</th>
            <th align="center">等级</th>
            <th align="center">URI</th>
            <th align="center">权限码</th>
            <th align="center">状态</th>
            <th align="center">排序</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="record" items="${pageUser.list}" varStatus="status">
            <tr>
                <td align="center"><c:out value="${record.id}"/></td>
                <td align="center"><c:out value="${record.name}"/></td>
                <td align="center"><c:out value="${record.parentId}"/></td>
                <td align="center"><c:out
                        value="${record.grade == 'A'?'一级':''}${record.grade == 'B'?'二级':''}${record.grade == 'C'?'三级':''}"/></td>
                <td align="center"><c:out value="${record.uri}"/></td>
                <td align="center"><c:out value="${record.code}"/></td>
                <td align="center"><c:out
                        value="${record.status == 'O'?'打开':''}${record.status == 'C'?'关闭':''}"/></td>
                <td align="center"><c:out value="${record.orderBy}"/></td>

                <td align="center">
                        <a class="btn btn-green" data-title="修改" data-id="form"
                           data-toggle="dialog" data-width="800" data-height="400" data-id="user-update"
                           href="fun/updatepage/${record.id}">修改</a>
                        <a class="btn btn-red" data-confirm-msg="确定要删除吗？" data-toggle="doajax"
                           href="fun/delete/${record.id}">删除</a>
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
