<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>

    <link rel="stylesheet" href="${contextPath}/resources/css/main-style.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/boardDetail-style.css">

    <link rel="stylesheet" href="${contextPath}/resources/css/reply-style.css">


    <script src="https://kit.fontawesome.com/a2e8ca0ae3.js" crossorigin="anonymous"></script>
</head>
<body>
    <main>
        <jsp:include page="/WEB-INF/views/common/header.jsp"/>

        <section class="board-detail">  

            <!-- 제목 -->
            <h1 class="board-title">${detail.boardTitle}  <span> - ${detail.boardName}</span>    </h1>

            <!-- 프로필 + 닉네임 + 작성일 + 조회수 -->
            <div class="board-header">
                <div class="board-writer">

                    <c:if test="${empty detail.profileImage}">
                        <!-- 프로필 이미지가 없는 경우 -->
                        <img src="${contextPath}/resources/images/user.png">
                    </c:if>

                    <c:if test="${ !empty detail.profileImage}">
                         <!-- 프로필 이미지가 있는 경우 -->
                        <img src="${contextPath}${detail.profileImage}">
                    </c:if>

                    <span>${detail.memberNickname}</span>

                </div>

                <div class="board-info">
                    <p> <span>작성일</span>         ${detail.createDate} </p>     

                    <c:if test="${ !empty detail.updateDate}">
                        <!-- updateDate가 존재하는 경우 -->
                        <p> <span>마지막 수정일</span>  ${detail.updateDate} </p>   
                    </c:if>

                    <p> <span>조회수</span>         ${detail.readCount} </p>                    
                </div>
            </div>

            <!-- 이미지가 있을 경우 -->
            <c:if test="${!empty detail.imageList}">
                
                <!-- 썸네일이 있을 경우 변수 생성 -->
                <c:if test="${detail.imageList[0].imageLevel == 0}">
                    <c:set var="thumbnail" value="${detail.imageList[0]}" />
                    <!-- page scope (페이지 어디서든 사용 가능) -->
                </c:if>


                <!-- 썸네일 영역(썸네일이 있을 경우) -->
                <c:if test="${!empty thumbnail}">
                    <h5>썸네일</h5>
                    <div class="img-box">
                        <div class="boardImg thumbnail">
                            <img src="${contextPath}${thumbnail.imageReName}">
                            <a href="${contextPath}${thumbnail.imageReName}" download="${thumbnail.imageOriginal}">다운로드</a>         
                        </div>
                    </div>
                </c:if>



                <c:if test="${empty thumbnail}"> <!-- 썸네일 X -->
                    <c:set var="start" value="0"/>
                </c:if>
                
                <c:if test="${!empty thumbnail}"> <!-- 썸네일 O -->
                    <c:set var="start" value="1"/>
                </c:if>


                <!-- 업로드 이미지가 있는 경우 -->
                <c:if test="${fn:length(detail.imageList) > start}">

                    <!-- 업로드 이미지 영역 -->
                    <h5>업로드 이미지</h5>
                    <div class="img-box">
                        <c:forEach var="i" begin="${start}" end="${fn:length(detail.imageList) -1 }">
                        
                            <div class="boardImg">
                                <img src="${contextPath}${detail.imageList[i].imageReName}">
                                <a href="${contextPath}${detail.imageList[i].imageReName}" download="${detail.imageList[i].imageOriginal}">다운로드</a>                
                            </div>

                        </c:forEach>
                    </div>

                </c:if>        

            </c:if>

            <!-- 내용 -->
            <div class="board-content">
                ${detail.boardContent}
            </div>


            <!-- 버튼 영역-->
            <div class="board-btn-area">

                <c:if test="${loginMember.memberNo == detail.memberNo}">
                                                            <!-- detail?type=1&cp=3&no=100 -->
                                                            <!-- detail?no=1522&type=2 -->
                    <%-- cp가 없을 경우에 대한 처리 --%>
                    <c:if test="${empty param.cp}">
                        <!-- 파라미터에 cp가 없을 경우 1 -->
                        <c:set var="cp" value="1" />
                    </c:if>

                    <c:if test="${!empty param.cp}">
                        <!-- 파라미터에 cp가 있을 경우 param.cp -->
                        <c:set var="cp" value="${param.cp}" />
                    </c:if>


                    <button id="updateBtn" onclick="location.href='write?mode=update&type=${param.type}&cp=${cp}&no=${detail.boardNo}'">수정</button>                       
                    <button id="deleteBtn">삭제</button>
                </c:if>

                <!-- onclick="history.back();"  뒤로가기 
                    history.go(숫자) : 양수(앞으로가기), 음수(뒤로가기)
                -->
                <button id="goToListBtn">목록으로</button>
            </div>
        </section>

        <!-- 댓글 -->
        <jsp:include page="/WEB-INF/views/board/reply.jsp"/>

    </main>

    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>


    <!-- jQuery 추가 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

    <script src="${contextPath}/resources/js/board/board.js"></script>


    <script>
        // 댓글 관련 JS 코드에 필요한 값을 전역 변수로 선언

        // jsp 파일 : html, css, js, el, jstl 사용 가능
        // js  파일 : js

        // 코드 해석 순서  :   EL == JSTL > HTML > JS

        // ** JS 코드에서 EL/JSTL을 작성하게 된다면 반드시 ""를 양쪽에 추가 **

        // 최상위 주소
        const contextPath = "${contextPath}";
        
        // 게시글 번호
        const boardNo = "${detail.boardNo}"; // "500"

        // 로그인한 회원 번호
        const loginMemberNo = "${loginMember.memberNo}";
        // -> 로그인 O  : "10";
        // -> 로그인 X  : "";  (빈문자열)

    </script>

    <script src="${contextPath}/resources/js/board/reply.js"></script>



</body>
</html>