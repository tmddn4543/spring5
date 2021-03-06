$(document).ready(function(){
	
	
  	var login_id = $("#login_id").val();
	
	
	var emp_id = "";
	var emp_nm = "";
	var tel_no = "";
	var tel_no_070 ="";
	var pass = "";
	var pass_check = "";
	var auth_cd = "";
    var branch_cd = "";	
    var rec_type = "";
    var down_type = "";
    var arr_batch = new Array();
    var arr_batch1 = new Array();
    var branch_nm = "";
    var results = "";
    var u_auth_cd = $("#u_auth_cd").val();
    //검색시 검색그룹 가져오기
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
			$(".group_btn_act_search").jqxDropDownList({ source: arr_batch, selectedIndex: 0, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
		},
		error : function() {
			alert("알수없는 오류가 발생하였습니다. \n로그인을 다시 해주시기 바랍니다.");
			location.href="/";
		}
	});
    
    
    /* 반응형 */
    $(window).resize(function(){
    var width = parseInt($(this).width()); //parseint는 정수로 하기 위함
        if (width > 1464 || width < 451){
            $('body').removeClass('mini-navbar');
        } else { 
            $('body').addClass('mini-navbar');
        }
    }).resize();
    
    
    
    main(null,null,null,null);
    
    function main(branch_cd, tel_no,rec_type,auth_cd){
    	var source =
        {
             datatype: "json",
             data : {
            	 branch_cd :branch_cd,
            	 tel_no :tel_no,
            	 rec_type :rec_type,
            	 auth_cd :auth_cd
             },
             datafields: [
    			 { name: 'num'},
    			 { name: 'branch_cd'},
    			 { name: 'auth_cd'},
    			 { name: 'emp_id'},
    			 { name: 'tel_no'},
    			 { name: 'rec_regdate'},
    			 { name: 'rec_type_regdate'},
    			 { name: 'rec_type'},
    			 { name: 'work_emp_id'},
    			 { name: 'user_detail'},
    			 { name: 'user_checkbox'}
            ],
    	    url: '/user/user_page_ajax',
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
	    	localizationobject.emptydatastring = "유저 검색 결과가 없습니다.";
	    	$("#user_grid").jqxGrid('localizestrings', localizationobject);
	    	return localizationobject;
	    }
        
        if(u_auth_cd=="00" || u_auth_cd=="12" || u_auth_cd=="13"){
        	$("#user_grid").jqxGrid(
                    {
                    	source: dataadapter,
                        width: 100 + "%",
                        autoheight: true, 
                        theme: 'material',
                        virtualmode: true,
                        localization: getLocalization(),
                        pageable: true,
                        rendergridrows: function(obj)
        				{
        					  return obj.data;     
        				},
                        columns:  [
                           { text: "선택", datafield: "user_checkbox", width: 5 + "%", minwidth: 50.9},
                            { text: "순번", datafield: "num", width: 5 + "%", minwidth: 50.9},
                            { text: "그룹", datafield: "branch_cd", width: 10 + "%", minwidth: 101.8},
                            { text: "권한등급", datafield: "auth_cd", width: 10 + "%", minwidth: 101.8},
                            { text: "사용자ID", datafield: "emp_id", width: 10 + "%", minwidth: 101.8},
                            { text: "전화번호", datafield: "tel_no", width: 10 + "%", minwidth: 101.8},
                            { text: "녹취 등록일", datafield: "rec_regdate", width: 10 + "%", minwidth: 101.8},
                            { text: "녹취 변경일", datafield: "rec_type_regdate", width: 10 + "%", minwidth: 101.8},
                            { text: "녹취 유형", datafield: "rec_type", width: 10 + "%", minwidth: 101.8},
                            { text: "작업자", datafield: "work_emp_id", width: 10 + "%", minwidth: 101.8},
                            { text: "상세보기", datafield: "user_detail", width: 10 + "%", minwidth: 101.8}
                        ]
                });
        }else{
        	$("#user_grid").jqxGrid(
                    {
                    	source: dataadapter,
                        width: 100 + "%",
                        autoheight: true, 
                        theme: 'material',
                        virtualmode: true,
                        localization: getLocalization(),
                        pageable: true,
                        rendergridrows: function(obj)
        				{
        					  return obj.data;     
        				},
                        columns:  [
                            { text: "순번", datafield: "num", width: 5 + "%", minwidth: 50.9},
                            { text: "그룹", datafield: "branch_cd", width: 11.875  + "%", minwidth: 120.8875 },
                            { text: "권한등급", datafield: "auth_cd", width: 11.875  + "%", minwidth: 120.8875 },
                            { text: "사용자ID", datafield: "emp_id", width: 11.875  + "%", minwidth: 120.8875 },
                            { text: "전화번호", datafield: "tel_no", width: 11.875  + "%", minwidth: 120.8875 },
                            { text: "녹취 등록일", datafield: "rec_regdate", width: 11.875  + "%", minwidth: 120.8875 },
                            { text: "녹취 변경일", datafield: "rec_type_regdate", width: 11.875  + "%", minwidth: 120.8875 },
                            { text: "녹취 유형", datafield: "rec_type", width: 11.875  + "%", minwidth: 120.8875 },
                            { text: "작업자", datafield: "work_emp_id", width: 11.875  + "%", minwidth: 120.8875 }
                        ]

                });
        }
        
    };
    
    
	
	$("#user_search_bt").click(function(){
		branch_cd = $(".group_btn_act_search").val();
		tel_no = $("#tel_no_id").val();
		//$("input[name=inlineRadioOptions-add]:checked").val();
		//
		rec_type = $("input[name=inlineRadioOptions-add]:checked").val();
		auth_cd = $(".authority_num1").val();
		if(branch_cd=="전체"){
			branch_cd = "";
		}
		$('#window').jqxWindow('close');
		main(branch_cd, tel_no,rec_type,auth_cd);
	});
	
	
	
	
    //$(".group_btn_act_search").jqxDropDownList({ source: , selectedIndex: 1, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
    $(".authority_num1").jqxDropDownList({ source: [ "전체","시스템관리자","운용사용자","그룹관리자","상담원"], selectedIndex: 0, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
	$('#user_add').on('shown.bs.modal', function (event) {
		var res = $(event.relatedTarget);
		
		if(res.context.value=="user_view"){
			$('#myModalLabel1').html("사용자 상세보기");
			//res.context.name
			$("#modal_ajax").html("수정");
			tel_no = res.context.name;
			//사용자 상세보기가져오기
			$("#pass").val("");
			$("#pass_check").val("");
			$.ajax({
				type : "POST",
				data : {tel_no : tel_no},
				url : "/user/user_edit_get",
				success : function(result) {
					$("#emp_id").val(result.user_result.emp_id);
					$("#emp_nm").val(result.user_result.emp_nm);
					$("#tel_no").val(result.user_result.tel_no);
					$("#tel_no_070").val(result.user_result.tel_no_070);
					
					var sum = "";
					for(var i=0; i<result.batch.length; i++){
						arr_batch1[i] = result.batch[i].branch_cd;
						if(result.user_result.branch_cd==result.batch[i].branch_cd){
							sum = i;
						}
					}
					$(".authority_num").jqxDropDownList({disabled: true,  source: [ "전체","시스템관리자","운용사용자","그룹관리자","상담원"], selectedIndex: 1, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
					
					

					
					if(result.user_result.rec_type=="B"){
						$("#rec_inlineRadio1").prop("checked","checked");
					}else if(result.user_result.rec_type=="N"){
						$("#rec_inlineRadio2").prop("checked","checked");
					}
					
					if(result.user_result.down_type=="N"){
						$("#down_inlineRadio1").prop("checked","checked");
					}else if(result.user_result.down_type=="Y"){
						$("#down_inlineRadio2").prop("checked","checked");
					}
					
					if(result.user_result.auth_cd=="00"){//시스템
						$(".authority_num").jqxDropDownList({selectedIndex: 1});
					}else if(result.user_result.auth_cd=="12"){//그룹
						$(".authority_num").jqxDropDownList({selectedIndex: 3});
					}else if(result.user_result.auth_cd=="13"){//상담
						$(".authority_num").jqxDropDownList({selectedIndex: 4});
					}else if(result.user_result.auth_cd=="11"){//운용
						$(".authority_num").jqxDropDownList({selectedIndex: 2});
					}
					
					$("#emp_id").prop("readonly",true);
					$("#tel_no").prop("readonly",true);
					$("#tel_no_070").prop("readonly",true);
					
					
					$(".group_btn_act").jqxDropDownList({source: arr_batch1, selectedIndex: sum, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
				}
			});
			
			
			
			
		}else if(res.context.value=="user_insert"){
			
			//branch_cd 그룹들 가져오기
		    $.ajax({
				type : "POST",
				url : "/user/user_branch_get",
				success : function(batch) {
					for(var i=0; i<batch.length; i++){
						arr_batch1[i] = batch[i].branch_cd;
						
					}
					$(".group_btn_act").jqxDropDownList({source: arr_batch1, selectedIndex: 0, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
				},
				error : function() {
					alert("알수없는 오류가 발생하였습니다. \n로그인을 다시 해주시기 바랍니다.");
					location.href="/";
				}
			});
			
		    $(".authority_num").jqxDropDownList({disabled: false,  source: [ "전체","시스템관리자","운용사용자","그룹관리자","상담원"], selectedIndex: 1, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
			
		    
		    
		    $("#down_inlineRadio1").prop("checked",true);
			$('#myModalLabel1').html("사용자 등록");
			$("#modal_ajax").html("등록");
			emp_id = $("#emp_id").val("");
			$("#emp_id").prop("readonly",false);
			$("#tel_no").prop("readonly",false);
			$("#tel_no_070").prop("readonly",false);
			emp_nm = $("#emp_nm").val("");
			tel_no = $("#tel_no").val("");
			tel_no_070 = $("#tel_no_070").val("");
			pass = $("#pass").val("");
			pass_check = $("#pass_check").val("");
			$("#rec_inlineRadio2").prop("checked","checked");
		}
	});
	
	$("#modal_ajax").click(function (){
		
		
		if($('#myModalLabel1').html()=="사용자 등록"){
			
			emp_id = $("#emp_id").val();
			emp_nm = $("#emp_nm").val();
			tel_no = $("#tel_no").val();
			tel_no_070 = $("#tel_no_070").val();
			pass = $("#pass").val();
			pass_check = $("#pass_check").val();
			
			auth_cd = authFormat2($(".authority_num").val());
			branch_cd = $(".group_btn_act").val();
			rec_type = $("input[name=inlineRadioOptions]:checked").val();
			down_type = $("input[name=download]:checked").val();
		
			if(auth_cd!="13" && rec_type!="N"){
				alert("상담원 등급만 녹취가 가능합니다. 이외의 등급은 녹취가 되지 않습니다.");
				return false;
			}
			
			if(emp_id==null || emp_id==""){
				alert("사용자 아이디를 입력해주세요.");
				$("#emp_id").focus();
				return false;
			}else if(emp_nm==null || emp_nm==""){
				alert("사용자 이름을 입력해주세요.");
				$("#emp_nm").focus();
				return false;
			}else if(tel_no==null || tel_no==""){
				alert("녹취번호를 입력해주세요.");
				$("#tel_no").focus();
				return false;
			}else if(tel_no_070==null || tel_no_070==""){
				alert("표시 번호를 입력해주세요.");
				$("#tel_no_070").focus();
				return false;
			}else if(pass==null || pass==""){
				alert("비밀번호를 입력해주세요.");
				$("#pass").focus();
				return false;
			}else if(pass_check==null || pass_check==""){
				alert("비밀번호 확인을 입력해주세요.");
				$("#pass_check").focus();
				return false;
			}else if(pass!=pass_check){
				alert("비밀번호가 다릅니다.");
				$("#pass").focus();
				return false;
			}else if(auth_cd=="전체"){
				alert("전체는 사용할 수 없습니다.");
				return false;

			}
			
			
			
				
			
			
			
			
			
			
			
			
			
			
			$.ajax({
				type : "POST",
				url : "/user/user_Insert",
				data : {
					emp_id :emp_id,
					emp_nm :emp_nm,
					tel_no :tel_no,
					tel_no_070 :tel_no_070,
					pass :pass,
					auth_cd :auth_cd,
					branch_cd :branch_cd,
					rec_type :rec_type,
					down_type :down_type
				},
				success : function(result) {
					if(result=="true"){
						alert("사용자 등록이 완료되었습니다.");
						$("#user_add").modal("hide");
						main();
					}else if(result=="return 4"){
						alert("허용 가능한 녹취 라이센스 에 의해 수정 및 등록이 취소 되었거나 \n 허용 가능한 알람서비스 등록 갯수에의해 등록 취소 되었습니다.\n사용자를 미리 등록하시기 위해서는 녹취사용안함으로 미리 등록 후\n라이센스 추가 문의는 콜센터 1877-9907로 문의해주세요.");
						return false;
					}else if(result=="return 1"){
						alert("아이디가 중복 되었습니다.");
						return false;
					}else if(result=="return 2"){
						alert("녹취번호가 중복 되었습니다.");
						return false;
					}else if(result=="return 3"){
						alert("표시번호가 중복 되었습니다.")
						return false;
					}
				},
				error : function() {
					alert("알수없는 오류가 발생하였습니다. \n로그인을 다시 해주시기 바랍니다.");
					location.href="/";
				},
				complete : function() {
				}
			});
			
			
		}else if($('#myModalLabel1').html()=="사용자 상세보기"){
			emp_id = $("#emp_id").val();
			emp_nm = $("#emp_nm").val();
			tel_no = $("#tel_no").val();
			tel_no_070 = $("#tel_no_070").val();
			pass = $("#pass").val();
			pass_check = $("#pass_check").val();
			
			auth_cd = authFormat2($(".authority_num").val());
			branch_cd = $(".group_btn_act").val();
			rec_type = $("input[name=inlineRadioOptions]:checked").val();
			down_type = $("input[name=download]:checked").val();
		
		
			
			
			if(auth_cd!="13" && rec_type!="N"){
				alert("상담원 등급만 녹취가 가능합니다. 이외의 등급은 녹취가 되지 않습니다.");
				return false;
			}
			
			
			
			if(emp_id==null || emp_id==""){
				alert("사용자 아이디를 입력해주세요.");
				$("#emp_id").focus();
				return false;
			}else if(emp_nm==null || emp_nm==""){
				alert("사용자 이름을 입력해주세요.");
				$("#emp_nm").focus();
				return false;
			}else if(tel_no==null || tel_no==""){
				alert("녹취번호를 입력해주세요.");
				$("#tel_no").focus();
				return false;
			}else if(tel_no_070==null || tel_no_070==""){
				alert("표시 번호를 입력해주세요.");
				$("#tel_no_070").focus();
				return false;
			}else if(auth_cd=="전체"){
				alert("전체는 사용할 수 없습니다.");
				return false;
			}else if(pass!=null || pass!="" || pass_check!=null || pass_check!=""){
				if(pass!=pass_check){
					alert("비밀번호가 다릅니다.");
					$("#pass").focus();
					return false;
				}
			}
			
			
			$.ajax({
				type : "POST",
				url : "/user/user_update",
				data : {
					emp_id :emp_id,
					emp_nm :emp_nm,
					tel_no :tel_no,
					tel_no_070 :tel_no_070,
					pass :pass,
					auth_cd :auth_cd,
					branch_cd :branch_cd,
					rec_type :rec_type,
					down_type :down_type
				},
				success : function(result) {
					if(result=="true"){
						alert("수정되었습니다.");
						$("#user_add").modal("hide");
						main();
					}else{
						alert("허용 가능한 녹취 라이센스 에 의해 수정 및 등록이 취소 되었거나 \n 허용 가능한 알람서비스 등록 갯수에의해 등록 취소 되었습니다.\n사용자를 미리 등록하시기 위해서는 녹취사용안함으로 미리 등록 후\n라이센스 추가 문의는 콜센터 1877-9907로 문의해주세요. ");
						return false;
					}
					
				},
				error : function() {
					alert("알수없는 오류가 발생하였습니다. \n로그인을 다시 해주시기 바랍니다.");
					location.href="/";
				},
				complete : function() {
				}
			});
		}
	});
	
	
//	$("input:radio[name=inlineRadioOptions]").click(function(){
//		if($(this).val()=="B"){
//			$("input:radio[name='download']").prop("disabled",false);
//		}else if($(this).val()=="N"){
//			$("#down_inlineRadio1").prop("checked",true);
//			$("input:radio[name='download']").prop("disabled",true);
//		}
//	});
	
	$("#user_delete").click(function(){
		var count = 0;
    	var arr = new Array();
    	var admin_check = "false";
    	var login_check = "false";
    	$(".user_checkbox").each(function(){  // .each()는 forEach를 뜻한다.
			if($(this).is(":checked")){
				var res = $(this).val();
				arr[count] = res;
				count = count+1;
				if(res=="0000000000"){
					admin_check = "true";
				}
				if(res==login_id){
					login_check = "true";
				}
			}  
    	});
    	if(admin_check=="true"){
    		alert("관리자는 삭제하실수 없습니다.");
    		return false;
    	}
    	if(login_check=="true"){
    		alert("자기 자신은 삭제하실수 없습니다.");
    		return false;
    	}
    	var check = confirm("정말 삭제 하시겠습니까?");
    	if(check){
    		$.ajax({
    			type : "POST",
    			url : "/user/user_delete",
    			data : {arr : arr},
    			success : function() {
    				alert("유저가 삭제가 되었습니다.");
    				main();
    			},
    			error : function() {
					alert("알수없는 오류가 발생하였습니다. \n로그인을 다시 해주시기 바랍니다.");
					location.href="/";
				}
    		});
    	}
	});
	
	
	

	$(".groupSet_open").click(
	    function(){
	    	$("#branch_cd").prop("readonly",false);
	    	$("#branch_nm").val("");
	    	$("#branch_cd").val("");
	    	
	    	$.ajax({
				type : "POST",
				url : "/user/user_branch_get",
				success : function(batch) {
					
					var data = new Array();
					  
				      // generate sample data.
				      var generatedata = function (startindex, endindex) {
				          var data = {};
				          for (var i = startindex; i < batch.length; i++) {
				              var row = {};
				              row["num"] = i;
				              row["groupdelete"] = "<input type='checkbox' class='checkbox_name' value='"+batch[i].branch_cd+"'>"
				              row["groupcode"] = batch[i].branch_cd;
				              row["groupname"] = batch[i].branch_nm;
				              data[i] = row;
				          }
				          return data;
				      }
				      var source =
				      {
				          datatype: "array",
				          localdata: {},
				          totalrecords: batch.length
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
				      $("#groupSet_grid").jqxGrid(
				      {
				          width: 100 + "%",
				          height: 100 + "%",
				          autoheight: false, 
				          theme: 'material',
				          columnsresize: true,
				          pagermode: 'simple',
				          source: dataAdapter,                
				          virtualmode: true,
				          pageable: true,
				          rendergridrows: rendergridrows,
				          columns: [
				        	  { text: "그룹 삭제", datafield: "groupdelete", width: 80},
				              { text: "그룹 코드", datafield: "groupcode", width: 105},
				              { text: "그룹 명", datafield: "groupname", width: 104.5}
				             
				          ]
				      });
					
				},
				error : function() {
					alert("알수없는 오류가 발생하였습니다. \n로그인을 다시 해주시기 바랍니다.");
					location.href="/";
				},
				complete : function() {
					
				}
			});
	    	
	        setTimeout(function(){
	            if($("#groupSet_modal").hasClass("in")===true){
	                $(".modal-backdrop").addClass("bg-z");
	            }else{
	                $(".modal-backdrop").removeClass("bg-z");
	            }
	        }, 300);
	});
	
	//show 모달이랑 hide
	$("#groupSet_modal").click(
	    function(){
	        setTimeout(function(){
	            if($("#groupSet_modal").hasClass("in")===true){
	                $(".modal-backdrop").addClass("bg-z");
	            }else{
	                $(".modal-backdrop").removeClass("bg-z");
	            }
	        }, 300);
	});
	
	$("#branch_insert").click(function(){
		branch_nm = $("#branch_nm").val();
		if($("#branch_cd").prop("readonly")==false){
			alert("중복체크를 해주세요.");
			return false;
		}else if($("#branch_nm").val()==""){
			alert("그룹 이름을 적어주세요.");
			return false;
		}else{
			$.ajax({
				type : "POST",
				url : "/user/user_branch_insert",
				data : {branch_cd : branch_cd,
					branch_nm : branch_nm
				},
				success : function() {
					alert("그룹이 등록 되었습니다.");
					$("#groupSet_modal").modal("hide");
				},
				error : function() {
					alert("알수없는 오류가 발생하였습니다. \n로그인을 다시 해주시기 바랍니다.");
					location.href="/";
				}
			});
			//branch_cd
			$(".group_btn_act").jqxDropDownList('addItem',branch_cd);
			$(".group_btn_act_search").jqxDropDownList('addItem',branch_cd);
		}
	});
	
	$("#branch_check").click(function(){
		branch_cd = $("#branch_cd").val();
		$.ajax({
			type : "POST",
			url : "/user/user_branch_get",
			data : {branch_cd : branch_cd},
			success : function(result) {
				if(result.length==0){
					alert("사용가능한 그룹 코드 입니다.");
					$("#branch_cd").prop("readonly",true);
				}else{
					alert("이미 사용중인 그룹 코드 입니다.");
					return false;
				}
			},
			error : function() {
				alert("알수없는 오류가 발생하였습니다. \n로그인을 다시 해주시기 바랍니다.");
				location.href="/";
			}
		});
		
	});
	
	$(".next").click(
	    function(){
	        $('.show_group').addClass('disno');
	        $('.add_group').removeClass('disno');
	    }
	);
	$(".previous").click(
	    function(){
	    	$("#branch_cd").val("");
	    	$("#branch_nm").val("");
	    	$("#branch_cd").prop("readonly",false);
	        $('.show_group').removeClass('disno');
	        $('.add_group').addClass('disno');
	    }
	);
	$("#branch_delete").click(function(){
		var count = 0;
    	var arr = new Array();
    	$(".checkbox_name").each(function(){  // .each()는 forEach를 뜻한다.
			if($(this).is(":checked")){
				var res = $(this).val();
				arr[count] = res;
				count = count+1;
			}  
    	});
    	
    	var check = confirm("정말 삭제 하시겠습니까?");
    	if(check){
    		$.ajax({
    			type : "POST",
    			url : "/user/user_branch_delete",
    			data : {arr : arr},
    			success : function(result) {
    				if(result=="true"){
    					for(var i=0; i<arr.length; i++){
    						$(".group_btn_act").jqxDropDownList('removeItem',arr[i]);
    						$(".group_btn_act_search").jqxDropDownList('removeItem',arr[i]);
    					}
    					$("#groupSet_modal").modal("hide");
    					alert("그룹 삭제가 되었습니다.");
    					main();
    				}else{
    					alert("사용자가 포함되어 있는 그룹이 있습니다. \n사용자 수정 or 제거후 삭제해주시기 바랍니다.");
    				}
    				
    			},
    			error : function() {
					alert("알수없는 오류가 발생하였습니다. \n로그인을 다시 해주시기 바랍니다.");
					location.href="/";
				}
    		});
    	}
	
	});
	
	
	
	
	
});