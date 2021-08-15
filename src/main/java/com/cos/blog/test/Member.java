package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
@Data //Getter+Setter 

//@AllArgsConstructor //전체 생성자 -> Builder 쓸거라서 주석처리
//@RequiredArgsConstructor

@NoArgsConstructor //빈(bean) 생성자
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {	
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	

	
	
}
