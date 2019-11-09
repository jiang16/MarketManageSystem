package cn.market.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;


import cn.market.bean.Goods;
import cn.market.bean.NewUser;
import cn.market.bean.Order;
import cn.market.bean.User;
import cn.market.dao.UserDao;
import cn.market.util.AddGood;
import cn.market.util.CountPrice;
import cn.market.util.DelGood;
import cn.market.util.GoodQuantity;
import cn.market.util.PageHelper;
import cn.market.util.Result;
import cn.market.util.ReturnGood;
import cn.market.util.Search;
import cn.market.util.SelGood;
import cn.market.util.UpdateGood;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserDao dao;
	/*
	 * �û��˳�
	 */
	public Result exit(User user) {
		
		Result result = new Result();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//�������ڸ�ʽ
		 try{
		
			 user.setExittime(df.format(new Date()));
			 dao.updateExitTime(user);
		 }
		 catch(Exception e) {
			// �������쳣����
			    System.out.println(e);
				result.setMessage("����������");
				result.setSuccess(false);
				result.setCode(500);
				return result;
		 }
		 result.setCode(2);
		 result.setSuccess(true);
		 return result;
	}
	/*
	 * ��ȡ�û���Ϣ
	 */
	public Result login(User user) {
		// �������ض���
		
		Result result = new Result();
		User u = null;
		try {
			// ͨ���û�����ѯ�û�
			u = dao.login(user.getUsername());
			
			
		} catch (Exception e) {
			
			// �������쳣����
			result.setMessage("����������");
			result.setSuccess(false);
			result.setCode(500);
			return result;

		}

		if (u != null) {
		
			// �ж������Ƿ�Ϊ�� null "" �����
			if (StringUtils.isEmpty(user.getPassword())) {
				result.setCode(500);
				result.setSuccess(false);
				result.setMessage("����������");
				return result;
			}

			if (user.getPassword().equals(u.getPassword())) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//�������ڸ�ʽ
				 try{
					 u.setLogintime(df.format(new Date()));
					 dao.updateTime(u);
				 }
				 catch(Exception e) {
					// �������쳣����
						result.setMessage("����������");
						result.setSuccess(false);
						result.setCode(500);
						return result;
				 }
				result.setCode(2);
				result.setSuccess(true);
				result.setMessage("��¼�ɹ�");
				result.setData(u);
				
				return result;
			}
			result.setCode(1);
			result.setMessage("�������");
			return result;
		}
		result.setCode(0);
		result.setMessage("�û���������");
		return result;

	}
	/*
	 * ��ȡȫ����Ʒ
	 */
	public Result getGoods(PageHelper page) {
		 page.setStart((page.getCurrentPage()-1)*page.getPageSize());
		 page.setEnd(page.getPageSize());
		//�������ض���
		Result result = new Result();
		result.setSuccess(true);
		List<Goods> list = null;
		Integer totalCount = null;
		try {
			//��ѯ��ҳ����
			list = dao.selectGoodByPage(page);
			//��ѯ��������
			totalCount = dao.getAllGoods();
			//ΪPageHelper�������÷����¼����ͬʱ�Զ�������ҳ��
			page.setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(page.getTotalPage());
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
	
	/*
	 * ��ȡ��׼��Ʒ
	 */
	public Result getStandardGoods(PageHelper page) {
		 page.setStart((page.getCurrentPage()-1)*page.getPageSize());
		 page.setEnd(page.getPageSize());
		//�������ض���
		Result result = new Result();
		result.setSuccess(true);
		List<Goods> list = null;
		Integer totalCount = null;
		try {
			//��ѯ��ҳ����
			list = dao.selectStandardGoodByPage(page);
			//��ѯ��������
			totalCount = dao.getStandardGoods();
			//ΪPageHelper�������÷����¼����ͬʱ�Զ�������ҳ��
			page.setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(page.getTotalPage());
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
	
	/*
	 * ��ȡ������Ʒ
	 */
	public Result getWeightGoods(PageHelper page) {
		 page.setStart((page.getCurrentPage()-1)*page.getPageSize());
		 page.setEnd(page.getPageSize());
		//�������ض���
		Result result = new Result();
		result.setSuccess(true);
		List<Goods> list = null;
		Integer totalCount = null;
		try {
			//��ѯ��ҳ����
			list = dao.selectWeightGoodByPage(page);
			//��ѯ��������
			totalCount = dao.getWeightGoods();
		
			//ΪPageHelper�������÷����¼����ͬʱ�Զ�������ҳ��
			page.setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(page.getTotalPage());
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
	
	/*
	 * ɾ��һ����Ʒ
	 */
	public Result delOneGood(Search search) {
		 search.getPagination().setStart((search.getPagination().getCurrentPage()-1)*search.getPagination().getPageSize());
		 search.getPagination().setEnd(search.getPagination().getPageSize());
		
		    //�������ض���
			Result result = new Result();
			result.setSuccess(true);
			List<Goods> list = null;
			Integer totalCount = null;
		try {
			dao.deleteGood(search.getContent());
			//��ѯ��ҳ����
			list = dao.selectGoodByPage(search.getPagination());
			//��ѯ��������
			totalCount = dao.getAllGoods();
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
			result.setMessage("ɾ��ʧ��");
			return result;	
		}
	
	}
	/*
	 * ����ɾ����Ʒ
	 */
	public Result  delGoodsByGoodName(DelGood delgood) {
		 delgood.getPagination().setStart((delgood.getPagination().getCurrentPage()-1)*delgood.getPagination().getPageSize());
		 delgood.getPagination().setEnd(delgood.getPagination().getPageSize());
		
		    //�������ض���
			Result result = new Result();
			result.setSuccess(true);
			List<Goods> list = null;
			Integer totalCount = null;
		try {
			for(Goods good:delgood.getGoodname()) {
				dao.deleteGood(good.getGoodname());
			}
			//��ѯ��ҳ����
			list = dao.selectGoodByPage(delgood.getPagination());
			//��ѯ��������
			totalCount = dao.getAllGoods();
			//ΪPageHelper�������÷����¼����ͬʱ�Զ�������ҳ��
			delgood.getPagination().setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(delgood.getPagination().getTotalPage());
			result.setCode(1);
			result.setMessage("��ѯ�ɹ�");
			result.setData(list);
			return result;
			
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("ɾ��ʧ��");
			return result;	
		}
	
	}
	/*
	 * ������Ʒ�۸�
	 */
	public Result updateGoodPrice(UpdateGood good) {
		good.getPagination().setStart((good.getPagination().getCurrentPage()-1)*good.getPagination().getPageSize());
		 good.getPagination().setEnd(good.getPagination().getPageSize());
		
		Result result = new Result();
		result.setSuccess(true);
		List<Goods> list =null;
		Integer totalCount = null;
		try {
			dao.updateGoodPrice(good);
			//��ѯ��ҳ����
			list = dao.selectGoodByPage(good.getPagination());
			//��ѯ��������
			totalCount = dao.getAllGoods();
			//ΪPageHelper�������÷����¼����ͬʱ�Զ�������ҳ��
			good.getPagination().setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(good.getPagination().getTotalPage());
			result.setCode(1);
			result.setMessage("���³ɹ���");
			result.setData(list);
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("����ʧ��");
			return result;	
		}
		
		
	
	}
	/*
	 * ��ȥ��Ʒ�����
	 */
	public Result subGoodStock(SelGood selgood) {
	    selgood.getPagination().setStart((selgood.getPagination().getCurrentPage()-1)*selgood.getPagination().getPageSize());
	    selgood.getPagination().setEnd(selgood.getPagination().getPageSize());
	    Result result = new Result();
	    result.setSuccess(true);
	   System.out.println(selgood.getPradio());
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
	    try {
	    	for(Goods good:selgood.getContent()) {
	    		if(selgood.getPradio().equals("��ͨ")) {
	    			dao.InsertGoodRecord(good,good.getPrice(),df.format(new Date()),selgood.getSaleman());
	    		}
	    		else {
	    			dao.InsertGoodRecord(good,good.getMemberprice(),df.format(new Date()),selgood.getSaleman());
	    		}
	    		
	    		
	    		//���¿����
	    		String flagStock= null;
	    		for(int j=0;j<good.getStock().length();j++) {
        			if((good.getStock().charAt(j)<'0'||good.getStock().charAt(j)>'9')&&(good.getStock().charAt(j)!='.')) {
        				flagStock= String.valueOf(good.getStock().charAt(j));
        				break;
        			}
        		}
	    		String[] old = good.getStock().split(flagStock);
	    		String count = String.valueOf(Integer.parseInt(old[0])-Integer.parseInt(good.getBuy_num()));
        		String newstock = count+flagStock;
        	    System.out.println(newstock);
        		dao.updateGood(newstock, good.getGoodname());
	    		
	    	}
	    	List<Goods> list = null;
	    	Integer totalCount = null;
	    	//��ѯ��ҳ����
			list = dao.selectGoodByPage(selgood.getPagination());
			//��ѯ��������
			totalCount = dao.getAllGoods();
			//ΪPageHelper�������÷����¼����ͬʱ�Զ�������ҳ��
			selgood.getPagination().setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(selgood.getPagination().getTotalPage());
			result.setCode(1);
			result.setMessage("���³ɹ���");
			result.setData(list);
			return result;
	    
	    		
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("����������");
			return result;	
	    }
	    
	
	}
	
	/*
	 * ͳ����Ʒ������
	 * 
	 */
	public Result CountGoodQuantity() {
	    
		Result result =new Result();
		result.setSuccess(true);
		List<GoodQuantity> list = null;
		
 		try {
			list=dao.getGoodQuantity();
			String[] dataname = new String[list.size()];
			String[] goodnum = new String[list.size()];
			for(int i=0;i<list.size();i++) {
				dataname[i]=list.get(i).getGoodname();
				goodnum[i]=list.get(i).getGoodquantity();
				
			}
		   result.setDataname(dataname);
		   result.setGoodnum(goodnum);
		   result.setData(list);
			result.setCode(1);
			result.setMessage("ͳ����Ʒ�������ɹ�!");
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("ͳ��ʧ��!");
			return result;
		}
	}
	
	/*
	 * ͳ�������۶�
	 */
	public Result GetDayPrice() {
		Result result = new Result();
		result.setSuccess(true);
		List<CountPrice> list = null;
		try {
			list=dao.getAllPrice();
			for(int i=0;i<list.size();i++) {
			String price=String.format("%.2f",Double.parseDouble(list.get(i).getCountprice()));
		    list.get(i).setCountprice(price);
		    
		}
			 result.setData(list);
				result.setCode(1);
				result.setMessage("ͳ�������۶�ɹ�!");
				return result;
		}
		catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("ͳ��ʧ��!");
			return result;
		}
		
	}
	
	/*
	 *���� 
	 */
	
	public Result AddStock(AddGood good) {
		Result result =new Result();
		result.setSuccess(true);
		try{
			Goods g = dao.selectSupplierInfo(good.getGoodid());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
			dao.InsertGoodStock(good,g,df.format(new Date()));
			List<Goods> list =dao.getGoodsData();
			String flagStock =null;
			int flag=1;
			for(Goods i:list) {
				if(i.getGoodid().equals(good.getGoodid())) {
					flag=0;
					for(int j=0;j<i.getStock().length();j++) {
	           			if((i.getStock().charAt(j)<'0'||i.getStock().charAt(j)>'9')&&(i.getStock().charAt(j)!='.')) {
	           				flagStock= String.valueOf(i.getStock().charAt(j));
	           				break;
	           			}
	           		}
					String[] old = i.getStock().split(flagStock);
            		String count = String.valueOf(Integer.parseInt(old[0])+Integer.parseInt(good.getPurchasenum()));
            		String newstock = count+flagStock;
                    System.out.println(newstock);
            		i.setStock(newstock);
            		dao.updateGood(newstock,i.getGoodname());
            		break;
				}
			}
			result.setCode(1);
			result.setMessage("�����ɹ�");
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("����ʧ��");
			return result;
		}
	}
	/*
	 * �����Ա
	 */
	public Result addUser(User user) {
		Result result = new Result();
		result.setSuccess(true);
		int flag =1;
		try{
			List<NewUser> nlist = new ArrayList<NewUser>();
			List<User> uu = dao.getAllUser();
			for(User i:uu) {
				if(i.getUserid().equals(user.getUserid())) {
					flag = 0;
					result.setCode(505);
					result.setSuccess(false);
					result.setMessage("�û����Ѵ���,��������д!");
					return result;
				}
			}
			if(flag==1) {
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					dao.InsertUser(user);
					List<User> list = dao.getAllUserInfo();
					//��ѯ��������
					for(User u:list) {
						String strDate = format.format(u.getBirthdate());
						NewUser nuser = new NewUser();
						nuser.setUserid(u.getUserid());
						nuser.setUsername(u.getUsername());
						nuser.setBirthdate(strDate);
						nuser.setCount(u.getCount());
						nuser.setExittime(u.getExittime());
						nuser.setLogintime(u.getLogintime());
						nuser.setPassword(u.getPassword());
						nuser.setRole(u.getRole());
						nuser.setSex(u.getSex());
						nlist.add(nuser);
					
					}
					
					
					result.setCode(1);
					result.setData(nlist);
					result.setMessage("��ӳɹ�");
					return result;
				}catch(Exception e) {
					e.printStackTrace();
					result.setCode(500);
					result.setSuccess(false);
					result.setMessage("���ʧ��");
					return result;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("��ȡ�û���Ϣʧ��!");
			return result;
		}
	return result;
		
	}
	
	/*
	 * ��ȡ�����û���Ϣ
	 */
	@SuppressWarnings("null")
	public Result getAllUserInfo() {
		//�������ض���
		Result result = new Result();
		result.setSuccess(true);
		List<User> list = null;
		List<NewUser> nlist = new ArrayList<NewUser>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//��ѯ��ҳ����
			list = dao.getAllUserInfo();
			//��ѯ��������
			
			for(User u:list) {
				String strDate = format.format(u.getBirthdate());
				NewUser nuser = new NewUser();
				nuser.setUserid(u.getUserid());
				nuser.setUsername(u.getUsername());
				nuser.setBirthdate(strDate);
				nuser.setCount(u.getCount());
				nuser.setExittime(u.getExittime());
				nuser.setLogintime(u.getLogintime());
				nuser.setPassword(u.getPassword());
				nuser.setRole(u.getRole());
				nuser.setSex(u.getSex());
				nlist.add(nuser);
			
			}
			
			//ΪPageHelper�������÷����¼����ͬʱ�Զ�������ҳ��
			result.setCode(1);
			result.setMessage("��ѯ�ɹ�");
			result.setData(nlist);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("��ѯʧ��");
			return result;	
		}
	}
	/*
	 * ɾ���û�
	 */
	public Result delUser(User user) {
		//�������ض���
				Result result = new Result();
				result.setSuccess(true);
				List<User> list = null;
				List<NewUser> nlist = new ArrayList<NewUser>();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					dao.delUser(user);
					list = dao.getAllUserInfo();
					
					for(User u:list) {
						String strDate = format.format(u.getBirthdate());
						NewUser nuser = new NewUser();
						nuser.setUserid(u.getUserid());
						nuser.setUsername(u.getUsername());
						nuser.setBirthdate(strDate);
						nuser.setCount(u.getCount());
						nuser.setExittime(u.getExittime());
						nuser.setLogintime(u.getLogintime());
						nuser.setPassword(u.getPassword());
						nuser.setRole(u.getRole());
						nuser.setSex(u.getSex());
						nlist.add(nuser);
					
					}
					
					result.setCode(1);
					result.setMessage("ɾ���ɹ�");
					result.setData(nlist);
					return result;
				}catch(Exception e) {
					e.printStackTrace();
					result.setCode(500);
					result.setSuccess(false);
					result.setMessage("ɾ��ʧ��");
					return result;	
				}
	}
	
	/*
	 * �����û�
	 */
	public Result updateOneUser(User user) {
		Result result = new Result();
		result.setSuccess(true);
		List<User> list = null;
		List<NewUser> nlist = new ArrayList<NewUser>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dao.UpdateUser(user);
			list = dao.getAllUserInfo();
			
			for(User u:list) {
				String strDate = format.format(u.getBirthdate());
				NewUser nuser = new NewUser();
				nuser.setUserid(u.getUserid());
				nuser.setUsername(u.getUsername());
				nuser.setBirthdate(strDate);
				nuser.setCount(u.getCount());
				nuser.setExittime(u.getExittime());
				nuser.setLogintime(u.getLogintime());
				nuser.setPassword(u.getPassword());
				nuser.setRole(u.getRole());
				nuser.setSex(u.getSex());
				nlist.add(nuser);
			
			}
			result.setCode(1);
			result.setMessage("���ĳɹ�");
			result.setData(nlist);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("����ʧ��");
			return result;	
		}
		
	}
	
	/*
	 * ��ȡȫ������
	 */
	public Result getAllOrder(PageHelper page) {
		 page.setStart((page.getCurrentPage()-1)*page.getPageSize());
		 page.setEnd(page.getPageSize());
		//�������ض���
		Result result = new Result();
		result.setSuccess(true);
		List<Order> list = null;
		Integer totalCount = null;
		try {
			//��ѯ��ҳ����
			list = dao.selectOrderByPage(page);
			//��ѯ��������
			totalCount = dao.getAllOrder();
			//ΪPageHelper�������÷����¼����ͬʱ�Զ�������ҳ��
			page.setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(page.getTotalPage());
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
	/*
	 * ��ȡȫ����Ʒ
	 */
	public Result getGoodInfo(String goodid) {
		 
		//�������ض���
		Result result = new Result();
		result.setSuccess(true);
		List<Goods> list = null;
		Goods good = null;
		try {
		
			good = dao.selectSupplierInfo(goodid);
		
		}
		catch(Exception e) {
		
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("��Ϣ��ѯʧ��");
			return result;
		}
		if(good!=null) {
			result.setCode(1);
			result.setMessage("��ѯ�ɹ�");
			result.setData(good);
			return result;
		}
		else {
			
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("�������Ʒ�Ų�����,��˶Ժ�������!");
			return result;	
		}
		
	}
	/*
	 * �˻�
	 */
	public Result returnGood(ReturnGood good) {
	     Result result =new Result();
	     result.setSuccess(true);
	     try {
	    	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
			 dao.InsertReturnRecord(good, df.format(new Date()));
			 dao.delReturnGood(good.getGoodid());
			 result.setCode(1);
			result.setMessage("�˻��ɹ�");
			return result;
	     }
	     catch(Exception e) {
	    	 e.printStackTrace();
				result.setCode(500);
				result.setSuccess(false);
				result.setMessage("�˻�ʧ��");
				return result;
	     }
	}
	/*
	 * ��ȡȫ���˻���Ϣ
	 */
	public Result getAllReturnGood(PageHelper page) {
		 page.setStart((page.getCurrentPage()-1)*page.getPageSize());
		 page.setEnd(page.getPageSize());
		//�������ض���
		Result result = new Result();
		result.setSuccess(true);
		List<ReturnGood> list = null;
		Integer totalCount = null;
		try {
			//��ѯ��ҳ����
			list =dao.selectAllReturn(page);
			//��ѯ��������
			totalCount = dao.selectAllReturnGoodCount();
			//ΪPageHelper�������÷����¼����ͬʱ�Զ�������ҳ��
			page.setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(page.getTotalPage());
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
	/*
	 * ɾ���˻���¼
	 */
	public Result delReturnRecord(String goodid) {
		Result result = new Result();
		result.setSuccess(true);
		try {
			dao.delReturn(goodid);
			result.setCode(1);
			result.setMessage("ɾ���ɹ�");
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("ɾ��ʧ��");
			return result;
		}
	}
}
