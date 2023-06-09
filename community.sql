SELECT * FROM MEMBER
WHERE 1 = 0;

-- MEMBER 테이블을 복제해서 MEMBER_S 테이블 생성

-- 테이블 컬럼 복제(NOT NULL 제약조건만 복제됨)
CREATE TABLE MEMBER_S
AS SELECT * FROM MEMBER
WHERE 1 = 0;

-- 컬럼 기본값 지정
ALTER TABLE MEMBER_S
MODIFY ENROLL_DT DEFAULT SYSDATE;

ALTER TABLE MEMBER_S
MODIFY SECESSION_FL DEFAULT 'N';

-- PK 제약조건 추가
ALTER TABLE MEMBER_S
ADD CONSTRAINT MEMBER_S_PK PRIMARY KEY(MEMBER_NO);


-- CHECK 제약조건 추가
ALTER TABLE MEMBER_S
ADD CONSTRAINT MEMBER_S_CHK CHECK(SECESSION_FL IN('Y', 'N'));

-- UNIQUE 제약조건 추가
ALTER TABLE MEMBER_S
ADD CONSTRAINT MEMBER_S_UQ UNIQUE(MEMBER_NICK);


-- 시퀀스 생성
CREATE SEQUENCE SEQ_MEMBER_NO_S NOCACHE;


-- 샘플데이터 추가
INSERT INTO MEMBER_S VALUES(
	SEQ_MEMBER_NO_S.NEXTVAL,
	'test01@naver.com',
	'pass01!',
	'테스트1',
	'01012341234',
	DEFAULT, DEFAULT, DEFAULT, DEFAULT
);



-- 아이디, 비밀번호가 일치하는 회원의 전화번호 조회
-- 단, 정상회원만
SELECT MEMBER_TEL FROM MEMBER_S
WHERE MEMBER_EMAIL = 'test01@naver.com'
AND MEMBER_PW = 'pass01!'
AND SECESSION_FL = 'N';



UPDATE MEMBER_S SET
MEMBER_PW = '$2a$10$yV.li3Yq/WOw9qm5aGoVh.U9RdkOu4c8ynTwZqKyKTaDImBLnNxAm'
WHERE MEMBER_NO = 1;


SELECT COUNT(*) FROM MEMBER_S
WHERE MEMBER_EMAIL = 'test01@naver.com'
AND SECESSION_FL = 'N';

SELECT MEMBER_NO, MEMBER_EMAIL, MEMBER_NICK, MEMBER_TEL,
    		MEMBER_ADDR, PROFILE_IMG, 
    		TO_CHAR( ENROLL_DT, 'YYYY-MM-DD HH24:MI:SS') AS ENROLL_DT
		FROM MEMBER_S
		WHERE MEMBER_EMAIL = 'fadfad'
		AND SECESSION_FL = 'N';

SELECT MEMBER_NO, MEMBER_EMAIL, MEMBER_NICK, MEMBER_TEL,
MEMBER_ADDR, PROFILE_IMG, 
TO_CHAR( ENROLL_DT, 'YYYY-MM-DD HH24:MI:SS') AS ENROLL_DT
FROM MEMBER_S
WHERE SECESSION_FL = 'N';

SELECT COUNT(*) FROM MEMBER_S
		WHERE MEMBER_EMAIL = 'test01@naver.com'
		AND MEMBER_PW = '$2a$10$yV.li3Yq/WOw9qm5aGoVh.U9RdkOu4c8ynTwZqKyKTaDImBLnNxAm'
		AND SECESSION_FL = 'N';

/* 집에서도 board board_type... 이런거 없으면 해야할거 !*/
ALTER TABLE BOARD
DROP CONSTRAINT FK_BOARD_MEMBER;
/* 있든없든 해야할거 */
ALTER TABLE BOARD
ADD CONSTRAINT FK_BOARD_MEMBER_S
FOREIGN KEY(MEMBER_NO)
REFERENCES MEMBER_S
ON DELETE SET NULL;

/* 이름 다르니깐 찾아서 수정 */
ALTER TABLE BOARD_IMG
DROP CONSTRAINT SYS_C008455;

ALTER TABLE BOARD_IMG
ADD CONSTRAINT FK_BOARD_BOARD_IMG
FOREIGN KEY(BOARD_NO)
REFERENCES BOARD
ON DELETE SET NULL;

DELETE FROM BOARD_IMG
WHERE BOARD_NO NOT IN(
    SELECT BOARD_NO FROM BOARD
);

SELECT * FROM BOARD;

-- BOARD 테이블 샘플데이터
INSERT INTO BOARD VALUES(
    SEQ_BOARD_NO.NEXTVAL, '스프링 테스트', '테스트 내용 입니다.', 
    DEFAULT, DEFAULT, DEFAULT, DEFAULT, 1, 1
);


-- 이미지 샘플 데이터
INSERT INTO BOARD_IMG VALUES(SEQ_IMG_NO.NEXTVAL, '/resources/images/board/sample1.jpg', 'sample1.jpg', 0, SEQ_BOARD_NO.CURRVAL);
INSERT INTO BOARD_IMG VALUES(SEQ_IMG_NO.NEXTVAL, '/resources/images/board/sample2.jpg', 'sample1.jpg', 1, SEQ_BOARD_NO.CURRVAL);
INSERT INTO BOARD_IMG VALUES(SEQ_IMG_NO.NEXTVAL, '/resources/images/board/sample3.jpg', 'sample1.jpg', 2, SEQ_BOARD_NO.CURRVAL);
INSERT INTO BOARD_IMG VALUES(SEQ_IMG_NO.NEXTVAL, '/resources/images/board/sample4.jpg', 'sample1.jpg', 3, SEQ_BOARD_NO.CURRVAL);
INSERT INTO BOARD_IMG VALUES(SEQ_IMG_NO.NEXTVAL, '/resources/images/board/sample5.jpg', 'sample1.jpg', 4, SEQ_BOARD_NO.CURRVAL);

commit;

/* 여기까지 */

SELECT REPLY_NO, REPLY_CONTENT, CREATE_DT, BOARD_NO, MEMBER_NO, PARENT_REPLY_NO, MEMBER_NICK, PROFILE_IMG
FROM REPLY_S s
JOIN MEMBER_S USING (MEMBER_NO)
WHERE BOARD_NO = 500