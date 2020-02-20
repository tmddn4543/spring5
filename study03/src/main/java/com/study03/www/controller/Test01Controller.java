package com.study03.www.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study03.www.model.Board;
import com.study03.www.model.Notice;
import com.study03.www.serviceImpl.BoardService;
import com.study03.www.serviceImpl.NoticeService;

@Controller
@RequestMapping(value = "/bd")
public class Test01Controller {
	@Autowired
	BoardService bService;
	
	@Autowired
	NoticeService nService;
	
	@RequestMapping(value = "/bList", method= {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody HashMap<String, Object> bList(
    		@RequestParam(value="title", required=false, defaultValue="") String title
    		)
	{
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("title", title);
		List<Board> list = new ArrayList<Board>();
		
		list = bService.getList(param);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
    	return result;
    }
	
	@RequestMapping(value = "/bInsert", method= {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String bInsert(
    		@RequestParam(value="title", required=true, defaultValue="") String title,
    		@RequestParam(value="contents", required=true, defaultValue="") String contents
    		)
	{
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("title", title);
		param.put("contents", contents);
		
		bService.setInsert(param);
		
    	return "OK";
    }
	
	@RequestMapping(value = "/nList", method= {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody HashMap<String, Object> nList(
    		@RequestParam(value="title", required=false, defaultValue="") String title
    		)
	{
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("title", title);
		List<Notice> list = new ArrayList<Notice>();
		
		list = nService.getList(param);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
    	return result;
    }
	
	@RequestMapping(value = "/nInsert", method= {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String nInsert(
    		@RequestParam(value="title", required=true, defaultValue="") String title,
    		@RequestParam(value="contents", required=true, defaultValue="") String contents,
    		@RequestParam(value="onoff", required=true, defaultValue="") String onoff
    		)
	{
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("title", title);
		param.put("contents", contents);
		param.put("onoff", onoff);
		
		nService.setInsert(param);
		
    	return "OK";
    }
}
