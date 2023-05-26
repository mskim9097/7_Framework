<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!-- 
	
	*	http://localhost:8080/comm
	*	http://localhost:8080/comm/main 주소로 요청 위임
	-> forward 이기 때문에 출력되는 주소는 http://localhost:8080/comm 유지

 -->
 
 <c:choose>
 	 <c:when test="${ !empty sessionScope.loginMember }">
		 <% response.sendRedirect("/comm/board/list/1"); %>
	</c:when>
	<c:otherwise>
		<jsp:forward page="main"/>
	</c:otherwise>
 </c:choose>
 

 
