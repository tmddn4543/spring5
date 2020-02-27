package utils;

public class PagingAction {
	private int currentPage; // 현재페이지							1
	private int totalCount;	 // 전체 게시물 수						100
	private int totalPage;	 // 전체 페이지 수						10
	private int blockCount;	 // 한 페이지의  게시물의 수				5
	private int blockPage;	 // 한 화면에 보여줄 페이지 수				5
	private int startCount;	 // 한 페이지에서 보여줄 게시글의 시작 번호	
	private int endCount;	 // 한 페이지에서 보여줄 게시글의 끝 번호
	private int startPage;	 // 시작 페이지						1
	private int endPage;	 // 마지막 페이지						5
	private StringBuffer pagingHtml;
	// 페이징 생성자
	public PagingAction(int currentPage, int totalCount, int blockCount, int blockPage, String formName, String callback) {
		this.blockCount = blockCount;	//10
		this.blockPage = blockPage;		//5
		this.currentPage = currentPage;	//현재페이지
		this.totalCount = totalCount;	//총개수
		// 전체 페이지 수
		totalPage = (int) Math.ceil((double) totalCount / blockCount);
		if (totalPage == 0) {
			totalPage = 1;
		}
		// 현재 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		// 현재 페이지의 처음과 마지막 글의 번호 가져오기.
		startCount = (currentPage - 1) * blockCount + 1;
		endCount = (startCount - 1) + blockCount - 1;
		// 시작 페이지와 마지막 페이지 값 구하기.
		startPage = (int) ((currentPage - 1) / blockPage) * blockPage + 1;
		endPage = startPage + blockPage - 1;
		// 마지막 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		pagingHtml = new StringBuffer();
		pagingHtml.append("<ul class=\"pagination\">");
		// 이전 block 페이지
		if (currentPage > blockPage) {
			if(callback.equals("")) {
				pagingHtml.append("<li><a href=\"javascript:;\" onclick=\"document."+formName+".currentPage.value="+(startPage - 1)+";document."+formName+".submit();return false;\" aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>");
			} else {
				pagingHtml.append("<li><a href=\"javascript:;\" onclick=\"document."+formName+".currentPage.value="+(startPage - 1)+";"+callback+";return false;\" aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>");
			}
		}
		//페이지 번호.현재 페이지는 빨간색으로 강조하고 링크를 제거.
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == currentPage) {
				pagingHtml.append("<li class=\"active\"><a href=\"#\">");
				pagingHtml.append(i);
				pagingHtml.append("</a></li>");
			} else {
				pagingHtml.append("<li><a href='javascript:;' onclick='document."+formName+".currentPage.value=");
				pagingHtml.append(i);
				if(callback.equals("")) {
					pagingHtml.append(";document."+formName+".submit();return false;'>");
				} else {
					pagingHtml.append(";"+callback+";return false;'>");
				}
				pagingHtml.append(i);
				pagingHtml.append("</a></li>");
			}
			pagingHtml.append("&nbsp;");
		}
		// 다음 block 페이지
		if (totalPage - startPage >= blockPage) {
			if(callback.equals("")) {
				pagingHtml.append("<li><a href=\"javascript:;\" onclick=\"document."+formName+".currentPage.value="+(endPage + 1)+";document."+formName+".submit();return false;\" aria-label=\"Next\"><span aria-hidden=\"true\">&raquo;</span></a></li>");
			} else {
				pagingHtml.append("<li><a href=\"javascript:;\" onclick=\"document."+formName+".currentPage.value="+(endPage + 1)+";"+callback+";return false;\" aria-label=\"Next\"><span aria-hidden=\"true\">&raquo;</span></a></li>");
			}
		}
		pagingHtml.append("</ul>");
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getBlockCount() {
		return blockCount;
	}
	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}
	public int getBlockPage() {
		return blockPage;
	}
	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}
	public int getStartCount() {
		return startCount;
	}
	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}
	public int getEndCount() {
		return endCount;
	}
	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public StringBuffer getPagingHtml() {
		return pagingHtml;
	}
	public void setPagingHtml(StringBuffer pagingHtml) {
		this.pagingHtml = pagingHtml;
	}
}
