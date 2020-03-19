$(document).ready(
    function(){

    	
    	var emp = "";
        var branch_cd = "";
        var auth_cd = "";
        
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
        var source = [
            "전체",
            "admin"
        ];
        // Create a jqxDropDownList
        $(".group_btn_act").jqxDropDownList({ source: source, selectedIndex: 1, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
      
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
            "알람서비스등록",
            "상담원"
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
        
        
        $(".jqxcalendar_act").jqxDateTimeInput("setRange", new Date(), new Date());
        


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
        $(".next_action").click(function(){
        	branch_cd = $(".group_btn_act").val();
        	
        	console.log($(".jqxcalendar_act").val());
        	console.log($(".selStartTime").val());
        	console.log($(".selEndTime").val());
        	
        	if($("#emp1").val()==""){
        		emp = $("#emp2").val();
        	}else{
        		emp = $("#emp1").val();
        	}
        	$.ajax({
        		type : "POST",
        		url : "/admin/callSearch",
				data : {emp : emp, branch_cd : branch_cd, auth_cd : auth_cd},
				success : function(call) {
					/* 그리드 */
		            var customsortfunc = function (column, direction) {
		            var sortdata = new Array();
		            if (direction == 'ascending') direction = true;
		            if (direction == 'descending') direction = false;
		            if (direction != null) {
		                for (i = 0; i < data.length; i++) {
		                    sortdata.push(data[i]);
		                }
		            }
		            else sortdata = data;
		            var tmpToString = Object.prototype.toString;
		            Object.prototype.toString = (typeof column == "function") ? column : function () { return this[column] };
		            if (direction != null) {
		                sortdata.sort(compare);
		                if (!direction) {
		                    sortdata.reverse();
		                }
		            }
		            source.localdata = sortdata;
		            $("#grid").jqxGrid('updatebounddata', 'sort');
		            Object.prototype.toString = tmpToString;
		            }
		             // custom comparer.
		            var compare = function (value1, value2) {
		                value1 = String(value1).toLowerCase();
		                value2 = String(value2).toLowerCase();
		                try {
		                    var tmpvalue1 = parseFloat(value1);
		                    if (isNaN(tmpvalue1)) {
		                        if (value1 < value2) { return -1; }
		                        if (value1 > value2) { return 1; }
		                    }
		                    else {
		                        var tmpvalue2 = parseFloat(value2);
		                        if (tmpvalue1 < tmpvalue2) { return -1; }
		                        if (tmpvalue1 > tmpvalue2) { return 1; }
		                    }
		                }
		                catch (error) {
		                    var er = error;
		                }
		                return 0;
		            };

		            // prepare the data
		            var data = new Array();


		            // generate sample data.
		            var generatedata = function (startindex, endindex) {
		                var data = {};
		                for (var i = startindex; i < endindex; i++) {
		                    var row = {};
		                    row["num"] = i;
		                    row["groupname"] = call[i].branch_cd;
		                    row["userid"] = call[i].emp_id;
		                    row["name"] = call[i].emp_nm;
		                    row["send_num"] = call[i].caller;
		                    row["receive_num"] = call[i].called;
		                    row["call_date"] = dateFormat(call[i].btime);
		                    row["call_hour"] = hourFormat(call[i].btime,call[i].etime);
		                    row["call_time"] = timeFormat(call[i].btime,call[i].etime);
		                    row["call_type"] = recFormat(call[i].rec_typ);
		                    row["listen"] = "<label class='check_label'><input type='checkbox' value=''></label>";
		                    data[i] = row;
		                }
		                return data;
		            }
		            var source =
		            {
		                datatype: "array",
		                localdata: {},
		                totalrecords: call.length
		            };
		            
		            
		            // load virtual data.
		            var rendergridrows = function (params) {
		                var data = generatedata(params.startindex, params.endindex);
		                return data;
		            }
		            var totalcolumnrenderer = function (row, column, cellvalue) {
		                var cellvalue = $.jqx.dataFormat.formatnumber(cellvalue, "c2");
		                return "<span style="+"margin: 6px 3px; font-size: 12px; float: right; font-weight: bold;" + ">" + cellvalue + "</span>";
		            }
		            var dataAdapter = new $.jqx.dataAdapter(source);
		            $("#grid").jqxGrid(
		                    {
		                        width: 100 + "%",
		                        height:  + "%",
		                        autoheight: true, 
		                        theme: 'material',
		                        rowdetails: true,
		                        rowdetailstemplate: {
		                            rowdetails: "<div style='margin: 10px;'><div class='information'><audio autoplay controls><source src='/resource/assets/audio/sample.mp3' type='audio/mp3'></audio></div></div>",
		                            rowdetailsheight: 100
		                        },
		                        source: dataAdapter,                
		                        virtualmode: true,
		                        pageable: true,
		                        rendergridrows: rendergridrows,
		                        columns: [
		                            { text: "순번", datafield: "num", width: 5 + "%", minwidth: 50.9},
		                            { text: "그룹", datafield: "groupname", width: 9.090909 + "%", minwidth: 91.7333},
		                            { text: "사용자ID", datafield: "userid", width: 9.090909 + "%", minwidth: 91.7333},
		                            { text: "이름", datafield: "name", width: 9.090909 + "%", minwidth: 91.7333},
		                            { text: "발신번호", datafield: "send_num", width: 9.090909 + "%", minwidth: 91.7333},
		                            { text: "수신번호", datafield: "receive_num", width: 9.090909 + "%", minwidth: 91.7333},
		                            { text: "통화일자", datafield: "call_date", width: 9.090909 + "%", minwidth: 91.7333},
		                            { text: "통화시각", datafield: "call_hour", width: 13.181818 + "%", minwidth: 132.55},
		                            { text: "통화시간", datafield: "call_time", width: 9.090909 + "%", minwidth: 91.7333},
		                            { text: "유형", datafield: "call_type", width: 9.090909 + "%", minwidth: 91.7333},
		                            { text: "<button class='btn btn-default'>받기</button>", datafield: "listen", width: 9.090909 + "%", minwidth: 91.7333}
		                        ]
		            });
				},
				error : function() {
					alert("알수없는 오류가 발생하였습니다.");
				},
				complete : function() {
				}
        	});
            $(".hide").removeClass("hide");
            $(".first_page").css({"display" : "none"});
        });




       
        
        
        
        
        
        $("#listBoxgridpagerlistgrid").on("click", "span", function(event){
            console.log(event.target);
             var rows = event.target.innerText;
             console.log(rows);
             if(rows>14){
                $("#grid").jqxGrid({
                    height: 100 + "%",
                    autoheight: false
                });
                
             }else{
                $("#grid").jqxGrid({
                    height:  + "%",
                    autoheight: true
                });
             }
        });
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        function usersSearch(){
        	$.ajax({
        		type : "POST",
        		url : "/admin/usersSearch",
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
		                    row["call_type"] = users[i].rec_type;
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
        	branch_cd = $(".group_btn_act").val();
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
        
        
        
        
});