<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/JavaScript">
	function confirmDelete() {
		var agree = confirm("Bạn muốn xóa không?");
		if (agree)
			return true;
		else
			return false;
	}
</script>
<br />
<br />
<div id="game">
<div class="menu-r paddingTop5">
		      <img width="16" height="16" src="${contextPath}/css/images/view_list.png" alt="Danh sách">
						<h2 class="h2service" style="float:left;margin-right:10px">
								Danh sách các chủ đề đã có
						</h2>
			<a href="${contextPath}/backend/addCategory"><img width="16" height="16" src="${contextPath}/css/images/add_folder_archive.png" alt="Thêm mới"></a>		
						
						<div class="clear"></div>
						
		    </div>
</div>		    
<br /> <br />

<c:forEach items="${categoryTOs}" var="categoryTO">

<c:if test="${categoryTO.avatar ne null or categoryTO.avatar eq ''}"><img width="16"height="16" src="../${categoryTO.avatar}" style="margin-right:5px"></c:if>${categoryTO.language.language} - ${categoryTO.name}
		<a href="${contextPath}/backend/addSubCategory?idx=${categoryTO.idx}" title="Thêm">
			<img src="${contextPath}/css/images/folder_add.png" style="padding-right: 4px" >
		</a>
		<a href="${contextPath}/backend/editCategory?idx=${categoryTO.idx}" title="Sửa">
			<img src="${contextPath}/css/images/edit.png" style="padding-right: 4px" >
		</a>
		<a href="${contextPath}/backend/deleteCategory?idx=${categoryTO.idx}" onClick="return confirmDelete();" title="Xóa">
			<img src="${contextPath}/css/images/delete.png" style="padding-right: 4px" >
		</a> <br />
	
	<c:forEach items="${categoryTO.categories}" var="category">
		&nbsp;&nbsp;&nbsp;&nbsp;--> 
		<c:if test="${categoryTO.avatar ne null or categoryTO.avatar eq ''}">
		<img width="16"height="16" src="../${category.avatar}"></c:if>
		${category.name} 
		<a href="${contextPath}/backend/editSubCategory?idx=${category.idx}" title="Sửa">
			<img src="${contextPath}/css/images/edit.png" style="padding-right: 4px" >
		</a>
		<a href="${contextPath}/backend/deleteCategory?idx=${category.idx}" onClick="return confirmDelete();">
			<img src="${contextPath}/css/images/delete.png" style="padding-right: 4px" title="Xóa">
		</a> <br />
	</c:forEach>
	<br />
</c:forEach>