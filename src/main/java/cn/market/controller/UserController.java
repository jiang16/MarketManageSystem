package cn.market.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.market.bean.User;
import cn.market.service.UserService;

import cn.market.util.Result;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService service;
    
	@PostMapping("updateUser")
	public Result updateOneUser(@RequestBody User user) {
		return service.updateOneUser(user);
		
	}
	
	@PostMapping("delUser")
	public Result delOneUser(@RequestBody User user) {
		return service.delUser(user);
	}
	
	@PostMapping("/getAllUser")
	public Result getAllUser() {
		return service.getAllUserInfo();
	}
	
	@PostMapping("/addUser")
    public Result addUser(@RequestBody User user) {
    	return service.addUser(user);
    }
	@PostMapping("/login")
	public Result login(@RequestBody User user) {
    
		return service.login(user);
	}
	
	@PostMapping("/exit")
	public Result exit(@RequestBody User user) {
		return service.exit(user);
	}
	
 

}
