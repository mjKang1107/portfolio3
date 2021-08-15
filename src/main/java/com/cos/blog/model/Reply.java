package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.blog.dto.ReplySaveRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply{//답변테이블

	@Id //PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id; //시퀀스, auto_icrement
	
	@Column(nullable = false, length = 200)// 답변이라서 길게 할필요없음!
	private String content;
	
	@ManyToOne //여러글에 답변
	@JoinColumn(name = "boardId")
	private Board board;
	
	@ManyToOne //여러개의 답변을 한명의 유저가 작성가능
	@JoinColumn(name = "userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	public void update(User user,Board board,String content) {
		setUser(user);
		setBoard(board);
		setContent(content);
	}

	@Override
	public String toString() {
		return "Reply [id=" + id + ", content=" + content + ", board=" + board + ", user=" + user + ", createDate="
				+ createDate + "]";
	}
	
	
}
