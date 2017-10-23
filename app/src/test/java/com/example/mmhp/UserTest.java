package com.example.mmhp;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by yidingfan on 2017-10-22.
 */
public class UserTest {
    String name= "test";
    int id =0;
    String pass ="pass";
    User user1 = new User(name,id,pass);
    @Test
    public void log_in() throws Exception {
        assertNotNull(user1);
        assertTrue(user1.log_in(this.pass));
    }

    @Test
    public void add_friend() throws Exception {
        user1.add_friend(1);
        user1.add_friend(2);
        ArrayList friends= new ArrayList();
        friends.add(1);
        friends.add(2);
        assertTrue(user1.getFriends().equals(friends));
    }

    @Test
    public void isFriend() throws Exception {
        user1.add_friend(1);
        assertTrue(user1.isFriend(1));
    }

    @Test
    public void getFriends() throws Exception {
        user1.add_friend(1);
        user1.add_friend(2);
        ArrayList friends= new ArrayList();
        friends.add(1);
        friends.add(2);
        assertTrue(user1.getFriends().equals(friends));
    }

    @Test
    public void setName() throws Exception {
        user1.setName("test1");
        assertEquals(user1.getName(),"test1");
    }

    @Test
    public void setPassword() throws Exception {
        user1.setPassword("123");
        assertTrue(user1.log_in("123"));

    }

    @Test
    public void to_string() throws Exception {
        assertEquals(user1.to_string(),"Name: test\n");
    }

}