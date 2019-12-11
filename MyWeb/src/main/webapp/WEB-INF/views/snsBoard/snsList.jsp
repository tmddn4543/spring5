<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>인덱스를 만들어 보자</title>

    <link href="${pageContext.request.contextPath }/resources/css/bootstrap.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath }/resources/js/jquery.js"></script>
    <!--개인 디자인 추가-->
    <link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath }/resources/js/bootstrap.js"></script>
	<style type="text/css">
	section {
		margin-top: 70px;
	}
	
	/*부트스트랩 li의 drophover클래스 호버시에 드롭다운 적용코드*/
	ul.nav li.drophover:hover>ul.dropdown-menu {
		display: block;
		margin: 0;
	}
	
	.aside-inner {
		position: fixed;
		top: 70px;
		width: 180px;
	}
	
	.section-inner {
		border-right: 1px solid #ddd;
		border-left: 1px solid #ddd;
		background-color: white;
	}
	
	.section-inner p, .aside-inner p {
		margin: 0;
	}
	
	.title-inner {
		position: relative;
		padding: 15px 0;
	}
	
	.title-inner .profile {
		position: absolute; /*부모기준으로 위치지정 릴레이티브*/
		top: 15px;
		left: 0;
	}
	
	.title-inner .title {
		padding-left: 50px;
	}
	/*내용*/
	.content-inner {
		padding: 10px 0;
	}
	/* 이미지영역  */
	.image-inner img {
		width: 100%;
	}
	/*좋아요*/
	.like-inner {
		padding: 10px 0;
		border-bottom: 1px solid #ddd;
		border-top: 1px solid #ddd;
		margin-top: 10px;
	}
	
	.like-inner img {
		width: 20px;
		height: 20px;
	}
	
	.link-inner {
		overflow: hidden;
		padding: 10px 0;
	}
	
	.link-inner a {
		float: left;
		width: 33.3333%;
		text-align: center;
		text-decoration: none;
		color: #333333;
	}
	
	.link-inner i {
		margin: 0 5px;
	}
	
	/*767미만 사이즈에서 해당 css가 적용됨*/
	/*xs가 767사이즈   */
	@media ( max-width :767px) {
		aside {
			display: none;
		}
	}
	/* 파일업로드 버튼 바꾸기 */
	.filebox label {
		display: inline-block;
		padding: 6px 10px;
		color: #fff;
		font-size: inherit;
		line-height: normal;
		vertical-align: middle;
		background-color: #5cb85c;
		cursor: pointer;
		border: 1px solid #4cae4c;
		border-radius: none;
		-webkit-transition: background-color 0.2s;
		transition: background-color 0.2s;
	}
	
	.filebox label:hover {
		background-color: #6ed36e;
	}
	
	.filebox label:active {
		background-color: #367c36;
	}
	
	.filebox input[type="file"] {
		position: absolute;
		width: 1px;
		height: 1px;
		padding: 0;
		margin: -1px;
		overflow: hidden;
		clip: rect(0, 0, 0, 0);
		border: 0;
	}
	
	/* sns파일 업로드시 미리보기  */
	.fileDiv {
		height: 100px;
		width: 200px;
		display: none;
		margin-bottom: 10px;
	}
	
	.fileDiv img {
		width: 100%;
		height: 100%;
	}
	/* 모달창 조절 */
	.modal-body {
		padding: 0px;
	}
	
	.modal-content>.row {
		margin: 0px;
	}
	
	.modal-body>.modal-img {
		padding: 0px;
	}
	
	.modal-body>.modal-con {
		padding: 15px;
	}
	
	.modal-inner {
		position: relative;
	}
	
	.modal-inner .profile {
		position: absolute; /*부모기준으로 위치지정 릴레이티브*/
		top: 0px;
		left: 0px;
	}
	
	.modal-inner .title {
		padding-left: 50px;
	}
	
	.modal-inner p {
		margin: 0px;
	}
	</style>
	
</head>
<body>
	<%@ include file="../include/header.jsp" %>
	<section>
		<div class="container">
			<div class="row">
				<aside class="col-sm-2">
					<div class="aside-inner">
						<div class="menu1">
							<p>
								<img src="../resources/img/profile.png">박인욱
							</p>
							<ul>
								<li>내정보</li>
								<li>내쿠폰</li>
								<li>내좋아요게시물</li>
							</ul>
						</div>
						<div class="menu2">
							<p>둘러보기</p>
							<ul>
								<li>기부 캠페인</li>
								<li>페이지</li>
								<li>그룹</li>
								<li>이벤트</li>
								<li>친구리스트</li>
							</ul>
						</div>
					</div>
				</aside>
				<div class="col-xs-12 col-sm-8 section-inner">
					<h4>게시물 만들기</h4>
					<!-- 파일 업로드 폼입니다 -->
					<div class="fileDiv">
						<img id="fileImg" src="../resources/img/img_ready.png">
					</div>
					<div class="reply-content">
						<textarea class="form-control" rows="3" name="content"
							id="content" placeholder="무슨 생각을 하고 계신가요?"></textarea>
						<div class="reply-group">
							<div class="filebox pull-left">
								<label for="file">이미지업로드</label> 
								<input type="file" name="file" id="file">
							</div>
							<button type="button" class="right btn btn-info" id="uploadBtn">등록하기</button>
						</div>
					</div>


					<!-- 파일 업로드 폼 끝 -->
					<div id="contentDiv">
					
					</div>
					
					
				</div>
				<!--우측 어사이드-->
				<aside class="col-sm-2">
					<div class="aside-inner">
						<div class="menu1">
							<p>목록</p>
							<ul>
								<li>목록1</li>
								<li>목록2</li>
								<li>목록3</li>
								<li>목록4</li>
								<li>목록5</li>
							</ul>
						</div>
					</div>
				</aside>
			</div>
		</div>
	</section>
	<%@ include file="../include/footer.jsp" %>
	<!-- 모달 -->
	<div class="modal fade" id="snsModal" role="dialog">
			
	</div>



	<script>
		$(document).ready(function(){
			$("#uploadBtn").click(function(){
				regist();
			})
			function regist(){
				var userId = '${sessionScope.userId}';
				
				//자바스크립트 파일확장자체크
				var file = $("#file").val();
				file = file.slice(file.indexOf(".") + 1).toLowerCase();
				if(file != "jpg" && file != "png" && file != "bmp"){
					alert("이미지파일(jpg,png,bmp)만 등록가능");
					$("#file").val("");
					return false;
				}else if(/* userId == '' */ false){
					alert("로그인이필요한서비스");
					return false;
				}
				
				var formData = new FormData();
				var fileData = $("#file");
				
				formData.append("file",fileData[0].files[0]);
				var content = $("#content").val();
				formData.append("content", content);
				
				$.ajax({
					url:"upload",
					type:"POST",
					data:formData,
					processData:false,
					contentType:false,
					success:function(result){
						$(".fileDiv").css("display","");
						$("#content").val("");
						getList(true);
						alert("업로드성공!@#!@#!@#!");
					},
					error:function(result){
						$(".fileDiv").css("display","");
						$("#content").val("");
						alert("업로드실패"+result);
					}
				})
			}
			getList(true);
			function getList(reset){
				if(reset==true){
					str = "";
					
				}
				$.getJSON(
					"getList",
					function(data){
						var str = "";
						for(var i=0; i<data.length; i++){
							var time = data[i].regdate;
							var hour = parseInt((time/(1000*60*60))%24)+9;
							var min = parseInt((time/(1000*60))%60);
							hour = (hour < 10) ? "0" + hour : hour;
							min = (min < 10) ? "0" + min : min;
							str +="<div class='title-inner'>";
							str +="<div class='profile'>";
							str +="<img src='../resources/img/profile.png'>";
							str +="</div>";
							str +="<div class='title'>";
							str +="<p>"+data[i].writer+"</p>";
							str +="<small>"+hour+":"+min+"</small>";
							str +="</div>";
							str +="</div>";
							str +="<div class='content-inner'>";
							str +="<p>"+data[i].content+"</p>";
							str +="</div>";
							str +="<div class='image-inner'>";
							str +="<a href='"+data[i].bno+"'><img id='"+data[i].bno+"' src='view?fileLoca="+data[i].fileLoca+"&fileName="+data[i].fileName+"'></a>";
							str +="</div>";
							str +="<div class='like-inner'>";
							str +="<img src='../resources/img/icon.jpg'> <span>522</span>";
							str +="</div>";
							str +="<div class='link-inner'>";
							str +="<a href='##'><i class='glyphicon glyphicon-thumbs-up'></i>좋아요</a>";
							str +="<a href='##'><i class='glyphicon glyphicon-comment'></i>댓글달기</a>";
							str +="<a href='##'><i class='glyphicon glyphicon-remove'></i>삭제하기</a></div>";
						}
						$("#contentDiv").html(str);
					}
				)
				
			}
			
			$("#contentDiv").on("click", "a", function(event){
				event.preventDefault();
				var bno = event.target.id;
				$.getJSON(
					"snsDetail/"+bno,
					function(data){
						var str ="";
						var time = data.regdate;
						var hour = parseInt((time/(1000*60*60))%24)+9;
						var min = parseInt((time/(1000*60))%60);
						hour = (hour < 10) ? "0" + hour : hour;
						min = (min < 10) ? "0" + min : min;
						str += "<div class='modal-dialog modal-lg'>";
						str += "<div class='modal-content'>";
						str += "<div class='modal-body row'>";
						str += "<div class='modal-img col-sm-8 col-xs-6' >";
						str += "<img src='view?fileLoca="+data.fileLoca+"&fileName="+data.fileName+"'></div>";
						str += "<div class='modal-con col-sm-4 col-xs-6'>";
						str += "<div class='modal-inner'>";
						str += "<div class='profile'>";
						str += "<img src='../resources/img/profile.png'></div>";
					 	str += "<div class='title'>";
					 	str += "<p id='snsWriter'>"+data.writer+"</p>";
						str += "<small id='snsRegdate'>"+hour+":"+min+"</small></div>";
						str += "<div class='content-inner'>";
						str += "<p id='snsContent'>"+data.content+"</p></div>";
					 	str += "<div class='link-inner'>";
						str += "<a href='##'><i class='glyphicon glyphicon-thumbs-up'></i>좋아요</a>";
						str += "<a href='##'><i class='glyphicon glyphicon-comment'></i>댓글달기</a>";
						str += "<a href='##'><i class='glyphicon glyphicon-share-alt'></i>공유하기</a></div></div></div></div></div></div>";
						$("#snsModal").html(str);
						$("#snsModal").modal("show");
					}
				)
			})
		})
	</script>




	<script>
		//자바 스크립트 파일 미리보기 기능
		function readURL(input) {
        	if (input.files && input.files[0]) {
        		
            	var reader = new FileReader(); //비동기처리를 위한 파읽을 읽는 자바스크립트 객체
            	//readAsDataURL 메서드는 컨텐츠를 특정 Blob 이나 File에서 읽어 오는 역할 (MDN참조)
	        	reader.readAsDataURL(input.files[0]); 
            	//파일업로드시 화면에 숨겨져있는 클래스fileDiv를 보이게한다
	            $(".fileDiv").css("display", "block");
            	
            	reader.onload = function(event) { //읽기 동작이 성공적으로 완료 되었을 때 실행되는 익명함수
                	$('#fileImg').attr("src", event.target.result); 
                	console.log(event.target)//event.target은 이벤트로 선택된 요소를 의미
	        	}
        	}
	    }
		$("#file").change(function() {
	        readURL(this); //this는 #file자신 태그를 의미
	        
	    });
	</script>
	
	
	
</body>
</html>