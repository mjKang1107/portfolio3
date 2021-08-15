package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

//빈등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration //빈등록(IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired //주입
	private PrincipalDetailService principalDetailService;
		
	@Bean //어디에서나 DI가능
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean //IoC가 된다
	public BCryptPasswordEncoder encoderPWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인해주는데 password를 가로채기 하는데
	//해당 password가 어떤걸로 해쉬가 되어 회원가입이 되었는지 알아야 
	//같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교가 가능하다
	//알려주는 방법
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encoderPWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		 .csrf().disable() //csrf 토큰 비활성화(테스트시 걸어두는게 좋음)
		 .authorizeRequests()
			.antMatchers("/","/auth/**","/js/**","/css/**","/image/**","/dummy/**")
			.permitAll()
			.anyRequest()
			.authenticated()
		 .and()
			.formLogin()
			.loginPage("/auth/loginForm")
			.loginProcessingUrl("/auth/loginProc")//스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로챈다
			.defaultSuccessUrl("/");
			//.failureUrl("/fail") 실패시.. 
			
	}
}
