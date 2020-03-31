package com.nautestech.www.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nautestech.www.model.Call;
import com.nautestech.www.serviceImpl.CallService;

@Component
public class Scheduler {
	
	@Autowired
	CallService cService;

	
	//(cron="0 1 0 1 * *")
	//매월 1일  0시 1분 실행되는 메서드
	//@Scheduled(cron="*/10 * * * * *")
	@Scheduled(cron="*/10 * * * * *")
	public void createTable() {
		String YYYYMM = new SimpleDateFormat("yyyyMM", Locale.KOREA).format(new Date());
		HashMap<String, Object> param = new HashMap<>();
		String createQry = "";
		createQry+="CREATE TABLE if not exists `call_history_"+YYYYMM+"` (         ";
		createQry+="		  `c_id` int(11) NOT NULL AUTO_INCREMENT,              ";
		createQry+="		  `btime` varchar(19) DEFAULT NULL,                    ";
		createQry+="		  `etime` varchar(19) DEFAULT NULL,                    ";
		createQry+="		  `caller` varchar(50) DEFAULT NULL,                   ";
		createQry+="		  `called` varchar(50) DEFAULT NULL,                   ";
		createQry+="		  `callid` varchar(100) DEFAULT NULL,                  ";
		createQry+="		  `dirname` varchar(200) DEFAULT NULL,                 ";
		createQry+="		  `filesize` varchar(20) DEFAULT NULL,                 ";
		createQry+="		  `fname` varchar(100) DEFAULT NULL,                   ";
		createQry+="		  `emp_id` varchar(20) DEFAULT NULL,                   ";
		createQry+="		  `branch_cd` varchar(10) DEFAULT NULL,                ";
		createQry+="		  `emp_nm` varchar(42) DEFAULT NULL,                   ";
		createQry+="		  `system_id` varchar(6) DEFAULT NULL,                 ";
		createQry+="		  `is_statistics` char(1) DEFAULT 'N',                 ";
		createQry+="		  `auth_cd` varchar(10) DEFAULT NULL,                  ";
		createQry+="		  `rbtime` varchar(19) DEFAULT NULL,                   ";
		createQry+="		  `retime` varchar(19) DEFAULT NULL,                   ";
		createQry+="		  `duple_flag` char(1) DEFAULT 'N',                    ";
		createQry+="		  `rec_type` char(1) DEFAULT NULL,                     ";
		createQry+="		  `group_id` varchar(20) DEFAULT NULL,                 ";
		createQry+="		  PRIMARY KEY (`c_id`),                                ";
		createQry+="		  KEY `emp_id` (`emp_id`),                             ";
		createQry+="		  KEY `emp_nm` (`emp_nm`),                             ";
		createQry+="		  KEY `group_id` (`group_id`),                         ";
		createQry+="		  KEY `rec_type` (`rec_type`),                         ";
		createQry+="		  KEY `auth_cd` (`auth_cd`),                           ";
		createQry+="		  KEY `branch_cd` (`branch_cd`),                       ";
		createQry+="		  KEY `caller` (`caller`),                             ";
		createQry+="		  KEY `called` (`called`)                              ";
		createQry+="		) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=euckr ";
		param.put("createQry", createQry);
		cService.setCreate(param);
		System.out.println("월별 테이블 생성!");
	}
}
