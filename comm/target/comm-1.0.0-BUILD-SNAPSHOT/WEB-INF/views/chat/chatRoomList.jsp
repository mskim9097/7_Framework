<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<title>채팅방 목록</title>

    <link rel="stylesheet" href="${contextPath}/resources/css/main-style.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/boardList-style.css">
	<link rel="stylesheet" href="${contextPath}/resources/css/chat-style.css">

    <script src="https://kit.fontawesome.com/a2e8ca0ae3.js" crossorigin="anonymous"></script>
</head>

<body>
  	<main>
        <jsp:include page="/WEB-INF/views/common/header.jsp"/>

		<section class="board-list">
			<h1 class="board-name">채팅방 목록</h1>


			<div class="list-wrapper">
				<table class="list-table">
					<thead>
						<tr>
							<th>방번호</th>
							<th>채팅방 제목(주제)</th>
							<th>개설자</th>
							<th>참여인원수</th>
						</tr>
					</thead>
					
					<%-- 채팅 목록 출력 --%>
					<tbody>
						<c:choose>
						
							<%-- 조회된 게시글 목록이 없을 때 --%>
							<c:when test="${empty chatRoomList }">
								<tr>
									<td colspan="4">존재하는 채팅방이 없습니다.</td>
								</tr>
							</c:when>
							
							<%-- 조회된 채팅방 목록이 있을 때 --%>
							<c:otherwise>
								
								<c:forEach var="chatRoom" items="${chatRoomList}">
									<tr>
										<td>${chatRoom.chatRoomNo}</td> <%-- 채팅방번호 --%>
										
										<td> <%-- 제목 --%>
											${chatRoom.title}
											
											<c:if test="${!empty loginMember }">
												<button onclick="location.href='${contextPath}/chat/room/${chatRoom.chatRoomNo}'">참여</button>
											</c:if>
										</td> 
										
										<td>${chatRoom.memberNickname}</td> <%-- 개설자 --%>
										<td>${chatRoom.cnt}</td> <%-- 참여인원수 --%>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				
				
				<%-- 로그인이 되어있는 경우 --%>
				<c:if test="${!empty loginMember }">
					<div class="btn-area">
						<button id="openChatRoom">채팅방 만들기</button>
					</div>
				</c:if>
			</div>
		</section>
	</main>	



	<div class="modal" id="chat-modal">
		<span id="modal-close">&times;</span>

		<form class="modal-body" id="open-form" method="POST" action="${contextPath}/chat/openChatRoom">
			<h3>채팅방 만들기</h3>

			<input type="text" id="chatRoomTitle" name="title" class="form-control" placeholder="채팅방 제목" required> 
			
			<button type="submit">만들기</button>
		</form>

	</div>
			
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

	<script src="${contextPath}/resources/js/chat.js"></script>
</body>
</html>
