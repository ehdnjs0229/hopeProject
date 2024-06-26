package com.kh.hope.admin.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kh.hope.activityreport.model.vo.ActivityReport;
import com.kh.hope.admin.model.dao.AdminDao;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AdminServiceImpl implements AdminService{

	
	@Autowired
	public AdminDao adminDao;
	
	/* ============================================== 회원 시작 ==============================================*/
	// 사용자 조회
	@Override
	public List<User> selectAllUser() {
		return adminDao.selectAllUser();
	}
	
	// 사용자 삭제
	@Override
	public int deleteUser(int userNo, String reason) {
		
		int result = adminDao.deleteUser(userNo);
		
		if(result > 0) {
			// 블랙리스트 추가
			BlackList blackList = BlackList.builder()
									.reason(reason)
									.userNo(userNo)
									.build();
			adminDao.blackListInsert(blackList);
		}
		
		return result;
	}
	// 회원정보 확인
	@Override
	public User getUserOne(int userNo) {
		
		System.out.println("serviceImpl : " + userNo);
		return adminDao.getUserOne(userNo);
	}
	// 회원정보확인 수정
	@Override
	public int updateUserInfo(User user) {
		
		return adminDao.updateUserInfo(user);
	}
	// 회원 이름검색
	@Override
	public List<User> searchUserByName(String userName) {
		return adminDao.searchUserByName(userName);
	}
	
	// 블랙리스트 조회
	@Override
	public List<BlackList> blackListView() {
		return adminDao.blackListView();
	}
	//User 테이블 정지풀기
	@Override
	public int releseStop(int userNo) {
		return adminDao.releseStop(userNo);
	}
	// 블랙리스트 정지풀기
	@Override
	public int modifyUserStop(int userNo) {
		return adminDao.modifyUserStop(userNo);
	}
	// 블랙리스트 회원번호 검색
	@Override
	public List<BlackList> searchByUserNo(int userNo) {
		return adminDao.searchByUserNo(userNo);
	}
	
	/* ============================================== 회원 끝 ==============================================*/
	
	
	/* ============================================== 채팅 시작 ==============================================*/
	
	// 채팅방 리스트 조회
	@Override
	public List<Chat> selectChatRoomList() {
		return adminDao.selectChatRoomList();
	}
	
	// 채팅방 조인 조회
	@Override
	public List<ChatJoin> selectJoinList() {
		return adminDao.selectJoinList();
	}
	// 채팅방 생성
	@Override
	public int openChatRoom(Chat c) {
		return adminDao.openChatRoom(c);
	}
	
	// 채팅방 입장
	@Override
	public List<ChatMessage> joinChatRoom(ChatJoin join) {
		
		// chatJoin 데이터 INSERT 후, 채팅메시지 목록 조회후 반환
		List<ChatMessage> list = null;
		
		/**
		 * 1. 현재 회원이 해당 채팅방에 참여하고 있는지 확인, (SELECT)

		 * 2. 참여하고 있지 않다면 참여 (INSERT)
		 * */
		
		// 사용자가 채팅방 입장 했는지 조회
		int result = adminDao.joinCheck(join);

		try {
			if(result == 0) {
			// 처음 참가한 사용자는 insert
			result = adminDao.joinChatRoom(join);
			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(result > 0) {
			
			// 이미 참가했던 사용자는 메세지만 select
			list = adminDao.selectChatMessage(join.getChatNo());
		}
		return list;
		
	}
	// 채팅방 삭제
	@Override
	public int deleteChatRoom(ChatJoin join) {
		
		// chatJoin 채팅방 나가기 
		int result = adminDao.deleteChatRoom(join);

		// chat 채팅방 삭제
		if(result > 0) {
			int age = adminDao.deleteChat(join);
		}
		return 0;
	}
	// 채팅방 제목 검색
	@Override
	public List<Chat> chatByName(Chat c) {
		return adminDao.chatByName(c);
	}
	
	
	/* ============================================== 채팅 끝 ==============================================*/
	
	
	
	/* ============================================== 대시보드 시작 ==============================================*/	

	// 회원 리스트 조회
		@Override
		public List<User> dashboardUser() {
			List<User> userList = adminDao.dashboardUser();
			if(userList == null) {
				return new ArrayList<>();
			}
			return userList;
		}
	// 기부금액 합계 	
		@Override
		public int dashboardAmount() {
			
			int Amount = adminDao.dashboardAmount();
			
			if(Amount < 0) {
				return Amount;
			}
			
			return Amount;
		}

	// 게시판 수
		@Override
		public int dashboardTotalBoardCount() {
			
			int count = adminDao.dashboardTotalBoardCount();
			
			if(count < 0) {
				return count;
			}
			
			return count;
		}
	// 채팅방 합계
		@Override
		public int dashboardChatTotalCount() {
			
			int count  = adminDao.dashboardChatTotalCount();
			
			if(count < 0) {
				return count;
			}
			
			return count;
		}

	// 기부그래프	
		@Override
		public List<PaymentInfo> getDailyIncome() {
			
			List<PaymentInfo> count = adminDao.getDailyIncome();
			
			if(count == null) {
				return new ArrayList<>();
			}
				
			
			return count; 
		}
	// donate 리스트 뽑기
		@Override
		public List<PaymentInfo> selectDonate() {
			
			List<PaymentInfo> count = adminDao.selectDonate();
			
			if(count == null) {
				return new ArrayList<>();
			}
			
			return count;
		}
		
	// userList
		@Override
		public List<User> dashboarduserList() {
			
			List<User> count = adminDao.dashboarduserList();
			
			if (count == null) {
				return count;
			}
			
			return count;
		}		


	/* ============================================== 대시보드  끝 =============================================*/

		/*==============================================신고리스트 시작===============================*/
		//신고리스트
			
			@Override
			public List<Report> reportBoardList() {
				return adminDao.reportBoardList();
			}
			@Override
			public List<Report> reportReplyList() {
				return adminDao.reportReplyList();
			}
			@Override
			public int deleteReport(int reportNo) {
				return adminDao.deleteReport(reportNo);
			}
			@Override
			public Board selectReportBoard(int reportNo) {
				return adminDao.selectReportBoard(reportNo);
			}
			@Override
			public List<Attachment> selectReportImgList(int reportNo) {
				return adminDao.selectReportImgList(reportNo);
			}
			
			@Override
			public int deleteBoardReport(int boardNo) {
				int result= adminDao.deleteBoardReport(boardNo);		
				
				if(result>0) {
					result = adminDao.deleteReportList(boardNo);
				}
				
				return result;
			}
			@Override
			public Reply selectReply(int replyNo) {
				return adminDao.selectReply(replyNo);
			}
			@Transactional(rollbackFor = {Exception.class})
			@Override
			public int deleteReplyDatailReport(int replyNo) {
				int result= adminDao.deleteReplyDatailReport(replyNo);		
				
				if(result>0) {
					result = adminDao.deleteReportReplyList(replyNo);
				}
				
				return result;
			}	
			
/*===============댓글관리=============================*/
			
			@Override
			public List<Reply> ReplyList() {
				return adminDao.ReplyList();
			}

			@Override
			public List<Reply> ReplyTodayList() {
				return adminDao.ReplyTodayList();
			}	
			/*======================게시판관리===============================*/
			@Override
			public List<Product> productList() {
				return adminDao.productList();
			}
			//봉사활동
			@Override
			public int selectProgramCount(Map<String, Object> map) {
				return adminDao.selectProgramCount(map);
			}

			@Override
			public List<Program> programList(PageInfo pi, Map<String, Object> map) {
				return adminDao.programList(pi,map);
			}
			//후원모집
			@Override
			public int selectDonateCount(Map<String, Object> map) {
				return adminDao.selectDonateCount(map);
			}

			@Override
			public List<Donate> donateList(PageInfo pi, Map<String, Object> map) {
				return adminDao.donateList(pi,map);
			}
			//댓글관리삭제
			@Override
			public int deleteReply(int replyNo) {
				return adminDao.deleteReply(replyNo);
			}
			//물품수령
			@Override
			public int confirmProduct(int productNo) {
				return adminDao.confirmProduct(productNo);
			}
			//물품기부삭제
			@Override
			public int deleteProduct(int productNo) {
				return adminDao.deleteProduct(productNo);
			}

			@Override
			public List<Product> productConfirmList() {
				return adminDao.productConfirmList();
			}
			//프로그램 명단
			@Override
			public List<Program> programPeople(int programNo) {
				return adminDao.programPeople(programNo);
			}

			@Override
			public Program programPeopleCount(int programNo) {
				return adminDao.programPeopleCount(programNo);
			}
			//프로그램 삭제
			@Override
			public int deleteProgram(int programNo) {
				return adminDao.deleteProgram(programNo);
			}

			@Override
			public int deleteDonate(int donateNo) {
				return adminDao.deleteDonate(donateNo);
			}
			
			// 봉사활동 종료 리스트
			@Override
			public List<Program> selectProgramEndList() {
				return adminDao.selectProgramEndList();
			}
			
			// 후원모집 종료 리스트
			@Override
			public List<Donate> selectDonateEndList() {
				return adminDao.selectDonateEndList();
			}
			
			// 활동보고서 리스트
			@Override
			public List<ActivityReport> selectReportList(Map<String, Object> map) {
				return adminDao.selectReportList(map);
			}

			
			// 활동보고서 후원
			@Override
			public List<ActivityReport> selectDonateReportList(Map<String, Object> map) {
				return adminDao.selectDonateReportList(map);
			}


		
}
