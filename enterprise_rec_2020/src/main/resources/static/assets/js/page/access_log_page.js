$(document).ready(function(){
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
    
    $(".jqxcalendar_act").jqxDateTimeInput({ width: 98 + "%", height: 32,  selectionMode: "range", formatString: "yyyy/MM/dd", theme: "bootstrap" });
    $(".jqxcalendar_act").on("jqxcalendar", function (event) {
        var selection = $("#jqxWidget").jqxDateTimeInput("getRange");
        if (selection.from != null) {
            $("#selection").html("<div>From: " + selection.from.toLocaleDateString() + " <br/>To: " + selection.to.toLocaleDateString() + "</div>");
        }
    });
    
    
    main(null);
    
    function main(user_id){
    	var source =
        {
             datatype: "json",
             data : {
            	 user_id :user_id
             },
             datafields: [
    			 { name: 'num'},
    			 { name: 'user_id'},
    			 { name: 'filename'},
    			 { name: 'dirname'},
    			 { name: 'logtime'},
    			 { name: 'operation'}
            ],
    	    url: '/access_log/access_log_page_ajax',
    	    root: 'Rows',
            cache: false,
    		beforeprocessing: function(data)
    		{		
    			source.totalrecords = data.total;
    		}
        };		
        var dataadapter = new $.jqx.dataAdapter(source);

        // initialize jqxGrid
        
        $("#access_grid").jqxGrid(
                {
                	source: dataadapter,
                    width: 100 + "%",
                    autoheight: true, 
                    theme: 'material',
                    virtualmode: true,
                    pageable: true,
                    rendergridrows: function(obj)
    				{
    					  return obj.data;     
    				},
                    columns: [
                      { text: "순번", datafield: "num", width: 5 + '%', minwidth: 52.55},
                      { text: "일시", datafield: "logtime", width: 20 + "%", minwidth: 210.2},
                      { text: "사용자 ID", datafield: "user_id", width: 12.5 + "%", minwidth: 131.375},
                      { text: "활동코드", datafield: "operation", width: 12.5 + "%", minwidth: 131.375},
                      { text: "녹취파일", datafield: "filename", width: 50 + "%", minwidth: 525.5}
                    ]
            });
    }

    var user_id = "";
    
    $("#access_log_bt").click(function(){
    	user_id = $("#log_user_id").val();
    	$('#window').jqxWindow('close');
    	main(user_id);
    });

    
       
});