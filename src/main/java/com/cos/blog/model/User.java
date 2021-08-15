package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM -> Java(다른언어도 마찬가지) Object -> 테이블로 매핑해주는 기술
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴!!
@Entity //(테이블화 시키기위한 어노테이션) User 클래스가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert insert시에 null인 필드를 제외시켜줌
public class User {

	@Id //Primary key가 된다는 어노테이션
	@GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다..
	private int id; //시퀀스 , auto_increment
	
	@Column(nullable = false,length=100, unique = true) //nullable = false -> 낫널!!!
	private String username; // 아이디
	
	@Column(nullable = false,length=100) //해쉬=>비밀번호 암호화를 위해 넉넉하게 줌
	private String password;
	
	@Column(nullable = false,length=50)
	private String email;
	
	//@ColumnDefault("user")
	//DB는 RoleType이라는게 없기때문에-> 아래의 어노테이션 사용
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum을 쓰는게 좋음 //USER,ADMIN
	
	private String oauth; //kakao, google.... 
	
	@CreationTimestamp //시간이 자동으로 입력됨
	private Timestamp createDate;
	
	
}
