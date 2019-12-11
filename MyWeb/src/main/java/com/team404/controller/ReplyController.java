package com.team404.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team404.command.ReplyDTO;
import com.team404.freereply.service.FreeReplyService;
import com.team404.util.Criteria;

@RestController
@RequestMapping("/reply")
public class ReplyController {
	
	@Autowired
	@Qualifier("freeReplyService")
	private FreeReplyService rfs;
	
	
	@RequestMapping("/new")
	public int regist(@RequestBody ReplyDTO dto) {
		return rfs.regist(dto);
	}
	
	@RequestMapping("/getReply/{bno}/{pageNum}")
	public ArrayList<ReplyDTO> getReply(@PathVariable("bno") int bno,
			@PathVariable("pageNum") int pageNum){
		
		
		return rfs.getList(bno);
	}
	
	@RequestMapping("/delete")
	public int delete(@RequestBody ReplyDTO dto) {
		int result = rfs.pwCheck(dto);
		if(result == 1) {
			return rfs.delete(dto);
		}else {
			return 0;
		}
	}
	
	@RequestMapping("/update")
	public int update(@RequestBody ReplyDTO dto) {
		int result = rfs.pwCheck(dto);
		if(result==1) {
			return rfs.update(dto);
		}else {
			return 0;
		}
	}
}
