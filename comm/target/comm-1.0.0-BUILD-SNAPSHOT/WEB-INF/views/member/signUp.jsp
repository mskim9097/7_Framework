<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

 <!DOCTYPE html>
 <html lang="en">
 <head>
     <meta charset="UTF-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <title>KH 커뮤니티</title>
 
     <link rel="stylesheet" href="../resources/css/main-style.css">
     
     <link rel="stylesheet" href="../resources/css/signUp-style.css">
 
     <script src="https://kit.fontawesome.com/a2e8ca0ae3.js" crossorigin="anonymous"></script>
 </head>
 <body>
     <main>
        <!-- hedaer include -->
        <jsp:include page="/WEB-INF/views/common/header.jsp" />


         <!-- 회원가입  -->
         <section class="signUp-content">
 
            <!-- 회원가입 화면 전환 주소(GET)와 같은 주소로 
                실제 회원가입을 요청(POST)
                -> 요청 주소가 같아도 데이터 전달 방식이 다르면 중복 허용
            -->

            <!-- 
                절대경로 : /community/member/signUp
                상대경로 : signUp
             -->

            <form action="signUp" method="POST" name="signUp-form" onsubmit="return signUpValidate()">
 
                 <label for="memberEmail">
                     <span class="required">*</span> 아이디(이메일)
                 </label>
                 
                 <div class="signUp-input-area">
                     <input type="text" id="memberEmail" name="memberEmail"
                             placeholder="아이디(이메일)" maxlength="30"
                             autocomplete="off" required>
 
                     <!-- autocomplete="off" : 자동완성 미사용 -->
                     <!-- required : 필수 작성 input 태그 -->
                     
                     <!-- 자바스크립로 코드 추가 예정 -->
                     <button type="button" id="sendBtn">인증번호 받기</button>
                 </div>
 
                 <span class="signUp-message" id="emailMessage">메일을 받을 수 있는 이메일을 입력해주세요.</span>
 
 
 
                 <label for="emailCheck">
                     <span class="required">*</span> 인증번호
                 </label>
                 
                 <div class="signUp-input-area">
                     <!-- cNumber -->
                     <input type="text" id="cNumber"  
                             placeholder="인증번호 입력" maxlength="6"
                             autocomplete="off">
 
                     <button type="button" id="cBtn">인증하기</button>
                 </div>
 
                 <!-- 5:00 타이머 / 인증되었습니다(녹색) / 인증 시간이 만료되었습니다.(빨간색) -->
                 <span class="signUp-message" id="cMessage" ></span>
 
 
 
 
                 <label for="memberPw">
                     <span class="required">*</span> 비밀번호
                 </label>
                 
                 <div class="signUp-input-area">
                     <input type="text" id="memberPw" name="memberPw"
                             placeholder="비밀번호" maxlength="30">
                 </div>
 
                 <div class="signUp-input-area">
                     <input type="text" id="memberPwConfirm"
                             placeholder="비밀번호 확인" maxlength="30">
                 </div>
 
                 <span class="signUp-message" id="pwMessage">영어, 숫자, 특수문자(!,@,#,-,_) 6~30글자 사이로 작성해주세요.</span>
 
 
 
 
                 <label for="memberNickname">
                     <span class="required">*</span> 닉네임
                 </label>
                 
                 <div class="signUp-input-area">
                     <input type="text" id="memberNickname" name="memberNickname"
                             placeholder="닉네임" maxlength="10">
                 </div>
 
                 <span class="signUp-message" id="nicknameMessage">영어/숫자/한글 2~10글자 사이로 작성해주세요.</span>
 
 
 
                 <label for="memberTel">
                     <span class="required">*</span> 전화번호
                 </label>
                 
                 <div class="signUp-input-area">
                     <input type="text" id="memberTel" name="memberTel"
                             placeholder="(- 없이 숫자만 입력)" maxlength="11">
                 </div>
 
                 <span class="signUp-message" id="telMessage">전화번호를 입력해주세요.(- 제외)</span>
              
              
              
                 <label for="memberAddress">
                     주소
                 </label>
                 
                 <div class="signUp-input-area">
                     <input type="text" id="memberAddress" name="memberAddress"
                             placeholder="우편번호" maxlength="6">
                     
                     <button type="button">검색</button>
                 </div>
 
                 <div class="signUp-input-area">
                     <input type="text" name="memberAddress" placeholder="도로명주소">
                 </div>
 
                 <div class="signUp-input-area">
                     <input type="text" name="memberAddress" placeholder="상세주소">
                 </div>
 
                 <button type="submit" id="signUp-btn">가입하기</button>
 
             </form>
             
         </section>
 
 
     </main>
 
    <!-- footer include -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />


    <!-- jQuery 라이브러리 추가(CDN) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

    <!-- signUp.js 연결 -->
    <script src="${contextPath}/resources/js/member/signUp.js"></script>

 </body>
 </html>