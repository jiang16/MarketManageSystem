package cn.market.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;




import cn.market.bean.*;
import cn.market.util.AddGood;
import cn.market.util.CountPrice;
import cn.market.util.GoodQuantity;
import cn.market.util.PageHelper;
import cn.market.util.ReturnGood;
import cn.market.util.SearchOrder;
import cn.market.util.UpdateGood;

@Mapper
public interface UserDao {

	
	//更新用户
	@Update("update user set password=#{user.password},role=#{user.role} where userid=#{user.userid}")
	void UpdateUser(@Param("user")User user);
	
	//删除用户
	@Delete("delete from user where userid = #{user.userid}")
	void delUser(@Param("user")User user);
	
	
	//获取用户信息 除管理员外
	@Select("select * from user where role!='管理员'")
     List<User> getAllUserInfo();
	
	//获取用户数
	 @Select("select count(*) from user where role!='管理员'")
	 Integer getAllUserCount();
	
	

	//获取所有用户信息
	@Select("select * from user")
	List<User> getAllUser();
	/*新增用户*/
	
	@Insert("insert into user values(#{user.userid},#{user.username},#{user.password},#{user.role},null,null,null,#{user.birthdate},#{user.sex})")
	void InsertUser(@Param("user")User user);

	
	//通过用户名获取用户
	@Select("select * from user where username=#{username}")
	User login(@Param("username")String username);
	
	//用户登录成功时，将登录时间插入数据库
	@Update("update user set logintime=#{user.logintime},count=count+1 where username=#{user.username}")
	void updateTime(@Param("user")User user);
	
	//用户退出时,将退出时间插入数据库
	@Update("update user set exittime=#{user.exittime} where userid=#{user.userid}")
	void updateExitTime(@Param("user")User user);

}
