<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp1.jsp' starting page</title>
   <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<meta charset="UTF-8" />
<title></title>
<link href="./shop_list/css/shop_list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="./shop_list/js/jquery.js"></script>
<script type="text/javascript" src="./shop_list/js/shop_list.js"></script>

  </head>
  
  <body>
    <table id="taobao_table">
    <thead>
        <tr>
            <th><label><input type="checkbox" />全选</label></th>
            <th>商品名</th>
            <th width="70"><a class="btn" href="#">价格</a></th>
            <th width="70"><a class="btn" href="#">地区</a></th>
            <th>功能</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td align="center" width="60" hight="60"><input type="checkbox" /></td>
            <td align="center"><img src="./shop_list/img/1.jpg" alt="商品一"  /><a href="http://www.taobao.com/">果绿后背钩花蝴蝶镂空长袖针织衫开衫小披肩韩国气质薄2010秋新</a></td>
            <td align="center"><span>&yen;59.00</span></td>
            <td align="center">北京</td>
            <td align="center"><input type="button" value="删除" /></td>
        </tr>
        <tr>
            <td align="center"><input type="checkbox" /></td>
            <td align="center"><img src="./shop_list/img/2.jpg" alt="商品二" /><a href="http://www.taobao.com/">2010秋装新款韩版女装淑女花苞波西米亚条纹连衣裙Q0070</a></td>
            <td align="center"><span>&yen;98.00</span></td>
            <td align="center"><input type="button" value="删除" /></td>
        </tr>
        <tr>
            <td align="center"><input type="checkbox" /></td>
            <td align="center"><img src="./shop_list/img/3.jpg" alt="商品三"  /><a href="http://www.taobao.com/">随意美妙 O.SA欧莎热卖女装2010秋新韩版纯棉短袖T恤女ST00401</a></td>
            <td align="center"><span>&yen;33.00</span></td>
      
            <td align="center"><input type="button" value="删除" /></td>
        </tr>
 
    </tbody>
    <tfoot>
    	<tr>
        	<th colspan="5">选中的商品总价是：<span>&yen;0.00元</span></th>
        </tr>
    </tfoot>
</table>
  </body>
</html>
