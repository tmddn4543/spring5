package com.nautestech.www.controller;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;


import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController {

	
	//페이지로고설정
	@Value("${webLoginLogo}")
	String webLoginLogo;
	
	@Autowired
	ApplicationContext loa;
	
	
	@RequestMapping(value = "/", method= {RequestMethod.GET, RequestMethod.POST})
    public String loginPage(Model model){
		return "recording/login";
    }
	

	//ResponseEntity<byte[]>
	@RequestMapping(value = "/logo", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<byte[]> displayFile()throws Exception{
		
		
		ClassPathResource cpr = new ClassPathResource("static/assets/img/nautes_logo.png");

		
		
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		File path = new File(webLoginLogo);
		try {
			String formatName = webLoginLogo.substring(webLoginLogo.lastIndexOf(".")+1);
			MediaType mType = getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			if(!path.exists()) {
				in = cpr.getInputStream();
			}else {
				in = new FileInputStream(path);
			}
			
			//step: change HttpHeader ContentType
			if(mType != null) {
				//image file(show image)
				headers.setContentType(mType);
			}else {
				//another format file(download file)
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\"" + new String("nautes_logo.png".getBytes("UTF-8"), "ISO-8859-1")+"\""); 
			}
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
			
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			in.close();
		}
			return entity;
	}
	
	
	public MediaType getMediaType(String type){
		Map<String, MediaType> mediaMap = new HashMap<String, MediaType>();
		if(type.equals("jpg")) {
			mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		}else if(type.equals("gif")) {
			mediaMap.put("GIF", MediaType.IMAGE_GIF);
		}else if(type.equals("png")) {
			mediaMap.put("PNG", MediaType.IMAGE_PNG);
		}
		return mediaMap.get(type.toUpperCase());
	}
}
