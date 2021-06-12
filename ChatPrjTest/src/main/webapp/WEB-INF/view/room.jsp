<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="/js/room.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link href="/css/style.css" type="text/css" rel="stylesheet" />
<title>Chating Room</title>
</head>
<body>
	<div class="container">
		<h1>채팅방-수정테스트</h1>
		<div id="roomContainer" class="roomContainer">
			<table id="roomList" class="roomList">
			</table>
		</div>
		
		<div>
			<table class="inputTable">
				<tr>
					<th>방제목</th>
					<th><input type="text" name="roomName" id="roomName"></th>
					<th><button id="createRoom">방 만들기</button></th>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>