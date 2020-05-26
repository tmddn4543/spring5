var ws;
function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	} else {
		$("#conversation").hide();
	}
	$("#greetings").html("");
}

function connect() {
	$("#connect").prop("disabled",true);
	//connect to stomp where stomp endpoint is exposed
	var socket = new WebSocket("ws://localhost:8090/ws");
	ws = Stomp.over(socket);

	ws.connect({}, function(frame) {
		ws.subscribe("/user/queue/errors", function(message) {
			alert("err");
		});

		ws.subscribe("/user/queue/reply", function(message) {
		});
		
		ws.subscribe("/topic/errors", function(message) {
			alert("err");
		});

		ws.subscribe("/topic/reply", function(message) {
			showGreeting(message);
		});
	}, function(error) {
		$("#connect").attr("disabled", false);
		alert("실시간 모니터링 연결이 끊겼습니다.");
	});
	alert("실시간 모니터링을 시작합니다.");
}

function disconnect() {
	if (ws != null) {
		ws.close();
	}
	setConnected(false);
	console.log("Disconnected");
}


function showGreeting(message) {
//	var obj = jQuery.parseJSON(message.body);
//	console.log(obj.monitor);
//	var str = "";
//	for(var i=0; i<obj.count; i++){
//		str +="<div class='thumbnail ready'>";
//		str +="<div class='caption_head'>";
//		str +="<h3>No.1</h3>";
//		str +="</div>";
//		str +="<div class='caption'>";
//		str +="<div class='caption_con1'>";
//		str +="<p id='greetings_u_id'>"+obj.u_id+(i)+"</p>";
//		str +="<p id='greetings_stime'>"+obj.stime+i+"</p>";
//		str +="</div>";
//		str +="<div class='caption_con2'>";
//		str +="<p id='greetings_caller'>"+obj.caller+i+"</p>";
//		str +="<i class='fa fa-arrow-down' aria-hidden='true'></i>";
//		str +="<p id='greetings_called'>"+obj.called+i+"</p>";
//		str +="</div>";
//		str +="</div>";
//		str +="<div class='caption_bottom'>";
//		str +="<button href='#' class='btn btn-primary' role='button' disabled='disabled'>";
//		str +="<i class='fa fa-spinner' aria-hidden='true'></i>";
//		str +="청취준비";
//		str +="</button>";
//		str +="</div>";
//		str +="</div>";
//	}
	
	$("#append_id").html(message.body);
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendName();
	});
});
