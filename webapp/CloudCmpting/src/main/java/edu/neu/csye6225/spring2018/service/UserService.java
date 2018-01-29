package edu.neu.csye6225.spring2018.service;

import edu.neu.csye6225.spring2018.controller.BCryptSalt;
import edu.neu.csye6225.spring2018.dao.UserDao;
import edu.neu.csye6225.spring2018.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

//    public Iterable<User> findAll() {
//        return userDao.findAll();
//    }

//    public User findOne(int id) {
//        return userDao.findOne(id);
//    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    User user = new User();

    public List<User> findByEmailAndPassword(String email, String password) {
        return userDao.findByEmailAndPassword(email, password);
    }

    public void save(User user) {
//        user.setEnabled(true);
//        user.setPassword(encoder.encode(user.getPassword()));
        String salt = BCrypt.gensalt();
        System.out.println(salt);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCryptSalt.SALT));
        user.setEmail(user.getEmail());
        userDao.save(user);
    }

//    public String findOne (String email) {
//        return userDao.findOne(email);
//    }

    public boolean existsByEmail (String email) {
        return userDao.existsByEmail(email);
    }

    public String getByPassword (String password ) {
        userDao.getByPassword(user.getPassword());
        return password;
    }

}
