<%@ page language="java" import="java.util.*,org.json.*, bean.*,DB.*,Tool.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html>
<html>
<head>
	<title></title>
	<link rel="stylesheet" href="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<link href="css/main.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="js/jquery-1.10.2.min.js" ></script>
	<script type="text/javascript" src="js/touchslider.dev.js" ></script>
	<script type="text/javascript" src="js/banner.js" ></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
</head>
<!-- 
function doAction(){
document.mainForm.action="lx";
document.mainForm.submit();
}
$(document).click(function (e) { 
var v_id = $(e.target).attr('id'); 
} 
 -->
 
 <!-- 
 
 $(document).ready(function(){ 
	
 function getThis(obj){
   var a=myform.name.value;  
 	alert($(obj).attr("id"));
 }
    
}); 
  -->

<body >
<!--banner开始-->
<form  name="mainForm" action="" method="post">

<%-- <div id="wrapper">
  <div class="swipe">
    <ul id="slider">
      <li style="display:block">
      
       <li><img src="${item.adImagePath}" width="100%"><p>${item.adcGoodsName}"</p></li>
      </c:forEach>
     <!--  <li><img src="img/banner.jpg" width="100%" ></li>
      <li><img src="img/banner.jpg" width="100%" ></li>
      <li><img src="img/banner.jpg" width="100%" ></li> -->
    </ul>
    <div id="pagenavi"> <a href="javascript:void(0);" class="active">1</a> <a href="javascript:void(0);">2</a> <a href="javascript:void(0);">3</a> <a href="javascript:void(0);">4</a> </div>
  </div>
</div> --%>

<div id="myCarousel" class="carousel slide">
	<!-- 轮播（Carousel）指标 -->
	<ol class="carousel-indicators">
		<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		<li data-target="#myCarousel" data-slide-to="1"></li>
		<li data-target="#myCarousel" data-slide-to="2"></li>
	</ol>   
	<!-- 轮播（Carousel）项目 -->
	  <div class="carousel-inner">
	   
		<div class="item active">
			<img src="img/1.jpg" alt="广告页" style="width: 1080px; ">
			<div class="carousel-caption">广告</div>
		</div>
		<c:forEach   items="${Advertise}" var="item"  varStatus="status">   
		<div class="item" class="minbox align-center"> 
			<img src="${item.adImagePath}"  width="304" height="236"  >
			<p></p>
			<div class="carousel-caption">${item.adcGoodsName}</div>
		</div>
		 </c:forEach>
	</div>
	<!-- 轮播（Carousel）导航 -->
	<a class="carousel-control left" href="#myCarousel" 
	   data-slide="prev">&lsaquo;</a>
	<a class="carousel-control right" href="#myCarousel" 
	   data-slide="next">&rsaquo;</a>
</div> 

<!--banner结束-->

<!--分类开始-->
<div class="category">
  <ul>
    <%
		List<T_GroupType> list=(List<T_GroupType>)request.getAttribute("list");
    	 if(list!=null){
    	 for(T_GroupType e : list){

    	 %>
          <li id=<%= e.getcGroupTypeNo()%>&<%=e.getcGroupTypeName()%> onclick="fc(this)" > <a href="#" class="vegetables"  ><%= e.getcGroupTypeName() %></a> < </li>
           
    	 <%
    	 }
    	 
    	 }
    
     %>

   
  
  </ul>
</div>
<!--分类结束-->

<!--每日特价开始-->
<div class="hot"><span class="more"><a href="#">更多></a></span>每日特价</div>
<div class="newcp">
  <ul>
    <li><a href="#">
      <div class="img01"><img src="img/egg.jpg" width="120" height="120"></div>
      <div class="name">鸡蛋</div>
      <div class="price"> &yen;2.80元</div>
      </a>
      </li>
  </ul>
</div>
<!--每日特价结束-->

<!--新商品开始-->
<div class="hot"><span class="more"><a href="#">更多</a></span>新商品上架</div>
<div class="shop">
  <ul>
    <li><a href="#">
      <div class="img01"><img src="img/egg.jpg" width="120" height="120"></div>
      <div class="name">芙蓉王</div>
      <div class="price"> &yen;25.00元</div>
      </a>
      </li>
       <li><a href="#">
      <div class="img01"><img src="img/egg.jpg" width="120" height="120"></div>
      <div class="name">软芙蓉</div>
      <div class="price"> &yen;16.00元</div>
      </a>
      </li>
  </ul>
</div>
<!--新商品结束-->
<div class="hot"><span class="more"><a href="#">更多></a></span>推荐商品</div>
<div class="shop">
  <ul>
  		<%
  			List<T_Goods> tuijian=(List<T_Goods>)request.getAttribute("name");
  			if(tuijian!=null){
  			
  			 for(T_Goods r: tuijian){
  			 %>
    <li id=<%=r.getcGoodsNo()%>&<%=r.getfNormalPrice() %>&<%=r.getRecommend_price() %> onclick="tejia(this)"><a href="#">
      <div class="img01"  ><img src="img/egg.jpg" width="120" height="120"></div>
      <div class="name"><%=r.getcGoodsName() %></div>
      <div class="price"> &yen;<%=r.getfNormalPrice() %>元</div>
      </a>
      </li>
  			 
  			 <%
  			 }
  			}
  		 %>
      
  </ul>
</div>
</form>
</body>
<script type="text/javascript" >
function fc(dom){

 var id=document.getElementById(dom.id);
 
 window.location.href = "samson://fc_typeNo_typeName?"+dom.id; 
 window.samson.HtmlcallJava2(dom.id);
 
}


function tejia(dom){

 var id=document.getElementById(dom.id);

 /* window.location.href = "samson://tejia_goodsNo_normal_sNormal?"+dom.id; */
 window.samson.BargainMethod(dom.id);
 
}


</script>
</html>