package cn.market.util;

import java.util.Date;

public class Order {
	private Date starttime;
	private Date endtime;
	private PageHelper paginationOrder;
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public PageHelper getPaginationOrder() {
		return paginationOrder;
	}
	public void setPaginationOrder(PageHelper paginationOrder) {
		this.paginationOrder = paginationOrder;
	}
}
