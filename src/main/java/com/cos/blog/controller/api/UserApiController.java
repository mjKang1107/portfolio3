package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.http.SecurityHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired //주입
	private UserService userService;
	
	@Autowired //Bean으로 오버라이딩한거 주입
	private AuthenticationManager authenticationManager;
	
	@Autowired //주입
	private BCryptPasswordEncoder encoder;
		
//	@Autowired 세션을 위에서 주입해줘도 된다
//	private HttpSession session;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user, HttpSession session) {//username,password,email 저장되어있음
		System.out.println("UserApiController : save 호출됨");	
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);//자바오브젝트를 JSON으로 변환해서 Jackson으로 리턴
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){ //@RequestBody이 있어야 json데이터로 받을수있음
		userService.회원수정(user);
		//여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음
		//but!! 세션값은 변경되지않은 상태이기때문에 (웹사이트 변경X) 직접 세션값 변경해줘야함
		//세션등록(로그인처리)	
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user/*,HttpSession session*/){
//		System.out.println("UserApiController : login 호출됨");
//		
//		User principal = userService.로그인(user); //principal : 접근 주체
//		
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
//	}
	
	
}
 