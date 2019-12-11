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
    <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
    <!--개인 디자인 추가-->
    <link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath }/resources/js/bootstrap.js"></script>
	
</head>
<body>
	
	
	<%@ include file="../include/header.jsp" %>


	<!--폼 섹션-->
    <section>
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-9 col-sm-12 join-form">
                    <div class="titlebox">
                       	 회원가입
                    </div>
                    <form action="joinForm" id="joinForm" method="post">
                        <div class="form-group"><!--사용자클래스선언-->
                            <label for="id">아이디</label>
                            <div class="input-group"><!--input2탭의 input-addon을 가져온다 -->
                                <input type="text" class="form-control" id="userId" name="userId" placeholder="아이디를 (영문포함 4~12자 이상)">
                                <div class="input-group-addon">
                                    <button type="button" class="btn btn-primary" id="idConfirmBtn">아이디중복체크</button>
                                </div>
                            </div>
                            <span id="msgId"></span><!--자바스크립트에서 추가-->
                        </div>
                        <div class="form-group"><!--기본 폼그룹을 가져온다-->
                            <label for="password">비밀번호</label>
                            <input type="password" class="form-control" id="userPw" name="userPw" placeholder="비밀번호 (영 대/소문자, 숫자 조합 8~16자 이상)">
                            <span id="msgPw"></span><!--자바스크립트에서 추가-->
                        </div>
                        <div class="form-group">
                            <label for="password-confrim">비밀번호 확인</label>
                            <input type="password" class="form-control" id="pwConfirm" placeholder="비밀번호를 확인해주세요.">
                             <span id="msgPw-c"></span><!--자바스크립트에서 추가-->
                        </div>
                        <div class="form-group">
                            <label for="name">이름</label>
                            <input type="text" class="form-control" id="userName" name="userName" placeholder="이름을 입력하세요.">
                        </div>
                        <!--input2탭의 input-addon을 가져온다 -->
                        <div class="form-group">
                            <label for="hp">휴대폰번호</label>
                            <div class="input-group">
				<select class="form-control phone1" id="userPhone1" name="userPhone1">
					<option>010</option>
					<option>011</option>
					<option>017</option>
					<option>018</option>
				</select> 
				<input type="text" class="form-control phone2" id="userPhone2" name="userPhone2" placeholder="휴대폰번호를 입력하세요.">
                                <div class="input-group-addon">
                                    <button type="button" class="btn btn-primary">본인인증</button>
                                </div>
                            </div>
                        </div>
			<div class="form-group email-form">
			  <label for="email">이메일</label><br>
			  <input type="text" class="form-control" id="userEmail1" name="userEmail1" placeholder="이메일">
			  <select class="form-control" id="userEmail2" name="userEmail2">
			    <option>@naver.com</option>
			    <option>@daum.net</option>
			    <option>@gmail.com</option>
			    <option>@hanmail.com</option>
			    <option>@yahoo.co.kr</option>
			  </select>
			</div>
                        <!--readonly 속성 추가시 자동으로 블락-->
                        <div class="form-group">
                            <label for="addr-num">주소</label>
                            <div class="input-group">
                                <input type="text" class="form-control" id="addrZipNum" name="addrZipNum" placeholder="우편번호" readonly>
                                <div class="input-group-addon">
                                    <button type="button" class="btn btn-primary" id="addBtn">주소찾기</button>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" id="addrBasic"  name="addrBasic" placeholder="기본주소">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" id="addrDetail"name="addrDetail" placeholder="상세주소">
                        </div>

                        <!--button탭에 들어가서 버튼종류를 확인한다-->
                        <div class="form-group">
                            <button type="button" class="btn btn-lg btn-success btn-block" id="joinBtn">회원가입</button>
                        </div>

                        <div class="form-group">
                            <button type="button" class="btn btn-lg btn-info btn-block" id="loginBtn">로그인</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
	<%@ include file="../include/footer.jsp" %>
    
   
    
    
    <script>
    //아이디 중복 체크
    $("#idConfirmBtn").click(function(){
  	
    	if($("#userId").val() == '' || $("#userId").css("border-Color") == 'rgb(255, 0, 0)') {
    		alert("아이디 적거나 규칙확인행");
    		return false;//함수 종료
    	}
    	//POST형식으로 userId를 json형식으로 만들어서 idConfirm URL로 비동기요청을 처리
    	//아이디가 존재하면 1을 반환, 아이디가 존재하지 않는다면 0을반환
    	//아이디가 존재하지 않는다면 "사용가능한 아이디입니다."를 출력하고, 아이디를 바꾸지 못하도록 readonly처리. msgId에 "사용가능한 아이디입니다.
    	//아이디가 존재하다면 "중복된 아이디 입니다.를 출력 
		var userId = $("#userId").val();  	
    	
    	
    	$.ajax({
			type : "POST",
			url : "idConfirm",
			data : JSON.stringify({
				"userId" : userId
			}),
			contentType : "application/json; charset= utf-8",
			success : function(result) {
			    if(result==1){
				alert("이미등록된 아이디 입니다.");
				$("#userId").focus();
			    }else {
			    	alert("사용가능한 아이디입니다.");
			    	$("#userId").attr("readonly",true);//readonly집어넣음
			    	$("#msgId").html("사용가능한 아이디 입니다.");//문자열로 집어넣음
			    }
			    
			},
			error : function(status) {
				
				
			}  			
		})
    	
    	
    	
    	
    })
 
    
    $("#userJoinBtn").click(function(){
  
    	document.regForm.submit();
    	
    
    })
 
    //회원가입버튼
     $("#joinBtn").click(function(){
    													
    	 //1.아이디가 중복체크 되지 않았다면 경고창
         //2.비밀번호가 공백이거나, 규칙에 맞지 않다면, 비밀번호 확인란과 다르다면  경고창
         //3.이름공백시 경고창
         //4.연락처 공백시 경고창
    	 //5.이메일 공백시 경고창
    	 //6.주소 공백시 경고창
    	 //joinForm요청으로 submit().
    	 
    	 if(!$("#userId").attr("readonly")){
    		 alert("아이디 중복체크 행");
    		 return false;
    	 
    	 }else if($("#userPw").val()=="" || $("#pwConfirm").css("border-Color") == 'rgb(255, 0, 0)' ){
    		 alert("비밀번호 적거나 규칙체크행");
    		 return false;
    	 }else if($("#userName").val()==""){
    		 alert("이름적엉");
    		 return false;
    	 }else if($("#userPhone2").val()=="") {
    		 alert("핸드폰 번호 적엉");
    		 return false;
    	
    	 }else if($("#userEmail1").val()=="" || $("#userEmail2").val()=="") {
    		 alert("이메일 확인행");
    		 return false;
    	 }else if($("#addrDetail").val()=="" ||$("#addrBasic").val()=="" ){
    		 alert("주소적엉");
    		 return false;
    	 }else {
    		 if(!confirm('회원가입하시겠습니까?')) return;
    			
    		 $('#joinForm').submit();
    		 
    	 }
    
    })
    //회원가입페이지 이동
    $("#loginBtn").click(function(){
    	location.href="userLogin";	
    
   
    
    })
    
    
    
    
    
    
    
    
    
    
    
    
    
    </script>
    
   
    
 
    
    <script>
        /*아이디 형식 검사 스크립트*/
        var id = document.getElementById("userId");
        id.onkeyup = function() {
            /*자바스크립트의 정규표현식 입니다*/
            /*test메서드를 통해 비교하며, 매칭되면 true, 아니면 false반*/
            var regex = /^[A-Za-z0-9+]{4,12}$/; 
            if(regex.test(document.getElementById("userId").value )) {
                document.getElementById("userId").style.borderColor = "green";
                document.getElementById("msgId").innerHTML = "아이디중복체크는 필수 입니다";

            } else {
                document.getElementById("userId").style.borderColor = "red";
                document.getElementById("msgId").innerHTML = "";
            }
        }
        /*비밀번호 형식 검사 스크립트*/
        var pw = document.getElementById("userPw");
        pw.onkeyup = function(){
            var regex = /^[A-Za-z0-9+]{8,16}$/;
             if(regex.test(document.getElementById("userPw").value )) {
                document.getElementById("userPw").style.borderColor = "green";
                document.getElementById("msgPw").innerHTML = "사용가능합니다";
            } else {
                document.getElementById("userPw").style.borderColor = "red";
                document.getElementById("msgPw").innerHTML = "";
            }
        }
        /*비밀번호 확인검사*/
        var pwConfirm = document.getElementById("pwConfirm");
        pwConfirm.onkeyup = function() {
            var regex = /^[A-Za-z0-9+]{8,16}$/;
            if(document.getElementById("pwConfirm").value == document.getElementById("userPw").value ) {
                document.getElementById("pwConfirm").style.borderColor = "green";
                document.getElementById("msgPw-c").innerHTML = "비밀번호가 일치합니다";
            } else {
                document.getElementById("pwConfirm").style.borderColor = "red";
                document.getElementById("msgPw-c").innerHTML = "비밀번호 확인란을 확인하세요";
            }
        }    
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
        
        
     
   
</body>
</html>