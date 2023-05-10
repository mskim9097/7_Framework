package edu.kh.comm.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.comm.board.model.vo.Reply;

public interface ReplyService {

	/** 댓글 목록 조회 서비스
	 * @param boardNo
	 * @return rList
	 */
	List<Reply> selectReplyList(int boardNo);

	/** 댓글 등록 서비스
	 * @param reply
	 * @return result
	 */
	int insertReply(Reply reply);

	/** 댓글 수정 서비스
	 * @param map
	 * @return result
	 */
	int updateReply(Map<String, Object> map);

	/** 댓글 삭제 서비스
	 * @param map
	 * @return result
	 */
	int deleteReply(Map<String, Object> map);

}
