package edu.kh.comm.chat.model.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebsocketHandler extends TextWebSocketHandler {
	
	/*
	 * 
	 *  WebSocketHandler 인터페이스 : 웹소켓을 위한 메소드를 지원하는 인터페이스
	     
    	-> WebSocketHandler 인터페이스를 상속받은 클래스를 이용해 웹소켓 기능을 구현
    
    
    	WebSocketHandler 주요 메소드
            
        void handlerMessage(WebSocketSession session, WebSocketMessage message)
        - 클라이언트로부터 메세지가 도착하면 실행
        
        void afterConnectionEstablished(WebSocketSession session)
        - 클라이언트와 연결이 완료되고, 통신할 준비가 되면 실행

        void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
        - 클라이언트와 연결이 종료되면 실행

        void handleTransportError(WebSocketSession session, Throwable exception)
        - 메세지 전송중 에러가 발생하면 실행 
    
    
    ----------------------------------------------------------------------------
    
    	TextWebSocketHandler :  WebSocketHandler 인터페이스를 상속받아 구현한 텍스트 메세지 전용 웹소켓 핸들러 클래스
	 
     	handlerTextMessage(WebSocketSession session, TextMessage message)
        - 클라이언트로부터 텍스트 메세지를 받았을때 실행
     *
     *
     *
	 */
	
	// 클라이언트와 연결이 완료되고, 통신할 준비가 되면 수행
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// WebSocketSession : 웹소켓에 접속/요청한 클라이언트의 세션
		System.out.println(session.getId() + " 연결됨");
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {}
	
	
	

	
	
}
