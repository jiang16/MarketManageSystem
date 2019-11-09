package cn.market.util;

import java.util.List;

import cn.market.bean.Goods;
import cn.market.bean.User;

public class SelGood {
	private PageHelper pagination;
	private List<Goods> content;
	private User saleman;
	private String pradio;
	public PageHelper getPagination() {
		return pagination;
	}
	public void setPagination(PageHelper pagination) {
		this.pagination = pagination;
	}
	public List<Goods> getContent() {
		return content;
	}
	public void setContent(List<Goods> content) {
		this.content = content;
	}

	public User getSaleman() {
		return saleman;
	}
	public void setSaleman(User saleman) {
		this.saleman = saleman;
	}
	public String getPradio() {
		return pradio;
	}
	public void setPradio(String pradio) {
		this.pradio = pradio;
	}

}
