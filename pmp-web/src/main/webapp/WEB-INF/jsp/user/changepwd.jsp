<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改密码</title>
</head>
<body>
<div class="bjui-pageHeader">

</div>
<div class="bjui-pageContent tableContent">
 <form id="pagerForm" name="userManageForm" data-callback="passwdRefreshAndClose" data-toggle="validate" novalidate="novalidate" action="${pageContext.request.contextPath}/user/changepwd" method="post">
     <div class="bjui-searchBar">
    <input type="hidden" name="userid" value="<c:out value="${user.id}"/>"/>
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">账  号：</label>
      <input type="text" value="<c:out value="${user.name}"/>" name="username" size="15" data-rule="账号:required" />&nbsp;
      </div>

       <c:if test="${user.name eq admin}">
       <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">旧密码：</label>
      <input type="password" value="" name="oldpassword" size="15" data-rule="密码:required" />&nbsp;
      </div>
       </c:if>

       <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">新密码：</label>
      <input type="password" data-rule="required;" name="pass" id="secpassword" value="" placeholder="确认新密码" size="15">
      </div>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" class="btn-default" data-icon="save" reloadNavtab="true">更新</button>&nbsp;
    </div>
  </form>
</div>
<div class="bjui-pageFooter" id="pageFooter">
  <div class="pages">
  </div>
</div>

</div>

</body>
</html>
