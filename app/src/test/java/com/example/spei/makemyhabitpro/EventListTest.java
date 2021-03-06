/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by spei on 11/18/17.
 */

public class EventListTest {
    @Test
    public void add() throws Exception {


        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
        Event event1 = new Event(habit, "comment", "id", "UID");

        EventList event2= new EventList();

        event2.add(event1);
        assertTrue(event2.contains(event1));

    }

    @Test
    public void del() throws Exception {


        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
        Event event1 = new Event(habit, "comment", "id", "UID");
        EventList event2= new EventList();
        event2.add(event1);
        assertTrue(event2.contains(event1));
        event2.del(event1);
        assertFalse(event2.contains(event1));
    }

    @Test
    public void empty() throws Exception {

        EventList event = new EventList();
        assertTrue(event.empty());

    }

    @Test
    public void getById() throws Exception {


        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
        Event event1 = new Event(habit, "comment", "id", "UID");
        EventList event2= new EventList();
        event2.add(event1);
        assertEquals(event1,event2.getById("DJB"));
    }

}
