// 상세조회, 게시글 작성 - 목록으로 버튼

// 즉시 실행 함수
(function(){
    const goToListBtn = document.getElementById("goToListBtn");

    if(goToListBtn != null){ // 목록으로 버튼이 화면에 있을 때만 이벤트 추가

        goToListBtn.addEventListener("click", function(){

            // location 객체(BOM)

            const pathname = location.pathname; // 주소상에서 요청 경로 반환
            //  /community/board/detail

            // 문자열.substring(시작,끝) : 시작 이상 끝 미만 인덱스 까지 문자열 자르기

            // 문자열.indexOf("검색 문자열", 시작 인덱스)
            // : 문자열에서 "검색 문자열"의 위치(인덱스)를 찾아서 반환
            //  단, 시작 인덱스 이후 부터 검색

            // 이동할 주소 저장
            let url = pathname.substring(0,  pathname.indexOf("/", 1)); // 
            //   /community

            url += "/board/list/"+boardCode+"?";  //   /board/list/1?cp=1


            // URL 내장 객체 : 주소 관련 정보를 나타내는 객체
            // location.href : 현재 페이지 주소 + 쿼리스트링
            // URL.searchParams : 쿼리 스트링만 별도 객체로 반환

            // http://localhost:8080/community/board/detail?no=249&cp=6&type=1&key=c&query=9
            const params = new URL(location.href).searchParams;

            let cp;

            if(params.get("cp") != null){ // 쿼리스트링에 cp가 있을 경우
                cp =  "cp=" + params.get("cp");     
            }else{
                cp = "cp=1";
            }

            // 조립
            //   /commy/board/list/1?cp=1
            url += cp;


            // 검색 key, query가 존재하는 경우 url에 추가
            if(params.get("key") != null){
                const key = "&key=" + params.get("key");
                const query = "&query=" + params.get("query");

                url += key + query; // url 뒤에 붙이기
            }


            // location.href = "주소";  -> 해당 주소로 이동
            location.href = url; 

        });
    }

})();



// 즉시 실행 함수
(function(){
    const thumbnail = document.getElementsByClassName("list-thumbnail");

    if(thumbnail.length > 0){ // 목록에 썸네일 이미지가 있을 경우에만 이벤트 추가
      
        const modal = document.querySelector('.modal');
        const modalImage = document.getElementById("modal-image");
        const modalClose = document.getElementById("modal-close");

        for(let th of thumbnail){
            th.addEventListener("click", function(){
                modalImage.setAttribute("src", th.getAttribute("src") );
               
                /* on/off 스위치 */
                // classList.toggle("클래스명") : 클래스가 없으면 추가(add) 
                //                                클래스가 있으면 제거(remove)
                
                modal.classList.toggle('show'); // add
            });
        }

        // X버튼
        modalClose.addEventListener("click", function(){
            
            modal.classList.toggle('hide'); // hide 클래스 추가

            setTimeout(function(){ // 0.45초 후 동작
                modal.classList.toggle('hide'); // hide 클래스 제거

                modal.classList.toggle('show'); // remove
            }, 450);
        });


    }

})();


// 즉시 실행 함수 : 성능up, 변수명 중복 X
(function(){
    const deleteBtn = document.getElementById("deleteBtn"); // 존재하지 않으면 null

    if(deleteBtn != null){ // 버튼이 화면에 존재할 때
        deleteBtn.addEventListener("click", function(){
            // 현재 : /board/detail/{boardCode}/{boardNo}
            // 목표 : /board/delete/{boardCode}/{boardNo}
      
            let url = contextPath + "/board/delete/" + boardCode + "/" +  boardNo;
            // 삭제 성공 -> 해당 게시판 목록 조회 1페이지로 리다이렉트
            // 삭제 실패 -> 요청 이전 페이지(referer)로 리다이렉트

            // UPDATE BOARD SET
            // BOARD_ST = 'Y'
            // WHERE BOARD_NO = #{boardNo}


            if( confirm("정말로 삭제 하시겠습니까?") ){
                location.href = url; // get방식으로 url에 요청
            }

        });
    }

})();


// 검색창에 이전 검색기록 반영하기
(function(){
    const select = document.getElementById("search-key");

    // const option = select.children;
    const option = document.querySelectorAll("#search-key > option");

    const input = document.getElementById("search-query");

    if(select != null){ // 검색창이 화면이 존재할 때에만 코드 적용

        // 현재 주소에서 쿼리스트링(파라미터) 얻어오기
        const params = new URL(location.href).searchParams;

        // 얻어온 파라미터 중 key, query만 변수에 저장
        const key = params.get("key");
        const query = params.get("query");

        // input에 query 값 대입
        input.value = query;

        // option을 반복 접근해서 value와 key가 같으면 selected 속성 추가
        for(let op of option){
            if(op.value == key ){
                op.selected = true;
            }
        }
    }

})();



// 검색 유효성 검사(검색어를 입력 했는지 확인)
function searchValidate(){

    const query = document.getElementById("search-query");

    if(query.value.trim().length == 0){ // 미작성
        query.value = ""; // 빈칸
        query.focus();

        return false;
    }

    return true;
}

