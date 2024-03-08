<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>희망의 조각 Admin</title>
	
	<link href="${contextPath}/resources/style/css/hyun.css/admin/boardManagement.css" rel="stylesheet">
	<script src="${contextPath }/resources/js/hyun.js/admin/sb-admin-2.min.js"></script>
</head>
	
<body>
	
	   <!-- Page Wrapper -->
    <div id="wrapper">
    
    	<jsp:include page="/WEB-INF/views/admin/common/navBar.jsp"/>

        
        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">
            
            	<jsp:include page="/WEB-INF/views/admin/common/header.jsp"/>

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <h1 class="h3 mb-0 text-gray-800">게시판 관리</h1>
                        	<div class="btn_confirm">
                        	<button class="btn_medium" onclick="window.location.href='${contextPath}/admin/boardManagement'">물품기부</button>
							<button class="btn_medium" onclick="window.location.href='${contextPath}/admin/P'">봉사활동</button>
							<button class="btn_medium" onclick="window.location.href='${contextPath}/admin/D'">후원모집</button>
							<button class="btn_medium" onclick="window.location.href='${contextPath}/admin/R'">나눔후기</button>
							<button class="btn_medium" onclick="window.location.href='${contextPath}/admin/C'">자유게시판</button>							
						</div>
                    </div>
                    

				
				<c:if test="${not empty param.condition}">
										<c:set var="url"
											value="&condition=${param.condition}&keyword=${param.keyword}" />
										</c:if>

				<form id="fsearch" method="get" action="P">
						<div class="tbl_frm01">
							
							<div class="search_boardList">
		                    	<span class="h_titleLine"></span>
		                    	<span style="font-size:20px;">봉사활동</span>
		                    
			                </div>
							<table>
								<colgroup>
									<col class="w100">
									</col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">검색어</th>
										<td>
											<select name="condition">
												<option value="title" ${param.condition eq 'title'? 'selected':'' }>제목</option>												
											</select>
											<input type="text" name="keyword" value="${param.keyword}"class="frm_input" size="30">
										</td>
									</tr>
								
								</tbody>
							</table>								
						</div>
						
								<div class="btn_confirm">
							<input type="submit" value="검색" class="btn_medium"> 
							<input type="reset" value="초기화" id="frmRest" class="btn_medium grey">
						</div>
						
									
						<div class="boardList_table">
						
						    <table>
						     <colgroup>
							        <col style="width: 5%;">
							        <col style="width: 45%;">
							        <col style="width: 12%;">
							        <col style="width: 12%;">
							        <col style="width: 12%;">
							        <col style="width: 5%;">
							    </colgroup>
						        <thead>
						            <tr>
						                <th>번호</th>
						                <th>프로그램</th>
						                <th colspan="2">활동기간</th>
						                <th>모집기간</th>						               
						                <th>인원</th>							                
						                <th>비고</th>					          
						            </tr>
						        </thead>
						        <tbody>
						         <c:forEach var="b" items="${list}">		
						            <tr>
						                <td>${b.programNo }</td>
						                <td>${b.programName }</td>
						                <td>${b.programActivityStartDate }</td>
						                <td>${b.programActivityEndDate }</td>						               
						                <td>${b.programEnrollEndDate }</td>
						                <td><button onclick="programCount(${b.programNo})" type="button">명단</button></td>
						                
						                <td><button onclick="deleteProgram(${b.programNo})" type="button">삭제</button></td>
						                
						               
						            </tr>
						            </c:forEach>
						        </tbody>
						    </table>
						</div>
					
						<div id="pagingArea">
						<ul class="pagination">
							<c:if test="${pi.currentPage ne 1}">
								<li class="page-item"><a class="page-link"
									href="?currentPage=${pi.currentPage-1}${url}">Previous</a></li>
							</c:if>
							<c:forEach var="p" begin="${pi.startPage}" end="${pi.endPage}">
								<li class="page-item"><a class="page-link"
									href="?currentPage=${p}${url}">${p}</a></li>
							</c:forEach>
							<c:if test="${pi.currentPage ne pi.maxPage}">
								<li class="page-item"><a class="page-link"
									href="?currentPage=${pi.currentPage+1}${url}">Next</a></li>
							</c:if>
						</ul>
						</div>
				</form>
				</div>
			</div>
		</div>
	</div>
	<script>
	function programCount(programNo) {       
        var url = '${contextPath}/admin/program/people/' + programNo;
        window.location.href = url;
    }
	function deleteProgram(programNo) {
        
        var url = '${contextPath}/admin/delete/program/' + programNo;
        window.location.href = url;
    }
		
	</script>
</body>
</html>									