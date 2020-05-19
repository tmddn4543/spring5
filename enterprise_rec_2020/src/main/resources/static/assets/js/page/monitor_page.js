$(document).ready(function(){
	/* 반응형 메뉴 */
    $(window).resize(function(){
    var width = parseInt($(this).width()); //parseint는 정수로 하기 위함

        if (width > 1464 || width < 451){
            $('body').removeClass('mini-navbar');
        } else { 
            $('body').addClass('mini-navbar');
        }
    }).resize();
    
});