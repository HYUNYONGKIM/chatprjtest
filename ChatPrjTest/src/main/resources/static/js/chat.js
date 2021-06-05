//채팅방들어오면 가장 먼저 할 것 설정
window.onload = function(){
	$("#userName").focus(); // 사용자 이름 입력란 focus
}

//웹소켓 변수
var ws;

//2. 웹소켓 연결
function wsOpen(){
	ws = new WebSocket("ws://"+location.host+"/chating");
	wsEvt();
}

//1. 사용자 이름 등록
function chatName(){
	var userName = $("#userName").val();
	if(userName == null || userName.trim() ==""){
		alert("사용자 이름을 입력하세요");
		$("#userName").focus();
	}else{
		wsOpen();
		$("#yourName").hide();
		$("#yourMessage").show();
		
	}
}

//3. 연결 후 초기화, onOpen, onMessage
function wsEvt(){
	//소켓 열리면 초기화 세팅
	ws.onopen = function(data){
		console.log(data);
		$("#chatting").focus();
	}
	
	//메세지가 있으면
	ws.onmessage = function(data){
		var msg = data.data;
		if(msg != null && msg.trim() != ""){
			$("#chating").append("<p>"+msg+"</p>");
		}	
	}
	
	document.addEventListener("keypress", function(e){
		if(e.keyCode == 13){
			send();
		}
	})
}

function send(){
	var uN = $("#userName").val();
	var msg = $("#chatting").val();
	if(msg != null && msg.trim() != ""){
		ws.send(uN+":"+msg); // userName : msg 형식이니까...JSON형식으로 보내진다는 거..send(data)하면 onmessage시작
	}
	$("#chatting").val("");
	$("#chatting").focus();
}