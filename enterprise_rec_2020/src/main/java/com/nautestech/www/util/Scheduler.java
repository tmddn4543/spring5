package com.nautestech.www.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nautestech.www.model.Call;
import com.nautestech.www.model.CallJoin;
import com.nautestech.www.serviceImpl.CallService;

@Component
public class Scheduler {
	
	@Autowired
	CallService cService;

	
	@Value("${statisticsLimit}")
	int statisticsLimit;
	
	@Value("${callhistoryYMD}")
	Boolean callhistoryYMD;
	
	//(cron="0 1 0 * * *")
	//매월 매일  0시 1분 실행되는 메서드
	//10초마다실행되는메서드
	//@Scheduled(cron="*/10 * * * * *")
	@Scheduled(cron="0 1 0 * * *")
	public void createTable() {
		String YYYYMM = new SimpleDateFormat("yyyyMM", Locale.KOREA).format(new Date());
		HashMap<String, Object> param = new HashMap<>();
		if(YYYYMM.substring(4).equals("12")) {
			YYYYMM = String.valueOf(Integer.parseInt(YYYYMM)+89);
		}else {
			YYYYMM = String.valueOf(Integer.parseInt(YYYYMM)+1);
		}
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
	
	@Scheduled(cron="*/60 * * * * *")
	public void stat() {
		String YYYYMM = new SimpleDateFormat("yyyyMM", Locale.KOREA).format(new Date());
		HashMap<String, Object> param = new HashMap<>();
		String query = "";
        query = " select ch.c_id, ch.emp_id, ch.emp_nm, ch.branch_cd, BM.tel_no, ";
        query+= " case when BM.tel_no = ch.caller then 'O' else 'I' end as CALLTYPE, ";
        query+= " case when ch.rbtime = '0' then ";
        query+= " TIMESTAMPDIFF(SECOND, date_format(btime, '%Y%m%d%H%i%s'), date_format(etime, '%Y%m%d%H%i%s')) ";
        query+= " else ";
        query+= " TIMESTAMPDIFF(SECOND, date_format(rbtime, '%Y%m%d%H%i%s'), date_format(retime, '%Y%m%d%H%i%s')) ";
        query+= " end as calltime, ";
        query+= " case when ch.rbtime = '0' then ";
        query+= " date_format(btime, '%Y%m%d%H') ";
        query+= " else ";
        query+= " date_format(rbtime, '%Y%m%d%H') ";
        query+= " end as s_date ";
        query+= " from call_history"+(callhistoryYMD ? "_"+YYYYMM : "")+" ch inner join users BM on ch.emp_id = BM.emp_id ";
        query+= " where ch.is_statistics = 'N' and ch.emp_id != '' and ch.emp_id is not null ";
        query+= " limit "+statisticsLimit;
		param.put("query", query); 
		List<CallJoin> call = cService.setSelect(param);
		param = new HashMap<>();
		for(int i=0; i<call.size(); i++) {
			String createQry = "";
			createQry = " update call_history"+(callhistoryYMD ? "_"+YYYYMM : "")+" set is_statistics = 'Y' where c_id = "+call.get(i).getC_id();
			param.put("createQry", createQry);
			cService.setCreate(param);
			int s_caller_cnt = 0;
            int s_called_cnt = 0;
            int s_caller_time = 0;
            int s_called_time = 0;
            if(call.get(i).getCALLTYPE().equals("O"))
            {
                s_caller_cnt = 1;
                s_caller_time = Integer.parseInt(call.get(i).getCalltime());
            }
            else
            {
                s_called_cnt = 1;
                s_called_time = Integer.parseInt(call.get(i).getCalltime());
            }
			
			param = new HashMap<>();
			createQry = "";
			createQry = " insert into statistics(s_date, emp_id, emp_nm, branch_cd, tel_no, s_caller_cnt, s_called_cnt, s_caller_time, s_called_time) ";
			createQry+= " values('"+call.get(i).getS_date()+"', '"+call.get(i).getEmp_id()+"', '"+call.get(i).getEmp_nm()+"', '"+call.get(i).getBranch_cd()+"', '"+call.get(i).getTel_no()+"', "+s_caller_cnt+", "+s_called_cnt+", "+s_caller_time+", "+s_called_time+") on duplicate key update ";
			createQry+= " emp_nm='"+call.get(i).getEmp_nm()+"', branch_cd='"+call.get(i).getBranch_cd()+"', tel_no='"+call.get(i).getTel_no()+"', s_caller_cnt=s_caller_cnt+"+s_caller_cnt+", s_called_cnt=s_called_cnt+"+s_called_cnt+", s_caller_time=s_caller_time+"+s_caller_time+", s_called_time=s_called_time+"+s_called_time+" ";
			param.put("createQry", createQry);
			cService.setCreate(param);
		}
	}
}
