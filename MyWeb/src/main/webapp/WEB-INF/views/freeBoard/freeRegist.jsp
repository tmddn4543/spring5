<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>인덱스를 만들어 보자</title>

    <link href="${pageContext.request.contextPath }/resources/css/bootstrap.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!--개인 디자인 추가-->
    <link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath }/resources/js/bootstrap.js"></script>
	
</head>
<body>
	
	<%@ include file="../include/header.jsp" %>

	<!--게시판-->
    <section>
    
       <div class="container">
            <div class="row">
                <div class="col-xs-12 content-wrap">
                    <div class="titlebox">
                        <p>자유게시판</p>
                    </div>
                    <form action="freeBoardRegistForm" method="post" name="reg_form">
                    <table class="table">
                        <tbody class="t-control">
                            <tr>
                                <td class="t-title">NAME</td>
                                <td><input class="form-control input-sm" name="writer" id="writer" value="${sessionScope.userId }" readonly="readonly"></td>
                            </tr>
                            <tr>
                                <td class="t-title">TITLE</td>
                                <td><input class="form-control input-sm" name="title" id="title"></td>
                            </tr>
                            <tr>
                                <td class="t-title">CONTENT</td>
                                <td>
                                <textarea class="form-control" rows="7" id="content" name="content"></textarea>
                                </td>                 
                            </tr>
                        </tbody>
                    </table>
                    </form>
                    <div class="titlefoot">
                        <button class="btn" onclick="reg_check()">등록</button>
                        <button class="btn" onclick="location.href='freeList'">목록</button>
                    </div>
                </div>
            </div>    
       </div>
    </section>
    <script>
    function reg_check(){
    	if(document.reg_form.writer.value == 0){
    		alert('작성자는필수');
 			return;
    	}else if(document.reg_form.content.value == 0){
    		alert('내용은필수');
    		return
    	}else if(document.reg_form.title.value == 0){
    		alert('제목은필수');
    		return
    	}else if(confirm('회원가입할거?')){
    		reg_form.submit();
    	}
    }
    
    </script>
    
    
    <%@ include file="../include/footer.jsp" %>
    
</body>
</html>