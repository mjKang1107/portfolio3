package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;


@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	//@AuthenticationPrincipal PrincipalDetail principal
	@GetMapping({"","/"})
	public String index(Model model,@PageableDefault(size = 3,sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {// 컨트롤러에서 세션을 어떻게 찾는지?
		model.addAttribute("boards", boardService.글목록(pageable));
		return "index"; //viewResolver 작동함
	}
	
	@GetMapping("/board/{id}")
	public String findByid(@PathVariable int id,Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id,Model model) {
							//아이디값받고 , 해당데이터를 가지고 뷰까지 이동하는것 -> 모델
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm"; //뷰페이지
	}
	
	//유저의 권한 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
}
