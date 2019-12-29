package cn.market.dao;

import cn.market.util.PageHelper;
import cn.market.util.ReturnGood;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReturnRecordDao {

    //增加退货记录
    @Insert("insert into returnrecord(goodid,goodname,returnquantity,supplier,returndate,saleman) values( #{good.goodid},#{good.salemanid},#{good.goodname},#{good.returnquantity},#{good.supplier},#{returndate},#{good.saleman})")
    void InsertReturnRecord(@Param("good")ReturnGood good,@Param("returndate") String returndate);

    //删除退货记录
    @Delete("delete from returnrecord where goodid=#{goodid}")
    void delReturn(@Param("goodid") String goodid);

    //获取退货信息
    @Select("select * from returnrecord limit #{page.start},#{page.end}")
    List<ReturnGood> selectAllReturn(@Param("page") PageHelper page);
    //获取退货总数
    @Select("select count(*) from returnrecord")
    Integer selectAllReturnGoodCount();
}
