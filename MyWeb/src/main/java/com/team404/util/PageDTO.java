package com.team404.util;

import lombok.Data;

@Data
public class PageDTO {
	private int endPage;	//화면에그려질끝번호
	private int startPage;	//화면에그려질처음번호
	private boolean prev;	//화면에그려질다음버튼
	private boolean next;	//그려질이전버튼
	
	private int pageNum;	//현제조회중인페이지
	private int amount;		//한페이지에서몇개의데이터보여줄거
	private int total;		//총개수
	
	
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.pageNum = cri.getPageNum();
		this.amount = cri.getAmount();
		this.total = total;
		
		//전달되는 기준에 따라서 pageDTO 계산처리
		//끝페이지계산
		//현재조회하는페이지 12번->화면에 보여질끝페이지
		
		
		this.endPage = (int)Math.ceil(this.pageNum / 10.0) * 10;
		
		this.startPage = this.endPage - 10 + 1;
		
		int realEnd = (int)Math.ceil(total/(double)this.amount);
		if(this.endPage>realEnd) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		
		this.next = realEnd > this.endPage;
	}
		
}
