package cn.market.util;

public class Result {
	private Integer code;
	private Boolean success;
	private String message;
	private Object data;
	private String[] dataname;
	private String[] goodnum;
	//ÐÂÔö totalCount¡¢totalPage
		private int totalCount;
		private int totalPage;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean b) {
		this.success = b;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
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
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String[] getGoodnum() {
		return goodnum;
	}
	public void setGoodnum(String[] goodnum) {
		this.goodnum = goodnum;
	}
	public String[] getDataname() {
		return dataname;
	}
	public void setDataname(String[] dataname) {
		this.dataname = dataname;
	}
}
