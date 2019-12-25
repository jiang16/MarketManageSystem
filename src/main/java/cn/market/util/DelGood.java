package cn.market.util;

import java.util.List;

import cn.market.bean.Goods;

public class DelGood {

	private PageHelper pagination;
	private List<Goods> goodname;
	public PageHelper getPagination() {
		return pagination;
	}
	public void setPagination(PageHelper pagination) {
		this.pagination = pagination;
	}

	public List<Goods> getGoodname() {
		return goodname;
	}
	public void setGoodname(List<Goods> goodname) {
		this.goodname = goodname;
	}
}
