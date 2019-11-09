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
	 * 用户退出
	 */
	public Result exit(User user) {
		
		Result result = new Result();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		 try{
		
			 user.setExittime(df.format(new Date()));
			 dao.updateExitTime(user);
		 }
		 catch(Exception e) {
			// 服务器异常处理
			    System.out.println(e);
				result.setMessage("服务器错误");
				result.setSuccess(false);
				result.setCode(500);
				return result;
		 }
		 result.setCode(2);
		 result.setSuccess(true);
		 return result;
	}
	/*
	 * 获取用户信息
	 */
	public Result login(User user) {
		// 创建返回对象
		
		Result result = new Result();
		User u = null;
		try {
			// 通过用户名查询用户
			u = dao.login(user.getUsername());
			
			
		} catch (Exception e) {
			
			// 服务器异常处理
			result.setMessage("服务器错误");
			result.setSuccess(false);
			result.setCode(500);
			return result;

		}

		if (u != null) {
		
			// 判断密码是否为空 null "" 的情况
			if (StringUtils.isEmpty(user.getPassword())) {
				result.setCode(500);
				result.setSuccess(false);
				result.setMessage("请输入密码");
				return result;
			}

			if (user.getPassword().equals(u.getPassword())) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
				 try{
					 u.setLogintime(df.format(new Date()));
					 dao.updateTime(u);
				 }
				 catch(Exception e) {
					// 服务器异常处理
						result.setMessage("服务器错误");
						result.setSuccess(false);
						result.setCode(500);
						return result;
				 }
				result.setCode(2);
				result.setSuccess(true);
				result.setMessage("登录成功");
				result.setData(u);
				
				return result;
			}
			result.setCode(1);
			result.setMessage("密码错误");
			return result;
		}
		result.setCode(0);
		result.setMessage("用户名不存在");
		return result;

	}
	/*
	 * 获取全部商品
	 */
	public Result getGoods(PageHelper page) {
		 page.setStart((page.getCurrentPage()-1)*page.getPageSize());
		 page.setEnd(page.getPageSize());
		//创建返回对象
		Result result = new Result();
		result.setSuccess(true);
		List<Goods> list = null;
		Integer totalCount = null;
		try {
			//查询分页数据
			list = dao.selectGoodByPage(page);
			//查询数据总数
			totalCount = dao.getAllGoods();
			//为PageHelper对象设置分配记录数，同时自动设置总页数
			page.setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(page.getTotalPage());
			result.setCode(1);
			result.setMessage("查询成功");
			result.setData(list);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("查询失败");
			return result;	
		}
	}
	
	/*
	 * 获取标准商品
	 */
	public Result getStandardGoods(PageHelper page) {
		 page.setStart((page.getCurrentPage()-1)*page.getPageSize());
		 page.setEnd(page.getPageSize());
		//创建返回对象
		Result result = new Result();
		result.setSuccess(true);
		List<Goods> list = null;
		Integer totalCount = null;
		try {
			//查询分页数据
			list = dao.selectStandardGoodByPage(page);
			//查询数据总数
			totalCount = dao.getStandardGoods();
			//为PageHelper对象设置分配记录数，同时自动设置总页数
			page.setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(page.getTotalPage());
			result.setCode(1);
			result.setMessage("查询成功");
			result.setData(list);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("查询失败");
			return result;	
		}
	}
	
	/*
	 * 获取称重商品
	 */
	public Result getWeightGoods(PageHelper page) {
		 page.setStart((page.getCurrentPage()-1)*page.getPageSize());
		 page.setEnd(page.getPageSize());
		//创建返回对象
		Result result = new Result();
		result.setSuccess(true);
		List<Goods> list = null;
		Integer totalCount = null;
		try {
			//查询分页数据
			list = dao.selectWeightGoodByPage(page);
			//查询数据总数
			totalCount = dao.getWeightGoods();
		
			//为PageHelper对象设置分配记录数，同时自动设置总页数
			page.setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(page.getTotalPage());
			result.setCode(1);
			result.setMessage("查询成功");
			result.setData(list);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("查询失败");
			return result;	
		}
	}
	
	/*
	 * 删除一行商品
	 */
	public Result delOneGood(Search search) {
		 search.getPagination().setStart((search.getPagination().getCurrentPage()-1)*search.getPagination().getPageSize());
		 search.getPagination().setEnd(search.getPagination().getPageSize());
		
		    //创建返回对象
			Result result = new Result();
			result.setSuccess(true);
			List<Goods> list = null;
			Integer totalCount = null;
		try {
			dao.deleteGood(search.getContent());
			//查询分页数据
			list = dao.selectGoodByPage(search.getPagination());
			//查询数据总数
			totalCount = dao.getAllGoods();
			//为PageHelper对象设置分配记录数，同时自动设置总页数
			search.getPagination().setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(search.getPagination().getTotalPage());
			result.setCode(1);
			result.setMessage("查询成功");
			result.setData(list);
			return result;
			
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("删除失败");
			return result;	
		}
	
	}
	/*
	 * 批量删除商品
	 */
	public Result  delGoodsByGoodName(DelGood delgood) {
		 delgood.getPagination().setStart((delgood.getPagination().getCurrentPage()-1)*delgood.getPagination().getPageSize());
		 delgood.getPagination().setEnd(delgood.getPagination().getPageSize());
		
		    //创建返回对象
			Result result = new Result();
			result.setSuccess(true);
			List<Goods> list = null;
			Integer totalCount = null;
		try {
			for(Goods good:delgood.getGoodname()) {
				dao.deleteGood(good.getGoodname());
			}
			//查询分页数据
			list = dao.selectGoodByPage(delgood.getPagination());
			//查询数据总数
			totalCount = dao.getAllGoods();
			//为PageHelper对象设置分配记录数，同时自动设置总页数
			delgood.getPagination().setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(delgood.getPagination().getTotalPage());
			result.setCode(1);
			result.setMessage("查询成功");
			result.setData(list);
			return result;
			
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("删除失败");
			return result;	
		}
	
	}
	/*
	 * 更新商品价格
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
			//查询分页数据
			list = dao.selectGoodByPage(good.getPagination());
			//查询数据总数
			totalCount = dao.getAllGoods();
			//为PageHelper对象设置分配记录数，同时自动设置总页数
			good.getPagination().setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(good.getPagination().getTotalPage());
			result.setCode(1);
			result.setMessage("更新成功！");
			result.setData(list);
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("更新失败");
			return result;	
		}
		
		
	
	}
	/*
	 * 减去商品库存量
	 */
	public Result subGoodStock(SelGood selgood) {
	    selgood.getPagination().setStart((selgood.getPagination().getCurrentPage()-1)*selgood.getPagination().getPageSize());
	    selgood.getPagination().setEnd(selgood.getPagination().getPageSize());
	    Result result = new Result();
	    result.setSuccess(true);
	   System.out.println(selgood.getPradio());
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	    try {
	    	for(Goods good:selgood.getContent()) {
	    		if(selgood.getPradio().equals("普通")) {
	    			dao.InsertGoodRecord(good,good.getPrice(),df.format(new Date()),selgood.getSaleman());
	    		}
	    		else {
	    			dao.InsertGoodRecord(good,good.getMemberprice(),df.format(new Date()),selgood.getSaleman());
	    		}
	    		
	    		
	    		//更新库存量
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
	    	//查询分页数据
			list = dao.selectGoodByPage(selgood.getPagination());
			//查询数据总数
			totalCount = dao.getAllGoods();
			//为PageHelper对象设置分配记录数，同时自动设置总页数
			selgood.getPagination().setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(selgood.getPagination().getTotalPage());
			result.setCode(1);
			result.setMessage("更新成功！");
			result.setData(list);
			return result;
	    
	    		
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("服务器错误");
			return result;	
	    }
	    
	
	}
	
	/*
	 * 统计商品销售量
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
			result.setMessage("统计商品销售量成功!");
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("统计失败!");
			return result;
		}
	}
	
	/*
	 * 统计日销售额
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
				result.setMessage("统计日销售额成功!");
				return result;
		}
		catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("统计失败!");
			return result;
		}
		
	}
	
	/*
	 *进货 
	 */
	
	public Result AddStock(AddGood good) {
		Result result =new Result();
		result.setSuccess(true);
		try{
			Goods g = dao.selectSupplierInfo(good.getGoodid());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
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
			result.setMessage("进货成功");
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("进货失败");
			return result;
		}
	}
	/*
	 * 添加人员
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
					result.setMessage("用户号已存在,请重新填写!");
					return result;
				}
			}
			if(flag==1) {
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					dao.InsertUser(user);
					List<User> list = dao.getAllUserInfo();
					//查询数据总数
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
					result.setMessage("添加成功");
					return result;
				}catch(Exception e) {
					e.printStackTrace();
					result.setCode(500);
					result.setSuccess(false);
					result.setMessage("添加失败");
					return result;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("获取用户信息失败!");
			return result;
		}
	return result;
		
	}
	
	/*
	 * 获取所有用户信息
	 */
	@SuppressWarnings("null")
	public Result getAllUserInfo() {
		//创建返回对象
		Result result = new Result();
		result.setSuccess(true);
		List<User> list = null;
		List<NewUser> nlist = new ArrayList<NewUser>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//查询分页数据
			list = dao.getAllUserInfo();
			//查询数据总数
			
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
			
			//为PageHelper对象设置分配记录数，同时自动设置总页数
			result.setCode(1);
			result.setMessage("查询成功");
			result.setData(nlist);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("查询失败");
			return result;	
		}
	}
	/*
	 * 删除用户
	 */
	public Result delUser(User user) {
		//创建返回对象
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
					result.setMessage("删除成功");
					result.setData(nlist);
					return result;
				}catch(Exception e) {
					e.printStackTrace();
					result.setCode(500);
					result.setSuccess(false);
					result.setMessage("删除失败");
					return result;	
				}
	}
	
	/*
	 * 更新用户
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
			result.setMessage("更改成功");
			result.setData(nlist);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("更改失败");
			return result;	
		}
		
	}
	
	/*
	 * 获取全部订单
	 */
	public Result getAllOrder(PageHelper page) {
		 page.setStart((page.getCurrentPage()-1)*page.getPageSize());
		 page.setEnd(page.getPageSize());
		//创建返回对象
		Result result = new Result();
		result.setSuccess(true);
		List<Order> list = null;
		Integer totalCount = null;
		try {
			//查询分页数据
			list = dao.selectOrderByPage(page);
			//查询数据总数
			totalCount = dao.getAllOrder();
			//为PageHelper对象设置分配记录数，同时自动设置总页数
			page.setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(page.getTotalPage());
			result.setCode(1);
			result.setMessage("查询成功");
			result.setData(list);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("查询失败");
			return result;	
		}
	}
	/*
	 * 获取全部商品
	 */
	public Result getGoodInfo(String goodid) {
		 
		//创建返回对象
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
			result.setMessage("信息查询失败");
			return result;
		}
		if(good!=null) {
			result.setCode(1);
			result.setMessage("查询成功");
			result.setData(good);
			return result;
		}
		else {
			
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("输入的商品号不存在,请核对后再输入!");
			return result;	
		}
		
	}
	/*
	 * 退货
	 */
	public Result returnGood(ReturnGood good) {
	     Result result =new Result();
	     result.setSuccess(true);
	     try {
	    	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			 dao.InsertReturnRecord(good, df.format(new Date()));
			 dao.delReturnGood(good.getGoodid());
			 result.setCode(1);
			result.setMessage("退货成功");
			return result;
	     }
	     catch(Exception e) {
	    	 e.printStackTrace();
				result.setCode(500);
				result.setSuccess(false);
				result.setMessage("退货失败");
				return result;
	     }
	}
	/*
	 * 获取全部退货信息
	 */
	public Result getAllReturnGood(PageHelper page) {
		 page.setStart((page.getCurrentPage()-1)*page.getPageSize());
		 page.setEnd(page.getPageSize());
		//创建返回对象
		Result result = new Result();
		result.setSuccess(true);
		List<ReturnGood> list = null;
		Integer totalCount = null;
		try {
			//查询分页数据
			list =dao.selectAllReturn(page);
			//查询数据总数
			totalCount = dao.selectAllReturnGoodCount();
			//为PageHelper对象设置分配记录数，同时自动设置总页数
			page.setTotalCount(totalCount);
			result.setTotalCount(totalCount);
			result.setTotalPage(page.getTotalPage());
			result.setCode(1);
			result.setMessage("查询成功");
			result.setData(list);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("查询失败");
			return result;	
		}
	}
	/*
	 * 删除退货记录
	 */
	public Result delReturnRecord(String goodid) {
		Result result = new Result();
		result.setSuccess(true);
		try {
			dao.delReturn(goodid);
			result.setCode(1);
			result.setMessage("删除成功");
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("删除失败");
			return result;
		}
	}
}
