$(document).ready(
    function(){
    	var emp = "";
        var branch_cd = "";
        var auth_cd = "";
        var caller = "";
    	var called = "";
    	var selectday = "";
    	var selectstime = "";
    	var selectetime = "";
    	var bday = "";
    	var eday = "";
    	var rec_type = "";
    	var end_talk_time = "";
    	var start_talk_time = "";
    	var caller_attr = "";
    	var called_attr = "";
    	var startRow = "";
    	var pageSize = "";
    	var recordstartindex = 0;
    	var arr_batch = new Array();
    	var arr_c_id = new Array();
    	var arr_YYYYMM = new Array();

    	var u_auth_cd = $("#u_auth_cd").val();
    	var u_down_type = $("#u_down_type").val();    	
        /* 반응형 */
        $(window).resize(function(){
        var width = parseInt($(this).width()); //parseint는 정수로 하기 위함

            if (width > 1464 || width < 451){
                $('body').removeClass('mini-navbar');
            } else { 
                $('body').addClass('mini-navbar');
            }
        }).resize();
        
        $(".hide_darkbg").click(
            function(){
                $("body").removeClass("mini-navbar");
            }
        );

        /* 소스 */
        
        $.ajax({
    		type : "POST",
    		url : "/user/user_branch_get",
    		success : function(batch) {
    			for(var i=0; i<batch.length; i++){
    				if(i==0){
    					arr_batch[i] = "전체";
    				}
    				arr_batch[i+1] = batch[i].branch_cd;
    			}
    			$(".group_btn_act").jqxDropDownList({ source: arr_batch, selectedIndex: 0, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
    			$(".group_btn_act1").jqxDropDownList({ source: arr_batch, selectedIndex: 0, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
    		}
    	});
        var source = [
            "전체",
            "전수녹취",
            "인증녹취",
            "녹취정지"
        ];
        // Create a jqxDropDownList
        $(".record_type").jqxDropDownList({ source: source, selectedIndex: 1, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});


        var source = [
            "전체",
            "시스템관리자",
            "운용사용자",
            "그룹관리자",
            "상담원",
            "알람서비스등록"
        ];
        // Create a jqxDropDownList
        $(".authority_num").jqxDropDownList({ source: source, selectedIndex: 1, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});



        // create jqxcalendar.
        $(".jqxcalendar_act").jqxDateTimeInput({ width: 100 + "%", height: 32,  selectionMode: "range", formatString: "yyyy:MM:dd", theme: "bootstrap" });
        $(".jqxcalendar_act").on("jqxcalendar", function (event) {
            var selection = $("#jqxWidget").jqxDateTimeInput("getRange");
            if (selection.from != null) {
                $("#selection").html("<div>From: " + selection.from.toLocaleDateString() + " <br/>To: " + selection.to.toLocaleDateString() + "</div>");
            }
        });
        var cal_date = new Date();
        
        $(".jqxcalendar_act").jqxDateTimeInput("setRange", cal_date, cal_date);
        


        /* time select box */
        var Html = []; //배열로 담을 변수 선언
        var value = ""; //값을 입력할 변수 선언

        //시간 삽입
        for (var i = 0; i < 24; i++) {// 0 부터 23 까지 for문으로 생성
            if(i < 10) {// 값이 10보다 작을때에는 앞에 0을 하나 더 붙여주기 위해 if문으로 걸러냄.
                value = "0" + i;
            }else{
                value = i;
            }

            Html[i] = "<option value = "+value+" > "+value+"</option>"; //option값을 만든 다음 value값을 셋팅
            
        }
        $(".selStartTime").append(Html.join(""));
        $(".selEndTime").append(Html.join(""));
        $(".selEndTime").val("23").prop("selected",true);

        /* 조회클릭 */
        $("#next_action1").click(function(){
        	branch_cd = $("#group_btn_act1").val();
        	caller = $("#caller1").val();
        	called = $("#called1").val();
        	selectday = $("#jqxcalendar_act1").val();
        	selectstime = $("#selStartTime1").val();
        	selectetime = $("#selEndTime1").val();
        	bday = bdayFormat(selectday,selectstime);
        	eday = edayFormat(selectday,selectetime);
        	if($("#caller1_bt").text()=="선택 " && caller!=""){
        		alert("선택여부 확인해주세요.");
        		$("#caller1_bt").focus();
            	return false;
        	}
        	
        	if($("#called1_bt").text()=="선택 " && called!=""){
        		alert("선택여부 확인해주세요.");
        		$("#called1_bt").focus();
        		return false;
        	}
        	
        	$("#caller2").val(caller);
        	$("#called2").val(called);
        	
        	rec_type = recFormat2($("#record_type1").val());
        	emp = $("#emp1").val();
        	end_talk_time = $("#end_talk_time1").val();
        	start_talk_time = $("#start_talk_time1").val();
        	callSearch();
        	
            $(".hide").removeClass("hide");
            $(".first_page").css({"display" : "none"});
        });
        
        
        $("#next_action2").click(function(){
        	branch_cd = $("#group_btn_act2").val();
        	caller = $("#caller2").val();
        	called = $("#called2").val();
        	selectday = $("#jqxcalendar_act2").val();
        	selectstime = $("#selStartTime2").val();
        	selectetime = $("#selEndTime2").val();
        	bday = bdayFormat(selectday,selectstime);
        	eday = edayFormat(selectday,selectetime);
        	rec_type = recFormat2($("#record_type2").val());
        	emp = $("#emp2").val();
        	end_talk_time = $("#end_talk_time2").val();
        	start_talk_time = $("#start_talk_time2").val();
        	if($("#caller2_bt").text()=="선택 " && caller!=""){
        		alert("선택여부 확인해주세요.");
        		return false;
        	}
        	if($("#called2_bt").text()=="선택 " && called!=""){
        		alert("선택여부 확인해주세요.");
        		return false;
        	}
        	callSearch();
        });


        function callSearch(){
        	if(YYYYMM(bday, eday)=='false'){
        		alert("2달 이상 검색은 불가합니다.");
        		return false;
        	}
        	
        	var source =
            {
                 datatype: "json",
                 data : {emp : emp,
 					branch_cd : branch_cd,
 					auth_cd : auth_cd,
 					bday : bday,
 					eday : eday,
 					caller : caller,
 					called : called,
 					rec_type : rec_type,
 					end_talk_time : end_talk_time,
 					start_talk_time : start_talk_time,
 					caller_attr : caller_attr,
 					called_attr : called_attr},
                 datafields: [
					 { name: 'num'},
					 { name: 'emp_nm'},
					 { name: 'caller'},
					 { name: 'called'},
					 { name: 'auth_cd'},
					 { name: 'emp_id'},
					 { name: 'branch_cd'},
					 { name: 'rec_type'},
					 { name: 'call_date'},
					 { name: 'call_hour'},
					 { name: 'call_time'},
					 { name: 'dirname'},
					 { name: 'YYYYMM'},
					 { name: 'c_id'}
                ],
			    url: '/call/callSearch_YYYYMMDD',
			    root: 'Rows',
                cache: false,
				beforeprocessing: function(data)
				{		
					//recordstartindex = data.recordstartindex;
					source.totalrecords = data.total;
				}
            };		
			
		    var dataadapter = new $.jqx.dataAdapter(source);

            // initialize jqxGrid
		    
		    var initrowdetails = function (index, parentElement, gridElement, datarecord) {
		        var details = $($(parentElement).children()[0]);
		        details.html("Details: " + index);
		        
		        //details.html("<div class='information'><audio controls><source src='/call/media/"+arr_YYYYMM[index-recordstartindex]+"/"+arr_c_id[index-recordstartindex]+"' type='audio/wav'></audio></div>");
		    }
		    
		    
		    
		    if(u_auth_cd!="13" && (u_down_type=="Y" || u_down_type=="")){
		    	$("#grid").jqxGrid(//킵
	                    {
	                    	source: dataadapter,
	                        width: 100 + "%",
	                        height:  + "%",
	                        autoheight: true, 
	                        theme: 'material',
	                        rowdetails: true,
	                        rowdetailstemplate: {
	                            rowdetails: "<div style='margin: 20px !important;'>Row Details</div>",
	                            rowdetailsheight: 100
	                        },
	                        virtualmode: true,
	                        pageable: true,
	                        rendergridrows: function(obj)
	        				{
//	                        	for(var i=0; i<obj.data.length-recordstartindex; i++){
//	                        		arr_c_id[i] = obj.data[i+recordstartindex].c_id;
//	                        		arr_YYYYMM[i] = obj.data[i+recordstartindex].YYYYMM;
//	        					}
	        					return obj.data;     
	        				},
	        				initrowdetails: initrowdetails,
	                        columns: [
	                            { text: "순번", datafield: "num", width: 5 + "%", minwidth: 50.9},
	                            { text: "그룹", datafield: "branch_cd", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "사용자ID", datafield: "emp_id", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "이름", datafield: "emp_nm", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "발신번호", datafield: "caller", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "수신번호", datafield: "called", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "통화일자", datafield: "call_date", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "통화시각", datafield: "call_hour", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "통화시간", datafield: "call_time", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "유형", datafield: "rec_type", width: 9.090909 + "%", minwidth: 91.7333},//"+call[i].dirname+""+call[i].fname+"
	                            { text: "<button class='btn btn-default' id='zipDown_bt'>받기</button>", datafield: "dirname", width: 9.090909 + "%", minwidth: 91.7333}
	                        ]
	            });
		    }else{
		    	$("#grid").jqxGrid(//킵
	                    {
	                    	source: dataadapter,
	                        width: 100 + "%",
	                        height:  + "%",
	                        autoheight: true, 
	                        theme: 'material',
	                        rowdetails: true,
	                        rowdetailstemplate: {
	                            rowdetails: "<div style='margin: 20px !important;'>Row Details</div>",
	                            rowdetailsheight: 100
	                        },
	                        virtualmode: true,
	                        pageable: true,
	                        rendergridrows: function(obj)
	        				{
	                        	for(var i=0; i<obj.data.length-recordstartindex; i++){
	                        		arr_c_id[i] = obj.data[i+recordstartindex].c_id;
	                        		arr_YYYYMM[i] = obj.data[i+recordstartindex].YYYYMM;
	        					}
	        					return obj.data;     
	        				},
	        				initrowdetails: initrowdetails,
	                        columns: [
	                            { text: "순번", datafield: "num", width: 5 + "%", minwidth: 50.9},
	                            { text: "그룹", datafield: "branch_cd", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "사용자ID", datafield: "emp_id", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "이름", datafield: "emp_nm", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "발신번호", datafield: "caller", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "수신번호", datafield: "called", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "통화일자", datafield: "call_date", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "통화시각", datafield: "call_hour", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "통화시간", datafield: "call_time", width: 9.090909 + "%", minwidth: 91.7333},
	                            { text: "유형", datafield: "rec_type", width: 9.090909 + "%", minwidth: 91.7333},//"+call[i].dirname+""+call[i].fname+"
	                        ]
	            });
		    }
		    
        	
        }
        
        
  
        $('#grid').on('rowexpand', function (event) 
        		{
        		    // event arguments.
        		    var args = event.args;
        		    // row details.
        		    var details = args.details;
        		    // row's bound index.
        		    var rowBoundIndex = args.rowindex;
        		    console.log(event);
        		});
        
        
        
        function usersSearch(){
        	$.ajax({
        		type : "POST",
        		url : "/call/usersSearch",
				data : {emp : emp, branch_cd : branch_cd, auth_cd : auth_cd},
				success : function(users) {
					/* 사용자 검색 */
		            var data = new Array();
		            // generate sample data.
		            var generatedata = function (startindex, endindex) {
		                var data = {};
		                for (var i = startindex; i < users.length; i++) {
		                    var row = {};
		                    row["num"] = i;
		                    row["name"] = users[i].emp_nm;
		                    row["userid"] = users[i].emp_id;
		                    row["groupname"] = users[i].branch_cd;
		                    row["phone_num"] = users[i].tel_no_070;
		                    row["call_type"] = recFormat(users[i].rec_type);
		                    data[i] = row;
		                }
		                return data;
		            }
		            
		            // load virtual data.
		            var rendergridrows = function (params) {
		                var data = generatedata(params.startindex, params.endindex);
		                return data;
		            }
		            var totalcolumnrenderer = function (row, column, cellvalue) {
		                var cellvalue = $.jqx.dataFormat.formatnumber(cellvalue, "c2");
		                return "<span style="+"margin: 6px 3px; font-size: 12px; float: right; font-weight: bold;" + ">" + cellvalue + "</span>";
		            }
		            var source =
		            {
		                datatype: "array",
		                localdata: {},
		                totalrecords: users.length
		            };

		            var dataAdapter = new $.jqx.dataAdapter(source);
		            $(".user_search_grid").jqxGrid(
		            {
		                width: 100 + '%',
		                source: dataAdapter,
		                theme: 'material',
		                pageable: true,
		                autoheight: true,
		                columnsresize: true,
		                pagermode: 'simple',
		                rendergridrows: rendergridrows,
		                virtualmode: true,
		                columns: [
		                  { text: '순번', datafield: 'num', width: 5 + '%', minwidth: 41.9},
		                  { text: '사용자명', datafield: 'name', width: 19 + '%', minwidth: 159.217 },
		                  { text: '사용자 ID', datafield: 'userid', width: 19 + '%', minwidth: 159.217 },
		                  { text: '그룹', datafield: 'groupname', width: 19 + '%', minwidth: 159.217 },
		                  { text: '전화번호', datafield: 'phone_num', width: 19 + '%', minwidth: 159.217 },
		                  { text: '녹취유형', datafield: 'call_type', width: 19 + '%', minwidth: 159.217 }
		                ]
		            });
		            
		            alert("사용자를 검색했습니다.");
				},
				error : function() {
					alert("알수없는 오류가 발생하였습니다.");
				},
				complete : function() {
				}
        	});
        }
        
        
        $("#id_user_search3").click(function(){
        	emp = $("#emp3").val();
        	branch_cd = $(".group_btn_act1").val();
        	auth_cd = $(".authority_num").val();
        	usersSearch();
        });
        
        $("#id_user_search2").click(function(){
        	emp = $("#emp2").val();
        	usersSearch();
        	$("#user_search").modal("show");
        });
        
        
        $("#id_user_search1").click(function(){
        	emp = $("#emp1").val();
        	usersSearch();
        	$("#user_search").modal("show");
        });
        
        $("#excelExport").click(function(){
        	location.href="/call/xlsxDownload?emp="+emp+"&branch_cd="+branch_cd+"&auth_cd="+auth_cd+"&bday="+bday+"&eday="+eday+"&caller="+caller+"&called="+called+"&rec_type="+rec_type+"&end_talk_time="+end_talk_time+"&start_talk_time="+start_talk_time+"&called_attr="+called_attr+"&caller_attr="+caller_attr;
        });
        
        
        

       $("#grid").on("click","button",function(){
        	var count = 0;
        	var arr = new Array();
        	$(".checkbox_name").each(function(){  // .each()는 forEach를 뜻한다.
    			if($(this).is(":checked")){
    				var res = $(this).val();
    				arr[count] = res;
    				count = count+1;
    			}  
        	});
        	if(arr[0]==null){
        		alert("다운받으실 목록을 체크 해주세요.");
        		return false;
        	}
        	
        	$("#hidden_arr").val(arr);
        	$("#form_arr").submit();
       });
        
        $("#testid").click(function(){
        	var count = 0;
        	var arr = new Array();
        	$(".checkbox_name").each(function(){  // .each()는 forEach를 뜻한다.
    			if($(this).is(":checked")){
    				var res = $(this).val();
    				arr[count] = res;
    				count = count+1;
    			}  
        	});
        	
        	alert(count+"체크된개수");
        });
        
        $("#called1_ul").on("click", "li", function(){
        	var res = $(this).text();
        	$("#called1_bt").html(res+" <span class='caret'></span>");
        	$("#called2_bt").html(res+" <span class='caret'></span>");
        	if(res=="정확"){
        		called_attr = "eq";
        	}else if(res=="포함"){
        		called_attr = "like";
        	}
        });
        
        $("#called2_ul").on("click", "li", function(){
        	var res = $(this).text();
        	$("#called2_bt").html(res+" <span class='caret'></span>");
        	if(res=="정확"){
        		called_attr = "eq";
        	}else if(res=="포함"){
        		called_attr = "like";
        	}
        });
        
        
        $("#caller1_ul").on("click", "li", function(){
        	var res = $(this).text();
        	$("#caller1_bt").html(res+" <span class='caret'></span>");
        	$("#caller2_bt").html(res+" <span class='caret'></span>");
        	if(res=="정확"){
        		caller_attr = "eq";
        	}else if(res=="포함"){
        		caller_attr = "like";
        	}
        });
        
        $("#caller2_ul").on("click", "li", function(){
        	var res = $(this).text();
        	$("#caller2_bt").html(res+" <span class='caret'></span>");
        	if(res=="정확"){
        		caller_attr = "eq";
        	}else if(res=="포함"){
        		caller_attr = "like";
        	}
        });
        
        
});