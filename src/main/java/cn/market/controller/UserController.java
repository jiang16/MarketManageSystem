package cn.market.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.market.bean.User;
import cn.market.service.UserService;

import cn.market.util.Result;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService service;

	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	@PostMapping("updateUser")
	public Result updateOneUser(@RequestBody User user) {
		return service.updateOneUser(user);
		
	}

	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	@PostMapping("delUser")
	public Result delOneUser(@RequestBody User user) {
		return service.delUser(user);
	}

	/**
	 * 获取全部用户
	 * @return
	 */
	@PostMapping("/getAllUser")
	public Result getAllUser() {
		return service.getAllUserInfo();
	}

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@PostMapping("/addUser")
    public Result addUser(@RequestBody User user) {
    	return service.addUser(user);
    }

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	@PostMapping("/login")
	public Result login(@RequestBody User user) {
    
		return service.login(user);
	}

	/**
	 * 用户登出
	 * @param user
	 * @return
	 */
	@PostMapping("/exit")
	public Result exit(@RequestBody User user) {
		return service.exit(user);
	}
	
 

}
