package edu.kh.comm.board.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.comm.board.model.vo.BoardDetail;
import edu.kh.comm.board.model.vo.BoardType;

public interface BoardService {

	/** 게시판 코드, 이름 조회 서비스
	 * @return boardTypeList
	 */
	List<BoardType> selectBoardType();

	/** 게시글 목록 조회 서비스
	 * @param cp
	 * @param boardCode
	 * @return map
	 */
	Map<String, Object> selectBoardList(int cp, int boardCode);

	/** 게시글 상세 조회 서비스
	 * @param boardNo
	 * @return detail
	 */
	BoardDetail selectBoardDetail(int boardNo);

	/** 조회수 증가 서비스
	 * @param boardNo
	 * @return result
	 */
	int updateReadCount(int boardNo);

	/** 검색 게시글 목록 조회 서비스
	 * @param paramMap
	 * @return map
	 */
	Map<String, Object> searchBoardList(Map<String, Object> paramMap);

	/** 게시글 삽입 + 이미지 삽입 서비스
	 * @param detail
	 * @param imageList
	 * @param webPath
	 * @param folderPath
	 * @exception IOException
	 * @return boardNo
	 */
	int insertBoard(BoardDetail detail, List<MultipartFile> imageList, String webPath, String folderPath) throws IOException;

	/** 게시글 수정 서비스
	 * @param detail
	 * @param imageList
	 * @param webPath
	 * @param folderPath
	 * @param deleteList
	 * @return result
	 */
	int updateBoard(BoardDetail detail, List<MultipartFile> imageList, String webPath, String folderPath,
			String deleteList) throws IOException;
	
	/** 게시글 삭제 서비스
	 * @param boardNo
	 * @return result
	 */
	int deleteBoard(int boardNo);

	/** BOARD_IMG 이미지 목록 조회
	 * @return dbList
	 */
	List<String> selectDBList();

}
