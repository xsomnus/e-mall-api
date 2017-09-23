<%@ page language="java" import="java.util.*,bean.*,DB.*,Tool.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
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
        <c:forEach   items="${Shop_Car_list}" var="item"  varStatus="status">     
        <tr>
            <td align="center" width="60" hight="60"><input type="checkbox" /></td>
            <td align="center"><img src="${item.cGoodsImagePath}"/>" alt="商品一"  />
            <a href="http://www.taobao.com/">
            <c:out value="${item.cGoodsName}"/>
            </a>
            </td>
            <td align="center"><span>&yen;<c:out value="${item.cGoodsNo}"/></span></td> 
            <td align="center"><input type="button" value="删除" /></td>
        </tr>
          </c:forEach> 
    </tbody>
    <tfoot>
    	<tr>
        	<th colspan="5">选中的商品总价是：<span>&yen;0.00元</span></th>
        </tr>
    </tfoot>
</table>
</body>
</html>