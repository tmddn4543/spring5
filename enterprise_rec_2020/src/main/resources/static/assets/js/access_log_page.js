$(document).ready(
    function(){

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

        /* 접속로그 그리드 */
        var data = new Array();

        // generate sample data.
        var generatedata = function (startindex, endindex) {
            var data = {};
            for (var i = startindex; i < endindex; i++) {
                var row = {};
                row["num"] = i;
                row["date_time"] = ["2020-03-03 11:12:50"];
                row["userid"] = ["a123"];
                row["active_code"] = ["DOWNLOADWAV"];
                row["file_info"] = ["20200318145700_20210318_15210_01025799329_07077339306_1_00001.mxx"];
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
        $("#access_grid").jqxGrid(
        {
            pagesize: 20,
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
                { text: "순번", datafield: "num", width: 5 + '%', minwidth: 52.55},
                { text: "일시", datafield: "date_time", width: 20 + "%", minwidth: 210.2},
                { text: "사용자 ID", datafield: "userid", width: 12.5 + "%", minwidth: 131.375},
                { text: "활동코드", datafield: "active_code", width: 12.5 + "%", minwidth: 131.375},
                { text: "녹취파일", datafield: "file_info", width: 50 + "%", minwidth: 525.5}
            ]
        });

        $(window).resize(function(){
        var height = parseInt($(this).height()); //parseint는 정수로 하기 위함

            if (height < 601){
                $("#access_grid").jqxGrid({
                    height: 400,
                    autoheight: true
                });
            } else { 
                $("#access_grid").jqxGrid({
                    height: 100 + '%',
                    autoheight: false
                });
            }
        }).resize();
       
});