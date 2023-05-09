package edu.kh.comm.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kh.comm.board.model.dao.ReplyDAO;
import edu.kh.comm.board.model.vo.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	private ReplyDAO dao;

	// 댓글 목록 조회 서비스 구현
	@Override
	public List<Reply> selectReply(int boardNo) {

		return dao.selectReply(boardNo);
	}
	
	
}
