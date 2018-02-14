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
    private String email;
    private String password;
    private String username;
    private String imgPath;
    private String aboutMe;


    public User() {}

    public User (String email, String password){
        this.email=email;
        this.password = password;
    }

    public User(String email, String password,String username,String imgPath,String aboutMe) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.imgPath = imgPath;
        this.aboutMe = aboutMe;
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

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getImgPath() { return imgPath; }

    public void setImgPath(String imgPath) { this.imgPath = imgPath; }

    public String getAboutMe() { return aboutMe; }

    public void setAboutMe(String aboutMe) { this.aboutMe = aboutMe; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
