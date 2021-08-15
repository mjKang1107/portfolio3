package com.cos.blog.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {

	@Autowired // 주입(DI)
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
		
	@Transactional //하나의 트랜젝션으로 묶인다(정확성) -> 성공하면 자동으로 커밋됨(DB에 저장됨)
	public void 글쓰기(Board board,User user) {//title,content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board>글목록(Pageable pageable){//페이징처리
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	//IllegalArgumentException
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		 boardRepository.deleteById(id);
				
	}
	
	@Transactional
	public void 글수정하기(int id,Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
				}); //영속화 시키기
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수 종료시(Service가 종료될때) 트랜잭션이 종료된다 이때 더티체킹이 일어나면서 자동 업데이트 됨 ->커밋(DB flush) 
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		
//	 User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
//			return new IllegalArgumentException("댓글 작성실패: 회원 아이디를 찾을 수 없습니다.");
//		});	
//		
//	 Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
//			return new IllegalArgumentException("댓글 작성실패: 게시글 아이디를 찾을 수 없습니다.");
//		});
//	 
//	 Reply reply = Reply.builder()
//			 .user(user)
//			 .board(board)
//			 .content(replySaveRequestDto.getContent())
//			 .build();
//		
	
	int result = replyRepository.mSave(replySaveRequestDto.getUserId(),replySaveRequestDto.getBoardId(),replySaveRequestDto.getContent());
	System.out.println("BoardService :"+result);
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
}
