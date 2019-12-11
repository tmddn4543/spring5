package com.team404.controllerTest;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team404.command.TestVO;

@RestController
@RequestMapping("/restControl")
public class RestBasicController {
	//1.레스트컨트롤러는 기본적으로 리턴에 실린값이 리졸버 뷰로 전달되는것이ㅏ닌 요청된주소로 반환됨
	
	@RequestMapping(value = "/getText", produces = "text/html; charset=utf-8")
	public String getText() {
		return "asd";
	}
	
	@RequestMapping(value = "/getObject")
	public TestVO getObject() {
		TestVO vo = new TestVO(20, "홍길순", "kkk123");
		return vo;
	}
	
	@RequestMapping(value = "/getCollection")
	public ArrayList<TestVO> getCollection(@RequestParam("num") int num){
		ArrayList<TestVO> list = new ArrayList<>();
		for(int i=0; i<=num; i++) {
			TestVO vo = new TestVO(i,"길자"+i, "kkk"+i);
			list.add(vo);
		}
		return list;
	}
	
	@RequestMapping(value= "/getPath/{id}/{pw}")
	public HashMap<String, TestVO> getPath(@PathVariable("id") String id,
			@PathVariable("pw") String pw){
		System.out.println(id+"\n"+pw);
		HashMap<String, TestVO> map = new HashMap<String, TestVO>();
		map.put("info", new TestVO(10,"홍길순","hh"));
		return map;
	}
	
	@RequestMapping("/getJson")
	public ArrayList<TestVO> getJson(@RequestBody TestVO vo, HttpServletRequest request){
		ArrayList<TestVO> list = new ArrayList<TestVO>();
		list.add(new TestVO(20,"신사임당","kk123"));
		System.out.println("요청주소 : "+request.getRemoteAddr());
		return list;
	}
	
	@RequestMapping("/getMember/{str1}/{str2}")
	public HashMap<String, TestVO> getMember(@PathVariable("str1") String str1,
			@PathVariable("str2") String str2){
		HashMap<String, TestVO> map = new HashMap<>();
		if(str1.equals(str2)) {
			map.put("info", new TestVO(40,str1,"홍길동"));
			return map;
		}else {
			return null;
		}
	}
}
