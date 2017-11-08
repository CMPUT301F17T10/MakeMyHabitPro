package com.example.mmhp;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by yidingfan on 2017-10-23.
 */
public class HabitTest {
    @Test
    public void addEvent() throws Exception {
        String name="wow";
        ArrayList doDate=new ArrayList();
        doDate.add(4);
        doDate.add(5);
        int htype=0;
        Event event = new Event("location",name,"Ulr_img","farm HTOC","1");
        int owner=1;
        Habit habit = new Habit(owner,name,htype,doDate);
        habit.addEvent(event);
    }

    @Test
    public void getDoDate() throws Exception {
        String name="wow";
        ArrayList doDate=new ArrayList();
        doDate.add(4);
        doDate.add(5);
        int htype=0;
        Event event = new Event("location",name,"Ulr_img","farm HTOC","1");
        int owner=1;
        Habit habit = new Habit(owner,name,htype,doDate);
        assertEquals(habit.getDoDate(),doDate);
    }

    @Test
    public void getLastActive() throws Exception {
        String name="wow";
        ArrayList doDate=new ArrayList();
        doDate.add(4);
        doDate.add(5);
        int htype=0;
        Date test=new Date();
        Event event = new Event("location",name,"Ulr_img","farm HTOC","1");
        int owner=1;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Habit habit = new Habit(owner,name,htype,doDate);
        habit.addEvent(event);
        assertEquals(dateFormat.format(habit.getLastActive_Date()),dateFormat.format(test));
    }

    @Test
    public void compareTo() throws Exception {
        String name="wow";
        ArrayList doDate=new ArrayList();
        doDate.add(4);
        doDate.add(5);
        int htype=0;
        Event event = new Event("location",name,"Ulr_img","farm HTOS","1");
        int owner=1;

        Habit habit = new Habit(owner,name,htype,doDate);
        habit.addEvent(event);
        Thread.sleep(2000);
        Event event1 = new Event("location",name,"Ulr_img","farm MTOS","1");
        Habit habit2 = new Habit(owner,name,htype,doDate);
        habit2.addEvent(event1);

        assertEquals(1,habit2.compareTo(habit));
    }

}