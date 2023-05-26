<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<div id="reply-area">
    <!-- 댓글 목록 -->
    <div class="reply-list-area">
        
        <ul id="reply-list">

            <c:forEach var="reply" items="${rList}">
                							<%-- 답글일 경우 --%>
                <li class='reply-row  <c:if test="${reply.parentReplyNo != 0}"> child-reply </c:if>'>
                    <p class="reply-writer">

                        <c:if test="${empty reply.profileImage}">
                            <!-- 프로필 이미지가 없을 경우 -->
                            <img src="${contextPath}/resources/images/user.png">
                        </c:if>

                        <c:if test="${!empty reply.profileImage}">
                            <!-- 프로필 이미지가 있을 경우 -->
                            <img src="${contextPath}${reply.profileImage}">
                        </c:if>

                        <span>${reply.memberNickname}</span>
                        <span class="reply-date">(${reply.createDate})</span>
                    </p>
                    
                    <p class="reply-content">${reply.replyContent}</p>

                    <%-- 로그인 상태인 경우 답글 버튼 출력 --%>
                    <c:if test="${!empty loginMember}">
                        <div class="reply-btn-area">

                            <button onclick="showInsertReply(${reply.replyNo}, this)">답글</button>

                            <%-- 로그인한 회원의 댓글인 경우 --%>
                            <c:if test="${loginMember.memberNo == reply.memberNo}">
                                <button onclick="showUpdateReply(${reply.replyNo}, this);">수정</button>     
                                <button onclick="deleteReply(${reply.replyNo})">삭제</button>
                            </c:if>

                        </div>
                    </c:if>
                </li>
            </c:forEach>
            
        </ul>
    </div>
    

    <!-- 댓글 작성 부분 -->
    <div class="reply-write-area">
        <textarea id="replyContent"></textarea>
        <button id="addReply">
            댓글<br>
            등록
        </button>

    </div>

</div>