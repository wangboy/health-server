package com.wb.controller;

import com.wb.domain.User;
import com.wb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by wangbo on 2017/4/1.
 */
@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	//	@PreAuthorize("#oauth2.hasScope('server') or #name.equals('demo')")
	@RequestMapping(path = "/{name}", method = RequestMethod.GET)
	public User getUserByName(@PathVariable String name) {
		return userService.findByName(name);
	}
	
	@RequestMapping(path = "/current", method = RequestMethod.GET)
	public User getCurrentUser(Principal principal) {
		return userService.findByName(principal.getName());
	}
	
	@RequestMapping(path = "/current", method = RequestMethod.PUT)
	public void saveCurrentUser(Principal principal, @Valid @RequestBody User user) {
		userService.saveUserChange(principal.getName(), user);
	}
	
	@RequestMapping(path = "/", method = RequestMethod.POST)
	public User createNewUser(@Valid @RequestBody User user) {
		return userService.createUser(user);
	}
	
}
