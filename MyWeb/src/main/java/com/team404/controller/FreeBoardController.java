package com.team404.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team404.command.FreeBoardDTO;
import com.team404.freeboard.service.FreeBoardService;
import com.team404.util.Criteria;
import com.team404.util.PageDTO;

@Controller
@RequestMapping("/freeBoard")
public class FreeBoardController {
	
	@Autowired
	@Qualifier("freeBoardService")
	private FreeBoardService fs;
	
	
	@RequestMapping(value="/freeRegist", method=RequestMethod.GET)
	public String freeRegist() {
		return "freeBoard/freeRegist";
	}
	
	
	
	@RequestMapping(value="/freeList", method=RequestMethod.GET)
	public String freeList(Model model, Criteria cri) {
		//일반
		//ArrayList<FreeBoardDTO> dto = fs.list();
		
		//페이징
		
		ArrayList<FreeBoardDTO> list = fs.list(cri);
		int total = fs.getTotal(cri);
		PageDTO dto = new PageDTO(cri, total);
		
		model.addAttribute("dto", dto);
		model.addAttribute("list", list);
		return "freeBoard/freeList";
	}
	
	@RequestMapping(value="/freeModify", method=RequestMethod.GET)
	public String freeModify(@RequestParam("num") int num, Model model) {
		FreeBoardDTO dto = fs.detail(num);
		model.addAttribute("dto", dto);
		return "freeBoard/freeModify";
	}
	
	@RequestMapping(value="/freeDetail", method=RequestMethod.GET)
	public String freeDetail(@RequestParam("num") int num, Model model) {
		FreeBoardDTO dto = fs.detail(num);
		model.addAttribute("dto", dto);
		return "freeBoard/freeDetail";
	}
	
	
	
	@RequestMapping(value="/freeBoardRegistForm", method=RequestMethod.POST)
	public String freeBoardRegistForm(FreeBoardDTO dto, RedirectAttributes ra) {
		fs.regist(dto);
		ra.addFlashAttribute("msg", "게시물이 만들어짐");
		return "redirect:/freeBoard/freeList";
	}
	
	
	@RequestMapping(value="/freeBoardModifyForm", method=RequestMethod.POST)
	public String freeBoardModifyForm(FreeBoardDTO dto, Model model, RedirectAttributes ra) {
		fs.modify(dto);
		ra.addFlashAttribute("msg", "게시물이 수정됨");
		
		return "redirect:/freeBoard/freeList";
	}
	

	
	@RequestMapping(value="/freeDelete", method=RequestMethod.GET)
	public String freeDelete(@RequestParam("num") int num, RedirectAttributes ra) {
		fs.delete(num);
		ra.addFlashAttribute("msg", "게시물이 삭제됨");
		return "redirect:/freeBoard/freeList";
	}
}
