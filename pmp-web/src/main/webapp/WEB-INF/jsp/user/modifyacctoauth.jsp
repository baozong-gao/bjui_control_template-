<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>账户权限设置</title>
</head>
<body>
<div class="bjui-pageHeader">
    <div class="bjui-searchBar">
      <label>账户名：</label>
      <label><c:out value="${user.name}"/></label>&nbsp;
      <label>账户状态：</label>
      <label>${user.disableTag == '0'?"禁用":"启用"}</label>&nbsp;
    </div>
</div>
<div class="bjui-pageContent tableContent">
  <table  class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
    <thead>
    <tr>
      <th align="center">权限组</th>
      <th align="center">授权状态</th>
      <th align="center">操作</th>
    </tr>
    </thead>
    <tbody>
<c:forEach var="record" items="${roles}" varStatus="status">
<tr data-id="<c:out value="${record.id}"/>">
      <td align="center"><c:out value="${record.name}"/></td>
      <td align="center">${record.auth?"已授权":"未授权"}</td>
      <td align="center">
         <a href="${pageContext.request.contextPath}/user/auth/${user.id}/${record.id}/op" class="btn btn-red" data-toggle="doajax" >授权</a>&nbsp;
         <a href="${pageContext.request.contextPath}/user/auth/${user.id}/${record.id}/cl" class="btn btn-red" data-toggle="doajax" >禁止</a>&nbsp;
      </td>
</tr>
  </c:forEach>
    </tbody>
  </table>

</div>
<div class="bjui-pageFooter" id="pageFooter">

</div>

</div>

</body>
</html>
