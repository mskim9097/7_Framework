package edu.kh.comm.board.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.comm.board.model.service.BoardService;
import edu.kh.comm.board.model.vo.BoardDetail;
import edu.kh.comm.common.Util;
import edu.kh.comm.member.model.vo.Member;

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
							Model model,
							@RequestParam Map<String, Object> paramMap ) {
										// key, query, cp ?
		
		// 게시글 목록 조회 서비스 호출
		// 1) 게시판 이름 조회 -> 인터셉터로 application에 올려둔 boardTypeList 쓸 수 있을듯?
		// 2) 페이지네이션 객체 생성(listCount)
		// 3) 게시글 목록 조회
		
		Map<String, Object> map = null;
		
		if(paramMap.get("key") == null) { // 검색이 아닌 경우
			map = service.selectBoardList(cp, boardCode);
			
		} else { // 검색인 경우
			
			// 검색에 필요한 데이터를 paramMap에 모두 담아서 서비스 호출
			paramMap.put("cp", cp);
			paramMap.put("boardCode", boardCode);
			
			map = service.searchBoardList(paramMap);			
		}
				
				
		model.addAttribute("map", map);
		
		return "board/boardList";
	}
	
	
	// 게시글 상세 조회
	@GetMapping("/detail/{boardCode}/{boardNo}")
	public String boardDetail(@PathVariable("boardCode") int boardCode,
								@PathVariable("boardNo") int boardNo,
								@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
								Model model,
								HttpSession session,
								HttpServletRequest req, HttpServletResponse resp) {
		
		// 게시글 상세 조회 서비스 호출
		BoardDetail detail = service.selectBoardDetail(boardNo);
		
		
		// 쿠키를 이용한 조회수 중복 증가 방지 코드 + 본인의 글은 조회수 증가 X
		
		// @ModelAttribute("loginMember") Member loginMember (사용 불가)
		// -> @ModelAttribute는 별도의 required 속성이 없어서 무조건 필수!!!
		//		-> 세션에 loginMember가 없으면 예외 발생
		
		// 해결방법 : HttpSession을 이용
		// -> session.getAttribute("loginMember")
		
		
		
		// 상세 조회 성공 시		
		if(detail != null) {
			
			// 댓글 목록 조회
						
			// 세션이 있는지 없는지
			// 세션이 있으면 memberNo 세팅
			Member loginMember = (Member)session.getAttribute("loginMember");
			
			int memberNo = 0;
			if(loginMember != null) {
				memberNo = loginMember.getMemberNo();
			}
			
			// 글쓴이와 현재 클라이언트가 같은지 아닌지
			if(memberNo != detail.getMemberNo()) {
				// 같지 않으면 -> 조회수 증가
				
				// 쿠키가 있는지 없는지
				// 쿠키 이름이 "readBoardNo" 있는지 ?
				// 없다면 만들어라
				// 있다면 쿠키에 저장된 값 뒤쪽에 현재 조회된 게시글 번호를 추가
				// -> 단, 기존 쿠기값에 중복되는 번호 없어야함.
					
				Cookie cookie = null; // 기존에 존재하던 쿠키를 저장하는 변수
				
				Cookie[] cArr = req.getCookies();
				
				if(cArr != null && cArr.length > 0) { // 얻어온 쿠키가 있을 경우
					for(Cookie c : cArr) {
						
						// 얻어온 쿠키중 이름이 "readBoardNo"가 있으면 얻어오기
						if(c.getName().equals("readBoardNo")) {
							cookie = c;
						}
					}
				}
				
				int result = 0;
				
				if(cookie == null) { // 기존에 "readBoardNo" 이름의 쿠키가 없던 경우
					
					cookie = new Cookie("readBoardNo", boardNo+"");					
					result = service.updateReadCount(boardNo); // 조회 수 증가 서비스 호출
					
				} else { // 기존에 "readBoardNo" 이름의 쿠키가 있던 경우
					// "readBoardNo"	:	"1/2/3/5/30/500" + / + boardNo
					// -> 쿠키에 저장된 값 뒤쪽에 현재 조회된 게시글 번호 추가
					
					String[] temp = cookie.getValue().split("/");
					
					List<String> list = Arrays.asList(temp); // 배열 -> List 변환
					
					if(list.indexOf(boardNo+"") == -1) { // 기존 값에 같은 글번호가 없다면 추가
						cookie.setValue(cookie.getValue() + "/" + boardNo);
						result = service.updateReadCount(boardNo); // 조회 수 증가 서비스 호출
						
					}
					
				}
				
				// 이미 조회된 데이터 DB와 동기화
				if(result > 0) {
					
					detail.setReadCount(detail.getReadCount() + 1);
					
					cookie.setPath(req.getContextPath());
					cookie.setMaxAge(60 * 60 * 1);// + 쿠키 maxAge 1시간
					resp.addCookie(cookie);
				}
				
			}
			
		}
					
		model.addAttribute("detail", detail);
		
		return "board/boardDetail";
	}
	
	
	
	// 게시글 작성 화면 전환
	@GetMapping("/write/{boardCode}")
	public String boardWriteForm(@PathVariable("boardCode") int boardCode,
								String mode,
								@RequestParam(value="no", required=false, defaultValue="0") int boardNo,
								Model model) {
		
		if(mode.equals("update")) {
			// 게시글 상세 조회 서비스 호출(boardNo)
			BoardDetail detail = service.selectBoardDetail(boardNo);
			
			// -> 개행문자가 <br>로 되어있는 상태 -> textarea 출력 예정이기 때문에 \n 으로 변경
			
			detail.setBoardContent(Util.newLineClear(detail.getBoardContent()));
			
			model.addAttribute("detail", detail);
			
		}
		
		return "board/boardWriteForm";
	}
	
}
