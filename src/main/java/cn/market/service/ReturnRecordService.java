package cn.market.service;

import cn.market.dao.GoodsDao;
import cn.market.dao.ReturnRecordDao;
import cn.market.util.PageHelper;
import cn.market.util.Result;
import cn.market.util.ReturnGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReturnRecordService {
    @Autowired
    ReturnRecordDao dao;
    @Autowired
    GoodsDao goodsDao;

    /*
     * 退货
     */
    public Result returnGood(ReturnGood good) {
        Result result =new Result();
        result.setSuccess(true);
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            System.out.println(good.getGoodid());
            dao.InsertReturnRecord(good, df.format(new Date()));
            goodsDao.delGood(good.getGoodid());
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
