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
//		console.log("서버에서 내보낸 값 : "+data ); //[object ManageEvent]
		var msg = data.data; //그안의 데이터값
		//msg : {"sessionId":"2a8bd873-cf3c-7c9b-2d86-0b64226e3d7e","type":"getId"}
		//msg : {"msg":"안녕하지","sessionId":"6161d3c5-6bef-8bb1-032a-1d1609bac20b","type":"message","userName":"바나나"}
		if(msg != null && msg.trim() != ""){
			var d = JSON.parse(msg);
			if(d.type == "getId"){
				var si = d.sessionId != null ? d.sessionId: "";
				if(si != ""){
					$("#sessionId").val(si);
				}
			}else if(d.type == "message"){
				if(d.sessionId == $("#sessionId").val()){
					$("#chating").append("<p class='me'>나 : "+d.msg+"</p>");
				}else{
					$("#chating").append("<p class='others'>"+d.userName+" : "+d.msg+"</p>");
				}
			}
		}	
	}
	
	document.addEventListener("keypress", function(e){
		if(e.keyCode == 13){
			send();
		}
	})
}

function send(){
//	var uN = $("#userName").val();
//	var msg = $("#chatting").val();
	var option = {
		type: "message",
		sessionId: $("#sessionId").val(),
		userName: $("#userName").val(),
		msg: $("#chatting").val()
	}
	if(option.msg != null && option.msg.trim() != ""){
		ws.send(JSON.stringify(option));
		console.log("send()에서 보내는 값 : " + JSON.stringify(option));
	}
//	if(msg != null && msg.trim() != ""){
//		ws.send(uN+":"+msg); // userName : msg 형식이니까...JSON형식으로 보내진다는 거..send(data)하면 onmessage시작
//	}
	$("#chatting").val("");
	$("#chatting").focus();
}