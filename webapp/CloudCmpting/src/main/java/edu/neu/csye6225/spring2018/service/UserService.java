package edu.neu.csye6225.spring2018.service;

import edu.neu.csye6225.spring2018.controller.BCryptSalt;
import edu.neu.csye6225.spring2018.dao.UserRepository;
import edu.neu.csye6225.spring2018.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    User user = new User();

    public void save(User user) {
        String salt = BCrypt.gensalt();
        System.out.println(salt);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCryptSalt.SALT));
<<<<<<< HEAD
        user.setEmail(user.getEmail());
        user.setUsername(user.getUsername());
        user.setAboutMe(user.getAboutMe());
        user.setImgPath(user.getImgPath());
=======
        //user.setEmail(user.getEmail());
>>>>>>> 5c60689443bb356a3518688f529ba947a5f22eee
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public boolean existsByEmail (String email) {
        return userRepository.existsByEmail(email);
    }

    public void updateUser(int id, String aboutMe, String imageFilePath) {
        userRepository.updateUser(id, aboutMe, imageFilePath);
    }
}
