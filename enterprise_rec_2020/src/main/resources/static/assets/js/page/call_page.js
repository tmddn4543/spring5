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
    	
    	
    	
    	
    	var label ="";
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

        
        
        
        var source = [
            "포함",
            "정확"
        ];
        // Create a jqxDropDownList
        $(".search_option1").jqxDropDownList({ source: source, selectedIndex: 0, width: 100 + "%", height: 32, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
        $(".search_option2").jqxDropDownList({ source: source, selectedIndex: 0, width: 100 + "%", height: 32, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});

        
        
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

        
        selectday = $("#jqxcalendar_act2").val();
        selectstime = "00";
        selectetime = "23";
        bday = bdayFormat(selectday,selectstime);
        eday = edayFormat(selectday,selectetime);
        
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
				recordstartindex = data.recordstartindex;
				source.totalrecords = data.total;
			}
        };		
		
	    var dataadapter = new $.jqx.dataAdapter(source);

        // initialize jqxGrid
	    
	    var initrowdetails = function (index, parentElement, gridElement, datarecord) {
	        var details = $($(parentElement).children()[0]);
	        details.html("<div class='information'><audio controls><source src='/call/media/"+datarecord.YYYYMM+"/"+datarecord.c_id+"' type='audio/wav'></audio></div>");
	    }
	    
	    var grid_check = "";
	    if(u_auth_cd!="13"){
	    	if(u_down_type=="Y" || u_down_type==""){
	    		grid_check="true";
	    	}
	    }else{
	    	if(u_down_type=="Y"){
	    		grid_check="true";
	    	}
	    }
	    
	    
	    var getLocalization = function () {
	    	var localizationobject = {};
	    	localizationobject.emptydatastring = "이력 검색 결과가 없습니다.";
	    	$("#grid").jqxGrid('localizestrings', localizationobject);
	    	return localizationobject;
	    }
	    //u_auth_cd!="13" && (u_down_type=="Y" || u_down_type=="")
	    if(grid_check=="true"){
	    	$("#grid").jqxGrid(//킵
                    {
                    	source: dataadapter,
                        width: 100 + "%",
                        autoheight: false, 
                        theme: 'material',
                        rowdetails: true,
                        localization: getLocalization(),
                        rowdetailstemplate: {
                            rowdetails: "<div style='margin: 20px !important;'>Row Details</div>",
                            rowdetailsheight: 100
                        },
                        showtoolbar: true,
                        rendertoolbar: function (statusbar) {
                            // appends buttons to the status bar.
                            var container = $("<div style='overflow: hidden; position: relative; margin: 6px;'></div>");
                            var addButton = $("<div style='float: right; margin-left: 10px;'><img style='position: relative; margin-top: 2px;' ><span style='margin-left: 4px; position: relative; top: -3px;'>받기</span></div>");
                            var xlsButton = $("<div style='float: right; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' ><span style='margin-left: -2px; position: relative; top: -3px;'>엑셀다운</span></div>");
                            
                            container.append(addButton);
                            container.append(xlsButton);
                            
                            statusbar.append(container);
                            addButton.jqxButton({  width: 60, height: 20 });
                            xlsButton.jqxButton({  width: 60, height: 20 });
                            // add new row.
                            addButton.click(function (event) {
                            	var count = 0;
                            	var arr = new Array();
                            	
                            	console.log(source.records);
                            	
                            	$(".check_label").each(function(){  // .each()는 forEach를 뜻한다.
                        			if($(this).val()==true){
                        				var str = $(this).parents()[2].id;
                        				str = str.substring(3,4);
                        				str = Number(str);
                        				str = (recordstartindex+str);
                        				arr[count] = source.records[str].dirname;
                        				console.log(arr[count]);
                        				count = count+1;
                        			}
                            	});
                            	$("#hidden_arr").val(arr);
                            	if(count==0){
                            		alert("알집다운 체크를 해주시기 바랍니다.");
                            		return false;
                            	}
                            	form_arr.submit();
                            });
                            xlsButton.click(function (event) {
                            	location.href="/call/xlsxDownload?emp="+emp+"&branch_cd="+branch_cd+"&auth_cd="+auth_cd+"&bday="+bday+"&eday="+eday+"&caller="+caller+"&called="+called+"&rec_type="+rec_type+"&end_talk_time="+end_talk_time+"&start_talk_time="+start_talk_time+"&called_attr="+called_attr+"&caller_attr="+caller_attr;
                            });
                        },
                        virtualmode: true,
                        pageable: true,
                        rendergridrows: function(obj)
        				{
        					return obj.data;     
        				},
        				initrowdetails: initrowdetails,
        				columns: [
        					 { text: "순번", datafield: "num", width: 5 + "%", minwidth: 50.9},
                             { text: "그룹", datafield: "branch_cd", width: 9.5  + "%", minwidth: 96.71},
                             { text: "사용자ID", datafield: "emp_id", width: 9.5  + "%", minwidth: 96.71},
                             { text: "이름", datafield: "emp_nm", width: 9.5  + "%", minwidth: 96.71},
                             { text: "발신번호", datafield: "caller", width: 9.5  + "%", minwidth: 96.71},
                             { text: "수신번호", datafield: "called", width: 9.5  + "%", minwidth: 96.71},
                             { text: "통화일자", datafield: "call_date", width: 9.5  + "%", minwidth: 96.71},
                             { text: "통화시각", datafield: "call_hour", width: 9.5  + "%", minwidth: 96.71},
                             { text: "통화시간", datafield: "call_time", width: 9.5  + "%", minwidth: 96.71},
                             { text: "유형", datafield: "rec_type", width: 9.5  + "%", minwidth: 96.71},
                            //"<button class='btn btn-default' id='zipDown_bt'>받기</button>"
                            { text: "알집다운", datafield: "dirname", cellsalign: 'center', width: 9.090909 + "%", minwidth: 91.7333,
                            	
                               createwidget : function (row, column, value, cellElement){
                            	   label = $("<label class='check_label'></label>");
                            	   $(cellElement).append(label);
                            	   label.jqxCheckBox({ width: 120, height: 25 });
                                },
	                            initwidget: function (row, column, value, htmlElement) {
	                                $("#grid").on("pagechanged", function (event) {
	                                   $(htmlElement).children(0).jqxCheckBox('uncheck');
	                                  });
	                            }
                            }
                        ]
            });
	    }else{
	    	$("#grid").jqxGrid(//킵
                    {
                    	source: dataadapter,
                        width: 100 + "%",
                        autoheight: false, 
                        theme: 'material',
                        rowdetails: true,
                        localization: getLocalization(),
                        rowdetailstemplate: {
                            rowdetails: "<div style='margin: 20px !important;'>Row Details</div>",
                            rowdetailsheight: 100
                        },
                        virtualmode: true,
                        pageable: true,
                        rendergridrows: function(obj)
        				{
        					return obj.data;     
        				},
        				initrowdetails: initrowdetails,
                        columns: [
                            { text: "순번", datafield: "num", width: 5 + "%", minwidth: 50.9},
                            { text: "그룹", datafield: "branch_cd", width: 10.5555 + "%", minwidth: 107.45499 },
                            { text: "사용자ID", datafield: "emp_id", width: 10.5555 + "%", minwidth: 107.45499 },
                            { text: "이름", datafield: "emp_nm", width: 10.5555 + "%", minwidth: 107.45499 },
                            { text: "발신번호", datafield: "caller", width: 10.5555 + "%", minwidth: 107.45499 },
                            { text: "수신번호", datafield: "called", width: 10.5555 + "%", minwidth: 107.45499 },
                            { text: "통화일자", datafield: "call_date", width: 10.5555 + "%", minwidth: 107.45499 },
                            { text: "통화시각", datafield: "call_hour", width: 10.5555 + "%", minwidth: 107.45499 },
                            { text: "통화시간", datafield: "call_time", width: 10.5555 + "%", minwidth: 107.45499 },
                            { text: "유형", datafield: "rec_type", width: 10.5555 + "%", minwidth: 107.45499 },//"+call[i].dirname+""+call[i].fname+"
                        ]

            });
	    }
        
        
        $("#next_action2").click(function(){
        	$('#grid').jqxGrid('gotopage', 0);
        	
        	if($(".search_option1").val()=="포함"){
        		called_attr = "like";
        	}else if($(".search_option1").val()=="정확"){
        		called_attr = "eq";
        	}
        	
        	if($(".search_option2").val()=="포함"){
        		caller_attr = "like";
        	}else if($(".search_option2").val()=="정확"){
        		caller_attr = "eq";
        	}
        	
        	
        	
        	source.data.branch_cd = $("#group_btn_act2").val();
        	source.data.caller = $("#caller2").val();
        	source.data.called = $("#called2").val();
        	selectday = $("#jqxcalendar_act2").val();
        	selectstime = $("#selStartTime2").val();
        	selectetime = $("#selEndTime2").val();
        	source.data.bday = bdayFormat(selectday,selectstime);
        	source.data.eday = edayFormat(selectday,selectetime);
        	source.data.rec_type = recFormat2($("#record_type2").val());
        	source.data.emp = $("#emp2").val();
        	source.data.end_talk_time = $("#end_talk_time2").val();
        	source.data.start_talk_time = $("#start_talk_time2").val();
        	source.data.called_attr = called_attr;
        	source.data.caller_attr = caller_attr;
        	
        	
        	
        	
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
        	if(YYYYMM(bday, eday)=='false'){
        		alert("2달 이상 검색은 불가합니다.");
        		return false;
        	}
        	
        	$('#window').jqxWindow('close');
        	
        	$('#grid').jqxGrid('updatebounddata');
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
        
        
        
//        $("#called2_ul").on("click", "li", function(){
//        	var res = $(this).text();
//        	$("#called2_bt").html(res+" <span class='caret'></span>");
//        	if(res=="정확"){
//        		called_attr = "eq";
//        	}else if(res=="포함"){
//        		called_attr = "like";
//        	}
//        });
//        
//        
//        $("#caller2_ul").on("click", "li", function(){
//        	var res = $(this).text();
//        	$("#caller2_bt").html(res+" <span class='caret'></span>");
//        	if(res=="정확"){
//        		caller_attr = "eq";
//        	}else if(res=="포함"){
//        		caller_attr = "like";
//        	}
//        });
        
        
        
        $('.user_search_grid').on('rowclick', function (event) {
        	$("#emp2").val(event.args.row.bounddata.userid);
        	$("#user_search").modal("hide");
        });
        
});