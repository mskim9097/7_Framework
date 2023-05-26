package edu.kh.comm.chat.model.vo;


import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatMessage {
	private int cmNo;
	private String message;
	private Date createDate;
	private int chatRoomNo;
	private int memberNo;
	private String memberEmail;
	private String memberNickname;
}
