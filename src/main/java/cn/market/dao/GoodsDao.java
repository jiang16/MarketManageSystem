package cn.market.dao;

import cn.market.bean.Goods;
import cn.market.util.PageHelper;
import cn.market.util.ReturnGood;
import cn.market.util.UpdateGood;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsDao {


    //根据商品号 删除商品
    @Delete("delete from goods where goodid = #{goodid}")
    void delGood(@Param("goodid") String goodid);

    //根据商品id查询供应商信息
    @Select("select * from goods where goodid = #{goodid}")
    Goods selectSupplierInfo(@Param("goodid")String goodid);


    //更新商品售价和会员价
    @Update("update goods set price=#{goods.newprice},memberprice=#{goods.newmemberprice} where goodname = #{goods.goodname}")
    void updateGoodPrice(@Param("goods") UpdateGood goods);



    //更新商品库存数量
    @Update("update goods set stock=#{stock} where goodname = #{goodname}")
    void updateGood(@Param("stock")String stock,@Param("goodname") String goodname);

    //删除商品表格一行数据
    @Delete("delete from goods where goodname=#{goodname}")
    void deleteGood(@Param("goodname") String goodname);


    //模糊查询
    @Select("select * from goods where goodname like concat('%',#{gname},'%') limit #{page.start},#{page.end}")
    List<Goods> selectVugeGood(@Param("page")PageHelper page,@Param("gname") String gname);

    @Select("select count(*) from goods where goodname like  concat('%',#{gname},'%')")
    Integer selectGoodByVague(@Param("gname")String gname);

    //插入商品
    @Insert("insert into goods values(#{goods.goodid},#{goods.goodname},#{goods.stock},#{goods.price},#{goods.memberprice},#{goods.category},#{goods.supplier},#{goods.supplierphone},#{goods.supplieraddress})")
    void insertGoods(@Param("goods")Goods goods);

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
