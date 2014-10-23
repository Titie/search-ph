<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="${contextPath}/css/styles.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.9.1.js"></script>
<div class="mway-header">
         <h1>Wap tải game miễnn phí hoàn toàn cho điện thoại di dộng</h1>                
        <div class="banner">
                <div class="wap-name customhtml">
                <div class="content-img"><img title="" alt="" src="${contextPath}/css/images/bannerwap.png"></div> 
            </div>
        </div>
        <div class="navi">
          <div class="navigation">
              <div class="khome">
                  <a title="Game online, game mobile, game di dong" class="home" href="${contextPath}/home"></a>
              </div>
               <c:forEach items="${categoryListRoot}" var="category">
                 <div>  <a href="${contextPath}/gameListByCategory?idx=${category.idx}">&nbsp;${category.name}&nbsp;</a>
               </div>
              </c:forEach>
           </div>


        </div>
              <div style="padding-top: 10px">
	            <form method="get" action="search">
					 <input type="text" name="keywords" id="keywords">
					 	<select id="idx" name="idx">
					 			<option value="0">Tất cả</option>
					 		<c:forEach items="${categoryListRoot}" var="category">
		                 		<option value="${category.idx}">${category.name}</option>
             		 		</c:forEach>
             		 	</select>
					 <input type="submit" value="Tìm kiếm">
				</form>
              </div>
   </div>