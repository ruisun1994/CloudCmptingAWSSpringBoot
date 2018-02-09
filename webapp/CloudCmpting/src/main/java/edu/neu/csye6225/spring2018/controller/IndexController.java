package edu.neu.csye6225.spring2018.controller;

import edu.neu.csye6225.spring2018.dao.UserRepository;
import edu.neu.csye6225.spring2018.entity.User;
import edu.neu.csye6225.spring2018.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Date;

@Controller
@RequestMapping("/user/*")

public class IndexController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private String message = "";
    @Autowired
    private String errmsg = "";

    //index page
    @RequestMapping("/index")
    public String index(HttpSession session,Map<String, Object> model) {
        Object obj=session.getAttribute("user");
        if(obj instanceof User){
            User u =(User) obj;
            //session.setAttribute(WebSecurityConfig.SESSION_KEY, email);
            Date date = new Date();
            message = "Hi, " + u.getEmail() + " The time is: " + date.toString();
            model.put("message", message);
            return "home";
        }

        return "index";
    }

    //register page
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    //login page
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

//    @ModelAttribute("user")
//    public User constructUser() {
//        return new User();
//    }


    //register function
    @RequestMapping(value = "/registered")
    public String register(HttpServletRequest request, Map<String, Object> model) {
        String email = request.getParameter("email");
        String password = request.getParameter("pwd1");
        String password2 = request.getParameter("pwd2");;

//        System.out.println(userRepository.existsByEmail(email));
        if(userService.existsByEmail(email)) {
            errmsg = "This email address have been registered";
            model.put("errmsg", this.errmsg);
            return "register_err";
        }
        else if(password.equals(password2)) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            userService.save(user);
            System.out.println(email);
            System.out.println(user.getPassword());
            message = "Register successfully!";
            model.put("message", this.message);
            return "login";
        }else{
            errmsg = "Please type the same password";
            model.put("errmsg", this.errmsg);
            return "register";
        }
    }

    //logout function
    @GetMapping("/logout")
    public String logout(HttpSession session){
        return "login";
    }
}