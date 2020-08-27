package com.nautestech.www.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nautestech.www.model.Batch;
import com.nautestech.www.model.Session;
import com.nautestech.www.model.Users;
import com.nautestech.www.serviceImpl.MuService;
import com.nautestech.www.serviceImpl.UsersService;
import com.nautestech.www.util.listExcelDownload;

import utils.Utils;



@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UsersService uService;
	
	@Autowired
	MuService muService;
	
	@Value("${RecCount}")
	int RecCount;
	
	
	private static Logger main_logger = LogManager.getLogger(UserController.class);
	private static Logger user_logger = LogManager.getLogger("user_log");
	
	
	
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/user_page", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		main_logger.info("welcome to UserPage");
		return "recording/user_page";
    }
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER","ROLE_ENDUSER"})
	@RequestMapping(value = "/user_logout", method= {RequestMethod.GET, RequestMethod.POST})
    public String user_logout(Authentication authentication) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		param.put("emp_id", authentication.getName());
		param.put("result", "logout");
		user_logger.info("user_logout -> "+authentication.getName()+" : "+param.toString());
		uService.setInsertListen_log(param);
		return "redirect:/logout";
    }
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER","ROLE_ENDUSER"})
	@RequestMapping(value = "/user_logout_ajax", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void user_logout_ajax(Authentication authentication) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		param.put("emp_id", authentication.getName());
		param.put("result", "logout");
		user_logger.info("user_logout -> "+authentication.getName()+" : "+param.toString());
		uService.setInsertListen_log(param);
    }
	
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER","ROLE_ENDUSER"})
	@RequestMapping(value = "/user_branch_get", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public List<Batch> user_branch_get(
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd
    		,Authentication authentication) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		Session user = (Session) authentication.getDetails();
		if(user.getEmp_id().equals("admin")) {//어드민일때 
			param.put("branch_cd", branch_cd);
		}else if(user.getAuth_cd().equals("12") || user.getAuth_cd().equals("13")) {
			param.put("branch_cd", user.getBranch_cd());
		}else {
			param.put("branch_cd", branch_cd);
		}
		user_logger.info("user_branch_get -> "+authentication.getName()+" : "+param.toString());
		return uService.getListBranch(param);
    }
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/user_branch_insert", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public void user_branch_insert(
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
    		@RequestParam(value="branch_nm", required=false, defaultValue="")String branch_nm,
    		Authentication authentication
    		) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		param.put("branch_cd", branch_cd);
		param.put("branch_nm", branch_nm);
		HashMap<String, Object> param1 = new HashMap<>();
		param1.put("emp_id", authentication.getName());
		param1.put("result", "branch_insert");
		uService.setInsertListen_log(param1);
		user_logger.info("user_branch_insert -> "+authentication.getName()+" : "+param.toString());
		uService.setInsertBranch(param);
    }
	
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/user_branch_delete", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public String user_branch_delete(
    		@RequestParam(value="arr[]", required=false, defaultValue="")String[] arr,
    		Authentication authentication
    		) throws JsonProcessingException{
		HashMap<String, Object> param;
		List<Users> users;
		for(int i=0; i<arr.length; i++) {
			param = new HashMap<>();
			param.put("branch_cd", arr[i]);
			param.put("pagesize", 10);
			param.put("pagestart", 0);
			users = uService.getView(param);
			if(users.size()>0) {
				return "false";
			}
		}
		param = new HashMap<>();
		param.put("branch_cd", arr);
		user_logger.info("user_branch_delete -> "+authentication.getName()+" : "+param.toString());
		uService.setBranchDelete(param);
		HashMap<String, Object> param1 = new HashMap<>();
		param1.put("emp_id", authentication.getName());
		param1.put("result", "branch_delete");
		uService.setInsertListen_log(param1);
		return "true";
	}
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_delete", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public void user_delete(
    		@RequestParam(value="arr[]", required=false, defaultValue="")String[] arr,
    		Authentication authentication
    		) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		param.put("tel_no", arr);
		user_logger.info("user_delete -> "+authentication.getName()+" : "+param.toString());
		uService.setDelete(param);
		muService.setDeleteMrecordUser(param);
		HashMap<String, Object> param1 = new HashMap<>();
		param1.put("emp_id", authentication.getName());
		param1.put("result", "user_delete");
		uService.setInsertListen_log(param1);
	}
	
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/user_edit_get", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public HashMap<String, Object> user_edit_get(
    		@RequestParam(value="tel_no", required=false, defaultValue="")String tel_no,
    		Authentication authentication
    		) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		HashMap<String, Object> param1 = new HashMap<>();
		
		param.put("tel_no", tel_no);
		param.put("pagesize", 1);
		param.put("pagestart", 0);
		List<Users> users = uService.getView(param);
		Session user = (Session) authentication.getDetails();
		user_logger.info("user_edit_get -> "+authentication.getName()+" : "+param.toString());
		param = new HashMap<>();
		
		if(user.getAuth_cd().equals("12") || user.getAuth_cd().equals("13")) {
			param.put("branch_cd", user.getBranch_cd());
		}

		List<Batch> batch = uService.getListBranch(param);
		Users u = users.get(0);
		param1.put("user_result", u);
		param1.put("batch", batch);
		return param1;
	}
	
	
	
	
	
	
	
	
	
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN"})
	@RequestMapping(value = "/user_page_ajax", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public HashMap<String, Object> user_page_ajax(Model model,
			@RequestParam(value="tel_no", required=false, defaultValue="")String tel_no,
			@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
			@RequestParam(value="rec_type", required=false, defaultValue="")String rec_type,
			@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
    		@RequestParam(value="pagenum", required=false, defaultValue="")int pagenum,
    		@RequestParam(value="pagesize", required=false, defaultValue="")int pagesize,
    		@RequestParam(value="recordstartindex", required=false, defaultValue="")int recordstartindex,
    		@RequestParam(value="recordendindex", required=false, defaultValue="")int recordendindex,
    		Authentication authentication) throws JsonProcessingException{
		Session user = (Session) authentication.getDetails();
		
		listExcelDownload format = new listExcelDownload();
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		auth_cd = Utils.authFormat(auth_cd);
		HashMap<String, Object> param = new HashMap<>();
		HashMap<String, Object> param1 = new HashMap<>();
		param.put("pagesize", pagesize);
		param.put("pagestart", recordstartindex);
		param.put("tel_no_chk", tel_no);
		param.put("branch_cd", branch_cd);
		if(user.getAuth_cd().equals("12")) {
			param.put("branch_cd", user.getBranch_cd());
		}else if(user.getAuth_cd().equals("13")) {
			param.put("branch_cd", user.getBranch_cd());
			param.put("emp_id", user.getEmp_id());
		}
		param.put("rec_type", rec_type);
		param.put("auth_cd", auth_cd);
		user_logger.info("user_page_ajax -> "+authentication.getName()+" : "+param.toString());
		List<Users> users = uService.getView(param);
		if(users.size()!=0) { 
			int total = uService.getListCount(param);
			if(user.getAuth_cd().equals("12")) {
				param1.put("total", total-1);
			}else {
				param1.put("total", total);
			}
			for(int i=0; i<users.size(); i++) {
				if(user.getAuth_cd().equals("12") && users.get(i).getEmp_id().equals("admin")) {
					users.remove(i);
				}
				if(user.getAuth_cd().equals("12")) {
					users.get(i).setNum(total-i-recordstartindex-1);
				}else {
					users.get(i).setNum(total-i-recordstartindex);
				}
				users.get(i).setUser_detail("<button type='button' class= 'btn btn-default' data-toggle= 'modal' data-target='#user_add' name='"+users.get(i).getTel_no()+"' value='user_view'>상세보기</button>");
				users.get(i).setRec_type(format.recFormat(users.get(i).getRec_type()));
				users.get(i).setUser_checkbox("<input type='checkbox' class='user_checkbox' value='"+users.get(i).getTel_no()+"'>");
				users.get(i).setAuth_cd(format.authFormat(users.get(i).getAuth_cd()));
			}
		}
		param1.put("Rows", users);
		return param1;
    }
	
	
	
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_Insert", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String user_Insert(
			@RequestParam(value="emp_id", required=false, defaultValue="")String emp_id,
			@RequestParam(value="tel_no", required=false, defaultValue="")String tel_no,
			@RequestParam(value="tel_no_070", required=false, defaultValue="")String tel_no_070,
			@RequestParam(value="emp_nm", required=false, defaultValue="")String emp_nm,
			@RequestParam(value="pass", required=false, defaultValue="")String pass,
			@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
			@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
			@RequestParam(value="rec_type", required=false, defaultValue="")String rec_type,
			@RequestParam(value="down_type", required=false, defaultValue="")String down_type,
			Authentication authentication
			) throws JsonProcessingException{
		 
		HashMap<String, Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("pagesize", 10);
		param.put("pagestart", 0);
		List<Users> user = uService.getView(param);
		if(user.size()>0) {
			return "return 1";
		}
		
		param = new HashMap<>();
		param.put("tel_no", tel_no);
		param.put("pagesize", 10);
		param.put("pagestart", 0);
		user = uService.getView(param);
		if(user.size()>0) {
			return "return 2";
		}
		
		param = new HashMap<>();
		param.put("tel_no_070", tel_no_070);
		param.put("pagesize", 10);
		param.put("pagestart", 0);
		user = uService.getView(param);
		if(user.size()>0) {
			return "return 3";
		}
		
		
		if(!rec_type.equals("N") && auth_cd.equals("13")) {
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("pagesize", 1000);
			param1.put("pagestart", 0);
			param1.put("auth_cd", "13");
			List<Users> users = uService.getView(param1);
			int count1 = 0;
			for(int i=0; i<users.size(); i++) {
				if(!users.get(i).getRec_type().equals("N")) {
					count1++;
				}
			}
			if(count1>=RecCount) {
				return "return 4";
			}
		}
		if(auth_cd.equals("15")) {
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("pagesize", 1000);
			param1.put("pagestart", 0);
			param1.put("auth_cd", "15");
			List<Users> users = uService.getView(param1);
			if(users.size()>=2) {
				return "return 4";
			}
		}
		
		
		
		
		
		String rec_regdate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA).format(new Date());
		param = new HashMap<>();
		HashMap<String, Object> param1 = new HashMap<>();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		param.put("emp_id", emp_id);
		param.put("tel_no", tel_no);
		param.put("tel_no_070", tel_no_070);
		param.put("emp_nm", emp_nm);
		param.put("pass", passwordEncoder.encode(pass));
		param.put("auth_cd", auth_cd);
		param.put("branch_cd", branch_cd);
		param.put("rec_type", rec_type);
		param.put("down_type", down_type);
		param.put("rec_regdate", rec_regdate);
		
		
		String u_level = "3";
		String u_subid = "";
		if (auth_cd.equals("00")) {
			u_level = "1";
			u_subid = "root";
		} else if (auth_cd.equals("13")) {
			u_level = "3";
			u_subid = branch_cd;
		} else if (auth_cd.equals("11") || auth_cd.equals("12")) {
			u_level = "2";
			u_subid = "admin";
		}
		
		param1.put("u_id", tel_no_070);
		param1.put("u_subid", u_subid);
		param1.put("u_level", u_level);
		param1.put("u_name", emp_nm);
		param1.put("u_tel1", tel_no);
		param1.put("u_email", "/data"+"1"+"/nr_"+branch_cd+"/");
		param1.put("u_pwd", pass);
		param1.put("u_flag", rec_type);
		
		user_logger.info("user_Insert ->"+authentication.getName()+" : "+param.toString());
		uService.setInsert(param);
		muService.setInsertMrecordUser(param1);
		
		HashMap<String, Object> param2 = new HashMap<>();
		param2.put("emp_id", authentication.getName());
		param2.put("result", "user_Insert");
		uService.setInsertListen_log(param2);
		return "true";
    }
	
	
	@Secured({"ROLE_ADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER"})
	@RequestMapping(value = "/user_update", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String user_update(
			@RequestParam(value="emp_id", required=false, defaultValue="")String emp_id,
			@RequestParam(value="tel_no", required=false, defaultValue="")String tel_no,
			@RequestParam(value="tel_no_070", required=false, defaultValue="")String tel_no_070,
			@RequestParam(value="emp_nm", required=false, defaultValue="")String emp_nm,
			@RequestParam(value="pass", required=false, defaultValue="")String pass,
			@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
			@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
			@RequestParam(value="rec_type", required=false, defaultValue="")String rec_type,
			@RequestParam(value="down_type", required=false, defaultValue="")String down_type,
			Authentication authentication
			) throws JsonProcessingException{
		
		
		
		if(!rec_type.equals("N") && auth_cd.equals("13")) {
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("pagesize", 1000);
			param1.put("pagestart", 0);
			param1.put("auth_cd", "13");
			List<Users> users = uService.getView(param1);
			HashMap<String, Object> param2 = new HashMap<>();
			param2.put("pagesize", 1000);
			param2.put("pagestart", 0);
			param2.put("tel_no", tel_no);
			List<Users> users_check = uService.getView(param2);
			
			
			int count1 = 0;
			for(int i=0; i<users.size(); i++) {
				if(!users.get(i).getRec_type().equals("N")) {
					count1++;
				}
			}
			if(!users_check.get(0).getRec_type().equals("N")) {
				count1--;
			}
			if(count1>=RecCount) {
				return "false";
			}
		}
		if(auth_cd.equals("15")) {
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("pagesize", 1000);
			param1.put("pagestart", 0);
			param1.put("auth_cd", "15");
			List<Users> users = uService.getView(param1);
			HashMap<String, Object> param2 = new HashMap<>();
			param2.put("pagesize", 1000);
			param2.put("pagestart", 0);
			param2.put("tel_no", tel_no);
			List<Users> users_check = uService.getView(param2);
			int count1 = 0;
			for(int i=0; i<users.size(); i++) {
				if(users.get(i).getAuth_cd().equals("15")) {
					count1++;
				}
			}
			if(!users_check.get(0).getAuth_cd().equals("15")) {
				count1--;
			}
			if(count1>=2) {
				return "false";
			}
		}
		
		
		
		
		
		
		
		String rec_type_regdate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA).format(new Date());
		HashMap<String, Object> param = new HashMap<>();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(pass);
		param.put("tel_no", tel_no);
		param.put("login_date", "");
		param.put("emp_nm", emp_nm);
		if(pass.equals("")) {
			param.put("pass", "");
		}else {
			param.put("pass", passwordEncoder.encode(pass));
		}
		param.put("auth_cd", auth_cd);
		param.put("branch_cd", branch_cd);
		param.put("rec_type", rec_type);
		param.put("down_type", down_type);
		param.put("rec_type_regdate", rec_type_regdate);
		user_logger.info("user_update -> "+authentication.getName()+" : "+param.toString());
		
		
		
		
		String u_level = "3";
		String u_subid = "";
		if (auth_cd.equals("00")) {
			u_level = "1";
			u_subid = "root";
		} else if (auth_cd.equals("13")) {
			u_level = "3";
			u_subid = branch_cd;
		} else if (auth_cd.equals("11") || auth_cd.equals("12")) {
			u_level = "2";
			u_subid = "admin";
		}
		
		HashMap<String, Object> param1 = new HashMap<>();
		param1.put("u_id", tel_no_070);
		param1.put("u_subid", u_subid);
		param1.put("u_level", u_level);
		param1.put("u_name", emp_nm);
		param1.put("u_tel1", tel_no);
		param1.put("u_email", "/data"+"1"+"/"+branch_cd+"/");
		param1.put("u_pwd", pass);
		param1.put("u_flag", rec_type);
		
		
		
		muService.setInsertMrecordUser(param1);
		
		uService.setUpdate(param);
		
		HashMap<String, Object> param2 = new HashMap<>();
		param2.put("emp_id", authentication.getName());
		param2.put("result", "user_update");
		uService.setInsertListen_log(param2);
		
		return "true";
    }
	
	
}
