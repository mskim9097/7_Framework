<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<header>

	<!-- 클릭 시 메인페이지로 이동하는 로고 -->
	<section>
	    <a href="${contextPath}">
	    
	    	<!-- header를 별도의 jsp로 분리한 경우
	    		상대경로로 작성된 이미지의 경로가 일치하지 않게됨
	    		
	    		* 지금처럼 분리시켜둔 jsp에 경로를 지정하는 경우
	    		  상대경로는 문제가 발생할 가능성이 높음!!
	    		  -> 절대 경로 사용이 바람직함
	    	 -->
	  
	  		<%--
	  		/community
	   		<%=  request.getContextPath() %>
	    	${pageContext.servletContext.contextPath }
	    	
	    	위에 작성된 내용은 모두 같은 결과이나 하자가 조금씩 있음 ...
	    	-> 모든 주소 요청 시 동작하는 EncodingFilter에서
	    		application scope에 최상위 주소를 간단히 부를 수 있는 형태로 저장
	    	
	    	 --%>
	     
	        <img src="${contextPath}/resources/images/logo.jpg" id="home-logo">
	      	
	    </a>
	</section>
	
	<section>
	    <article class="search-area">
	        <!-- form 내부 input 태그 값을 서버 또는 페이지로 전달 -->
	        <form action="#" name="search-form">
	
	            <!-- fieldset: form 내부에서 input을 종류별로 묶는 용도로 많이 사용 -->
	            <fieldset>
	
	                <!-- autocomplete="off" : HTML 기본 자동완성 사용 X -->
	                <input type="search" id="query" name="query" 
	                    autocomplete="off" placeholder="검색어를 입력해주세요.">
	
	                <!-- 검색 버튼 -->
	                    <button type="submit" id="search-btn" class="fa-solid fa-magnifying-glass"></button>  
	                </fieldset>
	            </form>
	        </article>
	    </section>
	
	    <section></section>
	
</header>
	
<!-- 
	쿼리스트링 : 주소에 담겨져서 전달되는 파라미터를 나타내는 문자열
	주소?name속성=값&name속성=값
	/member/login  ?memberEmail=user01&memberPw=1234
 -->

<nav>
    <ul>
        <li><a href="${contextPath}/board/list?type=1">공지사항</a></li>

        <li><a href="${contextPath}/board/list?type=2">자유 게시판</a></li>

        <li><a href="${contextPath}/board/list?type=3">질문 게시판</a></li>


        <li><a href="#">FAQ</a></li>
        <li><a href="#">1:1문의</a></li>
    </ul>
</nav>