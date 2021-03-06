<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改权限</title>
</head>
<body>
	<div class="bjui-pageContent">
		<form:form class="nice-validator n-red" data-alertmsg="false"
				   data-toggle="validate" action="fun/update" novalidate="novalidate"
				   modelAttribute="rout" id="useraddfrom">
			<form:hidden path="id"/>
			<table class="table table-condensed table-hover" width="100%">
				<tr>
					<td><label class="control-label x85" for="j_custom_name">名称：</label>
						<form:input path="name" class="form-control" type="text"
									size="15" name="custom.name" style="width: 150px;"
									aria-required="true" data-rule="名称:required;length[~32, true]"
									data-tip="请输入" data-ok="可用"/></td>
				</tr>
				<tr>
					<td><label class="control-label x85" for="j_custom_name">父级ID：</label>
						<form:input path="parentId" class="form-control" type="text"
									size="15" name="custom.parentId" style="width: 150px;"
									aria-required="true" data-rule="父级ID:required;number"
									data-tip="请输入" data-ok="可用"/></td>
				</tr>
				<tr>
					<td><label class="control-label x85" for="j_custom_name">等级：</label>
						<form:select path="grade" data-toggle="selectpicker">
							<form:option value="A">一级</form:option>
							<form:option value="B">二级</form:option>
							<form:option value="C">三级</form:option>
						</form:select>
					</td>
				</tr>
				<tr>
					<td><label class="control-label x85" for="j_custom_name">URI：</label>
						<form:input path="uri" class="form-control" type="text"
									size="15" name="custom.uri" style="width: 150px;"
									data-tip="请输入" data-ok="可用"/></td>
				</tr>
				<tr>
					<td><label class="control-label x85" for="j_custom_name">权限码：</label>
						<form:input path="code" class="form-control" type="text"
									size="15" name="custom.code" style="width: 150px;"
									aria-required="true" data-rule="权限码:required;length[~255, true]"
									data-tip="请输入" data-ok="可用"/></td>
				</tr>
				<tr>
					<td><label class="control-label x85" for="j_custom_name">状态 ：</label>
						<form:select path="status" data-toggle="selectpicker">
							<form:option value="O">启用</form:option>
							<form:option value="C">禁用</form:option>
						</form:select>
					</td>
				</tr>
				<tr>
					<td><label class="control-label x85" for="j_custom_name">排序：</label>
						<form:textarea path="orderBy" class="form-control"
									   size="15" name="custom.orderBy" style="width: 150px;"
									   data-tip="请输入" data-ok="可用"/></td>
				</tr>
				<tr>
					<td><label class="control-label x85" for="j_custom_name">Logo：</label>
						<form:textarea path="logo" class="form-control"
									   size="15" name="custom.orderBy" style="width: 150px;"
									   data-tip="请输入" data-ok="可用"/></td>
				</tr>
			</table>
		</form:form>
	</div>
	<div class="bjui-pageFooter">
		<ul>
			<li><button type="button" class="btn-close">关闭</button></li>
			<li><button type="submit" class="btn-default">保存</button></li>
		</ul>
	</div>
</body>
</html>
