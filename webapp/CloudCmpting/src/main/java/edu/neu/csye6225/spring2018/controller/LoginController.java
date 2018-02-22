package edu.neu.csye6225.spring2018.controller;


import edu.neu.csye6225.spring2018.WebSecurityConfig;
import edu.neu.csye6225.spring2018.dao.UserRepository;
import edu.neu.csye6225.spring2018.entity.User;
import edu.neu.csye6225.spring2018.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

@Controller
@RequestMapping("/user/*")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Environment env;


    //checkAccount
    public boolean checkAccout(String email, String password) {
        User user = new User(email, password);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("password", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnorePaths("id")
                .withIgnorePaths("aboutMe")
                .withIgnorePaths("imageFilePath");

        Example<User> userExample = Example.of(user, matcher);
        List<User> userList = userRepository.findAll(userExample);
        if (!userList.isEmpty()) return true;
        else return false;
    }

    //login function
    @RequestMapping(value = "/loggedin")
    public String login(HttpServletRequest request, Map<String, Object> model, HttpSession session) {
        String email = request.getParameter("email");
        String rawPassword = request.getParameter("password");
        String enPassword = BCrypt.hashpw(rawPassword, BCryptSalt.SALT);
        String aboutMe = "";
        String imageFilePath = "";
        boolean checked = checkAccout(email, enPassword);

        String message = "";
        String errmsg = "";

        System.out.println("email3" + email);
        System.out.println("rawPassword:" + rawPassword);
        System.out.println("Is it in AWS: "+env.getProperty("AWS.status"));

        if (!userService.existsByEmail(email)) {
            errmsg = "Not yet registered";
            model.put("errmsg", errmsg);
            return "login_err";
        } else if (!checked) {
            errmsg = "Wrong Password!";
            System.out.println("enPassword" + enPassword);
            model.put("errmsg", errmsg);
            return "login_err";
        } else {
            Date date = new Date();
            message = "Hi, " + email + " The time is: " + date.toString();
            model.put("message", message);
            session.setAttribute(WebSecurityConfig.SESSION_KEY, email);
            User user = userService.findByEmail(email);
//            aboutMe = user.getAboutMe();
//            imageFilePath = user.getImageFilePath();
            model.put("aboutMe",user.getAboutMe());
            model.put("imageFilePath", user.getImageFilePath());
            //System.out.println("upload page session key" + imageFilePath);
//            System.out.println(session.getAttribute(SESSION_KEY));
            return "profile";
        }
    }
}