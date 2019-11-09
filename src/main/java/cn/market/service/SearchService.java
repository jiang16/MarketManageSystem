package cn.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.market.bean.Goods;
import cn.market.bean.Order;
import cn.market.dao.UserDao;
import cn.market.util.Result;
import cn.market.util.Search;
import cn.market.util.SearchOrder;

@Service
@Transactional
public class SearchService {
	
	@Autowired
	private UserDao dao;
	
	public Result selectGood(Search search) {
		 search.getPagination().setStart((search.getPagination().getCurrentPage()-1)*search.getPagination().getPageSize());
		 search.getPagination().setEnd(search.getPagination().getPageSize());
		 
		//�������ض���
			Result result = new Result();
			result.setSuccess(true);
			List<Goods> list = null;
			Integer totalCount = null;
			try {
				//��ѯ��ҳ����
				list = dao.selectVugeGood(search.getPagination(), search.getContent());
				//��ѯ��������
				totalCount = dao.selectGoodByVague(search.getContent());
				//ΪPageHelper�������÷����¼����ͬʱ�Զ�������ҳ��
				search.getPagination().setTotalCount(totalCount);
				result.setTotalCount(totalCount);
				result.setTotalPage(search.getPagination().getTotalPage());
				result.setCode(1);
				result.setMessage("��ѯ�ɹ�");
				result.setData(list);
				return result;
			}catch(Exception e) {
				e.printStackTrace();
				result.setCode(500);
				result.setSuccess(false);
				result.setMessage("��ѯʧ��");
				return result;	
			}
		
		
		
	}
	
	public Result getOrderByDate(SearchOrder order) {
		
		 order.getPaginationOrder().setStart((order.getPaginationOrder().getCurrentPage()-1)*order.getPaginationOrder().getPageSize());
		order.getPaginationOrder().setEnd(order.getPaginationOrder().getPageSize());
		
		
		//�������ض���
		Result result = new Result();
		result.setSuccess(true);
		List<Order> list = null;
		Integer totalCount = null;
		try {
			//��ѯ��ҳ����
			System.out.println(order.getPaginationOrder().getStart());
			System.out.println(order.getPaginationOrder().getEnd());
			list = dao.selectOrderByDate(order.getPaginationOrder(), order);
			//��ѯ��������
			totalCount = dao.selectOrderCountByDate(order);
			//ΪPageHelper�������÷����¼����ͬʱ�Զ�������ҳ��
			order.getPaginationOrder().setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(order.getPaginationOrder().getTotalPage());
			result.setCode(1);
			result.setMessage("��ѯ�ɹ�");
			result.setData(list);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("��ѯʧ��");
			return result;	
		}
	}
}
