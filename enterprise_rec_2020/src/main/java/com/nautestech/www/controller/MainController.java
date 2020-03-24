package com.nautestech.www.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.nautestech.www.model.Call;
import com.nautestech.www.model.Users;
import com.nautestech.www.model.listExcelDownload;
import com.nautestech.www.serviceImpl.CallService;
import com.nautestech.www.serviceImpl.UsersService;

import utils.Utils;

@Controller
@RequestMapping(value = "/admin")
public class MainController {
 
		
	@Value("${statisticsLimit}")
	int statisticsLimit;
	
	@Value("${callhistoryYMD}")
	boolean callhistoryYMD;
	
	@Autowired
	UsersService uService;
	
	@Autowired
	CallService cService;
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/index", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(){
		return "utime/index";
    }
	
	@RequestMapping(value = "/xlsxDownload", method= {RequestMethod.GET, RequestMethod.POST})
	public View down(@RequestParam(value="emp", required=false, defaultValue="")String emp,
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
    		@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
    		@RequestParam(value="bday", required=false, defaultValue="")String bday,
    		@RequestParam(value="eday", required=false, defaultValue="")String eday,
    		@RequestParam(value="caller", required=false, defaultValue="")String caller,
    		@RequestParam(value="called", required=false, defaultValue="")String called,
    		@RequestParam(value="rec_type", required=false, defaultValue="")String rec_type,
    		@RequestParam(value="start_talk_time", required=false, defaultValue="")String start_talk_time,
    		@RequestParam(value="end_talk_time", required=false, defaultValue="")String end_talk_time
    		,Model model) {
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		HashMap<String, Object> param = new HashMap<>();
		System.out.println("======================");
		System.out.println("emp : "+emp);
		System.out.println("branch_cd : "+branch_cd);
		System.out.println("bday : "+bday);
		System.out.println("eday : "+eday);
		System.out.println("caller : "+caller);
		System.out.println("called : "+called);
		System.out.println("rec_type : "+rec_type);
		System.out.println("start_talk_time : "+start_talk_time);
		System.out.println("end_talk_time : "+end_talk_time);
		param.put("emp_id", emp);
		param.put("emp_nm", emp);
		param.put("branch_cd", branch_cd);
		param.put("bday", bday);
		param.put("eday", eday);
		param.put("caller", caller);
		param.put("called", called);
		param.put("rec_type", rec_type);
		param.put("start_talk_time", start_talk_time);
		param.put("end_talk_time", end_talk_time);
		param.put("limit", statisticsLimit);
		List<Call> call = cService.getView(param);
		System.out.println(call.size());
		model.addAttribute("list", call);
		return new listExcelDownload();
	}
	
//	public View down(Model model){
//		return new listExcelDownload();
//    }
//	
	
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
    		@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
    		@RequestParam(value="bday", required=false, defaultValue="")String bday,
    		@RequestParam(value="eday", required=false, defaultValue="")String eday,
    		@RequestParam(value="caller", required=false, defaultValue="")String caller,
    		@RequestParam(value="called", required=false, defaultValue="")String called,
    		@RequestParam(value="rec_type", required=false, defaultValue="")String rec_type,
    		@RequestParam(value="start_talk_time", required=false, defaultValue="")String start_talk_time,
    		@RequestParam(value="end_talk_time", required=false, defaultValue="")String end_talk_time
    		){ 
		
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		HashMap<String, Object> param = new HashMap<>();
		System.out.println("emp : "+emp);
		System.out.println("branch_cd : "+branch_cd);
		System.out.println("bday : "+bday);
		System.out.println("eday : "+eday);
		System.out.println("caller : "+caller);
		System.out.println("called : "+called);
		System.out.println("rec_type : "+rec_type);
		System.out.println("start_talk_time : "+start_talk_time);
		System.out.println("end_talk_time : "+end_talk_time);
		param.put("emp_id", emp);
		param.put("emp_nm", emp);
		param.put("branch_cd", branch_cd);
		param.put("bday", bday);
		param.put("eday", eday);
		param.put("caller", caller);
		param.put("called", called);
		param.put("rec_type", rec_type);
		param.put("start_talk_time", start_talk_time);
		param.put("end_talk_time", end_talk_time);
		param.put("limit", statisticsLimit);
		List<Call> call = cService.getView(param);
		return call;
    }
	
}
