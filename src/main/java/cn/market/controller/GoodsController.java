package cn.market.controller;



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
	private UserService service;
	
	@PostMapping("delReturnRecord")
	public Result delReturn(@RequestBody String goodid) {
		return service.delReturnRecord(goodid);
	}
	@PostMapping("/allReturnGoods")
	public Result getReturn(@RequestBody  PageHelper page) {
		
		return service.getAllReturnGood(page);
	}
	@PostMapping("returngood")
	public Result getGood(@RequestBody ReturnGood good) {
		return service.returnGood(good);
	}
	
	@PostMapping("/getGoodsInfo")
	public Result getInfo(@RequestBody String goodid) {
		return service.getGoodInfo(goodid);
	}
	
	/*
	 * 获取订单
	 */
	@PostMapping("/allorder")
	public Result getOrder(@RequestBody PageHelper page)
	{
		return service.getAllOrder(page);
	}
	/*
	 * 日销售额
	 */
	@PostMapping("/addStock")
	public Result addStock(@RequestBody AddGood good) {
		System.out.println(good.getSupplier());
		return service.AddStock(good);
	}
	
	@PostMapping("/dayPrice")
	public Result getDayPrice() {
		return service.GetDayPrice();
	}
	/*
	 * 统计商品销售量
	 */
	@PostMapping("/goodQuantity")
	public Result getGoodQuantity() {
		return service.CountGoodQuantity();
	}
	/*
	 * 销售商品，减去数据库中的库存
	 */
	@PostMapping("/subGoodStock")
	public Result subGoodStock(@RequestBody SelGood good) {
		return service.subGoodStock(good);
	}
	/*
	 * 更新商品价格
	 */
	@PostMapping("/updateGoodPrice")
	public Result updateGoodPrice(@RequestBody UpdateGood good) {
		return service.updateGoodPrice(good);
	}
	/*
	 * 批量删除商品
	 */
	@PostMapping("/delGoodsByGoodName")
	public Result delGoodsByGoodName(@RequestBody DelGood delgood) {
		return service.delGoodsByGoodName(delgood);
		
	}
	
	
	/*
	 * 删除一行商品
	 */
	@PostMapping("/delOneGood")
	public Result delOneGood(@RequestBody Search search) {
		return service.delOneGood(search);
	}
	/*
	 * 获取全部商品
	 */
   @PostMapping("/listAllGoods")
   public Result getAllGoods(@RequestBody PageHelper page) {
	   return service.getGoods(page);
   }
   
   /*
    * 获取标准商品
    */
   @PostMapping("/listStandardGoods")
   public Result getStandardGood(@RequestBody PageHelper page) {
	   return service.getStandardGoods(page);
   }
   
   /*
    * 获取称重商品
    */
   @PostMapping("/listWeightGoods")
   public Result getWeightGoods(@RequestBody PageHelper page) {
	   return service.getWeightGoods(page);
   }

}
