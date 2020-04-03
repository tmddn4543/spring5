$(document).ready(function(){
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
    var branch_nm = "";
    
    /* 반응형 */
    $(window).resize(function(){
    var width = parseInt($(this).width()); //parseint는 정수로 하기 위함
        if (width > 1464 || width < 451){
            $('body').removeClass('mini-navbar');
        } else { 
            $('body').addClass('mini-navbar');
        }
    }).resize();
    
    
    
    
    
    var source =
    {
         datatype: "json",
         data : {},
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
			 { name: 'user_detail'}
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
    
    $("#user_grid").jqxGrid(//킵
            {
            	source: dataadapter,
                width: 100 + "%",
                height:  + "%",
                autoheight: true, 
                theme: 'material',
                virtualmode: true,
                pageable: true,
                rendergridrows: function(obj)
				{
					  return obj.data;     
				},
                columns: [
                    { text: "순번", datafield: "num", width: 5 + "%", minwidth: 50.9},
                    { text: "그룹", datafield: "branch_cd", width: 9.090909 + "%", minwidth: 91.7333},
                    { text: "권한등급", datafield: "auth_cd", width: 9.090909 + "%", minwidth: 91.7333},
                    { text: "사용자ID", datafield: "emp_id", width: 9.090909 + "%", minwidth: 91.7333},
                    { text: "전화번호", datafield: "tel_no", width: 9.090909 + "%", minwidth: 91.7333},
                    { text: "녹취 등록일", datafield: "rec_regdate", width: 9.090909 + "%", minwidth: 91.7333},
                    { text: "녹취 변경일", datafield: "rec_type_regdate", width: 9.090909 + "%", minwidth: 91.7333},
                    { text: "녹취 유형", datafield: "rec_type", width: 9.090909 + "%", minwidth: 91.7333},
                    { text: "작업자", datafield: "work_emp_id", width: 9.090909 + "%", minwidth: 91.7333},
                    { text: "상세보기", datafield: "user_detail", width: 9.090909 + "%", minwidth: 91.7333}
                ]
        });
	
    	
	
    
	
	
	
	
	
	
	
	
	
	
    $(".authority_num").jqxDropDownList({ source: [ "전체","시스템관리자","운용사용자","그룹관리자","상담원"], selectedIndex: 1, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
	$('#user_add').on('shown.bs.modal', function (event) {
		var res = $(event.relatedTarget);
		if(res.context.value=="user_view"){
			$('#myModalLabel1').html("사용자 상세보기");
			alert("준비중ㅇㅅㅇ");
		}else if(res.context.value=="user_insert"){
			
			//branch_cd 그룹들 가져오기
		    $.ajax({
				type : "POST",
				url : "/user/user_branch_get",
				success : function(batch) {
					for(var i=0; i<batch.length; i++){
						arr_batch[i] = batch[i].branch_cd;
					}
					$(".group_btn_act").jqxDropDownList({ source: arr_batch, selectedIndex: 0, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});
				}
			});
			
			
			$('#myModalLabel1').html("사용자 등록");
			emp_id = $("#emp_id").val("");
			$("#emp_id").prop("readonly",false);
			$("#tel_no").prop("readonly",false);
			$("#tel_no_070").prop("readonly",false);
			emp_nm = $("#emp_nm").val("");
			tel_no = $("#tel_no").val("");
			tel_no_070 = $("#tel_no_070").val("");
			pass = $("#pass").val("");
			pass_check = $("#pass_check").val("");
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
		
			
			
			if(emp_id==null || emp_id==""){
				alert("사용자 아이디를 입력해주세요.");
				return false;
			}else if(emp_nm==null || emp_nm==""){
				alert("사용자 이름을 입력해주세요.");
				return false;
			}else if(tel_no==null || tel_no==""){
				alert("녹취번호를 입력해주세요.");
				return false;
			}else if(tel_no_070==null || tel_no_070==""){
				alert("070 녹취번호를 입력해주세요.");
				return false;
			}else if(pass==null || pass==""){
				alert("비밀번호를 입력해주세요.");
				return false;
			}else if(pass_check==null || pass_check==""){
				alert("비밀번호 확인을 입력해주세요.");
				return false;
			}else if(pass!=pass_check){
				alert("비밀번호가 다릅니다.");
				return false;
			}else if(auth_cd=="전체"){
				alert("전체는 사용할 수 없습니다.");
				return false;
			}else if($("#emp_id").prop("readonly")==false){
				alert("아이디 중복 확인을 해주세요");
				return false;
			}else if($("#tel_no").prop("readonly")==false){
				alert("녹취번호 중복 확인을 해주세요");
				return false;
			}else if($("#tel_no_070").prop("readonly")==false){
				alert("070녹취번호 중복 확인을 해주세요");
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
				success : function() {
					alert("가입이 완료 되었습니다.");
					$("#user_add").modal("hide");
					location.href="/user/user_page";
				},
				error : function() {
					alert("알수없는 오류가 발생하였습니다.");
					$("#user_add").modal("hide");
				},
				complete : function() {
				}
			});
			
			
		}else if($('#myModalLabel1').html()=="사용자 상세보기"){
			alert("상세보기");
		}
	});
	
	
	$("#id_check").click(function(){
		emp_id = $("#emp_id").val();
		$.ajax({
			type : "POST",
			url : "/user/user_Check",
			data : {emp_id : emp_id},
			success : function(result) {
				if(result=="true"){
					alert("사용가능한 아이디 입니다.");
					$("#emp_id").prop("readonly",true);
				}else if(result=="false"){
					alert("이미 사용중인 아이디 입니다.");
					return false;
				}
			},
			error : function() {
				alert("알수없는 오류가 발생하였습니다.");
			},
			complete : function() {
			}
		});
	});
	
	$("#tel_check").click(function(){
		tel_no = $("#tel_no").val();
		$.ajax({
			type : "POST",
			url : "/user/user_Check",
			data : {tel_no : tel_no},
			success : function(result) {
				if(result=="true"){
					alert("사용가능한 아이디 입니다.");
					$("#tel_no").prop("readonly",true);
				}else if(result=="false"){
					alert("이미 사용중인 아이디 입니다.");
					return false;
				}
			},
			error : function() {
				alert("알수없는 오류가 발생하였습니다.");
			},
			complete : function() {
			}
		});
	});
	
	$("#tel_070_check").click(function(){
		tel_no_070 = $("#tel_no_070").val();
		$.ajax({
			type : "POST",
			url : "/user/user_Check",
			data : {tel_no_070 : tel_no_070},
			success : function(result) {
				if(result=="true"){
					alert("사용가능한 아이디 입니다.");
					$("#tel_no_070").prop("readonly",true);
				}else if(result=="false"){
					alert("이미 사용중인 아이디 입니다.");
					return false;
				}
			},
			error : function() {
				alert("알수없는 오류가 발생하였습니다.");
			},
			complete : function() {
			}
		});
	});
	

	$(".groupSet_open").click(
	    function(){
	    	
	    	
	    	
	    	
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
				          selectionmode: 'checkbox',
				          rendergridrows: rendergridrows,
				          columns: [
				              { text: "그룹 코드", datafield: "groupcode", width: 129.5},
				              { text: "그룹 명", datafield: "groupname", width: 129.5},
				             
				          ]
				      });
					
				},
				error : function() {
					alert("알수없는 오류가 발생하였습니다.");
				},
				complete : function() {
				}
			});
	    	
	    	
	    	
	    	
	    	/* 그룹관리 모달창 내 그리드 */
	      
	  
	    	
	    	
	    	
	    	
	    	
	        setTimeout(function(){
	            if($("#groupSet_modal").hasClass("in")===true){
	                $(".modal-backdrop").addClass("bg-z");
	            }else{
	                $(".modal-backdrop").removeClass("bg-z");
	            }
	        }, 300);
	});
	
	 
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
				}
			});
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
        
});