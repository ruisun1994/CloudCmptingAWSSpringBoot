//package edu.neu.csye6225.spring2018.service;
//
//import edu.neu.csye6225.spring2018.dao.UserDao;
//import edu.neu.csye6225.spring2018.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class LoginService {
//
//    @Autowired
//    private UserDao userDao;
//
//    public boolean verifyLogin(User user){
//
//        List<User> userList = UserDao.findByEmailAndPassword(user.getEmail(), user.getPassword());
//        return userList.size()>0;
//    }
//
//}
