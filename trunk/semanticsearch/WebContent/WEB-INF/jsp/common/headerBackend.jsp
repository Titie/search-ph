<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="${contextPath}/css/stylesbackend.css" rel="stylesheet" type="text/css" />


<link type="text/css" href="<%=request.getContextPath()%>/style/datatable.css" rel="stylesheet" />
<link type="text/css" href="<%=request.getContextPath()%>/style/smoothness/jquery-ui-1.8.17.custom.css" rel="stylesheet" />
<link type="text/css" href="<%=request.getContextPath()%>/style/alert/jquery.alerts.css" rel="stylesheet" />

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/util.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.columnFilter.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/TableTools.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.alerts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.unobtrusive-ajax.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/textarea-maxlength.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.blockUI.js"></script>


<c:if test="${userSession eq null}">
            <c:redirect url="login" />
</c:if>
<script type="text/JavaScript">
	function confirmDelete() {
		var agree = confirm("Bạn muốn xóa?");
		if (agree)
			return true;
		else
			return false;
	}
</script>
<style type="text/css">
/* Main */
#menu{
	width: 100%;
	margin: 0;
	padding: 10px 0 0 0;
	list-style: none;  
	background: #111;
	background: -moz-linear-gradient(#444, #111); 
        background: -webkit-gradient(linear,left bottom,left top,color-stop(0, #111),color-stop(1, #444));	
	background: -webkit-linear-gradient(#444, #111);	
	background: -o-linear-gradient(#444, #111);
	background: -ms-linear-gradient(#444, #111);
	background: linear-gradient(#444, #111);
	/* -moz-border-radius: 50px;
	border-radius: 50px; */
	-moz-box-shadow: 0 2px 1px #9c9c9c;
	-webkit-box-shadow: 0 2px 1px #9c9c9c;
	box-shadow: 0 2px 1px #9c9c9c;
}

#menu li{
	float: left;
	padding: 0 0 10px 0;
	position: relative;
}

#menu a{
	float: left;
	height: 25px;
	padding: 0 25px;
	color: #999;
	text-transform: uppercase;
	font: bold 12px/25px Arial, Helvetica;
	text-decoration: none;
	text-shadow: 0 1px 0 #000;
}

#menu li:hover > a{
	color: #fafafa;
}

*html #menu li a:hover{ /* IE6 */
	color: #fafafa;
}

#menu li:hover > ul{
	display: block;
}

/* Sub-menu */

#menu ul{
    list-style: none;
    margin: 0;
    padding: 0;    
    display: none;
    position: absolute;
    top: 35px;
    left: 0;
    z-index: 99999;    
    background: #444;
    background: -moz-linear-gradient(#444, #111);
    background: -webkit-gradient(linear,left bottom,left top,color-stop(0, #111),color-stop(1, #444));
    background: -webkit-linear-gradient(#444, #111);    
    background: -o-linear-gradient(#444, #111);	
    background: -ms-linear-gradient(#444, #111);	
    background: linear-gradient(#444, #111);	
    -moz-border-radius: 5px;
    border-radius: 5px;
}

#menu ul li{
    float: none;
    margin: 0;
    padding: 0;
    display: block;  
    -moz-box-shadow: 0 1px 0 #111111, 0 2px 0 #777777;
    -webkit-box-shadow: 0 1px 0 #111111, 0 2px 0 #777777;
    box-shadow: 0 1px 0 #111111, 0 2px 0 #777777;
}

#menu ul li:last-child{   
    -moz-box-shadow: none;
    -webkit-box-shadow: none;
    box-shadow: none;    
}

#menu ul a{    
    padding: 10px;
    height: auto;
    line-height: 1;
    display: block;
    white-space: nowrap;
    float: none;
    text-transform: none;
}

*html #menu ul a{ /* IE6 */   
	height: 10px;
	width: 150px;
}

*:first-child+html #menu ul a{ /* IE7 */    
	height: 10px;
	width: 150px;
}

#menu ul a:hover{
        background: #0186ba;
	background: -moz-linear-gradient(#04acec,  #0186ba);	
	background: -webkit-gradient(linear, left top, left bottom, from(#04acec), to(#0186ba));
	background: -webkit-linear-gradient(#04acec,  #0186ba);
	background: -o-linear-gradient(#04acec,  #0186ba);
	background: -ms-linear-gradient(#04acec,  #0186ba);
	background: linear-gradient(#04acec,  #0186ba);
}

#menu ul li:first-child a{
    -moz-border-radius: 5px 5px 0 0;
    -webkit-border-radius: 5px 5px 0 0;
    border-radius: 5px 5px 0 0;
}

#menu ul li:first-child a:after{
    content: '';
    position: absolute;
    left: 30px;
    top: -8px;
    width: 0;
    height: 0;
    border-left: 5px solid transparent;
    border-right: 5px solid transparent;
    border-bottom: 8px solid #444;
}

#menu ul li:first-child a:hover:after{
    border-bottom-color: #04acec; 
}

#menu ul li:last-child a{
    -moz-border-radius: 0 0 5px 5px;
    -webkit-border-radius: 0 0 5px 5px;
    border-radius: 0 0 5px 5px;
}

/* Clear floated elements */
#menu:after{
	visibility: hidden;
	display: block;
	font-size: 0;
	content: " ";
	clear: both;
	height: 0;
}
</style>
<div class="mway-header">               
        <div class="banner">
                <div class="wap-name customhtml">
                <div class="content-img">Learn</div> 
            </div>
            <div style="float:right;text-align: right;color: white;">
			Chào <a style="color: red;" href="${contextPath}/backend/editUser?userIdx=${userSession.idx}" title="">${userSession.fullName}!</a>
              | 
                <a style="color: red;" href="logout">Đăng xuất</a>
    </div>
        </div>
<div>
<c:if test="${userSession != null}">
<ul id="menu">
<%-- 	<li><a href="${contextPath}/backend/home">Home</a></li> --%>
<!-- 	<li> -->
<!-- 		<a href="#">Quản lý bài viết - Comment</a> -->
<!-- 		<ul> -->
<%-- 			<c:forEach items="${categoryTOListByUser}" var="categoryTO"> --%>
<%-- 				<c:forEach items="${categoryTO.categories}" var="category"> --%>
<%-- 					<li><a href="#">${categoryTO.name} - ${category.name}</a></li> --%>
<%-- 				</c:forEach> --%>
<%-- 			</c:forEach> --%>
			
<!-- 		</ul> -->
<!-- 	</li> -->
	<li><a href="${contextPath}/backend/home">Quản lý bài viết</a></li>
	<c:forEach items="${screenTOListByUser}" var="screenTO">
	<li>
		<a href="#">${screenTO.name}</a>
		<ul>
			<c:forEach items="${screenTO.screens}" var="screen">
				<li><a href="${contextPath}/${screen.url}">${screen.name}</a></li>
			</c:forEach>
		</ul>
	</li>
	</c:forEach>

</ul>
</c:if>
</div>
   </div>