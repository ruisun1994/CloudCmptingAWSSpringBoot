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
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    //checkAccount
    public boolean checkAccout(String email, String password) {
        User user = new User(email, password);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("password", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnorePaths("id");
        Example<User> userExample = Example.of(user, matcher);
        List<User> userList = userRepository.findAll(userExample);
        if (!userList.isEmpty()) return true;
        else return false;
    }

//    //test function
//    @RequestMapping(value = "/test")
//    public String testPage(HttpServletRequest request, Map<String, Object> model, HttpSession session){
//        Object obj = session.getAttribute(SESSION_KEY);
//        String email = (String)obj;
//        String message = "";
//        session.setAttribute(SESSION_KEY, email);
//        Date date = new Date();
//        message = "Hi, " + email + " The time is: " + date.toString();
//        model.put("message", message);
//        System.out.println(session.getAttribute(SESSION_KEY));
//        return "home";
//    }

    //login function
    @RequestMapping(value = "/loggedin")
    public String login(HttpServletRequest request, Map<String, Object> model, HttpSession session) {
        String email = request.getParameter("email");
        String rawPassword = request.getParameter("password");
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String enPassword = encoder.encode(rawPassword);
        String enPassword = BCrypt.hashpw(rawPassword, BCryptSalt.SALT);
        boolean checked = checkAccout(email, enPassword);

        String message = "";
        String errmsg = "";

        System.out.println(email);
        System.out.println(rawPassword);
        if (!userService.existsByEmail(email)) {
            errmsg = "Not yet registered";
            model.put("errmsg", errmsg);
            return "login_err";
        } else if (!checked) {
            errmsg = "Wrong Password!";
            System.out.println(enPassword);
            model.put("errmsg", errmsg);
            return "login_err";
        } else {
            session.setAttribute(SESSION_KEY, email);
            Date date = new Date();
            message = "Hi, " + email + " The time is: " + date.toString();
            model.put("message", message);
            System.out.println(session.getAttribute(SESSION_KEY));
            return "home";
        }
    }
}