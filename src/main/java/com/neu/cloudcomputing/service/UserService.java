package com.neu.cloudcomputing.service;

import com.neu.cloudcomputing.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {

    User save(User user);
    boolean findByUsername(String username);
    boolean findByUsernameAndPassword(String username, String password);
    User getUser(String username);
}
