package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController //응답만 할수있게 ..
public class DummyControllerTest {

	@Autowired //의존성주입(DI)
	private UserRepository userRepository;
	
	//email,password 받아야함 ->@RequestBody User reqestUser
	
	//삭제하기
	@DeleteMapping("/dummy/user/{id}") 
	public String delete(@PathVariable int id) {
		try {
		userRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return"삭제되었습니다.id:"+id;
	}
	
	//수정하기
	@Transactional // 함수종료시 자동으로 commit됨 (업데이트시 save사용하지않고 가능하게해줌)
	@PutMapping("/dummy/user/{id}") //update -> PutMapping
	public User updateUser(@PathVariable int id,@RequestBody User reqestUser) {//json 데이터를 요청 => Java Object(메세지컨버터의 잭슨라이브러리가 변환해서 받아줌)
		System.out.println("id:"+id);
		System.out.println("password:"+reqestUser.getPassword());
		System.out.println("email:"+reqestUser.getEmail());
		
		//업데이트순서 : save로 먼저하지말고 일단 첫번째로 찾아야함 -> findByID...
		//save함수는 id를 전달하지 않으면 insert를 하고 
		//id를 전달 -> id에 대한 데이터가 있으면 update
		//id를 전달 -> id에 대한 데이터가 없으면 insert
		
		//람다식
		User user= userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다");
		});
		user.setPassword(reqestUser.getPassword());
		user.setEmail(reqestUser.getEmail());
//		reqestUser.setId(id);
//		reqestUser.setUsername("ssar");
//		userRepository.save(reqestUser);
//		userRepository.save(user);
		//=>업데이트시 save 노노노노노노노노노놉!!
		
		//더티체킹 : 찌꺼기 체크한다는데.. 29강 영상 참고하기
		return user;
	}
	
	//http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}	
	
	//http://localhost:8000/blog/dummy/user?page=0 (첫번째 페이지 확인)
	//한페이지당 size : 2건의 데이터 / sort : 분류는 아이디로 /Sort.Direction.DESC 최근부터 순서대로 리턴
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 2,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		
//		if(pagingUser.isFirst()) {
//			
//		}
		
		List<User> users = pagingUser.getContent();
		return pagingUser;
	}	
	
	//{id} 주소로 파라미터를 전달 받을 수 있음
	//http://localhost:8000/blog/dummy/user/2
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//예를 들어 지정한 2이라는 user를 못찾아왔을경우 null값이 뜨기때문에
		//Optional로 User객체를 감싸서 가져와 null인지 아닌지 판단해서 return하겠다!
		//=>.get(); 좀위험쓰..에러났을때 메세지 표시 X 
		//=>.orElseGet(객체생성); 에러났을때 null리턴
		//=>.orElseThrow(객체생성);
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// 없을 때 실행(에러페이지 보여줌)
				return new IllegalArgumentException("해당 유저는 없습니다.id:"+id);
			}
		});

		//요청 : 웹브라우저
		//user 객체 = 자바 오브젝트
		//변환 (웹브라우저가 이해할 수 있는 데이터) -> json(Gson라이브러리)
		//스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson라이브러리를 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 던져줌
				return user;
	}
	
	//http://localhost:8000/blog/dummy/join(요청)
	//http의 post-> body -> x-www...에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	/*
	 * public String join(String username,String password,String email) {//key=value
	 * (약속된 규칙) System.out.println("username:"+username);
	 * System.out.println("password:"+password); System.out.println("email:"+email);
	 * return "회원가입이 완료되었습니다."; }
	 */
	
	public String join(User user) {//key=value (약속된 규칙)
		System.out.println("id:"+user.getId());
		System.out.println("username:"+user.getUsername());
		System.out.println("password:"+user.getPassword());
		System.out.println("email:"+user.getEmail());
		System.out.println("role:"+user.getRole());
		System.out.println("createDate"+user.getCreateDate());
		
		user.setRole(RoleType.USER);//디폴트값은 회원가입시 넣기
		userRepository.save(user); //회원가입(데이터를 DB에 입력-insert구문 = save)
		return "회원가입이 완료되었습니다.";
	}
}
