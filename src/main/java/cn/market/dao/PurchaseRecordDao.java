package cn.market.dao;

import cn.market.bean.Goods;
import cn.market.util.AddGood;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PurchaseRecordDao {

    //进货记录存入数据库
    @Insert("insert into purchaserecord values(#{good.goodid},#{good.goodname},#{good.purchasenum},#{purchasedate},#{goods.supplier},#{goods.supplierphone},#{goods.supplieraddress})")
    void InsertGoodStock(@Param("good") AddGood good, @Param("goods") Goods goods, @Param("purchasedate") String purchasedate);
}
