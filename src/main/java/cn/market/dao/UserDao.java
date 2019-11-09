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
	//删除退货记录
	@Delete("delete from returnrecord where goodid=#{goodid}")
	void delReturn(@Param("goodid") String goodid);
	
	//获取退货信息
	@Select("select * from returnrecord limit #{page.start},#{page.end}")
	List<ReturnGood> selectAllReturn(@Param("page")PageHelper page);
	//获取退货总数
	@Select("select count(*) from returnrecord")
	Integer selectAllReturnGoodCount();
	
	//根据商品号 删除商品
	 @Delete("delete from goods where goodid = #{goodid}")
	 void delReturnGood(@Param("goodid") String goodid);
	
	//退货记录
	@Insert("insert into returnrecord values(#{good.goodid},#{good.goodname},#{good.returnquantity},#{good.supplier},#{returndate},#{good.saleman},#{good.salemanid})")
	void InsertReturnRecord(@Param("good")ReturnGood good,String returndate);
	
	//按照日期查找订单
	@Select("select * from salerecord where saledate between #{order.starttime} and #{order.endtime} limit #{page.start},#{page.end}")
	List<Order> selectOrderByDate(@Param("page")PageHelper page,SearchOrder order);
	
	@Select("select count(*) from salerecord where saledate between #{order.starttime} and #{order.endtime}")
	Integer selectOrderCountByDate(@Param("order")SearchOrder order);
	
	//获取全部订单
	//分页 从0开始 查两条 （当前页-1）*pageset
	@Select("select * from salerecord limit #{page.start},#{page.end}")
	List<Order> selectOrderByPage(@Param("page")PageHelper page);

	//获取订单数量
	@Select("select count(*) from salerecord")
	Integer  getAllOrder();
	
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
	
	//根据商品id查询供应商信息
	@Select("select * from goods where goodid = #{goodid}")
	Goods selectSupplierInfo(@Param("goodid")String goodid);

	//进货记录存入数据库
	@Insert("insert into purchaserecord values(#{good.goodid},#{good.goodname},#{good.purchasenum},#{purchasedate},#{goods.supplier},#{goods.supplierphone},#{goods.supplieraddress})")
	void InsertGoodStock(@Param("good")AddGood good,Goods goods,String purchasedate);
	
	//统计日销售额
	@Select("select saledate,sum(salenum*price) as 'countprice' from salerecord group by saledate")
    List<CountPrice> getAllPrice();
	
	//统计商品销售量
	@Select("select goodname,sum(salenum) as 'goodquantity' from salerecord group by goodname")
    List<GoodQuantity> getGoodQuantity();
	
	//将数据插入到销售记录数据库
	@Insert("insert into salerecord values(#{good.goodid},#{good.goodname},#{good.buy_num},#{price},#{saledate},#{man.userid},#{man.username})")
	void InsertGoodRecord(@Param("good")Goods good,String price,String saledate,User man);

	
	//更新商品售价和会员价
	@Update("update goods set price=#{goods.newprice},memberprice=#{goods.newmemberprice} where goodname = #{goods.goodname}")
	void updateGoodPrice(@Param("goods") UpdateGood goods);
	
	
	//更新商品库存数量
	@Update("update goods set stock=#{stock} where goodname = #{goodname}")
	void updateGood(@Param("stock")String stock,String goodname);
	
	//删除商品表格一行数据
	@Delete("delete from goods where goodname=#{goodname}")
	void deleteGood(@Param("goodname") String goodname);
	
	
	//模糊查询
	@Select("select * from goods where goodname like concat('%',#{gname},'%') limit #{page.start},#{page.end}")
	List<Goods> selectVugeGood(@Param("page")PageHelper page,String gname);
	
	@Select("select count(*) from goods where goodname like  concat('%',#{gname},'%')")
	Integer selectGoodByVague(@Param("gname")String gname);
	
	//插入商品
	@Insert("insert into goods values(#{goods.goodid},#{goods.goodname},#{goods.stock},#{goods.price},#{goods.memberprice},#{goods.category},#{goods.supplier},#{goods.supplierphone},#{goods.supplieraddress})")
	void insertGoods(@Param("goods")Goods goods);
	
	//通过用户名获取用户
	@Select("select * from user where username=#{username}")
	User login(@Param("username")String username);
	
	//用户登录成功时，将登录时间插入数据库
	@Update("update user set logintime=#{user.logintime},count=count+1 where username=#{user.username}")
	void updateTime(@Param("user")User user);
	
	//用户退出时,将退出时间插入数据库
	@Update("update user set exittime=#{user.exittime} where userid=#{user.userid}")
	void updateExitTime(@Param("user")User user);
	
	//获取全部商品
	//分页 从0开始 查两条 （当前页-1）*pageset
	@Select("select * from goods limit #{page.start},#{page.end}")
	List<Goods> selectGoodByPage(@Param("page")PageHelper page);

	//获取全部商品信息
	@Select("select count(*) from goods")
	Integer  getAllGoods();
	
	//获取全部商品 不分页
	@Select("select * from goods")
	List<Goods> getGoodsData();
	//获取标准商品信息
	//分页
	@Select("select * from goods where category='标准' limit #{page.start},#{page.end} ")
	List<Goods> selectStandardGoodByPage(@Param("page")PageHelper page);
	
	//获取标准商品
	@Select("select count(*) from goods where category='标准'")
	Integer getStandardGoods();
	
	
	//获取称重商品
	//分页
	@Select("select * from goods where category='称重' limit #{page.start},#{page.end} ")
	List<Goods> selectWeightGoodByPage(@Param("page")PageHelper page);
	//获取称重商品
	@Select("select count(*) from goods where category='称重'")
	Integer getWeightGoods();
}
