package edu.neu.csye6225.spring2018.controller;

import edu.neu.csye6225.spring2018.dao.UserRepository;
import edu.neu.csye6225.spring2018.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/*")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/refreshInfo")
    public String refreshpage(HttpSession session){
        return "home";
    }


}