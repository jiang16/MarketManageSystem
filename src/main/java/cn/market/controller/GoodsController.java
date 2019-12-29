package cn.market.controller;



import cn.market.bean.Goods;
import cn.market.service.GoodsService;
import cn.market.service.ReturnRecordService;
import cn.market.service.SaleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.market.bean.Order;
import cn.market.service.UserService;
import cn.market.util.AddGood;
import cn.market.util.DelGood;
import cn.market.util.PageHelper;
import cn.market.util.Result;
import cn.market.util.ReturnGood;
import cn.market.util.Search;
import cn.market.util.SelGood;
import cn.market.util.UpdateGood;

@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ReturnRecordService returnRecordService;
	@Autowired
	private SaleRecordService saleRecordService;

	/**
	 * 删除退货记录
	 * @param goodid
	 * @return
	 */
	@PostMapping("delReturnRecord")
	public Result delReturn(@RequestBody String goodid) {
		return returnRecordService.delReturnRecord(goodid);
	}

	/**
	 * 获取全部退货记录
	 * @param page
	 * @return
	 */
	@PostMapping("/allReturnGoods")
	public Result getReturn(@RequestBody  PageHelper page) {
		
		return returnRecordService.getAllReturnGood(page);
	}

	/**
	 * 获取退货商品
	 * @param good
	 * @return
	 */
	@PostMapping("returngood")
	public Result getGood(@RequestBody ReturnGood good) {
		return returnRecordService.returnGood(good);
	}

	/**
	 * 获取单个商品
	 * @param goodid
	 * @return
	 */
	@PostMapping("/getGoodsInfo")
	public Result getInfo(@RequestBody String goodid) {
		return goodsService.getGoodInfo(goodid);
	}
	
	/*
	 * 获取订单
	 */
	@PostMapping("/allorder")
	public Result getOrder(@RequestBody PageHelper page)
	{
		return saleRecordService.getAllOrder(page);
	}

	/**
	 * 商品进货
	 * @param good
	 * @return
	 */
	@PostMapping("/addStock")
	public Result addStock(@RequestBody AddGood good) {

		return goodsService.AddStock(good);
	}

	/*
	 * 日销售额
	 */
	@PostMapping("/dayPrice")
	public Result getDayPrice() {
		return saleRecordService.GetDayPrice();
	}
	/*
	 * 统计商品销售量
	 */
	@PostMapping("/goodQuantity")
	public Result getGoodQuantity() {
		return saleRecordService.CountGoodQuantity();
	}
	/*
	 * 销售商品，减去数据库中的库存
	 */
	@PostMapping("/subGoodStock")
	public Result subGoodStock(@RequestBody SelGood good) {
		return goodsService.subGoodStock(good);
	}
	/*
	 * 更新商品价格
	 */
	@PostMapping("/updateGood")
	public Result updateGoodPrice(@RequestBody Goods good) {
		return goodsService.updateGood(good);
	}
	/*
	 * 批量删除商品
	 */
	@PostMapping("/delGoodsByGoodName")
	public Result delGoodsByGoodName(@RequestBody DelGood delgood) {
		return goodsService.delGoodsByGoodName(delgood);
		
	}
	
	
	/*
	 * 删除一行商品
	 */
	@PostMapping("/delOneGood")
	public Result delOneGood(@RequestBody Search search) {
		return goodsService.delOneGood(search);
	}
	/*
	 * 获取全部商品
	 */
   @PostMapping("/listAllGoods")
   public Result getAllGoods(@RequestBody PageHelper page) {
	   return goodsService.getGoods(page);
   }
   
   /*
    * 获取标准商品
    */
   @PostMapping("/listStandardGoods")
   public Result getStandardGood(@RequestBody PageHelper page) {
	   return goodsService.getStandardGoods(page);
   }
   
   /*
    * 获取称重商品
    */
   @PostMapping("/listWeightGoods")
   public Result getWeightGoods(@RequestBody PageHelper page) {
	   return goodsService.getWeightGoods(page);
   }

}
