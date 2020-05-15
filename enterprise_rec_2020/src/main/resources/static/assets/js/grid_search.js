$(document).ready(
    function(){

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
            "인증녹취"
        ];
        // Create a jqxDropDownList
        $(".record_type").jqxDropDownList({ source: source, selectedIndex: 1, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});


        var source = [
            "전체",
            "시스템관리자",
            "운용사용자",
            "그룹관리자",
            "상담원"
        ];
        // Create a jqxDropDownList
        $(".authority_num").jqxDropDownList({ source: source, selectedIndex: 1, width: 100 + "%", height: 34, autoItemsHeight: true, theme: "bootstrap", autoDropDownHeight: true});



        // create jqxcalendar.
        $(".jqxcalendar_act").jqxDateTimeInput({ width: 100 + "%", height: 32,  selectionMode: "range", formatString: "yyyy/MM/dd", theme: "bootstrap" });
        $(".jqxcalendar_act").on("jqxcalendar", function (event) {
            var selection = $("#jqxWidget").jqxDateTimeInput("getRange");
            if (selection.from != null) {
                $("#selection").html("<div>From: " + selection.from.toLocaleDateString() + " <br/>To: " + selection.to.toLocaleDateString() + "</div>");
            }
        });
         
        var date1 = new Date();
        date1.setFullYear(2020, 3, 7);
        var date2 = new Date();
        date2.setFullYear(2020, 3, 15);
        $(".jqxcalendar_act").jqxDateTimeInput("setRange", date1, date2);



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

        /* 조회클릭 */
        $(".next_action").click(function(){
            $(".hide").removeClass("hide");
            $(".first_page").css({"display" : "none"});
        });


        /* 사용자 검색 */
        var data = new Array();
        var groupNames =
        [
            "admin"
        ];
        var userId =
        [
            "a123", "b123", "c123", "d123"
        ];
        var userNames =
        [
            "기러기", "까마귀", "까치", "소쩍새", "강아지"
        ];
        var phoneNum =
        [
            "01033332222", "01111111111", "11211111111", "3333333333", "44444444444", "555555555555", "666666666", "777777777777", "8888888888", "08425116541", "99999999"
        ];
        var callType =[
            "전수녹취", "인증녹취"
        ];

        // generate sample data.
        var generatedata = function (startindex, endindex) {
            var data = {};
            for (var i = startindex; i < endindex; i++) {
                var row = {};
                row["num"] = i;
                
                row["name"] = userNames[Math.floor(Math.random() * userNames.length)];
                row["userid"] = userId[Math.floor(Math.random() * userId.length)];
                row["groupname"] = groupNames[Math.floor(Math.random() * groupNames.length)];

                row["phone_num"] = phoneNum[Math.floor(Math.random() * phoneNum.length)];
                row["call_type"] = callType[Math.floor(Math.random() * callType.length)];
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
            totalrecords: 135
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
        var groupNames =
        [
            "admin"
        ];
        var userId =
        [
            "a123", "b123", "c123", "d123"
        ];
        var userNames =
        [
            "기러기", "까마귀", "까치", "소쩍새", "강아지"
        ];
        var sendNum =
        [
            "01033332222", "01111111111", "11211111111", "3333333333", "44444444444", "555555555555", "666666666", "777777777777", "8888888888", "08425116541", "99999999"
        ];
        var receiveNum =
        [
            "01033332222", "01111111111", "11211111111", "3333333333", "44444444444", "555555555555", "666666666", "777777777777", "8888888888", "08425116541", "99999999"
        ];
        var callDate = [
            "2020-03-03", "2020-03-02", "2020-03-01", "2020-02-29", "2020-02-28"
        ];
        var callHour = [
            "12:23:38~12:25:41", "11:55:56~11:58:30", "11:54:24~11:55:32", "11:53:25~11:53:52", "11:52:01~11:53:01", "11:41:46~11:43:57", "11:39:46~11:40:55", "11:22:16~11:23:30"
        ];
        var callTime = [
            "00:01:44", "00:02:03", "00:02:34", "00:01:08", "00:00:27", "00:01:00", "00:02:11", "00:01:09", "00:01:14", "00:00:32"
        ];
        var callType = [
            "전수녹취", "인증녹취"
        ];
        var listenCell = [
            "<button type= button class= 'btn btn-default' data-toggle= 'modal' data-target='.audio_modal'>듣기</button>"
        ];

        // generate sample data.
        var generatedata = function (startindex, endindex) {
            var data = {};
            for (var i = startindex; i < endindex; i++) {
                var row = {};
                row["num"] = i;
                row["groupname"] = groupNames[Math.floor(Math.random() * groupNames.length)];
                row["userid"] = userId[Math.floor(Math.random() * userId.length)];
                row["name"] = userNames[Math.floor(Math.random() * userNames.length)];
                row["send_num"] = sendNum[Math.floor(Math.random() * sendNum.length)];
                row["receive_num"] = receiveNum[Math.floor(Math.random() * receiveNum.length)];
                row["call_date"] = callDate[Math.floor(Math.random() * callDate.length)];
                row["call_hour"] = callHour[Math.floor(Math.random() * callHour.length)];
                row["call_time"] = callTime[Math.floor(Math.random() * callTime.length)];
                row["call_type"] = callType[Math.floor(Math.random() * callType.length)];
                row["listen"] = listenCell
                data[i] = row;
            }
            return data;
        }
        var source =
        {
            datatype: "array",
            localdata: {},
            totalrecords: 100
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
                { text: "듣기", datafield: "listen", width: 9.090909 + "%", minwidth: 91.7333}
            ]
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
});