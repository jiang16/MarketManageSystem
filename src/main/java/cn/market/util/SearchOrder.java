package cn.market.util;

public class SearchOrder {
	private String starttime;
	private String endtime;
	private PageHelper paginationOrder;
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public PageHelper getPaginationOrder() {
		return paginationOrder;
	}
	public void setPaginationOrder(PageHelper paginationOrder) {
		this.paginationOrder = paginationOrder;
	}
}
