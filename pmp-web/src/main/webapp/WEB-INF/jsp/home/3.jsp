<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.shenxin.core.control.Enum.StatisticalTypeEnum"%>
<html>
<head>
<title>首页</title>
</head>
<body>
	<div style="margin: 15px auto 0; width: 800px;">
		<div style="mini-width: 400px; height: 350px" data-toggle="echarts"
            data-url="statistical/<%=StatisticalTypeEnum.ORDER_LOGIN%>"></div>
	</div>
</body>
</html>
