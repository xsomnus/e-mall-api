<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Order_Manager.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<style type="text/css">
<!--
#Layer1 {
	position:absolute;
	left:382px;
	top:11px;
	width:1003px;
	height:78px;
	z-index:1;
}
#Layer2 {
	position:absolute;
	left:163px;
	top:104px;
	width:127px;
	height:595px;
	z-index:2;
}
.STYLE1 {font-size: 18px}
.STYLE2 {font-size: 36px}
#Layer3 {
	position:absolute;
	left:375px;
	top:106px;
	width:1018px;
	height:590px;
	z-index:3;
}
-->
</style>
  </head>
  <body>
    <div id="Layer1">
  <table width="1001" height="81" border="1">
    <tr>
      <td width="211"><div align="center"><span class="STYLE2">未支付</span></div></td>
      <td width="230"><div align="center"><span class="STYLE2">代发货</span></div></td>
      <td width="250"><div align="center"><span class="STYLE2">待收货</span></div></td>
      <td width="425"><div align="center"><span class="STYLE2">已完成</span></div></td>
    </tr>
  </table>
</div>
<div id="Layer2">
  <table width="200" height="73" border="1">
     
	 <tr>
	 <x:forEach>
      <td width="89"><div align="center" class="STYLE1">用户名</div></td>
      <td width="95"><div align="center" class="STYLE1">手机号</div></td>
      </x:forEach>
     </tr>
  </table>
</div>
<div id="Layer3">
  <table width="1014" height="80" border="1">
    <tr>
      <td><div align="center"><span class="STYLE2">订单号</span></div></td>
      <td><div align="center"><span class="STYLE2">订单时间</span></div></td>
      <td><div align="center"><span class="STYLE2">支付方式</span></div></td>
      <td><div align="center"><span class="STYLE2">收货人</span></div></td>
    </tr>
  </table>
</div>
</body>
</html>
