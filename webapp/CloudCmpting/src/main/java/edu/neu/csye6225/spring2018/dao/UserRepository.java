package edu.neu.csye6225.spring2018.dao;

import edu.neu.csye6225.spring2018.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findByEmailAndPassword(String email, String password);
    public boolean existsByEmail(String email);
    //public List<User> findAllByOrderByUpdatedAtDesc();
    List<User> findAll();





}
