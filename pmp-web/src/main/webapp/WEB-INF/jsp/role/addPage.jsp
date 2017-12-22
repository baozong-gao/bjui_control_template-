<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>增加角色</title>
</head>
<body>
<div class="bjui-pageContent">
    <form:form class="nice-validator n-red" data-alertmsg="false"
               data-toggle="validate" action="role/add" novalidate="novalidate"
               modelAttribute="rout" id="useraddfrom">
        <table class="table table-condensed table-hover" width="100%">
            <tr>
                <td><label class="control-label x85" for="j_custom_name">名称：</label>
                    <form:input path="name" class="form-control" type="text"
                                size="15" name="custom.name" style="width: 150px;"
                                aria-required="true" data-rule="名称:required;length[~50, true]"
                                data-tip="请输入" data-ok="可用"/></td>
            </tr>
            <tr>
                <td><label class="control-label x85" for="j_custom_name">状态 ：</label>
                    <form:select path="disableTag" data-toggle="selectpicker">
                        <form:option value="1">启用</form:option>
                        <form:option value="0">禁用</form:option>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td><label class="control-label x85" for="j_custom_name">备注：</label>
                    <form:textarea path="remark" class="form-control"
                                   size="15" name="custom.remark" style="width: 150px;"
                                   aria-required="true" data-rule="备注:length[~255, true]"
                                   data-tip="请输入" data-ok="可用"/></td>
            </tr>
        </table>
    </form:form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li>
            <button type="button" class="btn-close">关闭</button>
        </li>
        <li>
            <button type="submit" class="btn-default">保存</button>
        </li>
    </ul>
</div>
</body>
</html>
