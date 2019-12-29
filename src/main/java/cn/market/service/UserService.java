package cn.market.service;

import cn.market.bean.User;
import cn.market.dao.UserDao;
import cn.market.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.druid.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * 用户登录
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
			result.setMessage(e.getMessage());
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
						result.setMessage(e.getMessage());
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
	 * 添加人员
	 */
	public Result addUser(User user) {
		Result result = new Result();
		result.setSuccess(true);
		int flag =1;
		try{
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
					result.setCode(1);
					result.setData(list);
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
		List<User> list = new ArrayList<User>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//查询分页数据
			list = dao.getAllUserInfo();
			//为PageHelper对象设置分配记录数，同时自动设置总页数
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
	 * 删除用户
	 */
	public Result delUser(User user) {
		//创建返回对象
				Result result = new Result();
				result.setSuccess(true);
				List<User> list = null;
//				List<NewUser> nlist = new ArrayList<NewUser>();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					dao.delUser(user);
					list = dao.getAllUserInfo();
					result.setCode(1);
					result.setMessage("删除成功");
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
	 * 更新用户
	 */
	public Result updateOneUser(User user) {
		Result result = new Result();
		result.setSuccess(true);
		List<User> list = null;
//		List<NewUser> nlist = new ArrayList<NewUser>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dao.UpdateUser(user);
			list = dao.getAllUserInfo();
			result.setCode(1);
			result.setMessage("更改成功");
			result.setData(list);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setSuccess(false);
			result.setMessage("更改失败");
			return result;
		}

	}
}
