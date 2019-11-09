package cn.market.util;

public class PageHelper {
	
	private Integer currentPage;//��ǰҳ
	private Integer pageSize;//ÿҳ��ʾ��¼��
	private Object condition;//��ѯ��������
	private Integer totalCount;//�ܼ�¼��
	private Integer totalPage;//��ҳ��
	//�������ݶ���
	private Object data;
	private Integer start;
	private Integer end;
	
	
	//��ҳҪ��  ����������������ҳ����ǰҳ�桢ҳ���С
	public Integer start() {
		return (currentPage-1)*pageSize;
	}
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		
	}
	public Object getCondition() {
		return condition;
	}
	public void setCondition(Object condition) {
		this.condition = condition;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
		setTotalPage((getTotalCount()+getPageSize()-1)/getPageSize());
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}


	
	
	
}
