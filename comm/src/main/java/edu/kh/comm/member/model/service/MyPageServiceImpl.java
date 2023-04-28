package edu.kh.comm.member.model.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.comm.common.Util;
import edu.kh.comm.member.model.dao.MyPageDAO;
import edu.kh.comm.member.model.vo.Member;

@Service
public class MyPageServiceImpl implements MyPageService {
	
	@Autowired
	private MyPageDAO dao;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	private Logger logger = LoggerFactory.getLogger(MyPageServiceImpl.class);

	// 회원 탈퇴 서비스 구현
	@Override
	public int secession(Member loginMember) {


		
		// 1) DB에서 현재 회원의 비밀번호를 조회 / 입력받은 비밀번호와 비교
		String encPw = dao.checkPw(loginMember.getMemberNo());
		
		if(bcrypt.matches(loginMember.getMemberPw(), encPw)) {
			
			// 2) 비밀번호가 일치하면 회원 번호를 이용해서 탈퇴 진행
			return dao.secession(loginMember.getMemberNo());
		}
		
		// 3) 비밀번호가 일치하지 않으면 0 반환
		return 0;
	}
	

	// 비밀번호 변경 서비스 구현
	@Override
	public int changePw(Map<String,Object> paramMap) {
		
		// 1) DB에서 현재 회원의 비밀번호를 조회
		String encPw = dao.checkPw((int)paramMap.get("memberNo"));
		
		// 2) 입력된 현재 비밀번호(평문)와
		//	  조회된 비밀번호(암호화)를 비교(bcrypt.matches() 이용)
		
		// 3) 비교 결과가 일치하면
		//		새 비밀번호를 암호화해서 update 구문 수행
		
		if(bcrypt.matches((String)paramMap.get("currentPw"), encPw)) {
			
			paramMap.put("newPw", bcrypt.encode((String)paramMap.get("newPw")));
			// Map에 이미 같은 Key가 존재하면
			// value만 덮어씌움
			
			return dao.changePw(paramMap);
			
		} 
		
		// 비교 결과가 일치하지 않으면 0반환
		// -> "현재 비밀번호가 일치하지 않습니다"
		return 0;
	}


	// 회원정보 변경 서비스 구현
	@Override
	public int updateInfo(Map<String, Object> paramMap) {
		
		return dao.updateInfo(paramMap);
	}


	// 프로필 이미지 수정 서비스 구현
	@Override
	public int updateProfile(Map<String, Object> map) throws IOException {
							// webPath, folderPath, uploadImage, delete, memberNo
		
		// 자주쓰는 값 변수에 저장
		MultipartFile uploadImage = (MultipartFile)map.get("uploadImage");
		String delete = (String)map.get("delete"); // "0" (변경) / "1" (삭제)
		
		// 프로필 이미지 삭제여부를 확인해서
		// 삭제가 아닌 경우(== 새 이미지로 변경) -> 업로드된 파일명을 변경
		// 삭제가 된 경우 -> NULL 값을 준비(DB update)
		
		String renameImage = null; // 변경된 파일명 저장
		
		if(delete.equals("0")) { // 이미지가 변경된 경우
			
			// 파일명 변경
			// uploadImage.getOriginalFilename() : 원본 파일명
			renameImage = Util.fileRename(uploadImage.getOriginalFilename());
			// 20230428154532.png
			
			map.put("profileImage", map.get("webPath") + renameImage);
			//		/resources/images/memberProfile/20230428154532.png
			
		} else {
			map.put("profileImage", null);
		}
		
		
		// DAO를 호출해서 프로필 이미지 수정
		int result = dao.updateProfile(map);
		
		// DB 수정 성공 시 메모리에 임시 저장되어있는 파일을 서버에 저장
		if(result > 0 && map.get("profileImage") != null) {
			uploadImage.transferTo(new File(map.get("folderPath") + renameImage));
		}
		
		
		
		return result;
	}
	
	
	
	
	
}
