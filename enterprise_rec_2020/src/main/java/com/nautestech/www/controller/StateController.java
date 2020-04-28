package com.nautestech.www.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nautestech.www.model.Stat;
import com.nautestech.www.serviceImpl.StatService;

@Controller
@RequestMapping(value = "/state")
public class StateController {

	@Autowired
	StatService sService;
	
	
	private static Logger main_logger = LogManager.getLogger(StateController.class);
	private static Logger state_logger = LogManager.getLogger("state_log");
	
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/state_page", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){
		main_logger.info("welcome to State_Page");
		return "recording/state_page";
    }
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/state_page_ajax", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public HashMap<String, Object> state_page_ajax(Model model,
    		@RequestParam(value="res", required=false, defaultValue="M")String res,
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
    		@RequestParam(value="emp_id", required=false, defaultValue="")String emp_id,
    		@RequestParam(value="date", required=false, defaultValue="")String date,
    		@RequestParam(value="pagenum", required=false, defaultValue="")int pagenum,
    		@RequestParam(value="pagesize", required=false, defaultValue="")int pagesize,
    		@RequestParam(value="recordstartindex", required=false, defaultValue="")int recordstartindex,
    		@RequestParam(value="recordendindex", required=false, defaultValue="")int recordendindex,
    		Authentication authentication) throws JsonProcessingException{
		String active = "active page_open";
		model.addAttribute("state_active", active);
		HashMap<String, Object> param = new HashMap<>();
		HashMap<String, Object> param1 = new HashMap<>();
		
		String[] day = null;
		if(!date.equals("")) {
			day = dateFormat(date);
			param.put("bday", day[0]);
			param.put("eday", day[1]);
		}
		param.put("pagesize", pagesize);
		param.put("pagestart", recordstartindex);
		param.put("res", res);
		param.put("branch_cd", branch_cd);
		param.put("emp_id", emp_id);
		List<Stat> stat = sService.getView(param);
		state_logger.info("state_page_ajax -> "+authentication.getName()+" : "+param.toString());
		if(stat.size()!=0) {
			List<Stat> stat1 = sService.getListStateCount(param);
			param1.put("total", stat1.size());
			for(int i=0; i<stat.size(); i++) {
				stat.get(i).setNum(recordstartindex+i+1);
				stat.get(i).setS_call_cnt_total(stat.get(i).getS_called_cnt()+stat.get(i).getS_caller_cnt());
				stat.get(i).setS_call_time_total(stat.get(i).getS_called_time()+stat.get(i).getS_caller_time());
			}
		}
		param1.put("Rows", stat);
		return param1;
	}
	
	
	public String[] dateFormat(String date) {
		String bday = date.substring(0, 10);
		String eday = date.substring(13, 23);
		bday = bday.replaceAll(":", "");
		eday = eday.replaceAll(":",	"");
		String day[] = {bday,eday};
		return day;
	}
}
