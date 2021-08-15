package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
import com.cos.blog.model.User;

import lombok.Data;
import lombok.Getter;

@Data //getter + setter = Data
public class PrincipalDetail implements UserDetails{
	
	private User user; //컴포지션 (객체를 품고있는것)

	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정이 만료되지 않았는지 리턴한다 (true : 만료X / false : 만료됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠겨있지 않았는지 리턴한다 (true : 잠기지 않음 / false: 잠김)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호가 만료되지 않았는지 리턴한다 (true : 만료X)
	@Override
	public boolean isCredentialsNonExpired() {
		return true; //false면 로그인안됨
	}

	//계정이 활성화(사용가능)인지 리턴한다 (tru : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//계정 권한 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		//자동완성되는 오버라이딩 메소드와 동일
		Collection<GrantedAuthority> collectors = new ArrayList();
		collectors.add(()->{return "ROLE_"+user.getRole();});
		
		return collectors;
	}

	
}
