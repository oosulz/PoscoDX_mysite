<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@page import="mysite.vo.BoardVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
List<BoardVo> boardlist = (List<BoardVo>) request.getAttribute("boardlist");
%>



<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>

<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>

				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${boardlist }" var="vo" varStatus="status">

						<input type="hidden" name="gno" value="${vo.gNo}">
						<input type="hidden" name="ono" value="${vo.oNo}">
						<input type="hidden" name="depth" value="${vo.depth}">
						<tr>
							<td>${totalCount-((currentPage - 1) * pageSize + status.index)}</td>
							<c:choose>
								<c:when test="${vo.depth < 1}">
									<td style="text-align:left; padding-left: ${1 * 20}px"><a
										href="${pageContext.request.contextPath }/board?a=boarddetail&id=${vo.id}">${vo.title }</a>
									</td>
								</c:when>
								<c:when test="${vo.depth >= 1}">
									<td style="text-align:left; padding-left: ${vo.depth * 20}px">
										<img
										src="${pageContext.request.contextPath}/assets/images/reply.png">
										<a
										href="${pageContext.request.contextPath }/board?a=boarddetail&id=${vo.id}">${vo.title }</a>
									</td>
								</c:when>
							</c:choose>

							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td><c:choose>
									<c:when test="${authUser.id == vo.userId}">
										<a
											href="${pageContext.request.contextPath}/board?a=boarddelete&id=${vo.id }"
											class="del">삭제</a></td>
							</c:when>
							</c:choose>
						</tr>
					</c:forEach>
				</table>

				<div class="pager">
					<ul>

						<c:if test="${hasPrev}">
							<li><a
								href="${pageContext.request.contextPath}/board?boardpage=${currentPage-1}">◀</a>
							</li>
						</c:if>


						<c:forEach begin="${startPage}" end="${startPage + pageBlock - 1}"
							var="page">
							<c:choose>

								<c:when test="${page == currentPage}">
									<li class="selected">${page}</li>
								</c:when>


								<c:when test="${disabledPages.contains(page)}">
									<li class="disabled">${page}</li>
								</c:when>


								<c:otherwise>
									<li><a
										href="${pageContext.request.contextPath}/board?boardpage=${page}">${page}</a>
									</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:if test="${hasNext}">
							<li><a
								href="${pageContext.request.contextPath}/board?boardpage=${currentPage+1}">▶</a>
							</li>
						</c:if>
					</ul>
				</div>

				<c:choose>
					<c:when test="${not empty authUser }">
						<div class="bottom">
							<a href="${pageContext.request.contextPath}/board?a=boardwrite"
								id="new-book">글쓰기</a>
						</div>
					</c:when>
				</c:choose>
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>