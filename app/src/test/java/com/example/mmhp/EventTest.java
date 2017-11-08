package com.example.mmhp;

/**
 * Created by spei on 10/21/17.
 */

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;

public class EventTest extends ActivityInstrumentationTestCase2{

    public EventTest() {
        super(com.example.mmhp.Event.class);
    }


    public void testGetId(){
        Event event = new Event("location","habit","Ulr_img","comment","1");
        assertNotNull(event.getId());
    }

    public void testGetOwner_comment(){
        Event event = new Event("location","habit","Ulr_img","comment","1");
        assertEquals("comment", event.getOwner_comment());
    }

    public void testGetHabit(){
        Event event = new Event("location","habit","Ulr_img","comment","1");
        assertEquals("habit", event.getHabit());
    }

    public void testGetHappened(){
        Event event = new Event("location","habit","Ulr_img","comment","1");
        assertNotNull(event.getHappend());
    }

    public void testGetHabitDate(){
        Date date = new Date();
        Event event = new Event("location","habit","Ulr_img","comment","1");
        assertEquals(date, event.getHabitDate());
    }

    public void testGetPub_comment (){
        Event event = new Event("location","habit","Ulr_img","comment","1");
        event.pub_comment("pub");
        ArrayList<String> pub = event.getPub_comment();
        assertEquals("pub", pub.get(0));
    }

    public void testGetUrl_img (){
        Event event = new Event("location","habit","Ulr_img","comment","1");
        assertEquals("Url_img", event.getUrl_img());
    }

    public void testLocation(){
        Event event = new Event("location","habit","Ulr_img","comment","1");
        assertEquals("location", event.getLocation());
    }
}


