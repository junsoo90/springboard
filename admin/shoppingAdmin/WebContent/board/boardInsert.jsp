<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert.jsp</title>
</head>

<script type="text/javascript">
	function validCheck() {
		var str = document.getElementById("title");
		var blank = /[\s]/g;

		if (blank.test(str.value) == true) {
			alert("제목에 공백은 사용할 수 없습니다");
			return false;
		}
		if (str.value == "") {
			alert("제목은 필수 입력사항입니다");
			return false;
		}

		var str = document.getElementById("name");
		var blank = /[\s]/g;
		if (blank.test(str.value) == true) {
			alert("이름에 공백은 사용할 수 없습니다 ");
			return false;
		}
		if (str.value == "") {
			alert("이름은 필수 입력사항입니다");
			return false;
		}

		var str = document.getElementById("pass");
		var blank = /[\s]/g;
		if (blank.test(str.value) == true) {
			alert("비밀번호에 공백은 사용할 수 없습니다 ");
			return false;
		}
		if (str.value == "") {
			alert("비밀번호는 필수 입력사항입니다");
			return false;
		}

		var str = document.getElementById("content");
		var blank = /[\s]/g;
		if (blank.test(str.value) == true) {
			alert("내용에 공백은 사용할 수 없습니다 ");
			return false;
		}
		if (str.value == "") {
			alert("내용은 필수 입력사항입니다");
			return false;
		}

		return checkfile();
	}

	function checkfile() {
		var filemaxsize = 10 * 1024 * 1024;
		var size = document.getElementById("uploadFile").files[0].size;
	
		if( filemaxsize < size){
			alert("파일은 최대 10Byte 까지만 올리실수 있습니다");
			document.getElementById("uploadFile").value="";
			return false;
		}
		var content = document.getElementById('content');
		return fnChkByte4(content);
	}

	function fnChkByte1(obj) {
		var maxByte = 100;
		var str = obj.value;
		var str_len = str.length;

		var rbyte = 0;
		var rlen = 0;
		var one_char = "";
		var str2 = "";

		for (var i = 0; i < str_len; i++) {
			one_char = str.charAt(i);

			if (escape(one_char).length > 4) {
				rbyte += 3; //한글2Byte
			} else {
				rbyte++; //영문 등 나머지 1Byte
			}

			if (rbyte <= maxByte) {
				rlen = i + 1; //return할 문자열 갯수
			}
		}
		if (rbyte > maxByte) {
			alert("한글 " + (maxByte / 2) + "자 / 영문 " + maxByte
					+ "자를 초과 입력할 수 없습니다.");
			str2 = str.substr(0, rlen); //문자열 자르기
			obj.value = str2;
			fnChkByte1(obj, maxByte);
		} else {
			document.getElementById('byteInfo1').innerText = rbyte + "/"
					+ maxByte + "byte";
		}
	}

	function fnChkByte2(obj) {
		var maxByte = 50;
		var str = obj.value;
		var str_len = str.length;

		var rbyte = 0;
		var rlen = 0;
		var one_char = "";
		var str2 = "";

		for (var i = 0; i < str_len; i++) {
			one_char = str.charAt(i);

			if (escape(one_char).length > 4) {
				rbyte += 3; //한글2Byte
			} else {
				rbyte++; //영문 등 나머지 1Byte
			}

			if (rbyte <= maxByte) {
				rlen = i + 1; //return할 문자열 갯수
			}
		}
		if (rbyte > maxByte) {
			alert("한글 " + (maxByte / 2) + "자 / 영문 " + maxByte
					+ "자를 초과 입력할 수 없습니다.");
			str2 = str.substr(0, rlen); //문자열 자르기
			obj.value = str2;
			fnChkByte2(obj, maxByte);
		} else {
			document.getElementById('byteInfo2').innerText = rbyte + "/"
					+ maxByte + "byte";
		}
	}

	function fnChkByte3(obj) {
		var maxByte = 10;
		var str = obj.value;
		var str_len = str.length;

		var rbyte = 0;
		var rlen = 0;
		var one_char = "";
		var str2 = "";

		for (var i = 0; i < str_len; i++) {
			one_char = str.charAt(i);

			if (escape(one_char).length > 4) {
				rbyte += 3; //한글2Byte
			} else {
				rbyte++; //영문 등 나머지 1Byte
			}

			if (rbyte <= maxByte) {
				rlen = i + 1; //return할 문자열 갯수
			}
		}
		if (rbyte > maxByte) {
			alert("한글 " + (maxByte / 2) + "자 / 영문 " + maxByte
					+ "자를 초과 입력할 수 없습니다.");
			str2 = str.substr(0, rlen); //문자열 자르기
			obj.value = str2;
			fnChkByte3(obj, maxByte);
		} else {
			document.getElementById('byteInfo3').innerText = rbyte + "/"
					+ maxByte + "byte";
		}
	}
	function fnChkByte4(obj) {
		var maxByte = 3000;
		var str = obj.value;
		var str_len = str.length;

		var rbyte = 0;
		var rlen = 0;
		var one_char = "";
		var str2 = "";
		alert(content.value);
		for (var i = 0; i < str_len; i++) {
			one_char = str.charAt(i);

			if (escape(one_char).length > 4) {
				rbyte += 3; //한글2Byte
			} else {
				rbyte++; //영문 등 나머지 1Byte
			}

			if (rbyte <= maxByte) {
				rlen = i + 1; //return할 문자열 갯수
			}
		}
		
		if (rbyte > maxByte) {
			alert("한글 " + (maxByte / 2) + "자 / 영문 " + maxByte
					+ "자를 초과 입력할 수 없습니다.");
			str2 = str.substr(0, rlen); //문자열 자르기
			obj.value = str2;
			return false;
		} else {
			document.getElementById('byteInfo3').innerText = rbyte + "/"
					+ maxByte + "byte";
		}
	}

	function passCheck() {
		var pass = document.getElementById("pass").value;
		if (pass == "${vo.pass}") {
			return true;
		} else {
			alert("비밀번호가 일치하지 않습니다");
			return false;
		}
	}
	function hideName() {
		document.getElementById("deleteCheck").value = "y";
		document.getElementById("delete").innerText = "";
	}
</script>
<body>
<h1>글쓰기</h1>
	<form action="insert.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="seq" value="${param.seq }">
		<input type="hidden" name="ref" value="${param.ref }">
		<input type="hidden" name="lev" value="${param.lev }">
		<input type="hidden" name="step" value="${param.step }">
		<label>제목</label><input type="text" name="title" id="title" onkeyup="fnChkByte1(this)" ><span id="byteInfo1"></span><br>
		<label>이름</label><input type="text" name="name" id="name" onkeyup="fnChkByte2(this)"><span id="byteInfo2"></span><br>
		<label>비밀번호</label><input type="text" name="pass" id="pass" onkeyup="fnChkByte3(this)"><span id="byteInfo3"></span><br>
		<label>내용</label><textarea rows="5" cols="cols" name="content" id="content"></textarea><br>
		<label>파일첨부(10MB이하)</label><input type="file" name="uploadFile" id="uploadFile"><br>
		<input type="submit" value="등 록" onclick="return validCheck()">
		<input type="button" value="취 소" onclick="location.href='${pageContext.request.contextPath}/board/list.do'">
	</form>

</body>
</html>