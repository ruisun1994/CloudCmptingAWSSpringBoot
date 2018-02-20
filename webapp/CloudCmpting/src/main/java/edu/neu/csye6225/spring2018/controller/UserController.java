package edu.neu.csye6225.spring2018.controller;


import edu.neu.csye6225.spring2018.dao.UserRepository;
import edu.neu.csye6225.spring2018.entity.User;
import edu.neu.csye6225.spring2018.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import static edu.neu.csye6225.spring2018.WebSecurityConfig.SESSION_KEY;


@Controller
@RequestMapping("/user/*")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    User user = new User();

    @RequestMapping(value = "/refreshInfo")
    public String refreshpage(HttpSession session,  Map<String, Object> model){
        user = userService.findByEmail((String)session.getAttribute(SESSION_KEY));
        String message="";
        String imageFilePath="";
        String aboutMe="";

        Date date = new Date();
        message = "Hi, " + user.getEmail() + " The time is: " + date.toString();
        model.put("message", message);
        //session.setAttribute(WebSecurityConfig.SESSION_KEY, email);
        imageFilePath=user.getImageFilePath();
        aboutMe=user.getAboutMe();

        model.put("aboutMe",aboutMe);
        model.put("imageFilePath", imageFilePath);
//            System.out.println(session.getAttribute(SESSION_KEY));
        return "profile";
    }


}