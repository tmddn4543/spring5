package com.study03.www.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study03.www.model.Board;
import com.study03.www.serviceImpl.BoardService;

@Controller
public class Test02Controller {
	  
	
	@Autowired
	BoardService bService;
	
	@RequestMapping(value = "/", method= {RequestMethod.GET, RequestMethod.POST})
    public String loginPage(){
		return "utime/index";
    }
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(value = "/admin/boardEdit/{seq}")
	@ResponseBody
	public Board boardEdit(@PathVariable("seq") String seq) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("seq", seq);
		return bService.getView(param);
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(value = "/admin/boardUpdate", method = {RequestMethod.POST})
	@ResponseBody
	public void boardUpdate(@RequestBody Board board) {
		
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/admin/admin01", method= {RequestMethod.GET, RequestMethod.POST})
    public String admin01(Model model) {
		HashMap<String, Object> param = new HashMap<>();
		List<Board> result = bService.getList(param);
		StringBuffer asd;
		for(int i=0; i<result.size(); i++) {
			asd = new StringBuffer(result.get(i).getRegdate());
			asd.insert(4, "-");
			asd.insert(7, "-");
			asd.insert(10, " ");
			asd.insert(13, ":");
			asd.insert(16, ":");
			result.get(i).setRegdate(String.valueOf(asd));
		}
		model.addAttribute("board", result);
        return "utime/admin01";
    }
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/admin/admin02", method= {RequestMethod.GET, RequestMethod.POST})
    public String admin02(Model model) {
        return "utime/admin02";
    }
	
	@Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/admin03", method= {RequestMethod.GET, RequestMethod.POST})
    public String admin03(Model model) {
        return "utime/admin03";
    }
}
