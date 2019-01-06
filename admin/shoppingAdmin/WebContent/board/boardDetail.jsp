<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script>
	function passCheck(){
		var check=prompt("비밀번호를 입력해주세요");
		console.log("${vo.pass}");
			
			if(check=="${vo.pass}"){
				
				alert("삭제 완료");
				location.href='${pageContext.request.contextPath}/board/delete.do?seq=${vo.seq}';
			}else if(check==null){
				return false;
			}
			else{
				alert("비밀번호가 일치하지 않습니다");
				return false;
			}
	}
</script>
<body>
<h1>상세 보기 </h1>
		<input type="hidden" name="seq" value="${vo.seq}"><br>
		<label>제목</label><input type="text" name="title" value="${vo.title }" readonly="readonly"><br>
		<label>이름</label><input type="text" name="name" value="${vo.name }" readonly="readonly"><br>
		<label>내용</label><textarea rows="5" cols="cols" name="content" readonly="readonly">${vo.content } </textarea><br>
		<label>파일첨부</label><a href="download.do?seq=${vo.seq }">${vo.orgfilename }</a><br>
		<input type="button" value="답글" style="font-family: Gulim; font-size: 12px;" onclick="location.href='${pageContext.request.contextPath}/board/boardInsert.jsp?seq=${vo.seq }&ref=${vo.ref}&lev=${vo.lev }&step=${vo.step }'">
		<input type="button" value="수정" style="font-family: Gulim; font-size: 12px;" onclick="location.href='update.do?seq=${vo.seq }'">
		<input type="button" value="삭제" style="font-family: Gulim; font-size: 12px;" onclick="return passCheck();">
		<input type="button" value="목록" style="font-family: Gulim; font-size: 12px;" onclick="location.href='${pageContext.request.contextPath}/board/list.do'">
</body>
</html>