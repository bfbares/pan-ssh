package com.borjabares.pan_ssh.model.panservice;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
	private int pageNum;
	private int actualPage;
	private boolean beginEllipsis; // 1...12,13,15
	private boolean endEllipsis; // 1,2,3...15
	private List<Integer> pageList;

	private int calculatePageNum(int count, int total){
		int pageNum; 
		pageNum = total/count;
		if (total%count!=0){
			pageNum++;
		}
		return pageNum;
	}
	
	public Pagination (int startIndex, int count, int total){
		this.pageNum = calculatePageNum(count, total);
		this.actualPage = startIndex/count;
		this.beginEllipsis = true;
		this.endEllipsis = true;
		this.pageList = new ArrayList<Integer>();
		if (actualPage < 10){
			this.beginEllipsis = false;
			for (int i=2;i<pageNum&&i<=11;i++){
				pageList.add(i);
			}
			if (pageNum <=11){
				endEllipsis = false;
			}
		} else {
			if (actualPage >= pageNum-11){
				this.endEllipsis = false;
				for (int i=pageNum-11;i<pageNum;i++){
					pageList.add(i);
				}
			} else {
				for (int i=actualPage-5;i<=actualPage+5;i++){
					pageList.add(i);
				}
			}
		}
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public int getActualPage() {
		return actualPage;
	}

	public boolean getBeginEllipsis() {
		return beginEllipsis;
	}

	public boolean getEndEllipsis() {
		return endEllipsis;
	}

	public List<Integer> getPageList() {
		return pageList;
	}

}
