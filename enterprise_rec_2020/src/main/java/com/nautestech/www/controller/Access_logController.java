package com.nautestech.www.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nautestech.www.model.Callhistory_log;
import com.nautestech.www.serviceImpl.LogService;

@Controller
@RequestMapping(value = "/access_log")
public class Access_logController {
	
	@Autowired
	LogService lService;
	
	
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/access_log_page", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){
		String active = "active page_open";
		model.addAttribute("access_log_active", active);
		return "recording/access_log_page";
    }
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/access_log_page_ajax", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public HashMap<String, Object> user_page_ajax(Model model,
			@RequestParam(value="user_id", required=false, defaultValue="")String user_id,
    		@RequestParam(value="pagenum", required=false, defaultValue="")int pagenum,
    		@RequestParam(value="pagesize", required=false, defaultValue="")int pagesize,
    		@RequestParam(value="recordstartindex", required=false, defaultValue="")int recordstartindex,
    		@RequestParam(value="recordendindex", required=false, defaultValue="")int recordendindex) throws JsonProcessingException{
		String active = "active page_open";
		model.addAttribute("access_log_active", active);
		HashMap<String, Object> param = new HashMap<>();
		HashMap<String, Object> param1 = new HashMap<>();
		param.put("user_id", user_id);
		param.put("pagesize", pagesize);
		param.put("pagestart", recordstartindex);
		List<Callhistory_log> log = lService.getView(param);
		if(log.size()!=0) {
			int total = lService.getListCount(param);
			param1.put("total", total);
			for(int i=0; i<log.size(); i++) {
				log.get(i).setNum(recordstartindex+i+1);
			}
		}
		param1.put("Rows", log);
		return param1;
	}
	
	
}