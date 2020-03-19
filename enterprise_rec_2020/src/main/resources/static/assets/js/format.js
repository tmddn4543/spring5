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
	}else if(rec_type="B"){
		return "전수녹취";
	}else if(rec_type="N"){
		return "녹취정지";
	}else{
		return "";
	}
};