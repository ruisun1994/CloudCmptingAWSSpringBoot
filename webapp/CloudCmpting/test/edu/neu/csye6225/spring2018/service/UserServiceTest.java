package edu.neu.csye6225.spring2018.service;

import edu.neu.csye6225.spring2018.dao.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @BeforeEach
    public void  setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Add a new user")
    public void saveTest() {
    }

    @Test
    @DisplayName("Check whether have certain email")
    public void existsByEmail() {
//        User user1 = new User("user1@qq.com","123");
//        String email1 = user1.getEmail();
//        Boolean usersByEmail = userRepository.existsByEmail(email1);
//        assertEquals(usersByEmail, true);
    }
}