package com.nautestech.www.controller;

import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nautestech.www.model.Call;
import com.nautestech.www.model.Users;
import com.nautestech.www.serviceImpl.CallService;
import com.nautestech.www.serviceImpl.UsersService;
import com.nautestech.www.util.Command;
import com.nautestech.www.util.ZipDownload;
import com.nautestech.www.util.listExcelDownload;

import utils.Utils;

@Controller
@RequestMapping(value = "/call")
public class CallController {
 
		
	@Value("${statisticsLimit}")
	int statisticsLimit;
	
	@Value("${callhistoryYMD}")
	Boolean callhistoryYMD;
	
	@Autowired
	UsersService uService;
	
	@Autowired
	CallService cService;
	
	
	@Value("${isMxxMode}")
	String isMxxMode;
	
	
	@RequestMapping(value = "/zip", method= {RequestMethod.GET, RequestMethod.POST})
	public String zip(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		HashMap<String, Object> param1 = new HashMap<>();
		param1.put("emp_id", authentication.getName());
		param1.put("result", "zip");
		uService.setInsertListen_log(param1);
		
		
		
		String arr = request.getParameter("arr");
		String[] sp_arr = arr.split(",");
		ZipDownload zip_class = new ZipDownload();
		String active = "active page_open";
		request.setAttribute("callhistoryYMD", callhistoryYMD);
		request.setAttribute("call_active", active);
		if(sp_arr.length>1) {
			zip_class.down(request, response, sp_arr);
		}
		return "/recording/call_page";
	}
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/call_page", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){
		String active = "active page_open";
		model.addAttribute("call_active", active);
		model.addAttribute("callhistoryYMD", callhistoryYMD);
		return "recording/call_page";
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
    		,Model model,
    		Authentication authentication) {
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		HashMap<String, Object> param1 = new HashMap<>();
		param1.put("emp_id", authentication.getName());
		param1.put("result", "xlsx");
		uService.setInsertListen_log(param1);
		
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
		//엑셀다운할때는 전체를 다운해야되니까 즉 페이징은 10,20개씩보여줄려고 리미트를 거는데
		//엑셀다운할때는 페이징된10,20개를다운할필요업으니까 xlsx가 값이있으면 전체다운 없으면 그냥검색이니 리미트걸어서 검색
		param.put("xlsx", "true");
		param.put("limit", statisticsLimit);
		List<Call> call = null;
		if(callhistoryYMD) {
			String startYYYYMM = bday.substring(0,7);
			String endYYYYMM = eday.substring(0,7);
			
			startYYYYMM = startYYYYMM.replace(":", "");
			endYYYYMM = endYYYYMM.replace(":", "");
			param.put("startYYYYMM", startYYYYMM);
			param.put("endYYYYMM", endYYYYMM);
			call = cService.getViewYYYYMM(param);
		}else {
			call = cService.getView(param);
		}
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
		System.out.println(emp);
		System.out.println(branch_cd);
		System.out.println(auth_cd);
		param.put("emp_id", emp);
		param.put("emp_nm", emp);
		param.put("branch_cd", branch_cd);
		param.put("auth_cd", auth_cd);
		param.put("pagesize", 100);
		param.put("pagestart", 0);
		param.put("xlsx", "false");
		List<Users> users = uService.getView(param);
		return users;
    }
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/callSearch", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public HashMap<String, Object> callSearch(
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
    		@RequestParam(value="called_attr", required=false, defaultValue="")String called_attr,
    		@RequestParam(value="pagenum", required=false, defaultValue="")int pagenum,
    		@RequestParam(value="pagesize", required=false, defaultValue="")int pagesize,
    		@RequestParam(value="recordstartindex", required=false, defaultValue="")int recordstartindex,
    		@RequestParam(value="recordendindex", required=false, defaultValue="")int recordendindex
    		) throws ParseException{ 
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
		param.put("pagesize", pagesize);
		param.put("pagestart", recordstartindex);
		param.put("xlsx", "false");
		HashMap<String, Object> param1 = new HashMap<>();
		List<Call> call = cService.getView(param);
		
		listExcelDownload format = new listExcelDownload();
		if(call.size()!=0) {
			int total = cService.getListCount(param);
			param1.put("total", total);
			for(int i=0; i<call.size(); i++) {
				call.get(i).setDirname("<label class='check_label'><input type='checkbox' class='checkbox_name' value='"+call.get(i).getDirname()+""+call.get(i).getFname()+"'></label>");
				call.get(i).setCall_date(format.dateFormat(call.get(i).getBtime()));
				call.get(i).setCall_hour(format.hourFormat(call.get(i).getBtime(), call.get(i).getEtime()));
				call.get(i).setCall_time(format.timeFormat(call.get(i).getBtime(), call.get(i).getEtime()));
				call.get(i).setRec_type(format.recFormat(call.get(i).getRec_type()));
				call.get(i).setNum(recordstartindex+i+1);
			}
		}
		param1.put("Rows", call);
		return param1;
    }
	
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/callSearch_YYYYMMDD", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public HashMap<String, Object> callSearch_YYYYMMDD(
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
    		@RequestParam(value="called_attr", required=false, defaultValue="")String called_attr,
    		@RequestParam(value="pagenum", required=false, defaultValue="")int pagenum,
    		@RequestParam(value="pagesize", required=false, defaultValue="")int pagesize,
    		@RequestParam(value="recordstartindex", required=false, defaultValue="")int recordstartindex,
    		@RequestParam(value="recordendindex", required=false, defaultValue="")int recordendindex
    		) throws Exception{ 
		String startYYYYMM = bday.substring(0,7);
		String endYYYYMM = eday.substring(0,7);
		
		startYYYYMM = startYYYYMM.replace(":", "");
		endYYYYMM = endYYYYMM.replace(":", "");
		
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		HashMap<String, Object> param = new HashMap<>();
		param.put("xlsx", "false");
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
		param.put("pagesize", pagesize);
		param.put("pagestart", recordstartindex);
		param.put("xlsx", "false");
		System.out.println(bday +"~"+eday);
		HashMap<String, Object> param1 = new HashMap<>();
		List<Call> call = cService.getViewYYYYMM(param);
		listExcelDownload format = new listExcelDownload();
		Command cmd = new Command();
		if(call.size()!=0) {
			int total = cService.getListCountYYYYMM(param);
			param1.put("total", total);
			for(int i=0; i<call.size(); i++) {
				//폴더 존재여부를 먼저 체크한다.
				File checkF = new File(call.get(i).getDirname());
				
				if(!checkF.exists())
				{
					cmd.chkFolder(call.get(i).getDirname());
				}
				cmd.CopyMXX(call.get(i).getFname(), call.get(i).getDirname());
				cmd.ConvertMXX(call.get(i).getFname(), call.get(i).getDirname(),"0");
				call.get(i).setDirname("<label class='check_label'><input type='checkbox' class='checkbox_name' value='"+call.get(i).getDirname()+""+call.get(i).getFname()+"'></label>");
				call.get(i).setCall_date(format.dateFormat(call.get(i).getBtime()));
				call.get(i).setCall_hour(format.hourFormat(call.get(i).getBtime(), call.get(i).getEtime()));
				call.get(i).setCall_time(format.timeFormat(call.get(i).getBtime(), call.get(i).getEtime()));
				call.get(i).setRec_type(format.recFormat(call.get(i).getRec_type()));
				call.get(i).setNum(recordstartindex+i+1);
			}
		}
		param1.put("Rows", call);
		return param1;
    }
	
}
