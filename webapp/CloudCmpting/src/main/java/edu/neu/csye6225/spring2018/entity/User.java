package edu.neu.csye6225.spring2018.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "user")
public class User {
    //使用@Id指定主键.使用代码@GeneratedValue(strategy=GenerationType.AUTO)
    //指定主键的生成策略，mysql默认的是自增长
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; //primary key
//    private String username;
    private String email;
    private String password;


    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
