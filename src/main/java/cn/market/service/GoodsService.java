package cn.market.service;

import cn.market.bean.Goods;
import cn.market.dao.GoodsDao;
import cn.market.dao.PurchaseRecordDao;
import cn.market.dao.SaleRecordDao;
import cn.market.dao.UserDao;
import cn.market.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GoodsService {

    @Autowired
    private GoodsDao dao;
    @Autowired
    private SaleRecordDao saleRecordDao;
    @Autowired
    private PurchaseRecordDao purchaseRecordDao;

    /*
     * 根据id查询商品信息
     */
    public Result getGoodInfo(String goodid) {

        //创建返回对象
        Result result = new Result();
        result.setSuccess(true);
        List<Goods> list = null;
        Goods good = null;
        try {

            good = dao.selectSupplierInfo(goodid);

        }
        catch(Exception e) {

            e.printStackTrace();
            result.setCode(500);
            result.setSuccess(false);
            result.setMessage("信息查询失败");
            return result;
        }
        if(good!=null) {
            result.setCode(1);
            result.setMessage("查询成功");
            result.setData(good);
            return result;
        }
        else {

            result.setCode(500);
            result.setSuccess(false);
            result.setMessage("输入的商品号不存在,请核对后再输入!");
            return result;
        }

    }
    /*
     * 获取全部商品
     */
    public Result getGoods(PageHelper page) {
        page.setStart((page.getCurrentPage()-1)*page.getPageSize());
        page.setEnd(page.getPageSize());
        //创建返回对象
        Result result = new Result();
        result.setSuccess(true);
        List<Goods> list = null;
        Integer totalCount = null;
        try {
            //查询分页数据
            list = dao.selectGoodByPage(page);
            //查询数据总数
            totalCount = dao.getAllGoods();
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
     * 获取标准商品
     */
    public Result getStandardGoods(PageHelper page) {
        page.setStart((page.getCurrentPage()-1)*page.getPageSize());
        page.setEnd(page.getPageSize());
        //创建返回对象
        Result result = new Result();
        result.setSuccess(true);
        List<Goods> list = null;
        Integer totalCount = null;
        try {
            //查询分页数据
            list = dao.selectStandardGoodByPage(page);
            //查询数据总数
            totalCount = dao.getStandardGoods();
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
     * 获取称重商品
     */
    public Result getWeightGoods(PageHelper page) {
        page.setStart((page.getCurrentPage()-1)*page.getPageSize());
        page.setEnd(page.getPageSize());
        //创建返回对象
        Result result = new Result();
        result.setSuccess(true);
        List<Goods> list = null;
        Integer totalCount = null;
        try {
            //查询分页数据
            list = dao.selectWeightGoodByPage(page);
            //查询数据总数
            totalCount = dao.getWeightGoods();

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
     * 删除一行商品
     */
    public Result delOneGood(Search search) {
        search.getPagination().setStart((search.getPagination().getCurrentPage()-1)*search.getPagination().getPageSize());
        search.getPagination().setEnd(search.getPagination().getPageSize());

        //创建返回对象
        Result result = new Result();
        result.setSuccess(true);
        List<Goods> list = null;
        Integer totalCount = null;
        try {
            dao.deleteGood(search.getContent());
            //查询分页数据
            list = dao.selectGoodByPage(search.getPagination());
            //查询数据总数
            totalCount = dao.getAllGoods();
            //为PageHelper对象设置分配记录数，同时自动设置总页数
            search.getPagination().setTotalCount(totalCount);
            result.setTotalCount(totalCount);
            result.setTotalPage(search.getPagination().getTotalPage());
            result.setCode(1);
            result.setMessage("查询成功");
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
     * 批量删除商品
     */
    public Result  delGoodsByGoodName(DelGood delgood) {
        delgood.getPagination().setStart((delgood.getPagination().getCurrentPage()-1)*delgood.getPagination().getPageSize());
        delgood.getPagination().setEnd(delgood.getPagination().getPageSize());

        //创建返回对象
        Result result = new Result();
        result.setSuccess(true);
        List<Goods> list = null;
        Integer totalCount = null;
        try {
            for(Goods good:delgood.getGoodname()) {
                dao.deleteGood(good.getGoodname());
            }
            //查询分页数据
            list = dao.selectGoodByPage(delgood.getPagination());
            //查询数据总数
            totalCount = dao.getAllGoods();
            //为PageHelper对象设置分配记录数，同时自动设置总页数
            delgood.getPagination().setTotalCount(totalCount);
            result.setTotalCount(totalCount);
            result.setTotalPage(delgood.getPagination().getTotalPage());
            result.setCode(1);
            result.setMessage("查询成功");
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
     *进货
     */

    public Result AddStock(AddGood good) {
        Result result =new Result();
        result.setSuccess(true);
        try{
            Goods g = dao.selectSupplierInfo(good.getGoodid());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            purchaseRecordDao.InsertGoodStock(good,g,df.format(new Date()));
            List<Goods> list =dao.getGoodsData();
            String flagStock =null;
            int flag=1;
            for(Goods i:list) {
                if(i.getGoodid().equals(good.getGoodid())) {
                    flag=0;
                    for(int j=0;j<i.getStock().length();j++) {
                        if((i.getStock().charAt(j)<'0'||i.getStock().charAt(j)>'9')&&(i.getStock().charAt(j)!='.')) {
                            flagStock= String.valueOf(i.getStock().charAt(j));
                            break;
                        }
                    }
                    String[] old = i.getStock().split(flagStock);
                    String count = String.valueOf(Integer.parseInt(old[0])+Integer.parseInt(good.getPurchasenum()));
                    String newstock = count+flagStock;
                    System.out.println(newstock);
                    i.setStock(newstock);
                    dao.updateGood(newstock,i.getGoodname());
                    break;
                }
            }
            result.setCode(1);
            result.setMessage("进货成功");
            return result;
        }
        catch(Exception e) {
            e.printStackTrace();
            result.setCode(500);
            result.setSuccess(false);
            result.setMessage("进货失败");
            return result;
        }
    }

    /*
     * 更新商品价格
     */
    public Result updateGoodPrice(UpdateGood good) {
        good.getPagination().setStart((good.getPagination().getCurrentPage()-1)*good.getPagination().getPageSize());
        good.getPagination().setEnd(good.getPagination().getPageSize());

        Result result = new Result();
        result.setSuccess(true);
        List<Goods> list =null;
        Integer totalCount = null;
        try {
            dao.updateGoodPrice(good);
            //查询分页数据
            list = dao.selectGoodByPage(good.getPagination());
            //查询数据总数
            totalCount = dao.getAllGoods();
            //为PageHelper对象设置分配记录数，同时自动设置总页数
            good.getPagination().setTotalCount(totalCount);
            result.setTotalCount(totalCount);
            result.setTotalPage(good.getPagination().getTotalPage());
            result.setCode(1);
            result.setMessage("更新成功！");
            result.setData(list);
            return result;
        }
        catch(Exception e) {
            e.printStackTrace();
            result.setCode(500);
            result.setSuccess(false);
            result.setMessage("更新失败");
            return result;
        }



    }
    /*
     * 减去商品库存量
     */
    public Result subGoodStock(SelGood selgood) {
        selgood.getPagination().setStart((selgood.getPagination().getCurrentPage()-1)*selgood.getPagination().getPageSize());
        selgood.getPagination().setEnd(selgood.getPagination().getPageSize());
        Result result = new Result();
        result.setSuccess(true);
        System.out.println(selgood.getPradio());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        try {
            for(Goods good:selgood.getContent()) {
                if(selgood.getPradio().equals("普通")) {
                    System.out.println(selgood.getSaleman()+"fsdfs");
                    saleRecordDao.InsertGoodRecord(good,good.getPrice(),df.format(new Date()),selgood.getSaleman());
                }
                else {
                    saleRecordDao.InsertGoodRecord(good,good.getMemberprice(),df.format(new Date()),selgood.getSaleman());
                }


                //更新库存量
                String flagStock= null;
                for(int j=0;j<good.getStock().length();j++) {
                    if((good.getStock().charAt(j)<'0'||good.getStock().charAt(j)>'9')&&(good.getStock().charAt(j)!='.')) {
                        flagStock= String.valueOf(good.getStock().charAt(j));
                        break;
                    }
                }
                String[] old = good.getStock().split(flagStock);
                String count = String.valueOf(Integer.parseInt(old[0])-Integer.parseInt(good.getBuy_num()));
                String newstock = count+flagStock;
                System.out.println(newstock);
                dao.updateGood(newstock, good.getGoodname());

            }
            List<Goods> list = null;
            Integer totalCount = null;
            //查询分页数据
            list = dao.selectGoodByPage(selgood.getPagination());
            //查询数据总数
            totalCount = dao.getAllGoods();
            //为PageHelper对象设置分配记录数，同时自动设置总页数
            selgood.getPagination().setTotalCount(totalCount);
            result.setTotalCount(totalCount);
            result.setTotalPage(selgood.getPagination().getTotalPage());
            result.setCode(1);
            result.setMessage("更新成功！");
            result.setData(list);
            return result;


        }
        catch(Exception e) {
            e.printStackTrace();
            result.setCode(500);
            result.setSuccess(false);
            result.setMessage("服务器错误");
            return result;
        }


    }

}
