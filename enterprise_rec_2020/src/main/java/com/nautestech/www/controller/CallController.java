package com.nautestech.www.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.nautestech.www.model.Call;
import com.nautestech.www.model.Session;
import com.nautestech.www.model.Users;
import com.nautestech.www.serviceImpl.CallService;
import com.nautestech.www.serviceImpl.UsersService;
import com.nautestech.www.util.Command;
import com.nautestech.www.util.listExcelDownload;

import utils.Utils;

@Controller
@RequestMapping(value = "/call")
public class CallController {
 
	
	
	
	@Value("${statisticsLimit}")
	int statisticsLimit;
	
	@Value("${callhistoryYMD}")
	Boolean callhistoryYMD;
	
	@Autowired
	UsersService uService;
	
	@Autowired
	CallService cService;
	
	
	@Value("${isMxxMode}")
	String isMxxMode;
	
	
	
	private static Logger main_logger = LogManager.getLogger(CallController.class);
	private static Logger call_logger = LogManager.getLogger("call_log");
	
	
	
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/zip", method= {RequestMethod.GET, RequestMethod.POST})
	public void zip(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws Exception {
		HashMap<String, Object> param1 = new HashMap<>();
		param1.put("emp_id", authentication.getName());
		param1.put("result", "zip");
		uService.setInsertListen_log(param1);
		call_logger.info("zip ->"+authentication.getName()+" : "+param1.toString());
		String arr = request.getParameter("arr");
		String[] sp_arr = arr.split(",");
		
		File zipfolder = new File("/home/zip");
		
		
		File checkF = null;
		File f_mxx = null;
		Command cmd = new Command();
		if(!zipfolder.exists()){
			cmd.chkFolder("/home/zip");
		}
		String dirname = "";
		String[] fname = null;
		if(sp_arr.length>0) {
			for(int i=0; i<sp_arr.length; i++) {
				sp_arr[i] = sp_arr[i].replace("mxx", isMxxMode);
				dirname = sp_arr[i].substring(0,36);
				System.out.println(sp_arr[i]+"| download");
				System.out.println(dirname+"|dirname");
				checkF = new File(dirname);
				f_mxx = new File(sp_arr[i]);
				if(!checkF.exists()){
					cmd.chkFolder(dirname);
				}
				if(!f_mxx.exists()) {
					fname = sp_arr[i].split("/");
					System.out.println(fname[5]+"| file make");
					System.out.println(dirname+"| dirname make");
					cmd.CopyMXX(fname[5], dirname);
					cmd.ConvertMXX(fname[5], dirname,isMxxMode);
					cmd.dencMp3(fname[5], dirname,isMxxMode);
				}
			}
			String day = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date());
			//다운받을위치
			String zipFile = "/home/zip/call_history_"+day+".zip";
			String downloadFileName = "call_history_"+day;
			List<String> sourceFiles = new ArrayList<String>();
			
			//파일위치+파일이름
			//경로와파일이름적는것
			for(int i=0; i<sp_arr.length; i++){
				sourceFiles.add(sp_arr[i]);
			}
			
			try{
				
			    // ZipOutputStream을 FileOutputStream 으로 감쌈
			    FileOutputStream fout = new FileOutputStream(zipFile);
			    ZipOutputStream zout = new ZipOutputStream(fout);
			    for(int i=0; i < sourceFiles.size(); i++){

			        //본래 파일명 유지, 경로제외 파일압축을 위해 new File로 
			        ZipEntry zipEntry = new ZipEntry(new File(sourceFiles.get(i)).getName());
			        zout.putNextEntry(zipEntry);

			        //경로포함 압축
			        //zout.putNextEntry(new ZipEntry(sourceFiles.get(i)));

			        FileInputStream fin = new FileInputStream(sourceFiles.get(i));
			        byte[] buffer = new byte[1024];
			        int length;

			        // input file을 1024바이트로 읽음, zip stream에 읽은 바이트를 씀
			        while((length = fin.read(buffer)) > 0){
			            zout.write(buffer, 0, length);
			        }
			        zout.closeEntry();
			        fin.close();
			    }
			    zout.close();
			    response.setContentType("application/zip");
			    response.addHeader("Content-Disposition", "attachment; filename=" + downloadFileName + ".zip");
			    FileInputStream fis=new FileInputStream(zipFile);
			    BufferedInputStream bis=new BufferedInputStream(fis);
			    ServletOutputStream so=response.getOutputStream();
			    BufferedOutputStream bos=new BufferedOutputStream(so);
			    byte[] data=new byte[2048];
			    int input=0;
			    while((input=bis.read(data))!=-1){
			        bos.write(data,0,input);
			        bos.flush();
			    }
			    if(bos!=null) bos.close();
			    if(bis!=null) bis.close();
			    if(so!=null) so.close();
			    if(fis!=null) fis.close();

			    } catch(IOException ioe){System.out.println(ioe); }
		}
	}
	
	
	@Secured({"ROLE_ADMIN","ROLE_ENDUSER","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/call_page", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){
		main_logger.info("welcome to CallPage");
		return "recording/call_page";
    }
	
	@Secured({"ROLE_ADMIN","ROLE_ENDUSER","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/xlsxDownload", method= {RequestMethod.GET, RequestMethod.POST})
	public View down(@RequestParam(value="emp", required=false, defaultValue="")String emp,
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
    		@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
    		@RequestParam(value="bday", required=false, defaultValue="")String bday,
    		@RequestParam(value="eday", required=false, defaultValue="")String eday,
    		@RequestParam(value="caller", required=false, defaultValue="")String caller,
    		@RequestParam(value="called", required=false, defaultValue="")String called,
    		@RequestParam(value="rec_type", required=false, defaultValue="")String rec_type,
    		@RequestParam(value="start_talk_time", required=false, defaultValue="")String start_talk_time,
    		@RequestParam(value="end_talk_time", required=false, defaultValue="")String end_talk_time,
    		@RequestParam(value="caller_attr", required=false, defaultValue="")String caller_attr,
    		@RequestParam(value="called_attr", required=false, defaultValue="")String called_attr
    		,Model model,
    		Authentication authentication) {
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		bday = bday.replace(".", ":");
		eday = eday.replace(".", ":");
		HashMap<String, Object> param1 = new HashMap<>();
		param1.put("emp_id", authentication.getName());
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
		call_logger.info("xlsxDownload -> "+authentication.getName()+":"+param.toString());
		if(callhistoryYMD) {
			String startYYYYMM = bday.substring(0,7);
			String endYYYYMM = eday.substring(0,7);
			
			startYYYYMM = startYYYYMM.replace(":", "");
			endYYYYMM = endYYYYMM.replace(":", "");
			param.put("startYYYYMM", startYYYYMM);
			param.put("endYYYYMM", endYYYYMM);
			call = cService.getViewYYYYMM(param);
		}else {
			call = cService.getView(param);
		}
		model.addAttribute("list", call);
		model.addAttribute("option", "call");
		return new listExcelDownload();
	}
	
	
	@Secured({"ROLE_ADMIN","ROLE_ENDUSER","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/usersSearch", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public List<Users> usersSearch(
    		@RequestParam(value="emp", required=false, defaultValue="")String emp,
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
    		@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
    		Authentication authentication
    		){
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		auth_cd = Utils.authFormat(auth_cd);
		HashMap<String, Object> param = new HashMap<>();
		param.put("emp_id_get", emp);
		param.put("emp_nm_get", emp);
		param.put("branch_cd", branch_cd);
		param.put("auth_cd", auth_cd);
		param.put("pagesize", 100);
		param.put("pagestart", 0);
		param.put("xlsx", "false");
		call_logger.info("usersSearch -> "+authentication.getName()+" : "+param.toString());
		List<Users> users = uService.getView(param);
		return users;
    }
	
	
	//@Secured({"ROLE_ADMIN","ROLE_ENDUSER","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/media/{YYYYMM}/{c_id}", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public String media(
    		@PathVariable("YYYYMM") String YYYYMM,
    		@PathVariable("c_id") String c_id,
    		HttpServletRequest request,
    		HttpServletResponse response,
    		Authentication authentication
    		) throws Exception{
		System.out.println(YYYYMM);
		System.out.println(c_id);
		HashMap<String, Object> param = new HashMap<>();
		
		param.put("c_id", c_id);
		param.put("xlsx","true");
		param.put("emp_id", "");
		param.put("emp_nm", "");
		param.put("branch_cd", "");
		param.put("bday", "");
		param.put("eday", "");
		param.put("caller", "");
		param.put("called", "");
		param.put("caller_attr", "");
		param.put("called_attr", "");
		param.put("rec_type", "");
		param.put("start_talk_time", "");
		param.put("end_talk_time", "");
		call_logger.info("media/{YYYYMM}/{c_id} -> "+authentication.getName()+" : "+param.toString());
		List<Call> call = null;
		System.out.println(YYYYMM+"ㅇㅇ");
		if(!callhistoryYMD) {
			call = cService.getView(param);
		}else {
			param.put("startYYYYMM", YYYYMM);
			param.put("endYYYYMM", YYYYMM);
			call = cService.getViewYYYYMM(param);
		}
		Command cmd = new Command();
		File checkF = null;
		File f_mxx = null;
		//폴더 존재여부를 먼저 체크한다.
		checkF = new File(call.get(0).getDirname());
		f_mxx = new File(call.get(0).getDirname()+call.get(0).getFname());
		System.out.println(call.get(0).getDirname()+"|dirname");
		if(!checkF.exists()){
			cmd.chkFolder(call.get(0).getDirname());
		}
		if(!f_mxx.exists()) {
			cmd.CopyMXX(call.get(0).getFname(), call.get(0).getDirname());
			cmd.ConvertMXX(call.get(0).getFname(), call.get(0).getDirname(),isMxxMode);
			cmd.dencMp3(call.get(0).getFname(), call.get(0).getDirname(),isMxxMode);
		}
		File file = new File(call.get(0).getDirname()+call.get(0).getFname().replace("mxx", isMxxMode));

		
		
		//듣기로그 넣기
		HashMap<String, Object>param1 = new HashMap<>();
		param1.put("emp_id", authentication.getName());
		param1.put("result", "listen");
		param1.put("dirname", call.get(0).getDirname());
		param1.put("filename", call.get(0).getFname().replace("mxx", isMxxMode));
		uService.setInsertListen_log(param1);
		
		RandomAccessFile randomFile = new RandomAccessFile(file, "r");
		long rangeStart = 0; //요청 범위의 시작 위치 
		long rangeEnd = 0; //요청 범위의 끝 위치 
		boolean isPart=false;
		try{ //동영상 파일 크기 
			long movieSize = randomFile.length(); //스트림 요청 범위, request의 헤더에서 range를 읽는다.
			String range = request.getHeader("range"); //브라우저에 따라 range 형식이 다른데, 기본 형식은 "bytes={start}-{end}" 형식이다. 
			//range가 null이거나, reqStart가 0이고 end가 없을 경우 전체 요청이다. 
			//요청 범위를 구한다. 
			if(range!=null) { //처리의 편의를 위해 요청 range에 end 값이 없을 경우 넣어줌 
				if(range.endsWith("-")){ range = range+(movieSize - 1);
				} int idxm = range.trim().indexOf("-"); //"-" 위치
				rangeStart = Long.parseLong(range.substring(6,idxm)); rangeEnd = Long.parseLong(range.substring(idxm+1));
				if(rangeStart > 0){
					isPart = true; 
					} 
				}else{ //range가 null인 경우 동영상 전체 크기로 초기값을 넣어줌. 0부터 시작하므로 -1 
					rangeStart = 0; rangeEnd = movieSize - 1;
				} //전송 파일 크기
			long partSize = rangeEnd - rangeStart + 1; //전송시작 
			response.reset(); //전체 요청일 경우 200, 부분 요청일 경우 206을 반환상태 코드로 지정
			response.setStatus(isPart ? 206 : 200); //mime type 지정 
			response.setContentType("audio/"+isMxxMode); //전송 내용을 헤드에 넣어준다. 마지막에 파일 전체 크기를 넣는다.
			response.setHeader("Content-Range", "bytes "+rangeStart+"-"+rangeEnd+"/"+movieSize);
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-Length", ""+partSize);
			OutputStream out = response.getOutputStream(); 
			//동영상 파일의 전송시작 위치 지정 
			randomFile.seek(rangeStart); 
			//파일 전송... java io는 1회 전송 byte수가 int로 지정됨 
			//동영상 파일의 경우 int형으로는 처리 안되는 크기의 파일이 있으므로 
			//8kb로 잘라서 파일의 크기가 크더라도 문제가 되지 않도록 구현 
			int bufferSize = 8*1024; 
			byte[] buf = new byte[bufferSize]; 
			do{
				int block = partSize > bufferSize ? bufferSize : (int)partSize;
				int len = randomFile.read(buf, 0, block);
				out.write(buf, 0, len); partSize -= block;
				}while(partSize > 0);
			}catch(IOException e){ //전송 중에 브라우저를 닫거나, 화면을 전환한 경우 종료해야 하므로 전송취소. // progressBar를 클릭한 경우에는 클릭한 위치값으로 재요청이 들어오므로 전송 취소. 
			}finally{
				randomFile.close(); 
			}
		return null;
    }
	

	
	
	@Secured({"ROLE_ADMIN","ROLE_ENDUSER","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/callSearch_YYYYMMDD", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public HashMap<String, Object> callSearch_YYYYMMDD(
    		@RequestParam(value="emp", required=false, defaultValue="")String emp,
    		@RequestParam(value="branch_cd", required=false, defaultValue="")String branch_cd,
    		@RequestParam(value="auth_cd", required=false, defaultValue="")String auth_cd,
    		@RequestParam(value="bday", required=false, defaultValue="")String bday,
    		@RequestParam(value="eday", required=false, defaultValue="")String eday,
    		@RequestParam(value="caller", required=false, defaultValue="")String caller,
    		@RequestParam(value="called", required=false, defaultValue="")String called,
    		@RequestParam(value="rec_type", required=false, defaultValue="")String rec_type,
    		@RequestParam(value="start_talk_time", required=false, defaultValue="")String start_talk_time,
    		@RequestParam(value="end_talk_time", required=false, defaultValue="")String end_talk_time,
    		@RequestParam(value="caller_attr", required=false, defaultValue="")String caller_attr,
    		@RequestParam(value="called_attr", required=false, defaultValue="")String called_attr,
    		@RequestParam(value="pagenum", required=false, defaultValue="")int pagenum,
    		@RequestParam(value="pagesize", required=false, defaultValue="")int pagesize,
    		@RequestParam(value="recordstartindex", required=false, defaultValue="")int recordstartindex,
    		@RequestParam(value="recordendindex", required=false, defaultValue="")int recordendindex,
    		Authentication authentication) throws Exception{ 
		bday = bday.replace(".", ":");
		eday = eday.replace(".", ":");
		Session user = (Session) authentication.getDetails();
		String startYYYYMM = bday.substring(0,7);
		String endYYYYMM = eday.substring(0,7);
		startYYYYMM = startYYYYMM.replace(":", "");
		endYYYYMM = endYYYYMM.replace(":", "");
		System.out.println(authentication.getDetails().toString());
		if(branch_cd.equals("전체")) {
			branch_cd = "";
		}
		String bday_h = bday.substring(11,13);
		String eday_h = eday.substring(11,13);
		HashMap<String, Object> param = new HashMap<>();
		param.put("xlsx", "false");
		param.put("emp_id", emp);
		param.put("emp_nm", emp);
		if(user.getAuth_cd().equals("12")) {
			param.put("branch_cd", user.getBranch_cd());
		}else if(user.getAuth_cd().equals("13")) {
			param.put("emp_id", user.getEmp_id());
			param.put("branch_cd", user.getBranch_cd());
		}else if(user.getAuth_cd().equals("00")){
			param.put("branch_cd",branch_cd);
		}else {
			param.put("branch_cd",branch_cd);
		}
		param.put("bday", bday);
		param.put("eday", eday);
		param.put("startYYYYMM",startYYYYMM);
		param.put("endYYYYMM",endYYYYMM);
		param.put("caller", caller);
		param.put("called", called);
		param.put("caller_attr", caller_attr);
		param.put("called_attr", called_attr);
		param.put("rec_type", rec_type);
		param.put("bday_h", bday_h);
		param.put("eday_h", eday_h);
		param.put("start_talk_time", start_talk_time);
		param.put("end_talk_time", end_talk_time);
		param.put("pagesize", pagesize);
		param.put("pagestart", recordstartindex);
		param.put("xlsx", "false");
		System.out.println(bday +"~"+eday);
		HashMap<String, Object> param1 = new HashMap<>();
		List<Call> call = null;
		if(callhistoryYMD) {
			call = cService.getViewYYYYMM(param);
		}else {
			call = cService.getView(param);
		}
		String a = "";
		call_logger.info("callSearch_YYYYMMDD ->"+authentication.getName()+" : "+param.toString());
		listExcelDownload format = new listExcelDownload();
		if(call.size()!=0) {//"+call.get(i).getDirname()+""+call.get(i).getFname()+"
			int total = 0;
			if(callhistoryYMD) {
				total = cService.getListCountYYYYMM(param);
			}else {
				total = cService.getListCount(param);
			}
			
			param1.put("total", total);
			for(int i=0; i<call.size(); i++) {
				call.get(i).setYYYYMM(startYYYYMM);
				call.get(i).setDirname(call.get(i).getDirname()+call.get(i).getFname());
				call.get(i).setCall_date(format.dateFormat(call.get(i).getBtime()));
				call.get(i).setCall_hour(format.hourFormat(call.get(i).getBtime(), call.get(i).getEtime()));
				call.get(i).setCall_time(format.timeFormat(call.get(i).getBtime(), call.get(i).getEtime()));
				call.get(i).setRec_type(format.recFormat(call.get(i).getRec_type()));
				call.get(i).setNum(total-i-recordstartindex);
			}
		}
		param1.put("recordstartindex", recordstartindex);
		param1.put("Rows", call);
		return param1;
    }
	
}
