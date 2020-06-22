package com.nautestech.www.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
import com.nautestech.www.model.Callhistory_log;
import com.nautestech.www.serviceImpl.LogService;

@Controller
@RequestMapping(value = "/access_log")
public class Access_logController {
	
	@Autowired
	LogService lService;
	
	private static Logger main_logger = LogManager.getLogger(Access_logController.class);
	private static Logger access_logger = LogManager.getLogger("access_log");
	
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/access_log_page", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){
		main_logger.info("welcome to AccessPage");
		return "recording/access_log_page";
    }
	
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/access_log_page_ajax", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public HashMap<String, Object> user_page_ajax(Model model,
			@RequestParam(value="user_id", required=false, defaultValue="")String user_id,
			@RequestParam(value="a_day", required=false, defaultValue="")String a_day,
    		@RequestParam(value="pagenum", required=false, defaultValue="")int pagenum,
    		@RequestParam(value="pagesize", required=false, defaultValue="")int pagesize,
    		@RequestParam(value="recordstartindex", required=false, defaultValue="")int recordstartindex,
    		@RequestParam(value="recordendindex", required=false, defaultValue="")int recordendindex,
    		Authentication authentication) throws JsonProcessingException{
		String active = "active page_open";
		model.addAttribute("access_log_active", active);
		HashMap<String, Object> param = new HashMap<>();
		HashMap<String, Object> param1 = new HashMap<>();
		if(!a_day.equals("")) {
			String[] s_day = dateFormat(a_day);
			param.put("bday", s_day[0].replaceAll("/", "-"));
			param.put("eday", s_day[1].replaceAll("/", "-"));
			System.out.println(s_day[1]);
		}else {
			String day = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date());
			param.put("bday", day);
			param.put("eday", day);
		}
		
		param.put("user_id", user_id);
		param.put("pagesize", pagesize);
		param.put("pagestart", recordstartindex);
		access_logger.info("access_log_page_ajax -> "+authentication.getName()+" : "+param.toString());
		List<Callhistory_log> log = lService.getView(param);
		if(log.size()!=0) {
			int total = lService.getListCount(param);
			param1.put("total", total);
			for(int i=0; i<log.size(); i++) {
				log.get(i).setNum(total-i-recordstartindex);
			}
		}
		param1.put("Rows", log);
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
