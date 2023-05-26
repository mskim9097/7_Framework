package edu.kh.comm.member.model.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.comm.member.model.vo.Member;

@Repository
public class MyPageDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private Logger logger = LoggerFactory.getLogger(MyPageDAO.class);
	
	
	
	/** 비밀번호 확인 DAO
	 * @param memberNo
	 * @return currentPw
	 */
	public String checkPw(int memberNo) {

		return sqlSession.selectOne("myPageMapper.checkPw", memberNo);
	}


	
	/** 회원 탈퇴 DAO
	 * @param memberNo
	 * @return result
	 */
	public int secession(int memberNo) {
		
		return sqlSession.update("myPageMapper.secession", memberNo);
	}



	/** 비밀번호 변경 DAO
	 * @param paramMap
	 * @return result
	 */
	public int changePw(Map<String, Object> paramMap) {
		
		return sqlSession.update("myPageMapper.changePw", paramMap);
	}



	/** 회원정보 수정 DAO
	 * @param paramMap
	 * @return result
	 */
	public int updateInfo(Map<String, Object> paramMap) {

		return sqlSession.update("myPageMapper.updateInfo", paramMap);
	}



	/** 프로필 이미지 수정
	 * @param map
	 * @return result
	 */
	public int updateProfile(Map<String, Object> map) {

		return sqlSession.update("myPageMapper.updateProfile", map);
	}

}
