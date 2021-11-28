package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌 -> IOC를 해준다(메모리를 대신 띄워줌)
//서비스가 필요한 이유
//1.트랜잭션 관리 -> 
//2.서비스 의미 떄문 -> 여러개 묶어서 가능하게함
@Service
public class UserService {

	@Autowired // 주입(DI)
	private UserRepository userRepository;
	
	@Autowired //주입
	private BCryptPasswordEncoder encoder;
	
	@Transactional(readOnly = true )
	public User 회원찾기(String username) {
		
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
		
	@Transactional //하나의 트랜젝션으로 묶인다(정확성) -> 성공하면 자동으로 커밋됨(DB에 저장됨)
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); //1234 원문
		String encPassword = encoder.encode(rawPassword); //해쉬화 됨
		user.setPassword(encPassword);
		user.setRole(RoleType.USER); //롤타입 추가해줘야함
		userRepository.save(user);
	}
	
	@Transactional
	public void 회원수정(User user) {
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고 , 영속화된 User 오브젝트를 수정
		//select를 해서 User오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서 
		//영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려주게됨
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		//Validate 체크(유효성) => oauth에 값이 없으면 수정 가능
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword); //수정하는 패스워드 영속화 시키기
			persistance.setEmail(user.getEmail());
		}
		
		
		//회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = 자동으로 commit됨
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 자동으로 날려줌
	}
	
//	@Transactional(readOnly = true)//select할때 트랜잭션 시작, 해당 서비스가 종료될때 트랜잭션 종료(정합성 유지)
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
//	}
}
