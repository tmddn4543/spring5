package com.nautestech.www.controller;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class IndexController {

	
	//페이지로고설정
	@Value("${webLoginLogo}")
	String webLoginLogo;
	
	@Autowired
	ApplicationContext loa;
	
	
	@RequestMapping(value = "/", method= {RequestMethod.GET, RequestMethod.POST})
    public String loginPage(Model model,HttpServletRequest request,
			HttpServletResponse response){
		return "recording/login";
    }
	
	@RequestMapping(value = "/test", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public HashMap<String,Object> test(@RequestParam(value="CID", required=false, defaultValue="")String CID,
    		@RequestParam(value="SERVICE", required=false, defaultValue="")String SERVICE,
    		@RequestParam(value="METHOD", required=false, defaultValue="")String METHOD){
		System.out.println(CID + " = CID");
		System.out.println(SERVICE+" = Service");
		System.out.println(METHOD+" = Method");
		HashMap<String, Object> param = new HashMap<>();
		HashMap<String, Object> param1 = new HashMap<>();
		HashMap<String, Object> main = new HashMap<>();
		param1.put("CID", "01022223333");
		param1.put("USER_TYPE", "VIP");
		param.put("MESSAGE", "정상적으로 조회 완료 하였습니다.");
		param.put("resultFind", param1);
		main.put("payload", param);
		return main;
//		{"payload":{"MESSAGE":"정상적으로 조회 완료 하였습니다.","resultFind":{"USER_TYPE":"VIP","CID":"01022223333"}}}
    }
	
	@RequestMapping(value = "/test2", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public boolean test2(@RequestParam(value="CID", required=false, defaultValue="")String CID,
    		@RequestParam(value="LMS", required=false, defaultValue="")String LMS){
		System.out.println(CID + " = CID");
		System.out.println(LMS + " = LMS");
		return true;
    }
	
	@RequestMapping(value = "/logo", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<byte[]> displayFile(HttpServletResponse response)throws Exception{
		ClassPathResource cpr = new ClassPathResource("static/assets/img/nautes_logo.png");
		File path = new File(webLoginLogo); //C:\\home\\recording\\img\\nautes_logo.png
		String formatName = webLoginLogo.substring(webLoginLogo.lastIndexOf(".")+1);
		HttpHeaders headers = new HttpHeaders();
		InputStream in = null;
		if(!path.exists()) {
			in = cpr.getInputStream();
			headers.setContentType(MediaType.IMAGE_PNG);
		}else {
			in = new FileInputStream(path);
			if(formatName.equals("png")) {
				headers.setContentType(MediaType.IMAGE_PNG);
			}else if(formatName.equals("jpg") || formatName.equals("jpeg")) {
				headers.setContentType(MediaType.IMAGE_JPEG);
			}else if(formatName.equals("gif")) {
				headers.setContentType(MediaType.IMAGE_GIF);
			}
		}
		byte[] media = IOUtils.toByteArray(in);
	    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
	    ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
	    return responseEntity;
	}
}
