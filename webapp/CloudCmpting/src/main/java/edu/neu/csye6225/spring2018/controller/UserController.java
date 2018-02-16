package edu.neu.csye6225.spring2018.controller;


import edu.neu.csye6225.spring2018.dao.UserRepository;
import edu.neu.csye6225.spring2018.entity.User;
import edu.neu.csye6225.spring2018.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static edu.neu.csye6225.spring2018.WebSecurityConfig.SESSION_KEY;

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
