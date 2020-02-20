package com.study03.www.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    @RequestMapping(value = "/admin/admin01", method= {RequestMethod.GET, RequestMethod.POST})
    public String admin01(Model model) {
		HashMap<String, Object> param = new HashMap<>();
		List<Board> result = bService.getList(param);
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
