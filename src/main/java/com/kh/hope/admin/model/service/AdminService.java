package com.kh.hope.admin.model.service;

import java.util.List;
import java.util.Map;

import com.kh.hope.activityreport.model.vo.ActivityReport;
import com.kh.hope.admin.model.vo.BlackList;
import com.kh.hope.attachment.model.vo.Attachment;
import com.kh.hope.board.model.vo.Board;
import com.kh.hope.board.model.vo.Reply;
import com.kh.hope.board.model.vo.Report;
import com.kh.hope.chat.model.vo.Chat;
import com.kh.hope.chat.model.vo.ChatJoin;
import com.kh.hope.chat.model.vo.ChatMessage;
import com.kh.hope.common.model.vo.PageInfo;
import com.kh.hope.donate.model.vo.Donate;
import com.kh.hope.payment.model.vo.PaymentInfo;
import com.kh.hope.product.model.vo.Product;
import com.kh.hope.program.model.vo.Program;
import com.kh.hope.user.model.vo.User;

public interface AdminService {

/* ============================================== 회원 시작 ==============================================*/
	
	// 사용자 List 조회
	List<User> selectAllUser();
	
	
	// 사용자 삭제
	int deleteUser(int userNo, String reason);
	
	// 회원정보 확인
	User getUserOne(int userNo);
	
	// 회원정보 업데이트
	int updateUserInfo(User user);

	// 회원 이름검색
	List<User> searchUserByName(String userName);

	// 블랙리스트 List 조회
	List<BlackList> blackListView();

	// User 테이블 정지풀기
	int releseStop(int userNo);

	// 블랙리스트 테이블 정지풀기
	int modifyUserStop(int userNo);

	// 블랙리스트 회원번호 검색
	List<BlackList> searchByUserNo(int userNo);
	
/* ============================================== 회원 끝 ==============================================*/	


/* ============================================== 채팅 시작 =============================================*/
	
	// 채팅방 조회
	List<Chat> selectChatRoomList();

	// 채팅방 조인 조회
	List<ChatJoin> selectJoinList();

	// 채팅방 생성 
	int openChatRoom(Chat c);
	
	// 채팅방 입장
	List<ChatMessage> joinChatRoom(ChatJoin join);

	// 채팅방 삭제
	int deleteChatRoom(ChatJoin join);

	// 채팅방 제목 검색
	List<Chat> chatByName(Chat c);

	
	
/* ============================================== 대시보드 시작 ==============================================*/	

	// 회원 리스트 조회
	List<User> dashboardUser();

	// 기부금액 합계
	int dashboardAmount();
	
	// 게시글 수
	int dashboardTotalBoardCount();

	// 채팅방 합계
	int dashboardChatTotalCount();

	// 기부 그래프
	List<PaymentInfo> getDailyIncome();

	// donate_INFO 리스트 뽑기
	List<PaymentInfo> selectDonate();
	
	// userList 
	List<User> dashboarduserList();


/* ============================================== 대시보드  끝 =============================================*/

	
	// 신고리스트


	List<Report> reportBoardList();

	List<Report> reportReplyList();

	int deleteReport(int reportNo);

	Board selectReportBoard(int reportNo);

	List<Attachment> selectReportImgList(int reportNo);

	int deleteBoardReport(int boardNo);

	Reply selectReply(int replyNo);

	int deleteReplyDatailReport(int replyNo);

	//댓글관리
	List<Reply> ReplyList();

	List<Reply> ReplyTodayList();
	
	//게시판관리 시작
	List<Product> productList();

	//프로그램
	int selectProgramCount(Map<String, Object> map);


	List<Program> programList(PageInfo pi, Map<String, Object> map);

	//후원모집
	int selectDonateCount(Map<String, Object> map);


	List<Donate> donateList(PageInfo pi, Map<String, Object> map);

	//댓글삭제
	int deleteReply(int replyNo);

	//물품 수령 삭제
	int confirmProduct(int productNo);
	

	int deleteProduct(int productNo);
	
	//물품확인내역

	List<Product> productConfirmList();

	//program 명단
	List<Program> programPeople(int programNo);


	Program programPeopleCount(int programNo);
	
	//프로그램 삭제

	int deleteProgram(int programNo);

	//후원 삭제
	int deleteDonate(int donateNo);

	// 봉사활동 종료 리스트
	List<Program> selectProgramEndList();

	// 후원모집 종료 리스트
	List<Donate> selectDonateEndList();

	// 활동보고서 봉사 리스트
	List<ActivityReport> selectReportList(Map<String, Object> map);


	// 활동보고서 후원 리스트
	List<ActivityReport> selectDonateReportList(Map<String, Object> map);



}
