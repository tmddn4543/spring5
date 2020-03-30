function authFormat(auth_cd){
	if(auth_cd=="00"){
		return "시스템관리자";
	}else if(auth_cd=="12"){
		return "그룹관리자";
	}else if(auth_cd=="13"){
		return "상담원";
	}else if(auth_cd=="11"){
		return "운용사용자";
	}else if(auth_cd=="15"){
		return "알람서비스등록";
	}
}

function YYYYMM(bday, eday){
	bday = bday.substr(0,7);
	eday = eday.substr(0,7);
	bday = bday.replace(":", "");
	eday = eday.replace(":", "");
	var result = eday - bday;
	if(result>1 && result != 89){
		return 'false';
	}else{
		return 'true';
	}
};
function bdayFormat(selectday,selectstime){
	selectday = selectday.substr(0,10);
	selectday = selectday+":"+selectstime+":00:00";
	return selectday;
};
function edayFormat(selectday,selectetime){
	selectday = selectday.substr(13,23);
	selectday = selectday+":"+selectetime+":59:59";
	return selectday;
};

function dateFormat(btime){
	btime = btime.substr(0,10);
	return btime;
};

function hourFormat(btime,etime){
	btime = btime.substr(11,19);
	etime = etime.substr(11,19);
	return btime+"~"+etime;
};

function timeFormat(btime,etime){
	
	var startTime = btime.replace(/:/gi, "");
	var endTime = etime.replace(/:/gi, "");
	 var startDate = new Date(parseInt(startTime.substring(0,4), 10),
             parseInt(startTime.substring(4,6), 10)-1,
             parseInt(startTime.substring(6,8), 10),
             parseInt(startTime.substring(8,10), 10),
             parseInt(startTime.substring(10,12), 10),
             parseInt(startTime.substring(12,14), 10)
            );
           
   // 종료일시
   var endDate = new Date(parseInt(endTime.substring(0,4), 10),
             parseInt(endTime.substring(4,6), 10)-1,
             parseInt(endTime.substring(6,8), 10),
             parseInt(endTime.substring(8,10), 10),
             parseInt(endTime.substring(10,12), 10),
             parseInt(endTime.substring(12,14), 10)
            );

   // 두 일자(startTime, endTime) 사이의 차이를 구한다.
   var dateGap = endDate.getTime() - startDate.getTime();
   var timeGap = new Date(0, 0, 0, 0, 0, 0, endDate - startDate); 
   
   // 두 일자(startTime, endTime) 사이의 간격을 "일-시간-분"으로 표시한다.
   var diffDay  = Math.floor(dateGap / (1000 * 60 * 60 * 24)); // 일수
   var diffHour = timeGap.getHours();       // 시간
   var diffMin  = timeGap.getMinutes();      // 분
   var diffSec  = timeGap.getSeconds();      // 초

   if(diffMin<10){
	   diffMin = "0"+diffMin;
   }
   if(diffSec<10){
	   diffSec = "0"+diffSec;
   }
   return diffMin+":"+diffSec;
	
};

function recFormat(rec_type){
	if(rec_type=="S"){
		return "인증녹취";
	}else if(rec_type=="B"){
		return "전수녹취";
	}else if(rec_type=="N"){
		return "녹취정지";
	}else{
		return "";
	}
};
function recFormat2(rec_type){
	if(rec_type=="전체"){
		return "";
	}else if(rec_type=="인증녹취"){
		return "S";
	}else if(rec_type=="전수녹취"){
		return "B";
	}else if(rec_type=="녹취정지"){
		return "N";
	}else{
		return "";
	}
};