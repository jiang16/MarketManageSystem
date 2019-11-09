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
	//ɾ���˻���¼
	@Delete("delete from returnrecord where goodid=#{goodid}")
	void delReturn(@Param("goodid") String goodid);
	
	//��ȡ�˻���Ϣ
	@Select("select * from returnrecord limit #{page.start},#{page.end}")
	List<ReturnGood> selectAllReturn(@Param("page")PageHelper page);
	//��ȡ�˻�����
	@Select("select count(*) from returnrecord")
	Integer selectAllReturnGoodCount();
	
	//������Ʒ�� ɾ����Ʒ
	 @Delete("delete from goods where goodid = #{goodid}")
	 void delReturnGood(@Param("goodid") String goodid);
	
	//�˻���¼
	@Insert("insert into returnrecord values(#{good.goodid},#{good.goodname},#{good.returnquantity},#{good.supplier},#{returndate},#{good.saleman},#{good.salemanid})")
	void InsertReturnRecord(@Param("good")ReturnGood good,String returndate);
	
	//�������ڲ��Ҷ���
	@Select("select * from salerecord where saledate between #{order.starttime} and #{order.endtime} limit #{page.start},#{page.end}")
	List<Order> selectOrderByDate(@Param("page")PageHelper page,SearchOrder order);
	
	@Select("select count(*) from salerecord where saledate between #{order.starttime} and #{order.endtime}")
	Integer selectOrderCountByDate(@Param("order")SearchOrder order);
	
	//��ȡȫ������
	//��ҳ ��0��ʼ ������ ����ǰҳ-1��*pageset
	@Select("select * from salerecord limit #{page.start},#{page.end}")
	List<Order> selectOrderByPage(@Param("page")PageHelper page);

	//��ȡ��������
	@Select("select count(*) from salerecord")
	Integer  getAllOrder();
	
	//�����û�
	@Update("update user set password=#{user.password},role=#{user.role} where userid=#{user.userid}")
	void UpdateUser(@Param("user")User user);
	
	//ɾ���û�
	@Delete("delete from user where userid = #{user.userid}")
	void delUser(@Param("user")User user);
	
	
	//��ȡ�û���Ϣ ������Ա��
	@Select("select * from user where role!='����Ա'")
     List<User> getAllUserInfo();
	
	//��ȡ�û���
	 @Select("select count(*) from user where role!='����Ա'")
	 Integer getAllUserCount();
	
	

	//��ȡ�����û���Ϣ
	@Select("select * from user")
	List<User> getAllUser();
	/*�����û�*/
	
	@Insert("insert into user values(#{user.userid},#{user.username},#{user.password},#{user.role},null,null,null,#{user.birthdate},#{user.sex})")
	void InsertUser(@Param("user")User user);
	
	//������Ʒid��ѯ��Ӧ����Ϣ
	@Select("select * from goods where goodid = #{goodid}")
	Goods selectSupplierInfo(@Param("goodid")String goodid);

	//������¼�������ݿ�
	@Insert("insert into purchaserecord values(#{good.goodid},#{good.goodname},#{good.purchasenum},#{purchasedate},#{goods.supplier},#{goods.supplierphone},#{goods.supplieraddress})")
	void InsertGoodStock(@Param("good")AddGood good,Goods goods,String purchasedate);
	
	//ͳ�������۶�
	@Select("select saledate,sum(salenum*price) as 'countprice' from salerecord group by saledate")
    List<CountPrice> getAllPrice();
	
	//ͳ����Ʒ������
	@Select("select goodname,sum(salenum) as 'goodquantity' from salerecord group by goodname")
    List<GoodQuantity> getGoodQuantity();
	
	//�����ݲ��뵽���ۼ�¼���ݿ�
	@Insert("insert into salerecord values(#{good.goodid},#{good.goodname},#{good.buy_num},#{price},#{saledate},#{man.userid},#{man.username})")
	void InsertGoodRecord(@Param("good")Goods good,String price,String saledate,User man);

	
	//������Ʒ�ۼۺͻ�Ա��
	@Update("update goods set price=#{goods.newprice},memberprice=#{goods.newmemberprice} where goodname = #{goods.goodname}")
	void updateGoodPrice(@Param("goods") UpdateGood goods);
	
	
	//������Ʒ�������
	@Update("update goods set stock=#{stock} where goodname = #{goodname}")
	void updateGood(@Param("stock")String stock,String goodname);
	
	//ɾ����Ʒ���һ������
	@Delete("delete from goods where goodname=#{goodname}")
	void deleteGood(@Param("goodname") String goodname);
	
	
	//ģ����ѯ
	@Select("select * from goods where goodname like concat('%',#{gname},'%') limit #{page.start},#{page.end}")
	List<Goods> selectVugeGood(@Param("page")PageHelper page,String gname);
	
	@Select("select count(*) from goods where goodname like  concat('%',#{gname},'%')")
	Integer selectGoodByVague(@Param("gname")String gname);
	
	//������Ʒ
	@Insert("insert into goods values(#{goods.goodid},#{goods.goodname},#{goods.stock},#{goods.price},#{goods.memberprice},#{goods.category},#{goods.supplier},#{goods.supplierphone},#{goods.supplieraddress})")
	void insertGoods(@Param("goods")Goods goods);
	
	//ͨ���û�����ȡ�û�
	@Select("select * from user where username=#{username}")
	User login(@Param("username")String username);
	
	//�û���¼�ɹ�ʱ������¼ʱ��������ݿ�
	@Update("update user set logintime=#{user.logintime},count=count+1 where username=#{user.username}")
	void updateTime(@Param("user")User user);
	
	//�û��˳�ʱ,���˳�ʱ��������ݿ�
	@Update("update user set exittime=#{user.exittime} where userid=#{user.userid}")
	void updateExitTime(@Param("user")User user);
	
	//��ȡȫ����Ʒ
	//��ҳ ��0��ʼ ������ ����ǰҳ-1��*pageset
	@Select("select * from goods limit #{page.start},#{page.end}")
	List<Goods> selectGoodByPage(@Param("page")PageHelper page);

	//��ȡȫ����Ʒ��Ϣ
	@Select("select count(*) from goods")
	Integer  getAllGoods();
	
	//��ȡȫ����Ʒ ����ҳ
	@Select("select * from goods")
	List<Goods> getGoodsData();
	//��ȡ��׼��Ʒ��Ϣ
	//��ҳ
	@Select("select * from goods where category='��׼' limit #{page.start},#{page.end} ")
	List<Goods> selectStandardGoodByPage(@Param("page")PageHelper page);
	
	//��ȡ��׼��Ʒ
	@Select("select count(*) from goods where category='��׼'")
	Integer getStandardGoods();
	
	
	//��ȡ������Ʒ
	//��ҳ
	@Select("select * from goods where category='����' limit #{page.start},#{page.end} ")
	List<Goods> selectWeightGoodByPage(@Param("page")PageHelper page);
	//��ȡ������Ʒ
	@Select("select count(*) from goods where category='����'")
	Integer getWeightGoods();
}
