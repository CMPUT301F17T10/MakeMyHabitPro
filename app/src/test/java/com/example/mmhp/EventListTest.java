package com.example.mmhp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jimmy on 2017-10-22.
 */
public class EventListTest {
    @Test
    public void add() throws Exception {

        String id ="DJB";
        String location1 = "Calgary";
        String habit = "habit 1";
        String url = "url_image";
        String comment = "Comment";

        Event event1 = new Event(location1, habit, url, comment, id);

        EventList event2= new EventList();

        event2.add(event1);
        assertTrue(event2.contains(event1));

    }

    @Test
    public void del() throws Exception {

        String id = "DJB";
        String location1 = "Edmonton";
        String habit = "habit 1";
        String url = "url_image";
        String comment = "Comment";

        Event event1 = new Event(location1, habit, url, comment, id);
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

        String id = "DJB";
        String location1 = "Edmonton";
        String habit = "habit 1";
        String url = "url_image";
        String comment = "Comment";

        Event event1 = new Event(location1, habit, url, comment, id);
        EventList event2= new EventList();
        event2.add(event1);
        assertEquals(event1,event2.getById("DJB"));
    }

}