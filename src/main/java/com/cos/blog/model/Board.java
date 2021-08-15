package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // 테이블화 시키기!!!! 빼먹지말기
public class Board {

	@Id //PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id; //시퀀스, auto_icrement
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob //대용량 데이터
	private String content; //섬머노트 라이브러리 <html> 태그가 섞여서 디지안되기때문에 용량이 큼
	
	private int count; //조회수.
	
	//EAGER -> 모든 정보를 다 가져옴
	//LAZY -> 모든 정보X (펼치기기능이 있을경우 사용)
	@ManyToOne(fetch = FetchType.EAGER) //Many = Board , One = User => 연관관계를 나타내줌, 기본타입이 EAGER
	@JoinColumn(name = "userId")
	//private int userId; //작성자
	private User user; //DB는 오브젝트를 저장할수 없음 , FK, 자바는 오브젝트를 저장할수 있음
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	//cascade-remove : 게시글지울때 댓글 다 지우겠다
	//하나의 게시글은 여러개의 답변을 가질수있음 , 기본타입이 LAZY -> EAGER로 변경해주기
				// mappedBy  = "필드이름" -> 연관관계의 주인이 아니다 (FK가 아니니 DB에 컬럼을 만들지마라!!)
	//@JoinColumn(name = "replyId") //안해도 됨! 
	@JsonIgnoreProperties({"board"})// 무한호출방지(중복)
	@OrderBy("id desc") //내림차순 정렬
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
}

