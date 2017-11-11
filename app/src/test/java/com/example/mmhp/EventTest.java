package com.example.mmhp;

/**
 * Created by spei on 10/21/17.
 */

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;

public class EventTest extends ActivityInstrumentationTestCase2{

    public EventTest() {
        super(com.example.mmhp.Event.class);
    }

    private Habit habit = new Habit();
    private Location location;

    public void testGetId(){
        Event event = new Event("location",habit,"comment","1","Ulr_img");
        assertNotNull(event.getId());
    }

    public void testGetOwner_comment(){
        Event event = new Event("location",habit,"comment","1","Ulr_img");
        assertEquals("comment", event.getOwner_comment());
    }

    public void testGetHabit(){
        Event event = new Event("location",habit,"comment","1","Ulr_img");
        assertEquals("habit", event.getHabit());
    }

    public void testGetHappened(){
        Event event = new Event("location",habit,"comment","1","Ulr_img");
        assertNotNull(event.getHappend());
    }

    public void testGetHabitDate(){
        Date date = new Date();
        Event event = new Event("location",habit,"comment","1","Ulr_img");
        assertEquals(date, event.getHabitDate());
    }

    public void testGetPub_comment (){
        Event event = new Event("location",habit,"comment","1","Ulr_img");
        event.pub_comment("pub");
        ArrayList<String> pub = event.getPub_comment();
        assertEquals("pub", pub.get(0));
    }

    public void testGetUrl_img (){
        Event event = new Event("location",habit,"comment","1","Ulr_img");
        assertEquals("Url_img", event.getUrl_img());
    }

    public void testLocation(){
        Event event = new Event("location",habit,"comment","1","Ulr_img");
        assertEquals("location", event.getLocation());
    }
}


