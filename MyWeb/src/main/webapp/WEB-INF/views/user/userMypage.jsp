<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>인덱스를 만들어 보자</title>
	
	<!-- 제이쿼리 추가(가장먼저 로딩 되어야 하기때문에 첫째줄에 선언) -->
	<script src="${pageContext.request.contextPath }/resources/js/jquery.js"></script>
	
	
    <link href="${pageContext.request.contextPath }/resources/css/bootstrap.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!--개인 디자인 추가-->
    <link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath }/resources/js/bootstrap.js"></script>
	

	
</head>
<body>
	<%@ include file="../include/header.jsp" %>
	<section>
        <!--Toggleable / Dynamic Tabs긁어옴-->
        <div class="container">
            <div class="row">
                <div class="col-sm-12 col-md-10 col-lg-9 myInfo">
                    <div class="titlebox">
                        MEMBER INFO                    
                    </div>
                    
                    <ul class="nav nav-tabs tabs-style">
                        <li class="active"><a data-toggle="tab" href="#info">내정보</a></li>
                        <li><a data-toggle="tab" href="#myBoard">내글</a></li>
                        <li><a data-toggle="tab" href="#menu2">Menu 2</a></li>
                    </ul>
                    <div class="tab-content">
                        <div id="info" class="tab-pane fade in active">
 
                            <p>*표시는 필수 입력 표시입니다</p>
                            <form>
                            <table class="table">
                                <tbody class="m-control">
                                    <tr>
                                        <td class="m-title">*ID</td>
                                        <td><input class="form-control input-sm" value="${userDTO.userId }" id="userId" readonly="readonly"></td>
                                    </tr>
                                    <tr>
                                        <td class="m-title">*이름</td>
                                        <td><input class="form-control input-sm" value="${userDTO.userName }" id="userName"></td>
                                    </tr>
                                    <tr>
                                        <td class="m-title">*비밀번호</td>
                                        <td><input class="form-control input-sm" id="userPw" type="password"></td>
                                    </tr>
                                    <tr>
                                        <td class="m-title">*비밀번호확인</td>
                                        <td><input class="form-control input-sm" id="userPw_check" type="password"></td>
                                    </tr>
                                    <tr>
                                        <td class="m-title">*E-mail</td>
                                        <td>
                                            <input class="form-control input-sm" value="${userDTO.userEmail1 }" id="userEmail1">@
                                            <select class="form-control input-sm sel" id="userEmail2">
                                                <option ${userDTO.userEmail2 eq 'naver.com' ? 'selected' : '' }>naver.com</option>
                                                <option ${userDTO.userEmail2 eq 'gmail.com' ? 'selected' : '' }>gmail.com</option>
                                                <option ${userDTO.userEmail2 eq 'daum.net' ? 'selected' : '' }>daum.net</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="m-title">*휴대폰</td>
                                        <td>
                                            <select class="form-control input-sm sel" id="userPhone1">
                                                <option ${userDTO.userPhone1 eq '010' ? 'selected' : '' }>010</option>
                                                <option ${userDTO.userPhone1 eq '011' ? 'selected' : '' }>011</option>
                                                <option ${userDTO.userPhone1 eq '017' ? 'selected' : '' }>017</option>
                                                <option ${userDTO.userPhone1 eq '018' ? 'selected' : '' }>018</option>
                                            </select>
                                            <input class="form-control input-sm" value="${userDTO.userPhone2 }" id="userPhone2">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="m-title">*우편번호</td>
                                        <td><input class="form-control input-sm" id="addrZipNum" readonly value="${userDTO.addrZipNum }">
                                        	<button type="button" class="btn btn-primary" id="addBtn">중복확인</button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="m-title">*주소</td>
                                        <td><input class="form-control input-sm add" id="addrBasic" value="${userDTO.addrBasic }"></td>
                                    </tr>
                                    <tr>
                                        <td class="m-title">*상세주소</td>
                                        <td><input class="form-control input-sm add" id="addrDetail" value="${userDTO.addrDetail}"></td>
                                    </tr>
                                </tbody>
                            </table>
                            </form>

                            <div class="titlefoot">
                                <button class="btn" id="updateBtn">수정</button>
                            </div>
                        </div>
                        <!-- 첫번째 토글 끝 -->
                        <div id="myBoard" class="tab-pane fade">
                            <p>*내 게시글 관리</p>
                            <form>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <td>번호</td>
                                        <td>제목</td>
                                        <td>작성일</td>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="userDTO" items="${userDTO.userBoardList }">
                                    <tr>
                                        <td>${userDTO.num }</td>
                                        <td><a href="../freeBoard/freeDetail?num=${userDTO.num }">${userDTO.title }</a></td>
                                        <td>${userDTO.regdate }</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            </form>
                        </div>
                        <!-- 두번째 토글 끝 -->
                        <div id="menu2" class="tab-pane fade">
                            <h3>Menu 2</h3>
                            <p>Some content in menu 2.</p>
                        </div>
                    </div>
                </div>
            </div>

        </div>


    </section>
    
    
    <script>
    	$("#updateBtn").click(function(){
    		//1.태그들의 공백여부확인
    		//2.비밀번호란은 8~16 비밀번호확인란은 비밀번호란과동일한지확인
    		//3.나머지 조건은 공백이면경고장
    		//4.ajax요청을통해서 json형식으로 updateUser요청으로 전송을시켜서 업데이트처리
    		//5.업데이트가 성공이면 "정보가수정이됨"경고장으로 비밀번호와, 비밀번호 확인란을 공백처리
    		//6.업데이트실패면"정보수정실패"경고장으로출력후에 비밀번호와,비밀번호확인란공백처리
    		if($("#userName").val() == "" || $("#userPw").val() == "" || $("#userPw_check").val() == "" 
    				|| $("#userEmail1").val() == "" || $("#userPhone2").val() == "" 
    				|| $("#addrBasic").val() == "" || $("#addrDetail").val() == ""){
    			alert("공백들어감");
    			return false;
    		}else if($("#userPw").val() != $("#userPw_check").val()){
    			alert("비번안맞음");
    			return false;
    		}else if($("#userPw").val().length < 8 || $("#userPw").val().length > 16){
    			alert("8~16");
    			return false;
    		}
    		var userId = $("#userId").val();
    		var userName = $("#userName").val();
    		var userPw = $("#userPw").val();
    		var userEmail1 = $("#userEmail1").val();
    		var userEmail2 = $("#userEmail2").val();
    		var userPhone1 = $("#userPhone1").val();
    		var userPhone2 = $("#userPhone2").val();
    		var addrBasic = $("#addrBasic").val();
    		var addrDetail = $("#addrDetail").val();
    		var addrZipNum = $("#addrZipNum").val();
    		
    		$.ajax({
    			type: "update",
    			url:"../user/updateUser",
    			data: JSON.stringify({"userName" : userName,
    				"userPw" : userPw, 
    				"userEmail1" : userEmail1,
    				"userEmail2" : userEmail2,
    				"userPhone1" : userPhone1,
    				"userPhone2" : userPhone2,
    				"addrBasic" : addrBasic,
    				"addrDetail" : addrDetail,
    				"addrZipNum" : addrZipNum,
    				"userId" : userId
    				}),
    			contentType: "application/json; charset=utf-8",
    			success:function(result){
    				$("#userPw").val("");
    				$("#userPw_check").val("");
    				alert("성공");
    			},
    			error:function(status){
    				$("#userPw").val("");
    				$("#userPw_check").val("");
    				alert("삭제실패"+status);
    			}
    		})
    		
    	})
    </script>
    
    
    
    <script>
    /*주소API */
    var addBtn=document.getElementById("addBtn");
    addBtn.onclick = goPopup;
    function goPopup(){
    	var pop = window.open("${pageContext.request.contextPath}/resources/popup/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
    }
    function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
    		//팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
    		document.getElementById("addrBasic").value = roadAddrPart1;
    		document.getElementById("addrDetail").value = addrDetail+roadAddrPart2;
    		document.getElementById("addrZipNum").value = zipNo;
    }
    
    </script>
    
    
    <%@ include file="../include/footer.jsp" %>
    
</body>
</html>