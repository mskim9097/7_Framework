<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<div id="reply-area">
    <!-- 댓글 목록 -->
    <div class="reply-list-area">
        
        <ul id="reply-list">

            <c:forEach var="reply" items="${rList}">
                <li class="reply-row">
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

                    <c:if test="${loginMember.memberNo == reply.memberNo}">
                        <div class="reply-btn-area">

                            <button onclick="showUpdateReply(${reply.replyNo}, this);">수정</button>     

                            <button onclick="deleteReply(${reply.replyNo})">삭제</button>
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