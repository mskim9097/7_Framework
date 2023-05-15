package edu.kh.comm.chat.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.comm.chat.model.dao.ChatDAO;
import edu.kh.comm.chat.model.vo.ChatMessage;
import edu.kh.comm.chat.model.vo.ChatRoom;
import edu.kh.comm.chat.model.vo.ChatRoomJoin;
import edu.kh.comm.common.Util;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	private ChatDAO dao;

	// 채팅 목록 조회
	@Override
	public List<ChatRoom> selectChatRoomList() {
		return dao.selectChatRoomList();
	}

	// 채팅방 만들기
	@Override
	public int openChatRoom(ChatRoom room) {
		return dao.openChatRoom(room);
	}

	
	// 채팅방 입장 + 내용 얻어오기
	@Override
	public List<ChatMessage> joinChatRoom(ChatRoomJoin join) {

		// 현재 회원이 해당 채팅방에 참여하고 있는지 확인
		int result = dao.joinCheck(join);
		
		if(result == 0) { // 참여하고 있지 않은 경우 참여
			dao.joinChatRoom(join);
		}
		
		// 채팅 메세지 목록 조회
		return dao.selectChatMessage(join.getChatRoomNo());
	}

	
	// 채팅 메세지 삽입
	@Override
	public int insertMessage(ChatMessage cm) {
		
//		cm.setMessage(Util.XSSHandling(cm.getMessage()));
		cm.setMessage(Util.newLineHandling(cm.getMessage()));
		
		return dao.insertMessage(cm);
	}

	
	// 채팅방 나가기
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int exitChatRoom(ChatRoomJoin join) {
		
		// 채팅방 나가기
		int result = dao.exitChatRoom(join);
		
		if(result > 0) { // 채팅방 나가기 성공 시
			
			// 현재 방에 몇명이 있나 확인
			int cnt = dao.countChatRoomMember(join.getChatRoomNo());
			
			// 0명일 경우 방 닫기
			if(cnt == 0) {
				result = dao.closeChatRoom(join.getChatRoomNo());
			}
			
		}
		
		return result;
	}
	
}
