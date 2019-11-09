package cn.market.controller;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import cn.market.service.SearchService;
import cn.market.util.Order;
import cn.market.util.Result;
import cn.market.util.Search;
import cn.market.util.SearchOrder;


@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@PostMapping("/getOrder")
    public Result getOrder(@RequestBody Order order) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String strstartDate = format.format(order.getStarttime());
		String strendDate =format.format(order.getEndtime());
		SearchOrder norder = new SearchOrder();
		 norder.setStarttime(strstartDate);
		 norder.setEndtime(strendDate);
		 norder.setPaginationOrder(order.getPaginationOrder());
	  
		return searchService.getOrderByDate(norder);
	}
	@PostMapping("/getInputData")
   public Result getInputData(@RequestBody Search search) {
	

		return searchService.selectGood(search);
	}
}
