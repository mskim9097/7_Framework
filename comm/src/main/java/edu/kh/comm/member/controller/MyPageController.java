package edu.kh.comm.member.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.comm.member.model.service.MemberService;
import edu.kh.comm.member.model.service.MyPageService;
import edu.kh.comm.member.model.vo.Member;

@Controller
@RequestMapping("/member/myPage")
@SessionAttributes({"loginMember"}) //

public class MyPageController {
	private Logger logger = LoggerFactory.getLogger(MyPageController.class);
	
	@Autowired
	private MyPageService service;
	// 닉네임중복검사용 service
	
	@Autowired
	private MemberService memberService;
	
	// 프로필 화면 전환
	@GetMapping("/profile")
	public String myPageProfile() {
		
		return "/member/myPage-profile";
	}
	
	// 내정보 수정 화면 전환
	@GetMapping("/info")
	public String myPageInfo() {
		
		return "/member/myPage-info";
	}
	
	// 비밀번호 변경 화면 전환
	@GetMapping("/changePw")
	public String myPageChangePw() {
		
		return "/member/myPage-changePw";
	}
	
	// 회원 탈퇴 화면 전환
	@GetMapping("/secession")
	public String myPageSecession() {		
		
		return "/member/myPage-secession";
	}
	
	@PostMapping("/changePw")
	// 비밀변호 변경
	public String changePw(@ModelAttribute("loginMember") Member loginMember, 
						@RequestParam Map<String,Object> paramMap, 
						RedirectAttributes ra,
						SessionStatus status) {
		
		// 로그인된 회원의 번호를 paramMap 추가
		paramMap.put("memberNo", loginMember.getMemberNo());
		
		// 비밀번호 변경 서비스 호출
		int result = service.changePw(paramMap);
		
		if(result > 0) {
			
			logger.info("비밀번호 변경 기능 수행됨");
			ra.addFlashAttribute("message", "비밀번호가 변경되었습니다. 다시 로그인해주세요");
			
			status.setComplete(); // 세션이 할일이 완료됨 -> 없앰
			
		} else {
			ra.addFlashAttribute("message", "비밀번호가 일치하지 않습니다.");
			return "redirect:changePw";
		}
		
		
		return "redirect:/";
	}
	
	
	@PostMapping("/secession")
	// 회원 탈퇴			session 의 회원정보 + 입력받은 파라미터(비밀번호)
	public String secession(@ModelAttribute("loginMember") Member loginMember, 
						RedirectAttributes ra,
						SessionStatus status,
						HttpServletRequest req,
						HttpServletResponse resp) {
		
		// 회원 탈퇴 서비스 호출
		int result = service.secession(loginMember);
		
		if(result > 0) {
			
			logger.info("회원탈퇴 기능 수행됨");
			ra.addFlashAttribute("message", "회원탈퇴가 완료되었습니다.");
			
			// 세션이 할일이 완료됨 -> 없앰
			status.setComplete();
			//쿠키없애기
			Cookie cookie = new Cookie("saveId", "");
			cookie.setMaxAge(0);
			cookie.setPath(req.getContextPath());
			resp.addCookie(cookie);
						
		} else {
			ra.addFlashAttribute("message", "비밀번호가 일치하지 않습니다.");
			return "redirect:secession";
		}
		
		return "redirect:/";
	}
	
	// 회원 정보 수정
	@PostMapping("/info")
	public String updateInfo(@ModelAttribute("loginMember") Member loginMember,
							@RequestParam Map<String, Object> paramMap, // 요청시 전달된 파라미터를 구분하지 않고 모두 Map에 담아서 얻어옴
							String[] updateAddress,
							RedirectAttributes ra ) { 
		
		// 필요한 값
		// - 닉네임
		// - 전화번호
		// - 주소 ( String[] 로 얻어와서 String.join()을 이용해서 문자열로 변경 )
		// - 회원 번호(Session -> 로그인한 회원 정보를 통해서 얻어옴)
		//		-> @ModelAttribute, @SessionAttributes 필요
		
		// @SessionAttributes 의 역할 2가지
		// 1) Model에 세팅 데이터의 key값을 @SessionAttributes에 작성하면
		//	해당 key값과 같은 Model에 세팅된 데이터를 request - > session scope로 이동
		
		// 2) 기존에 session scope로 이동시킨 값을 얻어오는 역할
		// @ModelAttribute("loginMember") Member loginMember
		//					[session key]
		// --> @SessionAttributes를 통해 session scope에 등록된 값을 얻어와
		//		오른쪽에 작성된 Member loginMember 변수에 대입
		//		단, @SessionAttributes({"loginMember"}) 가 클래스 위에 작성되어있어야 가능
		
		// *** 매개변수를 이용해서 session, 파라미터 데이터를 동시에 얻어올 때 문제점 ***
		
		// session에서 객체를 얻어오기 위해 매개변수에 작성한 상태
		// -> @ModelAttribute("loginMember") Member loginMember
		
		// 파라미터의 name 속성값이 매개변수에 작성된 객체의 필드와 일치하면
		// -> name="memberNickname"
		// session 에서 얻어온 객체의 필드에 덮어쓰기가 된다!
		
		// [해결 방법] 파라미터의 name 속성을 변경해서 얻어오면 문제해결!
		// (필드명 겹쳐서 문제니깐 겹치지 않게 하자)
		
		// 닉네임 중복검사
		
		int count = 0;
		
		if(loginMember.getMemberNickname().equals((String)paramMap.get("updateNickname"))) {
			count = 0;
		} else {
			count = memberService.nicknameDupCheck((String)paramMap.get("updateNickname"));
		}
		
		
		if(count > 0) {
			ra.addFlashAttribute("message", "중복된 닉네임입니다.");
		} else {
			
			// 파라미터를 저장한 paramMap에 회원번호 , 주소를 추가
			String memberAddress = String.join(",,", updateAddress);
			
			if(memberAddress.equals(",,,,")) memberAddress = null;
			
			paramMap.put("memberNo", loginMember.getMemberNo());
			paramMap.put("memberAddress", memberAddress);
			
			int result = service.updateInfo(paramMap);
			
			if(result > 0) {
				logger.info("회원정보 수정 기능 수행됨");
				ra.addFlashAttribute("message", "회원 정보가 수정되었습니다.");
				
				// DB - Session의 회원 정보 동기화
				loginMember.setMemberNickname((String)paramMap.get("updateNickname"));
				loginMember.setMemberTel((String)paramMap.get("updateTel"));
				loginMember.setMemberAddress((String)paramMap.get("memberAddress"));
			} else {
				ra.addFlashAttribute("message", "회원 정보 수정 실패");
			}
		}		
		
		return "redirect:info";
		
	}
	
	@PostMapping("/profile")
	public String updateProfile(@ModelAttribute("loginMember") Member loginMember,
								@RequestParam("uploadImage") MultipartFile uploadImage, /*업로드 파일*/
								@RequestParam Map<String, Object> map,
								HttpServletRequest req, /* 파일 저장 경로 탐색용 */
								RedirectAttributes ra) throws IOException {
		
		// 경로 작성하기
		
		// 1) 웹 접근 경로 ( /comm/resources/images/memberProfile/ )
		String webPath = "/resources/images/memberProfile/";
		
		// 2) 서버 저장 폴더 경로
		String folderPath = req.getSession().getServletContext().getRealPath(webPath);
		// C:\workspace\7_Framework\comm\src\main\webapp\resources\images\memberProfile
		// Users\mskim\workspace\7_Framework\comm\src\main\webapp\resources\images\memberProfile
		
		
		// 경로 2개, 이미지, delete, 회원번호 map 담기
		map.put("webPath", webPath);
		map.put("folderPath", folderPath);
		map.put("uploadImage", uploadImage);
		map.put("memberNo", loginMember.getMemberNo());
		
		int result = service.updateProfile(map);
		
		String message = null;
		
		if(result > 0) {
			logger.info("이미지 변경 기능 수행됨");
			message = "프로필 이미지가 변경되었습니다.";
			
			// DB - 세션 동기화
			loginMember.setProfileImage((String)map.get("profileImage"));
			
		} else {
			message = "프로필 이미지가 변경 실패...";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:profile";
	}
}
