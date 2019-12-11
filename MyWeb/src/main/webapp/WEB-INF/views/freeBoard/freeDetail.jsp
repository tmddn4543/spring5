<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
                            <p>상세보기</p>
                        </div>
                        
                        <form>
                            <div>
                                <label>DATE</label>
                                <p>${dto.regdate }</p>
                            </div>   
                            <div class="form-group">
                                <label>번호</label>
                                <input class="form-control" name='##' readonly value="${dto.num }" id = "bno">
                            </div>
                            <div class="form-group">
                                <label>작성자</label>
                                <input class="form-control" name='##' readonly value="${dto.writer }">
                            </div>    
                            <div class="form-group">
                                <label>제목</label>
                                <input class="form-control" name='##' readonly value="${dto.title }">
                            </div>

                            <div class="form-group">
                                <label>내용</label>
                                <textarea class="form-control" rows="10" name='##' readonly>${dto.content }</textarea>
                            </div>

                            <button type="button" class="btn btn-primary" onclick="location.href='freeModify?num=${dto.num}'">변경</button>
                            <button type="button" class="btn btn-dark" onclick="location.href='freeList'">목록</button>
                    </form>
                </div>
            </div>
        </div>
        </section>
        
        <section style="margin-top: 80px;">
            <div class="container">
                <div class="row">
                    <div class="col-xs-12 col-md-9 write-wrap">
                        <form class="reply-wrap">
                            <div class="reply-image">
                                <img src="../resources/img/profile.png">
                            </div>
                            <!--form-control은 부트스트랩의 클래스입니다-->
	                    <div class="reply-content">
	                        <textarea class="form-control" rows="3" id="reply"></textarea>
	                        <div class="reply-group">
	                              <div class="reply-input">
	                              <input type="text" class="form-control" placeholder="이름" id="replyid">
	                              <input type="password" class="form-control" placeholder="비밀번호" id="replypw">
	                              </div>
	                              
	                              <button type="button" class="right btn btn-info" id="replyRegist">등록하기</button>
	                        </div>
	
	                    </div>
                        </form>

                        <!--여기에접근 반복-->
                        <div id="replyList">
                        
                        </div>
                        
                        <button type="button" class="btn btn-default btn-block" id="moreList">더보기</button>
                    </div>
                </div>
            </div>
        </section>
        
        
        <!-- 모달 -->
		<div class="modal fade" id="replyModal" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="btn btn-default pull-right" data-dismiss="modal">닫기</button>
						<h4 class="modal-title">댓글수정</h4>
					</div>
					<div class="modal-body">
						<!-- 수정폼 id값을 확인하세요-->
						<div class="reply-content">
						<textarea class="form-control" rows="4" id="modalReply" placeholder="내용입력"></textarea>
						<div class="reply-group">
							<div class="reply-input">
							    <input type="hidden" id="modalRno">
								<input type="password" class="form-control" placeholder="비밀번호" id="modalPw">
							</div>
							<button class="right btn btn-info" id="modalModBtn">수정하기</button>
							<button class="right btn btn-info" id="modalDelBtn">삭제하기</button>
						</div>
						</div>
						<!-- 수정폼끝 -->
					</div>
				</div>
			</div>
		</div>
        
        
        <%@ include file="../include/footer.jsp" %>
        
        
        
        <script>
        	$(document).ready(function(){
        		
        		$("#replyRegist").click(function(){
        			add();
        		})
        		
        		
        		
        		
        		//댓글등록
        		function add(){
        			var reply = $("#reply").val();
        			var replyid = $("#replyid").val();
        			var replypw = $("#replypw").val();
        			var bno = $("#bno").val();
        			if(reply == '' || replyid == '' || replypw == '' ){
        				alert("다채워야됨");
        				return false;//함수강제종료
        			}
        			$.ajax({
        				type:"POST",//전송형식
        				url:"../reply/new",//전송할 url
        				data: JSON.stringify({"bno":bno,"reply": reply,"replyid":replyid,"replypw":replypw}),//전송데이터
        				contentType:"application/json; charset= utf-8",//전송할 타입
        				success: function(result) {
        					//게시글 등록후 공백처리
        					$("#reply").val("");
        					$("#replyId").val("");
        					$("#replyPw").val("");
            				getList(1,true);
        				},//전송성공시 돌려받을 콜백함수 익명함수
        				error: function(status) {
        					alert("댓글등록에 실패했습니다.")
        					
        					
        				} //응답 실패시 실행되는 익명함수 			
        			})
            		
        		}
        		
        		
        		//목록함수
        		getList(1,true);
        		function getList(pageNum, reset){
        			if(reset == true){
        				str = "";
        			}
        			var bno = $("#bno").val();
        			$.getJSON(
        				"../reply/getReply/"+bno+"/"+pageNum,
        				function(data){
        					var str="";
        					for(var i = 0; i< data.length; i++){
        						console.log(data[i])
        						str += "<div class='reply-wrap'>";
                               	str += "<div class='reply-image'>";
                               	str += "<img src='../resources/img/profile.png'>";
                                str += "</div>";
                                str += "<div class='reply-content'>";
                                str += "<div class='reply-group'>";
								str += "<strong class='left'>"+data[i].replyid+"</strong>";
								str += "<small class='left'>"+data[i].updatedate+"</small>";
								str += "<a href='"+data[i].rno+"' class='right' id='replyModify'><span class='glyphicon glyphicon-pencil'></span>수정</a>";
								str += "<a href='"+data[i].rno+"' class='right' id='replyDelete'><span class='glyphicon glyphicon-remove'></span>삭제</a>";
								str += "</div>";
								str += "<p class='clearfix'>"+data[i].reply+"</p>";
								str += "</div>";
								str += "</div>";
        					}
        					$("#replyList").html(str);
        				}
        			)
        		}
        		
        		//에이젝스의 실행이 더늦게완료가되므로, 이벤트 선언이 먼저 일어나게됩니다.
        		//그렇다면화면에댓글과관련된 이벤트는 아무것도 등록이안된상태이므로, 일반적인 클릭이벤트는 동작하지않음.
        		//이때 이미존재하는요소 $("#replyList")에 이벤트를등록하고, 해당태그의 내부요소를 클릭하여 동작하는 이벤트 위임방식을 사용함
        			//modalDelBtn
        			//modalModBtn
        		$("#replyList").on("click","a", function(event){
        			event.preventDefault();
					$("#replyModal").modal("show");
        			if(event.target.id == "replyModify"){//block
        				var rno = $(this).attr("href");//클릭요소의 href값가져온다
        				$("#modalRno").val(rno);
        				$(".modal-title").html("댓글수정");
        				$("#modalReply").css("display", "block");  
        				$("#modalModBtn").css("display", "block");
						$("#modalDelBtn").css("display", "none");        
        			}else{
        				var rno = $(this).attr("href");//클릭요소의 href값가져온다
        				$("#modalRno").val(rno);
        				$(".modal-title").html("댓글삭제");
        				$("#modalReply").css("display", "none");  
        				$("#modalModBtn").css("display", "none");
        				$("#modalDelBtn").css("display", "block"); 
        			}
        		
        		})
				
        		$("#modalDelBtn").click(function(){
        			//1.모달 rno 값얻고, 모달 pw값을얻음
        			//2에이젝스를통해서 reply/delete로 제이슨형식으로 요청처리
        			//pwCheck()메서드를통해서 비밀번호가맞는지확인
        			//비밀번호가맞다면  delete()메서드로삭제를진행
        			//콜백함수로삭제성공이돌아오면, 비밀번호창을비우고,모달창을modal("hide")로처리

	
        			var rno = $("#modalRno").val();
        			var replypw = $("#modalPw").val();
        			$.ajax({
        				type: "delete",
        				url:"../reply/delete",
        				data: JSON.stringify({"rno" : rno, "replypw" : replypw}),
        				contentType: "application/json; charset=utf-8",
        				success:function(result){
        					if(result==1){
        						alert("삭제성공");
        						$("#modalPw").val("");
        						$("#replyModal").modal("hide");
								getList(1,true);
        					}else{//비밀번호틀렷을경우
        						alert("비밀번호틀림");
        						$("#modalPw").val("");
        					}
        				},
        				error:function(status){
        					alert("삭제실패"+status);
        				}
        			})
        		})
        		
        		$("#modalModBtn").click(function(){
        			var rno = $("#modalRno").val();
        			var reply = $("#modalReply").val();
        			var replypw = $("#modalPw").val();
        			$.ajax({
        				type:"put",
        				url:"../reply/update",
        				data:JSON.stringify({"rno" : rno, "reply" : reply, "replypw" : replypw}),
        				contentType: "application/json; charset=utf-8",
        				success:function(result){
        					if(result==1){
        						alert("업데이트성공");
        						$("#modalPw").val("");
        						$("#replyModal").modal("hide");
        						getList(1,true);
        					}else{
        						alert("비밀번호틀림");
        						$("#modalPw").val("");
        					}
        				},
        				error:function(status){
        					alert("삭제실패"+status);
        				}
        			})
        		})

				$("#moreList").click(function(){
					alert("asd");
				})
        	
        	})//레디끝
        	
        </script>
	
</body>
</html>