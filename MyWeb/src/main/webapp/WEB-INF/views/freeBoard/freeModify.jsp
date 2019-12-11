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
                <div class="col-xs-12 col-md-9 write-wrap">
                        <div class="titlebox">
                            <p>수정하기</p>
                        </div>
                        
                        <form method="post" action="freeBoardModifyForm" name="mdf_form">
                            <div>
                                <label>DATE</label>
                                <p>${dto.regdate }</p>
                            </div>   
                            <div class="form-group">
                                <label>번호</label>
                                <input class="form-control" name='##' readonly value="${dto.num }">
                                <input value="${dto.num }" type="hidden" name="num" id="num">
                            </div>
                            <div class="form-group">
                                <label>작성자</label>
                                <input class="form-control" name="writer" value="${dto.writer }" id="writer">
                            </div>    
                            <div class="form-group">
                                <label>제목</label>
                                <input class="form-control" name="title" value="${dto.title }" id="title">
                            </div>

                            <div class="form-group">
                                <label>내용</label>
                                <textarea class="form-control" rows="10" name="content" id="content">${dto.content }</textarea>
                            </div>

                            <button type="button" class="btn btn-dark" onclick="location.href='freeList'">목록</button>    
                            <button type="button" class="btn btn-primary" onclick="mdf_check()">변경</button>
                            <button type="button" class="btn btn-info" onclick="location.href='freeDelete?num=${dto.num}'">삭제</button>
                    </form>
                                    
                </div>
            </div>
        </div>
        </section>
        
        <script>
        function mdf_check(){
        	if(document.mdf_form.writer.value == 0){
        		alert('작성자는필수');
     			return;
        	}else if(document.mdf_form.content.value == 0){
        		alert('내용은필수');
        		return
        	}else if(document.mdf_form.title.value == 0){
        		alert('제목은필수');
        		return
        	}else if(confirm('수정할거??')){
        		mdf_form.submit();
        	}
        }
        </script>
        
        <%@ include file="../include/footer.jsp" %>
</body>
</html>