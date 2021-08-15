package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청-> 응답(HTML파일)
//@Controller

//사용자가 요청->응답(Data)

@RestController
public class HttpControllerTest {

	//인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다.
	//http://localhost:8000/blog/http/get(select)
	/*
	 * @GetMapping("/http/get") public String getTest(@RequestParam int
	 * id,@RequestParam String username) { return "get 요청:"+id+","+username; }
	 */
	
	private static final String TAG="HttpControllerTest:";
	
	//http://localhost:8000/blog/http/lombok
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		//build사용시 객체를 따로 만들지 않아도 됨(내가 넣고싶은거 넣기!)- 값의 순서 상관없음
		//System.out.println(TAG+"getter:"+m.getId());
		System.out.println(TAG+"getter:"+m.getUsername());
		//m.setId(5000);
		m.setUsername("cos");
		//System.out.println(TAG+"setter:"+m.getId());
		System.out.println(TAG+"setter:"+m.getUsername());
		return "lombok test 완료";
	}
	
	
	@GetMapping("/http/get")
	public String getTest(Member m) {//id=1&username=ssar&password=1234&email=ssar@nate.com
		return "get 요청:"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8000/blog/http/post(insert)
	@PostMapping("/http/post")//text/plain,application/json
	public String postTest(@RequestBody Member m) {
		return "post 요청:"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8000/blog/http/put(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청:"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8000/blog/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
