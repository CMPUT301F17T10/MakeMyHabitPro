/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



public class UserTest {
    String name= "test";
    String id ="0";
    String pass ="pass";
    User user1 = new User(name,id,pass);
    @Test
    public void log_in() throws Exception {
        assertNotNull(user1);
        assertTrue((user1.log_in(this.pass)==1|user1.log_in(this.pass)==2));
    }

    @Test
    public void add_friend() throws Exception {
        user1.add_friend("1");
        user1.add_friend("2");
        ArrayList<String> friends= new ArrayList<String>();
        friends.add("1");
        friends.add("2");
        assertTrue(user1.getFriends().equals(friends));
    }

    @Test
    public void isFriend() throws Exception {
        user1.add_friend("1");
        assertTrue(user1.isFriend("1"));
    }

    @Test
    public void getFriends() throws Exception {
        user1.add_friend("1");
        user1.add_friend("2");
        ArrayList<String> friends= new ArrayList<String>();
        friends.add("1");
        friends.add("2");
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
        assertTrue((user1.log_in("123")==1|user1.log_in("123")==2));

    }

    @Test
    public void to_string() throws Exception {
        assertEquals(user1.toString(),"Name: test");
    }
}
