package com.team404.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team404.command.UserDTO;
import com.team404.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	@Qualifier("userService")
	private UserService us;

	@RequestMapping(value = "/userLogin")
	public String userLogin() {
		return "user/userLogin"; 
	}

	@RequestMapping(value = "/userMypage")
	public String userMypage(HttpSession session,Model model) {
		String userId = (String) session.getAttribute("userId");
		UserDTO dto = us.getInfo(userId);
		System.out.println(dto.toString());
		model.addAttribute("userDTO", dto);
		return "user/userMypage";
	}

	@RequestMapping(value = "/userJoin")
	public String userJoin() {
		return "user/userJoin";
	}

	@RequestMapping("/idConfirm")
	@ResponseBody
	public int idConfirm(@RequestBody UserDTO dto) {
		return us.idConfirm(dto);
	}
	
	@RequestMapping("/updateUser")
	@ResponseBody
	public int updateUser(@RequestBody UserDTO dto) {
		return us.updateUser(dto);
	}

	@RequestMapping(value = "/joinForm", method = RequestMethod.POST)
	public String joinForm(UserDTO vo, RedirectAttributes RA) {

		int result = us.join(vo);
		if (result == 1) {
			RA.addFlashAttribute("msg", "회원가입을 축하합니다.");
			return "redirect:/user/userLogin";
		} else {
			RA.addFlashAttribute("msg", "회원가입을 실패했습니다.");
			return "redirect:/user/userLogin";
		}

	}
	
	
	@RequestMapping(value="/loginForm", method = RequestMethod.POST)
	   public String loginForm(UserDTO vo, RedirectAttributes RA, HttpSession session) {
	      if(us.login(vo) == 1) { // 성공
	    	  session.setAttribute("userId", vo.getUserId());
	         RA.addFlashAttribute("msg", "로그인 성공");
	         return "home";//홈화면
	      } else { // 실패
	         RA.addFlashAttribute("msg", "아이디 또는 비밀번호를 확인하세요.");
	         return "redirect:/user/userLogin";
	      }
	   }
	  
	  @RequestMapping("/userLogout")
	  public String userLogout(HttpSession session) {
		  session.invalidate();
		  return "redirect:/home";
	
	  }

}
