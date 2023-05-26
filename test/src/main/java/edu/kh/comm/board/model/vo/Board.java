package edu.kh.comm.board.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Board {
	private int boardNo;
	private String boardTitle;
	private String memberNickname;
	private String createDate;
	private int readCount;
	private String thumbnail;
}