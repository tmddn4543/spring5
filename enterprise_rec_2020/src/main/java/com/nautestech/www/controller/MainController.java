package com.nautestech.www.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.nautestech.www.serviceImpl.CallService;
import com.nautestech.www.serviceImpl.UsersService;
import com.nautestech.www.util.ZipDownload;
import com.nautestech.www.util.listExcelDownload;

import utils.Utils;

@Controller
@RequestMapping(value = "/admin")
public class MainController {
 
		
	@Value("${statisticsLimit}")
	int statisticsLimit;
	
	@Value("${callhistoryYMD}")
	Boolean callhistoryYMD;
	
	@Autowired
	UsersService uService;
	
	@Autowired
	CallService cService;
	
	
	
	@RequestMapping(value = "/zip", method= {RequestMethod.GET, RequestMethod.POST})
	public String zip(HttpServletRequest request, HttpServletResponse response) {
		String arr = request.getParameter("arr");
		String[] sp_arr = arr.split(",");
		ZipDownload zip_class = new ZipDownload();
		zip_class.down(request, response, sp_arr);
		return "/recording/index";
	}
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/index", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){
		model.addAttribute("callhistoryYMD", callhistoryYMD);
		return "recording/index";
    }
	
	@Secured({"ROLE_ADMIN"})
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
    		@RequestParam(value="end_talk_time", required=false, defaultValue="")String end_talk_time,
    		@RequestParam(value="caller_attr", required=false, defaultValue="")String caller_attr,
    		@RequestParam(value="called_attr", required=false, defaultValue="")String called_attr
    		,Model model) {
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		HashMap<String, Object> param = new HashMap<>();
		param.put("emp_id", emp);
		param.put("emp_nm", emp);
		param.put("branch_cd", branch_cd);
		param.put("bday", bday);
		param.put("eday", eday);
		param.put("caller", caller);
		param.put("called", called);
		param.put("caller_attr", caller_attr);
		param.put("called_attr", called_attr);
		param.put("rec_type", rec_type);
		param.put("start_talk_time", start_talk_time);
		param.put("end_talk_time", end_talk_time);
		param.put("limit", statisticsLimit);
		List<Call> call = cService.getView(param);
		System.out.println(call.size());
		model.addAttribute("list", call);
		return new listExcelDownload();
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
    		@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
    		@RequestParam(value="bday", required=false, defaultValue="")String bday,
    		@RequestParam(value="eday", required=false, defaultValue="")String eday,
    		@RequestParam(value="caller", required=false, defaultValue="")String caller,
    		@RequestParam(value="called", required=false, defaultValue="")String called,
    		@RequestParam(value="rec_type", required=false, defaultValue="")String rec_type,
    		@RequestParam(value="start_talk_time", required=false, defaultValue="")String start_talk_time,
    		@RequestParam(value="end_talk_time", required=false, defaultValue="")String end_talk_time,
    		@RequestParam(value="caller_attr", required=false, defaultValue="")String caller_attr,
    		@RequestParam(value="called_attr", required=false, defaultValue="")String called_attr
    		){ 
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		HashMap<String, Object> param = new HashMap<>();
		param.put("emp_id", emp);
		param.put("emp_nm", emp);
		param.put("branch_cd", branch_cd);
		param.put("bday", bday);
		param.put("eday", eday);
		param.put("caller", caller);
		param.put("called", called);
		param.put("caller_attr", caller_attr);
		param.put("called_attr", called_attr);
		param.put("rec_type", rec_type);
		param.put("start_talk_time", start_talk_time);
		param.put("end_talk_time", end_talk_time);
		param.put("limit", statisticsLimit);
		List<Call> call = cService.getView(param);
		return call;
    }
	
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/callSearch_YYYYMMDD", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public List<Call> callSearch_YYYYMMDD(
    		@RequestParam(value="emp", required=false, defaultValue="")String emp,
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
    		@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
    		@RequestParam(value="bday", required=false, defaultValue="")String bday,
    		@RequestParam(value="eday", required=false, defaultValue="")String eday,
    		@RequestParam(value="caller", required=false, defaultValue="")String caller,
    		@RequestParam(value="called", required=false, defaultValue="")String called,
    		@RequestParam(value="rec_type", required=false, defaultValue="")String rec_type,
    		@RequestParam(value="start_talk_time", required=false, defaultValue="")String start_talk_time,
    		@RequestParam(value="end_talk_time", required=false, defaultValue="")String end_talk_time,
    		@RequestParam(value="caller_attr", required=false, defaultValue="")String caller_attr,
    		@RequestParam(value="called_attr", required=false, defaultValue="")String called_attr
    		){ 
		String startYYYYMM = bday.substring(0,7);
		String endYYYYMM = eday.substring(0,7);
		
		startYYYYMM = startYYYYMM.replace(":", "");
		endYYYYMM = endYYYYMM.replace(":", "");
		
		System.out.println(startYYYYMM);
		System.out.println(endYYYYMM);
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		HashMap<String, Object> param = new HashMap<>();
		param.put("emp_id", emp);
		param.put("emp_nm", emp);
		param.put("branch_cd", branch_cd);
		param.put("bday", bday);
		param.put("eday", eday);
		param.put("startYYYYMM",startYYYYMM);
		param.put("endYYYYMM",endYYYYMM);
		param.put("caller", caller);
		param.put("called", called);
		param.put("caller_attr", caller_attr);
		param.put("called_attr", called_attr);
		param.put("rec_type", rec_type);
		param.put("start_talk_time", start_talk_time);
		param.put("end_talk_time", end_talk_time);
		param.put("limit", statisticsLimit);
		List<Call> call = cService.getViewYYYYMM(param);
		return call;
    }
	
}
