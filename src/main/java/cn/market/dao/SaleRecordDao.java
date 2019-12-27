package cn.market.dao;

import cn.market.bean.Goods;
import cn.market.bean.Order;
import cn.market.bean.User;
import cn.market.util.CountPrice;
import cn.market.util.GoodQuantity;
import cn.market.util.PageHelper;
import cn.market.util.SearchOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SaleRecordDao {
    //按照日期查找订单
    @Select("select * from salerecord where saledate between #{order.starttime} and #{order.endtime} limit #{page.start},#{page.end}")
    List<Order> selectOrderByDate(@Param("page") PageHelper page, @Param("order") SearchOrder order);

    @Select("select count(*) from salerecord where saledate between #{order.starttime} and #{order.endtime}")
    Integer selectOrderCountByDate(@Param("order")SearchOrder order);

    //获取全部订单
    //分页 从0开始 查两条 （当前页-1）*pageset
    @Select("select * from salerecord limit #{page.start},#{page.end}")
    List<Order> selectOrderByPage(@Param("page")PageHelper page);

    //获取订单数量
    @Select("select count(*) from salerecord")
    Integer  getAllOrder();

    //统计日销售额
    @Select("select saledate,sum(salenum*price) as 'countprice' from salerecord group by saledate")
    List<CountPrice> getAllPrice();

    //统计商品销售量
    @Select("select goodname,sum(salenum) as 'goodquantity' from salerecord group by goodname")
    List<GoodQuantity> getGoodQuantity();

    //将数据插入到销售记录数据库
    @Insert("insert into salerecord values(#{good.goodid},#{man.userid},#{good.goodname},#{good.buy_num},#{price},#{saledate},#{man.username})")
    void InsertGoodRecord(@Param("good") Goods good, @Param("price") String price, @Param("saledate") String saledate, @Param("man") User man);
}
