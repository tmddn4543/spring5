$(document).ready(
    function(){

    	var arr_batch = new Array();
    	var emp = "";
    	var emp_id = "";
    	var branch_cd = "";
    	var auth_cd = "";
    	
    	//월별인지,시간별인지,일별인지
    	var res = "";
    	
    	var date = "";
    	
    	/* 반응형 메뉴 */
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
        
        
        main(null,null,null,null);
        
        
        function main(res,branch_cd,date,emp_id){
        	var source =
            {
                 datatype: "json",
                 data : {
                	 emp_id : emp_id,
                	 res : res,
                	 branch_cd : branch_cd,
                	 date : date
                 },
                 datafields: [
        			 { name: 'num'},
        			 { name: 's_date'},
        			 { name: 's_call_cnt_total'},
        			 { name: 's_call_time_total'},
        			 { name: 's_caller_cnt'},
        			 { name: 's_called_cnt'},
        			 { name: 's_caller_time'},
        			 { name: 's_called_time'},
        			 { name: 'emp_id'},
        			 { name: 'branch_cd'},
                ],
        	    url: '/state/state_page_ajax',
        	    root: 'Rows',
                cache: false,
        		beforeprocessing: function(data)
        		{		
        			source.totalrecords = data.total;
        		}
            };		
            var dataadapter = new $.jqx.dataAdapter(source);

            // initialize jqxGrid
            
            var getLocalization = function () {
    	    	var localizationobject = {};
    	    	localizationobject.emptydatastring = "통계 검색 결과가 없습니다.";
    	    	$("#state").jqxGrid('localizestrings', localizationobject);
    	    	return localizationobject;
    	    }
            
            $("#state").jqxGrid(
                    {
                    	source: dataadapter,
                        width: 100 + "%",
                        autoheight: false, 
                        theme: 'material',
                        virtualmode: true,
                        localization: getLocalization(),
                        pageable: true,
                        showtoolbar: true,
                        rendertoolbar: function (statusbar) {
                            // appends buttons to the status bar.
                            var container = $("<div style='overflow: hidden; position: relative; margin: 5px;'></div>");
                            var xlsButton = $("<div style='float: right; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' ><span style='margin-left: -2px; position: relative; top: -3px;'>엑셀다운</span></div>");
                            
                            container.append(xlsButton);
                            statusbar.append(container);
                            xlsButton.jqxButton({  width: 60, height: 28 });
                            // add new row.
                            xlsButton.click(function (event) {
                            	xls();
                            });
                        },
                        rendergridrows: function(obj)
        				{
        					  return obj.data;     
        				},
        				columns: [
        					{ text: "순번", datafield: "num", width: 10 + '%', minwidth: 105.099999999 },
        	                { text: "조회기간", datafield: "s_date", width: 10 + "%", minwidth: 105.099999999 },
        	                { text: "그룹", datafield: "branch_cd", width: 10 + "%", minwidth: 105.099999999 },
        	                { text: "사용자", datafield: "emp_id", width: 10 + "%", minwidth: 105.099999999 },
        	                { text: "착신", datafield: "s_called_cnt", width: 10 + "%", minwidth: 105.099999999 },
        	                { text: "발신", datafield: "s_caller_cnt", width: 10 + "%", minwidth: 105.099999999 },
        	                { text: "착발신 합계", datafield: "s_call_cnt_total", width: 10 + "%", minwidth: 105.099999999 },
        	                { text: "착신 통화시간", datafield: "s_called_time", width: 10 + "%", minwidth: 105.099999999 },
        	                { text: "발신 통화시간", datafield: "s_caller_time", width: 10 + "%", minwidth: 105.099999999 },
        	                { text: "착발신시간 합계", datafield: "s_call_time_total", width: 10 + "%", minwidth: 105.099999999 }
        	            ]
                });
        }
      //사용자검색
        $("#state_user").click(function(){
        	emp = $("#emp_id").val();
        	usersSearch();
        });
        
        $("#id_user_search3").click(function(){
        	emp = $("#emp_id_modal").val();
        	branch_cd = $(".group_btn_act1").val();
        	auth_cd = $(".authority_num").val();
        	usersSearch();
        });
        //optionsRadios
        $("#state_select").click(function(){
        	res = $('input[name="optionsRadios"]:checked').val();
        	branch_cd = $(".group_btn_act").val();
        	date = $(".jqxcalendar_act").val();
        	emp_id = $("#emp_id").val();
        	if(branch_cd=="전체"){
        		branch_cd = "";
        	}
        	$('#window').jqxWindow('close');
        	main(res,branch_cd,date,emp_id);
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
	     
        };

        var source = [
            "전체",
            "시스템관리자",
            "운용사용자",
            "그룹관리자",
            "상담원"
        ];
        // Create a jqxDropDownList
        $(".authority_num").jqxDropDownList({ source: source, selectedIndex: 0, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});



        // create jqxcalendar.
        $(".jqxcalendar_act").jqxDateTimeInput({ width: 98 + "%", height: 32,  selectionMode: "range", formatString: "yyyy:MM:dd", theme: "bootstrap" });
        $(".jqxcalendar_act").on("jqxcalendar", function (event) {
            var selection = $("#jqxWidget").jqxDateTimeInput("getRange");
            if (selection.from != null) {
                $("#selection").html("<div>From: " + selection.from.toLocaleDateString() + " <br/>To: " + selection.to.toLocaleDateString() + "</div>");
            }
        });
         
        var cal_date = new Date();
        $(".jqxcalendar_act").jqxDateTimeInput("setRange", cal_date, cal_date);

        
        
        function xls(){
        	location.href="/state/xlsxDownload?res="+res+"&branch_cd="+branch_cd+"&date="+date+"&emp_id="+emp_id;
        }

});