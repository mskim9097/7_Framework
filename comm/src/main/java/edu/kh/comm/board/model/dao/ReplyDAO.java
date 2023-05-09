package edu.kh.comm.board.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.comm.board.model.vo.Reply;

@Repository
public class ReplyDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	/** 댓글 목록 조회 DAO
	 * @param boardNo
	 * @return rList
	 */
	public List<Reply> selectReply(int boardNo) {

		return sqlSession.selectList("replyMapper.selectReply", boardNo);
	}
}
