package edu.kh.comm.board.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.comm.board.model.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;
	
	
	// 게시글 목록 조회
	
	// @PathVariable("value") : URL 경로에 포함되어 있는 값을 변수로 사용할 수 있게하는 역할
	// -> 자동으로 request scope에 등록됨 -> jsp에서 ${value} EL 작성 가능
	
	// PathVariable : 요청 자원을 식별하는 경우
	// QueryString : 정렬, 검색 등의 필터링 옵션
	
	@GetMapping("/list/{boardCode}")
	public String boardList(@PathVariable("boardCode") int boardCode,
							@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
							Model model
							/*@RequestParam Map<String, Object> paramMap*/ ) {
		
		// 게시글 목록 조회 서비스 호출
		// 1) 게시판 이름 조회 -> 인터셉터로 application에 올려둔 boardTypeList 쓸 수 있을듯?
		// 2) 페이지네이션 객체 생성(listCount)
		// 3) 게시글 목록 조회
		
		Map<String, Object> map = null;
		
		map = service.selectBoardList(cp, boardCode);
		
		model.addAttribute("map", map);
		
		return "board/boardList";
	}
}
