<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%-- 문자열 관련 함수(메서드) 제공 JSTL (EL형식으로 작성) --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>My Page</title>

    <link rel="stylesheet" href="${contextPath}/resources/css/main-style.css">

    <link rel="stylesheet" href="${contextPath}/resources/css/myPage-style.css">


    <script src="https://kit.fontawesome.com/a2e8ca0ae3.js" crossorigin="anonymous"></script>
</head>
<body>
    <main>
       <jsp:include page="/WEB-INF/views/common/header.jsp"/>

        
        <!-- 마이페이지 - 내 정보 -->
        <section class="myPage-content">
            
			<!-- 사이드메뉴 include -->
			<!-- jsp 액션 태그 -->
			<jsp:include page="/WEB-INF/views/member/sideMenu.jsp"/>


            <!-- 오른쪽 마이페이지 주요 내용 부분 -->
            <section class="myPage-main">

                <h1 class="myPage-title">비밀번호 변경</h1>
                <span class="myPage-explanation">현재 비밀번호가 일치하는 경우 새 비밀번호로 변경할 수 있습니다.</span>

				<!-- 
				http://localhost:8080/community/member/myPage/changePw (GET)
				http://localhost:8080/community/member/myPage/changePw (POST) 
				-->
                <form action="changePw" method="POST" name="myPage-form" onsubmit="return changePwValidate()">

                    <div class="myPage-row">
                        <label>현재 비밀번호</label>
                        <input type="password" name="currentPw" id="currentPw" maxlength="30">              
                    </div>

                    <div class="myPage-row">
                        <label>새 비밀번호</label>
                        <input type="password" name="newPw" maxlength="30">              
                    </div>

                    <div class="myPage-row">
                        <label>새 비밀번호 확인</label>
                        <input type="password" name="newPwConfirm" maxlength="30">              
                    </div>

                    <button id="info-update-btn">변경하기</button>

                </form>

            </section>

        </section>

    </main>

	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

    <!-- myPage.js 추가 -->
    <script src="${contextPath}/resources/js/member/myPage.js"></script>

</body>
</html>