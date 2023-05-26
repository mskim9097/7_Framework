<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html>
<html>
<head>
    <title>Real-time Collaboration Example</title>
</head>
<body>
    <textarea id="content" rows="10" cols="50"></textarea>
    
    <script>
        const websocket = new WebSocket("ws://localhost:8080/websocket"); // WebSocket 서버 주소에 맞게 수정
        
        // WebSocket 연결 성공 시 실행되는 콜백 함수
        websocket.onopen = function() {
            console.log("WebSocket connection established.");
        };
        
        // WebSocket 메시지 수신 시 실행되는 콜백 함수
        websocket.onmessage = function(event) {
            const receivedMessage = event.data;
            const contentTextarea = document.getElementById("content");
            
            // 받은 메시지를 텍스트 영역에 표시
            contentTextarea.value = receivedMessage;
        };
        
        // 텍스트 영역 내용 변경 시 실행되는 함수
        function updateContent() {
            const contentTextarea = document.getElementById("content");
            const message = contentTextarea.value;
            
            // 변경된 내용을 WebSocket으로 전송
            websocket.send(message);
        }
        
        // 텍스트 영역 이벤트 리스너 등록
        const contentTextarea = document.getElementById("content");
        contentTextarea.addEventListener("input", updateContent);
    </script>
</body>
</html>