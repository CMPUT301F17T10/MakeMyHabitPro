//package com.example.spei.makemyhabitpro;
//
//import org.junit.Test;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Created by spei on 11/18/17.
// */
//
//public class HabitTest {
//    @Test
//    public void addEvent() throws Exception {
//        String name="wow";
//        ArrayList doDate=new ArrayList();
//        doDate.add(4);
//        doDate.add(5);
//        int htype=0;
//        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
//        Event event = new Event(habit, "comment", "id", "UID");
//        int owner=1;
//
//        habit.addEvent(event);
//    }
//
//    @Test
//    public void getDoDate() throws Exception {
//        String name="wow";
//        ArrayList doDate=new ArrayList();
//        doDate.add(4);
//        doDate.add(5);
//        int htype=0;
//        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
//        Event event = new Event(habit, "comment", "id", "UID");
//        int owner=1;
//
//        assertEquals(habit.getDoDate(),doDate);
//    }
//
//    @Test
//    public void getLastActive() throws Exception {
//        String name="wow";
//        ArrayList doDate=new ArrayList();
//        doDate.add(4);
//        doDate.add(5);
//        int htype=0;
//        Date test=new Date();
//        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
//        Event event = new Event(habit, "comment", "id", "UID");
//        int owner=1;
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//
//
//        habit.addEvent(event);
//        assertEquals(dateFormat.format(habit.getLastActive_Date()),dateFormat.format(test));
//    }
//
//    @Test
//    public void compareTo() throws Exception {
//        String name="wow";
//        ArrayList doDate=new ArrayList();
//        doDate.add(4);
//        doDate.add(5);
//        int htype=0;
//        Habit habit = new Habit("userId", "title", "reason", "detail", "type","startDate");
//        Event event = new Event(habit, "comment", "id", "UID");
//        int owner=1;
//
//        habit.addEvent(event);
//        Thread.sleep(2000);
//        Event event1 = new Event("location",name,"Ulr_img","farm MTOS","1");
//        Habit habit2 = new Habit(owner,name,htype,doDate);
//        habit2.addEvent(event1);
//
//        assertEquals(1,habit2.compareTo(habit));
//    }
//
//}
