package edu.kh.comm.chat.model.websocket;

import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import edu.kh.comm.chat.model.service.ChatService;
import edu.kh.comm.chat.model.vo.ChatMessage;

public class ChatWebsocketHandler extends TextWebSocketHandler {
	
	@Autowired
	private ChatService service;
	
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
	
	// Set<WebSocketSession> 을 왜 만들었는가??
	
	// - WebSocketSession == 웹소켓에 연결된 클라이언트 세션
	//	-> 세션을 통해서 누가 연결했는지 알 수 있다!
	
	// - WebSocketSession 모아둔다?
	// == 현재 웹소켓에 연결되어있는 모든 클라이언트를 알 수 있다.
	//	-> Set을 분석해서 원하는 클라이언트를 찾아서 메시지를 전달할 수 있다!
	
	private Set<WebSocketSession> sessions
		= Collections.synchronizedSet(new HashSet<WebSocketSession>());
	
	// synchronizedSet : 동기화된 Set 반환
	// -> 멀티스레드 환경에서 하나의 컬렉션 요소에 여러 스레드가 접근하면 충돌이 발생할 수 있으므로
	// 동기화(충돌이 안나도록 줄세움)를 진행
	
	
	// 클라이언트와 연결이 완료되고, 통신할 준비가 되면 수행
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		// WebSocketSession : 웹소켓에 접속/요청한 클라이언트의 세션
		System.out.println(session.getId() + " 연결됨");		
		
		sessions.add(session);
		// WebSocketSession을 Set에 추가
	}

	// 클라이언트로부터 텍스트 메세지를 전달 받았을 때 수행
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		// TextMessage : 웹소켓을 이용해 텍스트로 전달된 메시지가 담겨있는 객체
		
		// payload : 전송되는 데이터
		
		// message.getPayload() : JSON
		
		System.out.println("전달된 메시지 : " + message.getPayload());
		
		// Jackson 라이브러리 : Java에서 JSON을 다루기 위한 라이브러리		
		
		// Jackson-databind 라이브러리 :
		// ObjectMapper 객체를 이용해서
		// JSON 데이터를 특정 VO 필드에 맞게 자동 매핑
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
		
		// 시간세팅
		chatMessage.setCreateDate(new Date(System.currentTimeMillis()));
		
		System.out.println(chatMessage);
		
		
		// 채팅 메세지 DB삽입
		
		int result = service.insertMessage(chatMessage);
		
		if(result > 0) {
			
			// 같은 방에 접속 중인 클라이언트에게만 메세지 보내기
			// -> Set<WebSocketSession>에서 같은방 클라이언트만 골라내기
			
			for(WebSocketSession s : sessions) {
				
				// WebSocketSession == HttpSession(로그인정보, 채팅방번호)을 가로챈 것
				int chatRoomNo = (Integer)s.getAttributes().get("chatRoomNo");
				
				// WebSocketSession에 담겨있는 채팅방번호와
				// 메시지에 담겨있는 채팅방 번호가 같을경우
				// 같은방 클라이언트다.				
				if(chatRoomNo == chatMessage.getChatRoomNo())  {
					
					// 같은방 클라이언트에게 JSON형식 메시지를 보냄
					s.sendMessage(new TextMessage(new Gson().toJson(chatMessage)));
					
				}
				
			}
		}
		
	}

	
	// 클라이언트와 연결이 종료되는 수행
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		sessions.remove(session);
		// 웹소켓 연결이 종료되는 경우
		// 종료된 WebSocketSession을 Set에서 제거
		
	}
	
	
	

	
	
}
