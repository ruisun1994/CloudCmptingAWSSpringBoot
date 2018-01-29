//package edu.neu.csye6225.spring2018.controller;
//
//import edu.neu.csye6225.spring2018.dao.UserDao;
//import edu.neu.csye6225.spring2018.entity.User;
//import edu.neu.csye6225.spring2018.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//
//@RestController
//@RequestMapping("/user")
//
//public class UserController {
//    @Resource
//    private UserService userService;
//
//    //add user
//    @RequestMapping("/save")
//    public String save() {
//        User user = new User();
//        user.setUsername("Rui");
//        user.setPassword("123");
//        user.setEmail("373000335@qq.com");
//        userService.save(user);
//        return "saved";
//    }
//
//    //delete user
//    @RequestMapping("/delete")
//    public String delete() {
//        userService.delete(1);
//        return "deleted";
//    }
//
//    //search user
//    @RequestMapping("/getAll")
//    public Iterable<User> getAll() {
//        return userService.getAll();
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//package edu.neu.csye6225.spring2018.controller;
//
//import edu.neu.csye6225.spring2018.entity.User;
//import edu.neu.csye6225.spring2018.dao.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//@Controller
//public class UserController {
//    @Autowired
//    private UserDao userDao;
//    @RequestMapping("/getName")
//    @ResponseBody
//    public String getByName(String name) {
//        List<User> userList = userDao.findByName(name);
//        if (userList != null && userList.size()!=0) {
//            return "The user length is: " + userList.size();
//        }
//        return "user " + name + " is not exist.";
//    }
//
//    @RequestMapping("/getSex")
//    @ResponseBody
//    public String getBySex(char sex) {
//        List<User> userList = userDao.findBySex(sex);
//        if (userList != null && userList.size()!=0) {
//            return "The user length is: " + userList.size();
//        }
//        return "user " + sex + " is not exist.";
//    }
//
//    @RequestMapping("/getBirthday")
//    @ResponseBody
//    public String findByBirthday(String birthday) {
//        System.out.println("birthday:"+birthday);
//        SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd");
//        List<User> userList = null;
//        try {
//            userList = userDao.findByBirthday(formate.parse(birthday));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (userList != null && userList.size()!=0) {
//            return "The user length is: " + userList.size();
//        }
//        return "user " + birthday + " is not exist.";
//    }
//
//    @RequestMapping("/getSendtime")
//    @ResponseBody
//    public String findBySendtime(String sendtime) {
//        System.out.println("sendtime:"+sendtime);
//        SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<User> userList = null;
//        try {
//            userList = userDao.findBySendtime(formate.parse(sendtime));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (userList != null && userList.size()!=0) {
//            return "The user length is: " + userList.size();
//        }
//        return "user " + sendtime + " is not exist.";
//    }
//
//    @RequestMapping("/getPrice")
//    @ResponseBody
//    public String findByPrice(BigDecimal price) {
//        List<User> userList = null;
//        userList = userDao.findByPrice(price);
//        if (userList != null && userList.size()!=0) {
//            return "The user length is: " + userList.size();
//        }
//        return "user " + price + " is not exist.";
//    }
//
//    @RequestMapping("/getFloatprice")
//    @ResponseBody
//    public String findFloatprice(float floatprice) {
//        List<User> userList = null;
//        userList = userDao.findByFloatprice(floatprice);
//        if (userList != null && userList.size()!=0) {
//            return "The user length is: " + userList.size();
//        }
//        return "user " + floatprice + " is not exist.";
//    }
//
//    @RequestMapping("/getDoubleprice")
//    @ResponseBody
//    public String findByPrice(double doubleprice) {
//        List<User> userList = null;
//        userList = userDao.findByDoubleprice(doubleprice);
//        if (userList != null && userList.size()!=0) {
//            return "The user length is: " + userList.size();
//        }
//        return "user " + doubleprice + " is not exist.";
//    }
//}
