package edu.kh.comm.chat.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatRoom {
	private int chatRoomNo;
	private String title;
	private String status;
	private int memberNo;
	private String memberNickname;
	private int cnt; // 참여자 수
}
