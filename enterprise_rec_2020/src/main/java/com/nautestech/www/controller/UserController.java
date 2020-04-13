package com.nautestech.www.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nautestech.www.model.Batch;
import com.nautestech.www.model.Users;
import com.nautestech.www.serviceImpl.UsersService;
import com.nautestech.www.util.listExcelDownload;



@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UsersService uService;
	
	
	
	///user/user_logout
	
	
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_page", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		String active = "active page_open";
		model.addAttribute("user_active", active);
		return "recording/user_page";
    }
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_logout", method= {RequestMethod.GET, RequestMethod.POST})
    public String user_logout(Authentication authentication) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		param.put("emp_id", authentication.getName());
		param.put("result", "logout");
		uService.setInsertListen_log(param);
		return "redirect:/logout";
    }
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_branch_get", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public List<Batch> user_branch_get(
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd
    		) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		param.put("branch_cd", branch_cd);
		return uService.getListBranch(param);
    }
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_branch_insert", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public void user_branch_insert(
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
    		@RequestParam(value="branch_nm", required=false, defaultValue="")String branch_nm
    		) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		param.put("branch_cd", branch_cd);
		param.put("branch_nm", branch_nm);
		uService.setInsertBranch(param);
    }
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_branch_delete", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public String user_branch_delete(
    		@RequestParam(value="arr[]", required=false, defaultValue="")String[] arr
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
		uService.setBranchDelete(param);
		return "true";
	}
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_delete", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public void user_delete(
    		@RequestParam(value="arr[]", required=false, defaultValue="")String[] arr
    		) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		param.put("tel_no", arr);
		uService.setDelete(param);
	}
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_edit_get", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public HashMap<String, Object> user_edit_get(
    		@RequestParam(value="tel_no", required=false, defaultValue="")String tel_no
    		) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		HashMap<String, Object> param1 = new HashMap<>();
		param.put("tel_no", tel_no);
		param.put("pagesize", 1);
		param.put("pagestart", 0);
		List<Users> users = uService.getView(param);
		List<Batch> batch = uService.getListBranch(param);
		Users u = users.get(0);
		param1.put("user_result", u);
		param1.put("batch", batch);
		return param1;
	}
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_page_ajax", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public HashMap<String, Object> user_page_ajax(Model model,
			@RequestParam(value="tel_no", required=false, defaultValue="")String tel_no,
			@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
    		@RequestParam(value="pagenum", required=false, defaultValue="")int pagenum,
    		@RequestParam(value="pagesize", required=false, defaultValue="")int pagesize,
    		@RequestParam(value="recordstartindex", required=false, defaultValue="")int recordstartindex,
    		@RequestParam(value="recordendindex", required=false, defaultValue="")int recordendindex) throws JsonProcessingException{
		String active = "active page_open";
		model.addAttribute("user_active", active);
		listExcelDownload format = new listExcelDownload();
		
		HashMap<String, Object> param = new HashMap<>();
		HashMap<String, Object> param1 = new HashMap<>();
		param.put("pagesize", pagesize);
		param.put("pagestart", recordstartindex);
		param.put("tel_no", tel_no);
		param.put("branch_cd", branch_cd);
		
		List<Users> users = uService.getView(param);
		if(users.size()!=0) {
			int total = uService.getListCount(param);
			param1.put("total", total);
			for(int i=0; i<users.size(); i++) {
				users.get(i).setNum(recordstartindex+i+1);
				users.get(i).setUser_detail("<button type='button' class= 'btn btn-default' data-toggle= 'modal' data-target='#user_add' name='"+users.get(i).getTel_no()+"' value='user_view'>상세보기</button>");
				users.get(i).setRec_type(format.recFormat(users.get(i).getRec_type()));
				users.get(i).setUser_checkbox("<input type='checkbox' class='user_checkbox' value='"+users.get(i).getTel_no()+"'>");
				users.get(i).setAuth_cd(format.authFormat(users.get(i).getAuth_cd()));
			}
		}
		param1.put("Rows", users);
		return param1;
    }
	
	
	//user_idCheck
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_Check", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String user_idCheck(
			@RequestParam(value="emp_id", required=false, defaultValue="")String emp_id,
			@RequestParam(value="tel_no", required=false, defaultValue="")String tel_no,
			@RequestParam(value="tel_no_070", required=false, defaultValue="")String tel_no_070
			) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("tel_no", tel_no);
		param.put("tel_no_070", tel_no_070);
		param.put("pagesize", 100);
		param.put("pagestart", 0);
		List<Users> user = uService.getView(param);
		if(user.size()==0) {
			return "true";
		}else {
			return "false";
		}
    }
	
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_Insert", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void user_Insert(
			@RequestParam(value="emp_id", required=false, defaultValue="")String emp_id,
			@RequestParam(value="tel_no", required=false, defaultValue="")String tel_no,
			@RequestParam(value="tel_no_070", required=false, defaultValue="")String tel_no_070,
			@RequestParam(value="emp_nm", required=false, defaultValue="")String emp_nm,
			@RequestParam(value="pass", required=false, defaultValue="")String pass,
			@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
			@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
			@RequestParam(value="rec_type", required=false, defaultValue="")String rec_type,
			@RequestParam(value="down_type", required=false, defaultValue="")String down_type
			) throws JsonProcessingException{
		String rec_regdate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA).format(new Date());
		HashMap<String, Object> param = new HashMap<>();
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
		uService.setInsert(param);
    }
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_update", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void user_update(
			@RequestParam(value="emp_id", required=false, defaultValue="")String emp_id,
			@RequestParam(value="tel_no", required=false, defaultValue="")String tel_no,
			@RequestParam(value="tel_no_070", required=false, defaultValue="")String tel_no_070,
			@RequestParam(value="emp_nm", required=false, defaultValue="")String emp_nm,
			@RequestParam(value="pass", required=false, defaultValue="")String pass,
			@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
			@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
			@RequestParam(value="rec_type", required=false, defaultValue="")String rec_type,
			@RequestParam(value="down_type", required=false, defaultValue="")String down_type
			) throws JsonProcessingException{
		String rec_type_regdate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA).format(new Date());
		HashMap<String, Object> param = new HashMap<>();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(pass);
		param.put("tel_no", tel_no);
		param.put("login_date", "");
		param.put("emp_nm", emp_nm);
		param.put("pass", passwordEncoder.encode(pass));
		param.put("auth_cd", auth_cd);
		param.put("branch_cd", branch_cd);
		param.put("rec_type", rec_type);
		param.put("down_type", down_type);
		param.put("rec_type_regdate", rec_type_regdate);
		uService.setUpdate(param);
    }
	
	
}
