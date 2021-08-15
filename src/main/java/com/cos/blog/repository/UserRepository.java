package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.User;

//JSP로 생각하면 DAO 
//자동으로 bean 등록이 된다.
//-> @Repository 생략이 가능하다
public interface UserRepository extends JpaRepository<User, Integer>{
	//select * from user where username =1?;
	Optional<User> findByUsername(String username);
}


//JPA Naming 전략
	//select * from user where username =? and password =?;
	//User findByUsernameAndPassword(String username,String password);
	
	//@Query(value ="select * from user where username =?1 and password =?2;",nativeQuery = true)
	//User login(String username, String password);
	//위와 동일