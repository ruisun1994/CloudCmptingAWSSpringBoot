package edu.neu.csye6225.spring2018.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void getPassword() throws Exception {
        User test1 = new User("test1@qq.com","123","","");
        assertEquals("123",test1.getPassword());
    }

    @Test
    public void setPassword() {
        User test1 = new User();
        test1.setPassword("123");
        assertEquals(test1.getPassword(),"123");
    }

    @Test
    public void getEmail() {
        User test1 = new User("test1@qq.com","123","","");
        assertEquals("test1@qq.com",test1.getEmail());
    }

    @Test
    public void setEmail() {
        User test1 = new User();
        test1.setEmail("test1@qq.com");
        assertEquals(test1.getEmail(),"test1@qq.com");
    }
}