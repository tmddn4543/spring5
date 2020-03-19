package com.nautestech.www.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nautestech.www.model.Call;
import com.nautestech.www.model.Users;
import com.nautestech.www.serviceImpl.CallService;
import com.nautestech.www.serviceImpl.UsersService;

import utils.Utils;

@Controller
@RequestMapping(value = "/admin")
public class MainController {

	@Value("${webDbAccessIP}")
	String webDbAccessIP;
	
	@Value("${webSectionRecModeUse}")
	boolean webSectionRecModeUse;
	
	@Value("${statisticsLimit}")
	int statisticsLimit;
	
	@Autowired
	UsersService uService;
	
	@Autowired
	CallService cService;
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/index", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(){
		return "utime/index";
    }
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/usersSearch", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public List<Users> usersSearch(
    		@RequestParam(value="emp", required=false, defaultValue="")String emp,
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
    		@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd
    		){
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		auth_cd = Utils.authFormat(auth_cd);
		HashMap<String, Object> param = new HashMap<>();
		param.put("emp_id", emp);
		param.put("emp_nm", emp);
		param.put("branch_cd", branch_cd);
		param.put("auth_cd", auth_cd);
		List<Users> users = uService.getView(param);
		return users;
    }
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/callSearch", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public List<Call> callSearch(
    		@RequestParam(value="emp", required=false, defaultValue="")String emp,
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
    		@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd
    		){
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		HashMap<String, Object> param = new HashMap<>();
		param.put("limit", statisticsLimit);
		List<Call> users = cService.getView(param);
		return users;
    }
	
}
