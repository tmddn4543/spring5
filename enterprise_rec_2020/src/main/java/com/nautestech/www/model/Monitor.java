package com.nautestech.www.model;

import lombok.Data;

@Data
public class Monitor {

	public int c_id;
	public String u_id;
	public String status;
	public String status_er;
	public String status_ed;
	public String caller;
	public String called;
	public String callid;
	public String stime;
	public String sltime;
	public String rtime;
	public String rltime;
	public String rtime_er;
	public String rltime_er;
	public String rtime_ed;
	public String rltime_ed;
	public String fname;
	public String rtp1;
	public String rtp2;
}
//}c_id int(1)PK
//u_id             varchar(50)
//status           char(1)
//status_er        char(1)
//status_ed        char(1)
//caller           char(50)
//called           char(50)
//callid           char(100)
//stime            char(19)
//sltime           char(19)
//rtime            char(19)
//rltime           char(19)
//rtime_er         char(19)
//rltime_er        char(19)
//rtime_ed         char(19)
//rltime_ed        char(19)
//fname            varchar(150)
//rtp1             varchar(30)
//rtp2             varchar(30)