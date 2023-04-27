package edu.kh.comm.member.model.service;

import edu.kh.comm.member.model.vo.Member;

public interface MyPageService {

	/** 회원 탈퇴 서비스
	 * @param loginMember
	 * @return result
	 */
	public abstract int secession(Member loginMember);

	
	/** 비밀번호 변경 서비스
	 * @param loginMember
	 * @param newPw
	 * @return result
	 */
	public abstract int changePw(Member loginMember, String newPw);

	
	/** 회원정보 변경 서비스
	 * @param loginMember
	 * @return result
	 */
	public abstract int updateInfo(Member loginMember);


}
