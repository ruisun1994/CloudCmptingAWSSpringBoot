package edu.neu.csye6225.spring2018.controller;

import edu.neu.csye6225.spring2018.dao.UserRepository;
import edu.neu.csye6225.spring2018.entity.User;
import edu.neu.csye6225.spring2018.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static edu.neu.csye6225.spring2018.WebSecurityConfig.SESSION_KEY;

@Controller
@RequestMapping("/user/*")

public class IndexController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

//    private LoginService loginService;

    //index page
    @GetMapping("/user/")
    public String index(@SessionAttribute(SESSION_KEY) String email, Map<String, Object> model) {
        model.put("email", email);
        return "index";
    }

    private String message = "";
    private String errmsg = "";

    //index page
    @RequestMapping("/index")
    public String firstPage(Map<String, Object> model, HttpSession session) throws IOException {
        Object obj = session.getAttribute(SESSION_KEY);
        if (obj == null){
            return "home";
        }
        String email = (String) obj;
        System.out.print("email" + email);
        String message = "";
        Date date = new Date();
        message = "Hi, " + email + " The time is: " + date.toString();
        model.put("message", message);
        //System.out.println(session.getAttribute(SESSION_KEY));
        return "profile";
    }


    //register page
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    //home page
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }


    //login page
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    //register function
    @RequestMapping(value = "/registered")
    public String register(HttpServletRequest request, HttpSession session, Map<String, Object> model) {
        String email = request.getParameter("email");
        String password = request.getParameter("pwd1");
        String password2 = request.getParameter("pwd2");
//        String username = request.getParameter("username");
        String aboutMe = "";
        String imageFilePath = "";
        if (userService.existsByEmail(email)) {
            errmsg = "This email address have been registered";
            model.put("errmsg", this.errmsg);
            return "register_err";
        } else if (password.equals(password2)) {
            User user = new User(email, password, aboutMe, imageFilePath);
            user.setImageFilePath("default.png");
            userService.save(user);
            System.out.println("email2" + email);
//            System.out.println(user.getPassword());
            message = "Register successfully!";
            model.put("message", this.message);
//            session.setAttribute(SESSION_KEY, email);
            return "login";
        } else {
            errmsg = "Please type the same password";
            model.put("errmsg", this.errmsg);
            return "register_err";
        }
    }

    //logout function
    @GetMapping("/logout")
    public String logout(HttpSession session) {
//        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        session.removeAttribute(SESSION_KEY);
        //session.invalidate();
        return "home";
    }
}