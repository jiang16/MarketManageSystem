package cn.market.service;

import cn.market.bean.Order;
import cn.market.dao.SaleRecordDao;
import cn.market.util.CountPrice;
import cn.market.util.GoodQuantity;
import cn.market.util.PageHelper;
import cn.market.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SaleRecordService {
    @Autowired
    SaleRecordDao dao;

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
}
