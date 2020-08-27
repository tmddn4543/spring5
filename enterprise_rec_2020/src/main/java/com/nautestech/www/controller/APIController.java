package com.nautestech.www.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nautestech.www.model.Call;
import com.nautestech.www.model.ResultDto;
import com.nautestech.www.serviceImpl.CallService;
import com.nautestech.www.serviceImpl.UsersService;
import com.nautestech.www.util.Command;


@Controller
@RequestMapping(value = "/API")
public class APIController {

	@Value("${callhistoryYMD}")
	Boolean callhistoryYMD;
	
	@Value("${statisticsLimit}")
	int statisticsLimit;
	
		
	@Value("${premium}")
	String premium;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Autowired
	CallService cService;
	
	@Autowired
	UsersService uService;
	
	private static Logger call_logger = LogManager.getLogger("call_log");
	
	
	@RequestMapping(value = "/call_history", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResultDto down(@RequestParam(value="emp", required=false, defaultValue="")String emp,
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
    		@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
    		@RequestParam(value="bday", required=false, defaultValue="")String bday,		//2020.08.27
    		@RequestParam(value="eday", required=false, defaultValue="")String eday,		//2020.08.27
    		@RequestParam(value="caller", required=false, defaultValue="")String caller,
    		@RequestParam(value="called", required=false, defaultValue="")String called,
    		@RequestParam(value="rec_type", required=false, defaultValue="")String rec_type,
    		@RequestParam(value="start_talk_time", required=false, defaultValue="")String start_talk_time,
    		@RequestParam(value="end_talk_time", required=false, defaultValue="")String end_talk_time,
    		@RequestParam(value="caller_attr", required=false, defaultValue="")String caller_attr,
    		@RequestParam(value="called_attr", required=false, defaultValue="")String called_attr,
    		@RequestParam(value="callid", required=false, defaultValue="")String callid,
    		@RequestParam(value="serialkey", required=false, defaultValue="")String serialkey
    		,Model model,
    		Authentication authentication) throws Exception {
		Command command = new Command();
		String premiums = command.decAES(premium);
		
		JsonParser jsonParser = new JsonParser();
		Object obj = jsonParser.parse(premiums);
		JsonObject jo = (JsonObject)obj;
		
		Boolean api = jo.get("api").getAsBoolean();
		Boolean noti = jo.get("noti").getAsBoolean();
		String notiDestServer = jo.get("notiDestServer").getAsString();
		
		
		boolean day_boolean = true;		//
		

		HashMap<String, Object> result = new HashMap<>();
		ResultDto dto = new ResultDto();
		
		
		
		
		
		
		
		
		if(!api && !noti) {
			dto.setObj(null);
			dto.setFailMsg("사용하실수 없는 기능입니다.");
			dto.setResult(false);
			return dto;
		}
		
		
		if(bday.equals("")) {
			dto.setObj(null);
			dto.setFailMsg("시작일과 끝일을 넣어주세요.");
			dto.setResult(false);
			return dto;
		}else if(eday.equals("")) {
			dto.setObj(null);
			dto.setFailMsg("시작일과 끝일을 넣어주세요.");
			dto.setResult(false);
			return dto;
		}
		
		
		
		String byyyy = bday.substring(0,4);
		String eyyyy = eday.substring(0,4);
		
		int yyyy_result = Integer.parseInt(eyyyy)-Integer.parseInt(byyyy);
		
		
		String bdd = bday.substring(8,10);
		String edd = eday.substring(8,10);
		int dd_result = Integer.parseInt(edd) - Integer.parseInt(bdd);
		
		if(yyyy_result==0){
			String bmm = bday.substring(5,7);
			String emm = eday.substring(5,7);
			int mm_result = Integer.parseInt(emm) - Integer.parseInt(bmm);
			if(mm_result>1){
				if(dd_result>=0){
					day_boolean = false;
				}
			}
		}else{
			String bmm = bday.substring(5,7);
			String emm = eday.substring(5,7);
			
			int mm_result = Integer.parseInt(bmm) - Integer.parseInt(emm);
			if(mm_result==10){
				if(dd_result>=0){
					day_boolean = false;
				}
			}else if(mm_result<10){
				day_boolean = false;
			}
		}
		
		
		if(!day_boolean) {
			dto.setObj(null);
			dto.setFailMsg("2개월 이상은 검색할 수 없습니다.");
			dto.setResult(false);
			return dto;
		}
		
		
		
		
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		bday = bday.replace(".", ":");
		eday = eday.replace(".", ":");
		HashMap<String, Object> param1 = new HashMap<>();
		param1.put("emp_id", "API");
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
		call_logger.info("call_history -> API:"+param.toString());
		String startYYYYMM = bday.substring(0,7);
		String endYYYYMM = eday.substring(0,7);
		
		startYYYYMM = startYYYYMM.replace(":", "");
		endYYYYMM = endYYYYMM.replace(":", "");
		if(Integer.parseInt(startYYYYMM)>Integer.parseInt(endYYYYMM)) {
			dto.setObj(null);
			dto.setFailMsg("날짜가 맞지않습니다.");
			dto.setResult(false);
			return dto;
		}
		if(callhistoryYMD) {
			param.put("startYYYYMM", startYYYYMM);
			param.put("endYYYYMM", endYYYYMM);
			call = cService.getViewYYYYMM(param);
		}else {
			
			call = cService.getView(param);
		}
		model.addAttribute("list", call);
		result.put("list", call);
		result.put("total", call.size());
		dto.setObj(result);
		dto.setFailMsg("");
		dto.setResult(true);
		
		return dto;
	}
	
	
	
	@RequestMapping(value = "/call_part", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResultDto call_part(
			@RequestParam(value="param1", required=false, defaultValue="") String param1,		//사용자 아이디
			@RequestParam(value="param2", required=false, defaultValue="") String param2,		//시작,종료 [START,END]
			@RequestParam(value="param3", required=false, defaultValue="") String param3,		//시리얼키
			@RequestParam(value="param4", required=false, defaultValue="") String param4,		//발신번호 [Caller]
			@RequestParam(value="param5", required=false, defaultValue="") String param5,		//REQW(인증녹취), REQV(전수녹취) 
			@RequestParam(value="reserve1", required=false, defaultValue="") String reserve1,		//차장님께서 필요한 파라미터1
			@RequestParam(value="reserve2", required=false, defaultValue="") String reserve2,		//2
			@RequestParam(value="reserve3", required=false, defaultValue="") String reserve3		//3
			) throws Exception{
		Command command = new Command();
		String premiums = command.decAES(premium);
		
		JsonParser jsonParser = new JsonParser();
		Object obj = jsonParser.parse(premiums);
		JsonObject jo = (JsonObject)obj;
		
		Boolean api = jo.get("api").getAsBoolean();
		Boolean noti = jo.get("noti").getAsBoolean();
		String notiDestServer = jo.get("notiDestServer").getAsString();
		
		
		HashMap<String, Object> result = new HashMap<>();
		ResultDto dto = new ResultDto();
		
		
		if(!api && !noti) {
			dto.setObj(null);
			dto.setFailMsg("사용하실수 없는 기능입니다.");
			dto.setResult(false);
			return dto;
		}
		
		
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
		if(!param1.equals("")) {
			param.add("param1", param1);//요청번호 07047392859
		}
		if(!param2.equals("")) {
			param.add("param2", param2); //START,END
		}
		if(!param3.equals("")) {
			param.add("param3", param3);//시리얼키
		}
		if(!param4.equals("")) {
			param.add("param4", param4);
		}
		if(!param5.equals("")) {
			param.add("param5", param5);
		}
		ResponseEntity<String> ret = restTemplate.postForEntity//"http://192.168.1.200:20777/?"+param5+"|"+param4+"|"+param4+"|"+param2+"|$"
				("http://192.168.1.200:20777", param, String.class);
		System.out.println(ret.getBody().toString());
		String results = ret.getBody().toString().replaceAll("\u0000", "");
		if(results.equals("NOT OK")) {			//실패시
			dto.setObj(null);
			dto.setFailMsg(results);
			dto.setResult(false);
		}else {									//성공시
			dto.setObj(null);
			dto.setFailMsg(results);
			dto.setResult(true);
		}
		return dto;
	}
	
	
	
	
}
