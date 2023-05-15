package edu.kh.comm.chat.model.service;

import java.util.List;

import edu.kh.comm.chat.model.vo.ChatMessage;
import edu.kh.comm.chat.model.vo.ChatRoom;
import edu.kh.comm.chat.model.vo.ChatRoomJoin;

public interface ChatService {

	/** 채팅 목록 조회
	 * @return chatRoomList
	 */
	List<ChatRoom> selectChatRoomList();

	/** 채팅방 만들기
	 * @param room
	 * @return chatRoomNo
	 */
	int openChatRoom(ChatRoom room);

	
	/** 채팅방 입장 + 채팅 메세지 목록 조회
	 * @param join
	 * @return list
	 */
	List<ChatMessage> joinChatRoom(ChatRoomJoin join);

	
	/** 채팅 메세지 삽입
	 * @param cm
	 * @return result
	 */
	int insertMessage(ChatMessage cm);

	/** 채팅방 나가기
	 * @param join
	 * @return result
	 */
	int exitChatRoom(ChatRoomJoin join);

}
