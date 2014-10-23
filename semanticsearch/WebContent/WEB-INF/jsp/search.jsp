<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">
<title>Tìm kiếm thông tin máy tính xách tay </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript">
$('#searchForm input:text').keypress(function (e) {
  if (e.which == 13) {
    $("#SearchBT").click();
  }
});
</script>

</head>
<body style="margin-top: 20px;margin-left: 10px;">
<div class="container">
<img src="${contextPath}/css/images/Search-History.jpg" alt="" width="130px" height="130px" class="logo"> 
	<div class="formSearch">
		<s:form id="searchForm" action="search"  method="GET" accept-charset="UTF-8">
			<table>
				<tr>
					<td >
						
						<input name="q" id="q" value="${q}" type="text"/>
					</td>
					<td>
					
						<button id="SearchBT">Search</button>
					</td>
				</tr>
			</table>
		<input name="isSubmit" id="isSubmit" value="true" type="hidden"/>
		</s:form>
	</div>
	<c:if test="${isSubmit eq 'true'}">
	<div class="content"> 
		<div id="resultStats">Khoảng ${totalResult} kết quả<nobr> (${timeTaken} giây)&nbsp;</nobr></div>
		<ul class="listStats">
			<c:forEach items="${retrievals}" var="document">
				<li>
					<a href="${contextPath}/details?documentId=${document.id}" style="margin-bottom: 0px; margin-top: 10px;float:left;" target="_blank">
						<h2>${document.title}</h2>
					</a>
					<span class="tempo">Cosin(Q,Ds) = ${document.cosinWithQuery}</span>
				<li>
			</c:forEach>
		</ul>
		
		
		<ul id="pagination-flickr">
		<c:if test="${pageNo != null}">
		          <div style="text-align:center" class="more">
		          <c:if test="${pageIndex >= 10}">
		          	<li class="previous-off">
		          		<a href="${contextPath}/search?q=${q}&isSubmit=true&pageIndex=1"> «Trước </a>
		          	</li>
		          </c:if>
					<c:forEach var="page" begin="${pageFirstLoop}" end="${pageEndLoop}">
						<c:if test="${page ne pageIndex}">
						 	<li><a href="${contextPath}/search?q=${q}&isSubmit=true&pageIndex=${page}">${page}</a></li>
						</c:if>
						<c:if test="${page eq pageIndex}">
							<li class="active">${page}</li>
						</c:if>
					</c:forEach>
				   <c:if test="${(pageIndex + 4 )< pageNo}">
		          	<li class="next"><a href="${contextPath}/search?q=${q}&isSubmit=true&pageIndex=${pageNo}">Sau »</a></li>
		          </c:if>
				</div>
		</c:if>
		</ul>
	</div>	
	</c:if>
</div>
</body>