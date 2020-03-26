package com.nautestech.www.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nautestech.www.serviceImpl.CallService;

@Component
public class Scheduler {
	
	
	
	@Autowired
	CallService cService;

	//매월 1일  0시 1분 실행되는 메서드
	//@Scheduled(cron="0 1 0 1 * *")
	@Scheduled(cron="*/10 * * * * *")
	public void createTable() {
		String YYYYMM = new SimpleDateFormat("yyyyMM", Locale.KOREA).format(new Date());
		HashMap<String, Object> param = new HashMap<>();
		String createQry = "";
		createQry+="CREATE TABLE if not exists `call_history_"+YYYYMM+"` (                               ";
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
		createQry+="		  KEY `idx_branch_cd_ch"+YYYYMM+"` (`branch_cd`),                ";
		createQry+="		  KEY `idx_btime_ch"+YYYYMM+"` (`btime`),                        ";
		createQry+="		  KEY `idx_etime_ch"+YYYYMM+"` (`etime`),                        ";
		createQry+="		  KEY `idx_caller_ch"+YYYYMM+"` (`caller`),                      ";
		createQry+="		  KEY `idx_called_ch"+YYYYMM+"` (`called`),                      ";
		createQry+="		  KEY `idx_emp_id_ch"+YYYYMM+"` (`emp_id`),                      ";
		createQry+="		  KEY `idx_is_statistics_ch"+YYYYMM+"` (`is_statistics`),        ";
		createQry+="		  KEY `idx_rbtime_ch"+YYYYMM+"` (`rbtime`),                      ";
		createQry+="		  KEY `idx_retime_ch"+YYYYMM+"` (`retime`)                       ";
		createQry+="		) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=euckr ";
		param.put("value", createQry);
		cService.setCreate(param);
	}
}
