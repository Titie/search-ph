<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">
<title>Chi tiết: ${document.title}</title>
</head>
<body style="margin-top: 20px;margin-left: 10px;">
<div class="container">
<img src="${contextPath}/css/images/Search-History.jpg" alt="" width="130px" height="130px" class="logo"> 
<br/>
<br/>
<br/>
Tìm kiếm theo từ khóa
	<div class="formSearch">
		<s:form action="normalSearch"  method="GET" enctype="multipart/form-data" accept-charset="UTF-8">
			<table>
				<tr>
					<td >
						
						<input name="q" id="q" value="${q}" type="text"/>
					</td>
					<td>
					
						<button>Search</button>
					</td>
				</tr>
			</table>
		<input name="isSubmit" id="isSubmit" value="true" type="hidden"/>
		</s:form>
	</div>
<div class="content">
<h1>${document.title}</h1>
<div class="detail-content">
${document.content}
</div>
</div>
</div>
</body>