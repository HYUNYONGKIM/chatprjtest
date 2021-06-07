window.onload = function(){
	getRoom();
	createRoom();
}

//방정보 가져오기
function getRoom(){
	commonAjax('/getRoom', "", 'POST', function(result){
		createChatingRoom(result);
	});
}

//방 만들기 버튼 
function createRoom(){
	$("#createRoom").click(function(){
		var msg = { roomName : $("#roomName").val() };
		
		commonAjax('/createRoom', msg,'POST', function(result){
			createChatingRoom(result);
		});
		
		$("#roomName").val("");
		$("#roomName").focus();
		
	});
}

//채팅방 생성
function createChatingRoom(res){
	console.log(res);
	if(res != null){
		var tag = "<tr><th classs='num'>순서</th><th class='room'>방 이름</th><th class='go'></th></tr>"
		res.forEach(function(d, idx){
			var roomName = d.roomName.trim();
			var roomNum = d.roomNum;
			tag +="<tr>"+
						"<td class='num'>"+(idx+1)+"</td>"+
						"<td class='room'>"+roomName+"</td>"+
						"<td class='go'><button type='button' onclick='goRoom(\""+roomNum+"\",\""+roomName+"\")'>참여</button></td>"+
					"</tr>";
		});
		$("#roomList").empty().append(tag);
	}
}

//ajax연결
function commonAjax(url, parameter, type, calbak, contentType){
	$.ajax({
		url: url,
		data: parameter,
		type: type,
		contentType: contentType != null ? contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		success: function(res) {
			console.log("success");
			calbak(res);
		},
		error: function(err) {
			console.log(err);
			calbak(err);
		}
	})
}

//방 이동
function goRoom(roomNum, roomName){
	location.href="/moveChating?roomName="+roomName+"&roomNum="+roomNum;
}

