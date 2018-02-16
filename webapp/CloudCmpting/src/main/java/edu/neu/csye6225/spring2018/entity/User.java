package edu.neu.csye6225.spring2018.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; //primary key
//    private String username;
    private String email;
    private String password;
    private String userName;
    private String aboutMe;
    private String imageFilePath;


    public User() {}


    public User(String email, String password, String userName, String aboutMe, String imageFilePath) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.aboutMe = aboutMe;
        this.imageFilePath = imageFilePath;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
        this.userName = "";
        this.aboutMe = "";
        this.imageFilePath = "";

    }

    public User(String email, String password,String username){
        this.email = email;
        this.password = password;
        this.userName=username;
        this.imageFilePath="";
        this.aboutMe="";
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
