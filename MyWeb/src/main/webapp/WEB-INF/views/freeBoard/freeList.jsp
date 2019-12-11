<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
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
                <!--lg에서 9그리드, xs에서 전체그리드-->   
                <div class="col-lg-9 col-xs-12 board-table">
                    <div class="titlebox">
                        <p>자유게시판</p>
                    </div>
                    <hr>
                    
                    <!--form select를 가져온다 -->
            		<form action="freeList" method="get" name="searchForm" id="searchForm">
		   			<div class="search-wrap">
                    <span>총 ${dto.total }게시글</span>
                       <button type="button" class="btn btn-info search-btn" id="searchBtn">검색</button>
                       <input type="text" class="form-control search-input" name="searchName" value="${dto.cri.searchName}">
                       <select class="form-control search-select" name="searchType">
                            <option value="title" ${dto.cri.searchType eq 'title' ? 'selected' : '' }>제목</option>
                            <option value="content" ${dto.cri.searchType eq 'content' ? 'selected' : '' }>내용</option>
                            <option value="writer" ${dto.cri.searchType eq 'writer' ? 'selected' : '' }>작성자</option>
                            <option value="titcont" ${dto.cri.searchType eq 'titcont' ? 'selected' : '' }>제목+내용</option>
                       </select>
                    </div>
                    <input type="hidden" name="pageNum" value="1">
                    <input type="hidden" name="amount" value="${dto.cri.amount}">
		    		</form>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>번호</th>
                                <th class="board-title">제목</th>
                                <th>작성자</th>
                                <th>등록일</th>
                                <th>수정일</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="list" items="${list }">
                            <tr>
                                <td>${list.num }</td>
                                <td><a href="freeDetail?num=${list.num }">${list.title }</a></td>
                                <td>${list.writer }</td>
                                <td>${list.regdate }</td>
                                <td>${list.updatedate }</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!--페이지 네이션을 가져옴-->
		    		<form method="get" id="pageForm" name="pageForm">
                    <div class="text-center">
                    <hr>
                    <ul class="pagination pagination-sm">
                    <c:if test="${dto.prev }">
                        <li><a href="${dto.startPage - 1}" onclick="page(${dto.startPage-1})">이전</a></li>
                    </c:if>
                    <c:forEach var="bno" begin="${dto.startPage }" end="${dto.endPage }">
                        <li class="${dto.pageNum == bno ? 'active' : '' }"><a href="${bno }" onclick="page(${bno})">${bno }</a></li>
                    </c:forEach>
                    <c:if test="${dto.next }">
                        <li><a href="${dto.endPage + 1}" onclick="page(${dto.endPage+1})">다음</a></li>
                    </c:if>
                    </ul>
                    <input type="hidden" name="pageNum" id="pageNum" value="${dto.cri.pageNum }">
                    <input type="hidden" name="amount" id="amount" value="${dto.cri.amount }">
                    <input type="hidden" name="searchType" id="searchType" value="${dto.cri.searchType }">
                    <input type="hidden" name="searchName" id="searchName" value="${dto.cri.searchType }">
                    <button type="button" class="btn btn-info" onclick="location.href='freeRegist'">글쓰기</button>
<!--                     <ul class="pagination pagination-sm"> -->
<%--                     <c:if test="${dto.prev }"> --%>
<%--                         <li><a href="freeList?pageNum=${dto.startPage - 1}">이전</a></li> --%>
<%--                     </c:if> --%>
<%--                     <c:forEach var="bno" begin="${dto.startPage }" end="${dto.endPage }"> --%>
<%--                         <li class="${dto.pageNum == bno ? 'active' : '' }"><a href="freeList?pageNum=${bno }">${bno }</a></li> --%>
<%--                     </c:forEach> --%>
<%--                     <c:if test="${dto.next }"> --%>
<%--                         <li><a href="freeList?pageNum=${dto.endPage + 1}">다음</a></li> --%>
<%--                     </c:if> --%>
<!--                     </ul> -->
                    </div>
		    		</form>
                </div>
            </div>
        </div>
	</section>
	<script>
	window.onload = function(){
		if(history.state == '') {
	            return;
	         }
	    var msg = '${msg}';
	    if(msg != '') {
	    	alert(msg);
	        history.replaceState('', null, null); // 현재 히스토리 기록을 변경
	        return;
	    }
	  }
	var searchBtn = document.getElementById("searchBtn");
	searchBtn.onclick = function(){
		document.getElementById("searchForm").submit();
	}
	function page(bno){
		event.prevnetDafult(); //현재 실행되는 이벤트의 실행막는방법
		document.querySelector("#pageForm #pageNum").setAttribute("value", bno);
		document.getElementById("pageForm").submit();
	}
	</script>
</body>
<%@ include file="../include/footer.jsp" %>
</html>

