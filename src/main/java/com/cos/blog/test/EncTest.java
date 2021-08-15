package com.cos.blog.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {

	@Test
	public void 해쉬_암호화() {
		String encPassword = new BCryptPasswordEncoder().encode("1234");
		System.out.println("1234 해쉬:" +encPassword);
		//고정길이의 문자열로 바꿔줌  
		//1234 해쉬:$2a$10$fb2luama6FQEzhOKO2FQRu47ASwtJ4IsKDFCjdy.AQSYoqowSrSp2
	}
	
}
