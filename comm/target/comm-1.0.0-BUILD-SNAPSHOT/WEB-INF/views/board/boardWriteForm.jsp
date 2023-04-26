<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 등록</title>

    <link rel="stylesheet" href="${contextPath}/resources/css/main-style.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/boardWriteForm-style.css">
    <script src="https://kit.fontawesome.com/a2e8ca0ae3.js" crossorigin="anonymous"></script>

</head>
<body>
    <main>
        <jsp:include page="/WEB-INF/views/common/header.jsp"/>

        <form action="write" enctype="multipart/form-data" method="POST" class="board-write"
            onsubmit="return writeValidate()">

            <!-- 제목 -->
            <h1 class="board-title">
                <input type="text" name="boardTitle" placeholder="제목을 입력해주세요." value="${detail.boardTitle}">         
            </h1>


            <%-- imageList에 존재하는 이미지 레벨을 이용하여 변수 생성 --%>
            <c:forEach items="${detail.imageList}" var="boardImage">

                <c:choose>
                    <c:when test="${boardImage.imageLevel == 0}">
                        <%-- c:set 변수는 page scope가 기본값 (조건문이 끝나도 사용 가능)  --%>
                        <c:set var="img0"  value="${contextPath}${boardImage.imageReName}" />
                    </c:when>

                    <c:when test="${boardImage.imageLevel == 1}">
                        <c:set var="img1"  value="${contextPath}${boardImage.imageReName}" />
                    </c:when>

                    <c:when test="${boardImage.imageLevel == 2}">
                        <c:set var="img2"  value="${contextPath}${boardImage.imageReName}" />
                    </c:when>

                    <c:when test="${boardImage.imageLevel == 3}">
                        <c:set var="img3"  value="${contextPath}${boardImage.imageReName}" />
                    </c:when>

                    <c:when test="${boardImage.imageLevel == 4}">
                        <c:set var="img4"  value="${contextPath}${boardImage.imageReName}" />
                    </c:when>
                </c:choose>
            </c:forEach>




            <!-- 썸네일 -->
            <h5>썸네일</h5>
            <div class="img-box">
                <div class="boardImg thumbnail">
                    <label for="img0">
                        <img class="preview" src="${img0}">
                    </label>
                    <input type="file" class="inputImage" id="img0" name="0" accept="image/*">
                    <span class="delete-image">&times;</span>
                    <!-- &times;  :  x 모양의 문자 -->
                </div>
            </div>

            <!-- 업로드 이미지 -->
            <h5>업로드 이미지</h5>
            <div class="img-box">

                <div class="boardImg">
                    <label for="img1">
                        <img class="preview" src="${img1}">
                    </label>
                    <input type="file" class="inputImage" id="img1" name="1" accept="image/*">
                    <span class="delete-image">&times;</span>
                </div>

                <div class="boardImg">
                    <label for="img2">
                        <img class="preview" src="${img2}">
                    </label>
                    <input type="file" class="inputImage" id="img2" name="2" accept="image/*">
                    <span class="delete-image">&times;</span>
                </div>

                <div class="boardImg">
                    <label for="img3">
                        <img class="preview" src="${img3}">
                    </label>
                    <input type="file" class="inputImage" id="img3" name="3" accept="image/*">
                    <span class="delete-image">&times;</span>
                </div>
                
                <div class="boardImg">
                    <label for="img4">
                        <img class="preview" src="${img4}">
                    </label>
                    <input type="file" class="inputImage" id="img4" name="4" accept="image/*">
                    <span class="delete-image">&times;</span>
                </div>
           
            </div>

            <!-- 내용 -->
            <div class="board-content">

                <!-- 
                    XSS 처리로 인해서 &lt; 과 같은 형태로 변한 문자들은
                    HTML 문서에 출력 될 때 &lt; 가 아닌 해석된 형태 "<" 로 출력이 된다.
                    -> 이 특징을 이용하면 별도로 XSS 처리를 해제하는 코드를 작성할 필요가 없다!

                    하지만 개행문자 <br> -> \n으로 변경하는 코드는 필요하다!
                -->
                <textarea name="boardContent">${detail.boardContent}</textarea>
            </div>

            <!-- 버튼 영역 -->
            <div class="board-btn-area">
                <button type="submit" id="writebtn">등록</button>

                <!-- insert 모드 -->
                <c:if test="${param.mode == 'insert'}">
                    <button type="button" id="goToListBtn">목록으로</button>
                </c:if>
                
                <!-- update 모드 -->
                <c:if test="${param.mode == 'update'}">
                    <button type="button" onclick="location.href='${header.referer}'">이전으로</button>                           
                </c:if>


            </div>


            <!-- 숨겨진 값(hidden) -->
            <!-- 동작 구분 -->
            <input type="hidden" name="mode" value="${param.mode}">

            <!-- 게시판 구분 -->
            <input type="hidden" name="type" value="${param.type}">

            <!-- 게시글 번호 -->
            <input type="hidden" name="no" value="${param.no}">
            
            <!-- 현재 페이지 -->
            <input type="hidden" name="cp" value="${param.cp}">
            
            <!-- 존재하던 이미지가 제거되었음을 기록하여 전달하는 input -->
            <!-- value에 제거된 이미지의 레벨을 기록 (X버튼 클릭 시)-->
            <!-- DELETE FROM BOARD_IMG 
                 WHERE BOARD_NO = 1000 
                 AND IMG_LEVEL IN (0,3,1,2) -->
            <input type="hidden" name="deleteList" id="deleteList" value="">

        </form>
        
    </main>

    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
    

    <script src="${contextPath}/resources/js/board/board.js"></script>
    <script src="${contextPath}/resources/js/board/boardWriteForm.js"></script>


</body>
</html>