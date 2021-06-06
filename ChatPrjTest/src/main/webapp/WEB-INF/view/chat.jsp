<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="/js/chat.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link href="/css/style.css" type="text/css" rel="stylesheet" />
<title>Chating</title>
</head>
<body>
	<div id="container" class="container">
		<h1>채팅</h1>
		<input type="hidden" id="sessionId" value="">
		
		<div id="chating" class="chating">
		</div>
		
		<div id="yourName">
			<table>
				<tr>
					<th>사용자명</th>
					<th><input type="text" name="userName" id="userName"></th>
					<th><button onclick="chatName()" id="startBtn">이름 등록</button></th>
					<th><button onclick="location.href='/room';">채팅방</button></th>
				</tr>
			</table>
		</div>
		
		<div id="yourMessage">
			<table>
				<tr>
					<th>메시지</th>
					<th><input type="text" id="chatting" placeholder="보내실 메시지를 입력하세요."></th>
					<th><button onclick="send()" id="sendBtn">보내기</button></th>
					<th><button onclick="location.href='/room';">채팅방</button></th>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>