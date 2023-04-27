package edu.kh.comm.member.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

		int result = 0;

		
		// 비밀번호 확인
		String currentPw = dao.checkPw(loginMember);
		
		if(bcrypt.matches(loginMember.getMemberPw(), currentPw)) {
			result = dao.secession(loginMember);
		} else {
			result = 0;
		}
		
		return result;
	}
	

	// 비밀번호 변경 서비스 구현
	@Override
	public int changePw(Member loginMember, String newPw) {

		int result = 0;
		
		// 비밀번호 확인
		String currentPw = dao.checkPw(loginMember);
		
		if(bcrypt.matches(loginMember.getMemberPw(), currentPw)) {
			newPw = bcrypt.encode(newPw);
			loginMember.setMemberPw(newPw);
			result = dao.changePw(loginMember);
		} else {
			result = 0;
		}
		
		return result;
	}


	// 회원정보 변경 서비스 구현
	@Override
	public int updateInfo(Member loginMember) {
		
		return dao.updateInfo(loginMember);
	}
	
	
	
	
	
}
