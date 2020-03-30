$(document).ready(function(){
	var emp_id = "";
	var emp_nm = "";
	var tel_no = "";
	var tel_no_070 ="";
	var pass = "";
	var pass_check = "";
	
    	
	$.ajax({
		type : "POST",
		url : "/user/user_page_ajax",
		data : {},
		success : function(users) {
	
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
            "시스템관리자",
            "운용사용자",
            "그룹관리자",
            "상담원",
            "알람서비스등록"
        ];
        // Create a jqxDropDownList
        $(".authority_num").jqxDropDownList({ source: source, selectedIndex: 1, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});

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
            for (var i = startindex; i < users.length; i++) {
                var row = {};
                row["num"] = i;
                row["groupname"] = users[i].branch_cd;
                row["authority_num"] = authFormat(users[i].auth_cd);
                row["userid"] = users[i].emp_id;
                row["phone_num"] = users[i].tel_no_070;
                row["registration_data"] = users[i].rec_regdate;
                row["change_data"] = users[i].rec_type_regdate;
                row["call_type"] = recFormat(users[i].rec_type);
                row["worker_data"] = users[i].work_emp_id;
                row["showmore"] = "<button type= button class= 'btn btn-default' data-toggle= 'modal' data-target='#user_add' value='user_view'>상세보기</button>";
                data[i] = row;
            }
            return data;
        }
        var source =
        {
            datatype: "array",
            localdata: {},
            totalrecords: users.length
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
        $("#user_grid").jqxGrid(
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
                { text: "순번", datafield: "num", width: 7.8 + '%', minwidth: 52.55},
                { text: "그룹", datafield: "groupname", width: 10 + "%", minwidth: 104.1},
                { text: "권한등급", datafield: "authority_num", width: 10 + "%", minwidth: 104.1},
                { text: "사용자ID", datafield: "userid", width: 10 + "%", minwidth: 104.1},
                { text: "전화번호", datafield: "phone_num", width: 10 + "%", minwidth: 104.1},
                { text: "녹취 등록일", datafield: "registration_data", width: 10 + "%", minwidth: 104.1},
                { text: "녹취 변경일", datafield: "change_data", width: 10 + "%", minwidth: 104.1},
                { text: "녹취 유형", datafield: "call_type", width: 10 + "%", minwidth: 104.1},
                { text: "작업자", datafield: "worker_data", width: 10 + "%", minwidth: 104.1},
                { text: "상세보기", datafield: "showmore", width: 10 + "%", minwidth: 104.1}
            ]
        });
		},
		error : function() {
			alert("알수없는 오류가 발생하였습니다.");
		},
		complete : function() {
		}
	});
	
	$('#user_add').on('shown.bs.modal', function (event) {
		var res = $(event.relatedTarget);
		console.log($('#myModalLabel1').html());
		if(res.context.value=="user_view"){
			$('#myModalLabel1').html("사용자 상세보기");
			alert("준비중ㅇㅅㅇ");
		}else if(res.context.value=="user_insert"){
			$('#myModalLabel1').html("사용자 등록");
			emp_id = $("#emp_id").val("");
			emp_nm = $("#emp_nm").val("");
			tel_no = $("#tel_no").val("");
			tel_no_070 = $("#tel_no_070").val("");
			pass = $("#pass").val("");
			pass_check = $("#pass_check").val("");
		}
	});
	
	$("#modal_ajax").click(function (){
		alert("asd");
	});
	
        
});