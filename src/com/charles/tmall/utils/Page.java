package com.charles.tmall.utils;

public class Page {

	public Page() {
	}

	public Page(int start, int count) {
		this.start = start;
		this.count = count;
	}

	public boolean isHasPrevious() {
		return start != 0;
	}

	public boolean isHasNext() {
		System.out.println("start:"+start+",last:"+getLast());
		return start < getLast();
	}

	public int getTotalPage() {
		int totalPage = 0;

		totalPage = total % count == 0 ? total / count : total / count + 1;

		return totalPage == 0 ? 1 : totalPage;
	}

	public int getLast() {
		int last = 0;
		if (total % count == 0) {
			last = total - count;
		} else {
			last = total - total % count;
		}
		last = last < 0 ? 0 : last;
		return last;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	int start;
	int count;
	int total;
	String param;
}
