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
	 * ��ȡ����
	 */
	@PostMapping("/allorder")
	public Result getOrder(@RequestBody PageHelper page)
	{
		return service.getAllOrder(page);
	}
	/*
	 * �����۶�
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
	 * ͳ����Ʒ������
	 */
	@PostMapping("/goodQuantity")
	public Result getGoodQuantity() {
		return service.CountGoodQuantity();
	}
	/*
	 * ������Ʒ����ȥ���ݿ��еĿ��
	 */
	@PostMapping("/subGoodStock")
	public Result subGoodStock(@RequestBody SelGood good) {
		return service.subGoodStock(good);
	}
	/*
	 * ������Ʒ�۸�
	 */
	@PostMapping("/updateGoodPrice")
	public Result updateGoodPrice(@RequestBody UpdateGood good) {
		return service.updateGoodPrice(good);
	}
	/*
	 * ����ɾ����Ʒ
	 */
	@PostMapping("/delGoodsByGoodName")
	public Result delGoodsByGoodName(@RequestBody DelGood delgood) {
		return service.delGoodsByGoodName(delgood);
		
	}
	
	
	/*
	 * ɾ��һ����Ʒ
	 */
	@PostMapping("/delOneGood")
	public Result delOneGood(@RequestBody Search search) {
		return service.delOneGood(search);
	}
	/*
	 * ��ȡȫ����Ʒ
	 */
   @PostMapping("/listAllGoods")
   public Result getAllGoods(@RequestBody PageHelper page) {
	   return service.getGoods(page);
   }
   
   /*
    * ��ȡ��׼��Ʒ
    */
   @PostMapping("/listStandardGoods")
   public Result getStandardGood(@RequestBody PageHelper page) {
	   return service.getStandardGoods(page);
   }
   
   /*
    * ��ȡ������Ʒ
    */
   @PostMapping("/listWeightGoods")
   public Result getWeightGoods(@RequestBody PageHelper page) {
	   return service.getWeightGoods(page);
   }

}
