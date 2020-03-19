package com.study05.www.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study05.www.model.Board;
import com.study05.www.model.Member;
import com.study05.www.model.Notice;
import com.study05.www.model.ResultDto;
import com.study05.www.model.SelectLevel;
import com.study05.www.model.SelectOnOff;
import com.study05.www.model.Session;
import com.study05.www.serviceImpl.BoardService;
import com.study05.www.serviceImpl.MemberService;
import com.study05.www.serviceImpl.NoticeService;

import utils.PagingAction;

@Controller
@RequestMapping(value = "/bd")
public class MemberController {
	final int pageSize = 10;
	@Autowired
	BoardService bService;
	
	@Autowired
	NoticeService nService;														//"egovframework.ECALL_O.main.web"
	
	@Autowired
	MemberService mService;
	
	private static Logger my_logger = LogManager.getLogger(MemberController.class);
	private static Logger board_log = LogManager.getLogger("board_log");
	private static Logger notice_log = LogManager.getLogger("notice_log");
	private static Logger member_log = LogManager.getLogger("member_log");
	
	
	
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/bDelete", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResultDto bDelete(
    		Model model,
    		@RequestParam(value="delId", required=false, defaultValue="") List<String> seq
    		) {
		ResultDto result = new ResultDto();
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("seq", seq.toArray(new String[seq.size()]));
		bService.setDelete(param);
		result.setResult(true);
		
        return result;
    }
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(value="/bInsert",method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void bInsert(
			@RequestParam(value="title", required=false, defaultValue="")String title,
			@RequestParam(value="contents", required=false, defaultValue="")String contents,
			Authentication authentication) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("title", title);
		param.put("contents", contents);
		param.put("written", authentication.getName());
		bService.setInsert(param);
	}
	
	
	@PreAuthorize("#auth.isMyWritten(#seq) or hasRole('ROLE_ADMIN')")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(value="/bUpdate",method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void bUpdate(
			@RequestParam(value="seq", required=false, defaultValue="")String seq,
			@RequestParam(value="title", required=false, defaultValue="")String title,
			@RequestParam(value="contents", required=false, defaultValue="")String contents,
			@RequestParam(value="written", required=false, defaultValue="")String written,
			Authentication authentication) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("seq", seq);
		param.put("title", title);
		param.put("contents", contents);
		param.put("written", authentication.getName());
		bService.setUpdate(param);
		
	}
	
	

	
	
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/board", method= {RequestMethod.GET, RequestMethod.POST})
    public String board(
    		Model model,
    		@RequestParam(value="search_title", required=false, defaultValue="") String search_title,
    		@RequestParam(value="search_contents", required=false, defaultValue="") String search_contents,
    		@RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage,
    		Authentication authentication) {
		my_logger.info("welcome to board");
		HashMap<String, Object> param = new HashMap<String, Object>();
		List<Board> list = new ArrayList<Board>();
		param.put("user_log", authentication.getName());
		param.put("title", search_title);
		param.put("contents", search_contents);
		param.put("pageSize", pageSize);
		param.put("startRow", (currentPage - 1) * pageSize);
		board_log.info(authentication.getName() + " : " + param.toString());
		list = bService.getList(param);
		int total = bService.getListCount(param);
		
		PagingAction page = new PagingAction(
				currentPage, 
				total, 
				pageSize,
				5, 
				"searchFrm", 
				"");
		model.addAttribute("List", list);
		model.addAttribute("search_title", search_title);
		model.addAttribute("search_contents", search_contents);
		model.addAttribute("maxnumber", total - ((currentPage - 1) * pageSize));
		model.addAttribute("page", page.getPagingHtml());
        return "utime/board";
    }
	
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/bView", method= {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody Board boardView(
    		Model model,
    		@RequestParam(value="seq", required=false, defaultValue="") String seq,
    		Authentication authentication
    		) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("seq", seq);
		Board board = new Board();
		board = bService.getView(param);
        return board;
    }
	
	
	
	
	
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/notice", method= {RequestMethod.GET, RequestMethod.POST})
    public String notice(
    		Model model,
    		@RequestParam(value="search_title", required=false, defaultValue="") String search_title,
    		@RequestParam(value="search_contents", required=false, defaultValue="") String search_contents,
    		@RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage,
    		@RequestParam(value="search_onoff", required=false, defaultValue="") String search_onoff
    		,Authentication authentication) {
		my_logger.info("welcome to notice");
		Session member = (Session) authentication.getDetails();
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("title", search_title);
		param.put("contents", search_contents);
		param.put("pageSize", pageSize);
		param.put("onoff", search_onoff);
		param.put("startRow", (currentPage - 1) * pageSize);
		if(member.getLevel()==1) {
			param.put("onoff", "Y");
		}
		List<Notice> list = new ArrayList<Notice>();
		
		list = nService.getList(param);
		int total = nService.getListCount(param);
		SelectOnOff onoffs = new SelectOnOff();
		PagingAction page = new PagingAction(
				currentPage, 
				total, 
				pageSize,
				5, 
				"searchFrm", 
				"");
		notice_log.info(authentication.getName() + " : " + param.toString());
		model.addAttribute("onoffs", onoffs.getList());
		model.addAttribute("List", list);
		model.addAttribute("search_title", search_title);
		model.addAttribute("search_contents", search_contents);
		model.addAttribute("search_onoff", search_onoff);
		model.addAttribute("maxnumber", total - ((currentPage - 1) * pageSize));
		model.addAttribute("page", page.getPagingHtml());
        return "utime/notice";
    }
	
	
	//
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/nView", method= {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody Notice nView(
    		Model model,
    		@RequestParam(value="seq", required=false, defaultValue="") String seq
    		) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("seq", seq);
		Notice notice = nService.getView(param);
        return notice;
    }
	
	
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/nDelete", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResultDto nDelete(
    		Model model,
    		@RequestParam(value="delId", required=false, defaultValue="") List<String> seq
    		) {
		ResultDto result = new ResultDto();
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("seq", seq.toArray(new String[seq.size()]));
		nService.setDelete(param);
		result.setResult(true);
		
        return result;
    }
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(value="/nInsert",method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void nInsert(
			@RequestParam(value="title", required=false, defaultValue="")String title,
			@RequestParam(value="contents", required=false, defaultValue="")String contents,
			@RequestParam(value="onoff", required=false, defaultValue="Y")String onoff,
			Authentication authentication){
		HashMap<String, Object> param = new HashMap<>();
		param.put("written", authentication.getName());
		param.put("title", title);
		param.put("contents", contents);
		param.put("onoff", onoff);
		
		nService.setInsert(param);
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(value="/nUpdate",method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void nUpdate(
			@RequestParam(value="seq", required=false, defaultValue="")String seq,
			@RequestParam(value="title", required=false, defaultValue="")String title,
			@RequestParam(value="contents", required=false, defaultValue="")String contents) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("seq", seq);
		param.put("title", title);
		param.put("contents", contents);
		param.put("onoff", "1");
		nService.setUpdate(param);
	}
	
	
	//
	
	
	
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/member", method= {RequestMethod.GET, RequestMethod.POST})
    public String member(
    		Model model,
    		@RequestParam(value="search_id", required=false, defaultValue="") String search_id,
    		@RequestParam(value="search_level", required=false, defaultValue="") String search_level,
    		@RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage,
    		Authentication authentication) {
		my_logger.info("welcome to member");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("id", search_id);
		param.put("level", search_level);
		param.put("pageSize", pageSize);
		param.put("startRow", (currentPage - 1) * pageSize);
		member_log.info(authentication.getName() + " : " +param.toString());
		List<Member> list = new ArrayList<Member>();
		
		list = mService.getList(param);
		int total = mService.getListCount(param);
		
		PagingAction page = new PagingAction(
				currentPage, 
				total, 
				pageSize,
				5, 
				"searchFrm", 
				""); 
		SelectLevel levels = new SelectLevel();
		model.addAttribute("List", list);
		model.addAttribute("levels", levels.getList());
		model.addAttribute("search_id", search_id);
		model.addAttribute("search_level", search_level);
		model.addAttribute("maxnumber", total - ((currentPage - 1) * pageSize));
		model.addAttribute("page", page.getPagingHtml());
        return "utime/member";
    }
	
	
	
	
	//
	
	
	
	@Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/mView", method= {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody Member mView(
    		Model model,
    		@RequestParam(value="seq", required=false, defaultValue="") String seq,
    		@RequestParam(value="id", required=false, defaultValue="") String id
    		) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("seq", seq);
		Member member = mService.getView(param);
        return member;
    }

	
	
	@Secured("ROLE_ADMIN")
    @RequestMapping(value = "/mDelete", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResultDto memberDelete(
    		Model model,
    		@RequestParam(value="delId", required=false, defaultValue="") List<String> seq
    		) {
		ResultDto result = new ResultDto();
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("id", "admin");

		Member member = mService.getView(param);
		seq.removeIf(s -> s.equals(member.getSeq()));
		
		param.put("seq", seq.toArray(new String[seq.size()]));
		mService.setDelete(param);
		result.setResult(true);
		
        return result;
    }
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/mInsert",method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void mInsert(
			@RequestParam(value="id", required=false, defaultValue="")String id,
			@RequestParam(value="pwd", required=false, defaultValue="")String pwd,
			@RequestParam(value="name", required=false, defaultValue="")String name,
			@RequestParam(value="level", required=false, defaultValue="")String level){
		HashMap<String, Object> param = new HashMap<>();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		param.put("pwd", passwordEncoder.encode(pwd));
		param.put("id", id);
		param.put("level", level);
		param.put("name", name);
		
		mService.setInsert(param);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/mUpdate",method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void mUpdate(
			@RequestParam(value="id", required=false, defaultValue="")String id,
			@RequestParam(value="pwd", required=false, defaultValue="")String pwd,
			@RequestParam(value="level", required=false, defaultValue="")String level,
			@RequestParam(value="name", required=false, defaultValue="")String name,
			@RequestParam(value="seq", required=false, defaultValue="")String seq) {
		HashMap<String, Object> param = new HashMap<>();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		param.put("id", id);
		param.put("pwd", passwordEncoder.encode(pwd));
		param.put("level", level);
		param.put("seq", seq);
		param.put("name", name);
		mService.setUpdate(param);
	}
	
	
	
	//
	
	
}
