/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by spei on 11/18/17.
 */

public class EventTest extends ActivityInstrumentationTestCase2 {

    public EventTest() {
        super(com.example.spei.makemyhabitpro.Event.class);
    }

    private Habit habit ;
    private Location location;

    public void testGetId(){
        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
        Event event = new Event(habit, "comment", "id", "UID");
        assertNotNull(event.getId());
    }

    public void testGetOwner_comment(){
        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
        Event event = new Event(habit, "comment", "id", "UID");
        assertEquals("comment", event.getOwner_comment());
    }

    public void testGetHabit(){
        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
        Event event = new Event(habit, "comment", "id", "UID");
        assertEquals(habit, event.getHabit());
    }

    public void testGetHappened(){
        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
        Event event = new Event(habit, "comment", "id", "UID");
        assertNotNull(event.getHappend());
    }



    public void testGetPub_comment (){
        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
        Event event = new Event(habit, "comment", "id", "UID");
        event.pub_comment("pub");
        ArrayList<String> pub = event.getPub_comment();
        assertEquals("pub", pub.get(0));
    }

    /*public void testGetUrl_img (){
        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
        Event event = new Event(habit, "comment", "id", "UID");
        event.setUrl_img("Url_img");
        assertEquals("Url_img", event.getUrl_img());
    }*/

    public void testLocation(){
        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
        Event event = new Event(habit, "comment", "id", "UID");
        event.setLocation("location");
        assertEquals("location", event.getLocation());
    }
}