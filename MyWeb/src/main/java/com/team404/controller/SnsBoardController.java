package com.team404.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.team404.command.SnsBoardDTO;
import com.team404.snsboard.service.SnsBoardService;

@Controller
@RequestMapping("/snsBoard")
public class SnsBoardController {
	
	
	@Autowired
	@Qualifier("snsBoardService")
	private SnsBoardService sbs;
	
	
	@RequestMapping(value="/snsList")
	public String snsList() {
		return "snsBoard/snsList";
	}
	
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file,
			@RequestParam("content") String content) {
		System.out.println(file);
		System.out.println(content);
		//1.날짜별로 20191211형식으로 upload아래에 폴더를생성
		//2.uploadPath는 날짜폴더를 포함한경로
		//3.fileRealName은 변경하기전 파일이름
		//4.fileName은 변경해서 저장할파일
		//5.fileLoca 20191211 형식으로 만들어진폴더이름
		//6.transferTo()메서드를 이용해서 전송되는 파일을 해당날짜에 업로드
		//7.DB에 insert메서드를 통해서 값들을 저장
		//8.성공시에 화면에 success를반환, 실패시  fail를반환
		
		//파일명변경 ex)uuids.jpg
		
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString().replace("-", "");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String uploadDay = sdf.format(date); //업로드폴더를 생성할 날짜
		
		File folder = new File("D:\\Windows Pro\\upload\\" + uploadDay);
		if(!folder.exists() ) {
			folder.mkdir(); //폴더생성
		}
		
		try {
	         String fileRealName = file.getOriginalFilename(); // 파일 정보
	         SnsBoardDTO dto = new SnsBoardDTO();
	         String fileExtention = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length()); // 파일// 확장자
	         String writer = "asd";
	         String fileLoca = uploadDay;
	         String uploadPath = "D:\\Windows Pro\\upload\\"+uploadDay;
	         String fileName = uuids+"."+fileExtention;
	         File saveFile = new File("D:\\Windows Pro\\upload\\"+uploadDay+"\\"+ fileName);
	         
	         dto.setContent(content);
	         dto.setFileLoca(fileLoca);
	         dto.setFileName(fileName);
	         dto.setFileRealName(fileRealName);
	         dto.setUploadPath(uploadPath);
	         dto.setWriter(writer);
	         if(sbs.insert(dto)) {
	        	 file.transferTo(saveFile);
	         }
	      } catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public byte[] view(@RequestParam("fileLoca") String fileLoca,
			@RequestParam("fileName") String fileName) {
		File file = new File("D:\\Windows Pro\\upload\\"+fileLoca+"\\"+fileName);
		byte[] result = null;
		
		try {
			result = FileCopyUtils.copyToByteArray(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	} 
	
	@RequestMapping("/getList")
	@ResponseBody
	public ArrayList<SnsBoardDTO> getList(){
		return sbs.getList();
	}
	
	@RequestMapping("/snsDetail/{bno}")
	@ResponseBody
	public SnsBoardDTO snsDetail(@PathVariable("bno") int bno) {
		System.out.println("들어오냐?"+bno);
		return sbs.snsDetail(bno);
	}
}
